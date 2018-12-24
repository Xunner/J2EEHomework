import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 主Servlet
 * <br>
 * created on 2018/12/12
 *
 * @author 巽
 **/
@WebServlet(urlPatterns = {"/home", "/login", "/logout", "/add-to-cart", "/pay"})
public class MainServlet extends HttpServlet {
	private final static String CART_COOKIE = "cart";       // cookie名字，下同
	private final static String USER_ID_COOKIE = "userId";
	private final static String HOME_URI = "/home";         // 与注解中的URL pattern一致，下同
	private final static String LOGIN_URI = "/login";
	private final static String LOGOUT_URI = "/logout";
	private final static String ADD_TO_CART_URI = "/add-to-cart";
	private final static String PAY_URI = "/pay";
	private final static double DISCOUNT_THRESHOLD = 200.0;  // 折扣门槛
	private final static double DISCOUNT = 0.7;  // 折扣
	private DataSource dataSource;

	@Override
	public void init() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/J2EEHomework");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		HttpSession session = req.getSession(false);
		PrintWriter out = resp.getWriter();
		if (req.getRequestURI().equals(LOGIN_URI)) { // 请求访问登录页面
			System.out.println("get request: " + req.getRequestURI());
			out.println(this.toLogIn(req));
		} else if (session == null) {  // 首次打开网站的用户：跳转至登录页面
			resp.sendRedirect(LOGIN_URI);
		} else {    // 已登录用户
			System.out.println("get request: " + req.getRequestURI());
			switch (req.getRequestURI()) {
				case LOGOUT_URI: // 注销
					req.getSession().invalidate();
					resp.sendRedirect(LOGIN_URI);
					break;
				case HOME_URI:   // 已登录用户请求访问主页
					out.println(this.toIndex((String) session.getAttribute(USER_ID_COOKIE), (Map<Integer, Integer>) session.getAttribute(CART_COOKIE)));
					break;
				default:
					System.out.println("未处理请求：" + req.getRequestURI());
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		System.out.println("post request: " + req.getRequestURI());
		HttpSession session;
		switch (req.getRequestURI()) {
			case LOGIN_URI:  // 登录
				// 访问数据库核对用户密码
				try (Connection connection = dataSource.getConnection();
				     PreparedStatement preparedStatement = connection.prepareStatement(
						     "SELECT user_id FROM `user` WHERE user_id = ? AND password = ?")) {
					String userId = req.getParameter("userId");
					String password = req.getParameter("password");
					preparedStatement.setString(1, userId);
					preparedStatement.setString(2, password);
					try (ResultSet resultSet = preparedStatement.executeQuery()) {
						if (resultSet.first()) {    // 登录成功
							System.out.println("user succeed in log-in: " + userId);
							session = req.getSession(true);
							Map<Integer, Integer> cart = new HashMap<>();
							session.setAttribute(CART_COOKIE, cart); // 新建购物车
							session.setAttribute(USER_ID_COOKIE, userId);
							Cookie cookie = new Cookie(USER_ID_COOKIE, userId);
							resp.addCookie(cookie);
							resp.sendRedirect(HOME_URI);
						} else {    // 登录失败
							System.out.println("user failed in log-in: " + userId);
							PrintWriter out = resp.getWriter();
							out.println("<h1>Log in failed.</h1>");
							out.println("<h3>User id dose not exist, or password is wrong.</h2>");
							out.println("<h3>Input user id: " + userId + "</h2>");
							out.println("<h3>Click <a href=" + resp.encodeURL(LOGIN_URI) + ">here</a> to return.</h2>");
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			case ADD_TO_CART_URI:    // 加入购物车
				session = req.getSession(false);
				if (session == null) {  // 首次打开网站的用户：跳转至登录页面
					resp.sendRedirect(LOGIN_URI);
				} else {
					Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute(CART_COOKIE);
					for (String com_id : req.getParameterMap().keySet()) {
						Integer number = Integer.valueOf(req.getParameter(com_id));
						Integer id = Integer.valueOf(com_id);
						number += cart.getOrDefault(id, 0); // 本次添加 + 购物车原有
						if (number <= 0) {
							cart.remove(id);
						} else {
							cart.put(id, number);
						}
//						System.out.println(com_id + ":" + req.getParameter(com_id));
					}
					session.setAttribute(CART_COOKIE, cart);
					resp.sendRedirect(HOME_URI);
				}
				break;
			case PAY_URI:    // 支付
				session = req.getSession(false);
				if (session == null) {  // 首次打开网站的用户：跳转至登录页面
					resp.sendRedirect(LOGIN_URI);
				} else {
					Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute(CART_COOKIE);
					double price = 0.0, actualPayment = 0.0;
					// 访问数据库
					boolean success = false;
					try (Connection connection = dataSource.getConnection()) {
						try (PreparedStatement getCommodityPS = connection.prepareStatement(
								"SELECT price FROM commodity WHERE com_id = ?");
						     PreparedStatement newOrderPS = connection.prepareStatement(
								     "INSERT INTO `order`(user_id, price, actual_payment, time, state) VALUES(?,?,?,?,?)",
								     PreparedStatement.RETURN_GENERATED_KEYS);
						     PreparedStatement orderMapPS = connection.prepareStatement(
								     "INSERT INTO order_com_map(order_id, com_id, number) VALUES(?,?,?)")) {
							// 查询商品单价，计算总价
							for (Integer com_id : cart.keySet()) {
								Integer number = cart.get(com_id);
								getCommodityPS.setInt(1, com_id);
								try (ResultSet resultSet = getCommodityPS.executeQuery()) {
									resultSet.first();
									price += resultSet.getDouble("price") * number;
								}
							}
							actualPayment = price;
							if (price >= DISCOUNT_THRESHOLD) {  // 消费金额高：打折
								actualPayment *= DISCOUNT;
							}
							// 添加订单
							String userId = (String) session.getAttribute(USER_ID_COOKIE);
							newOrderPS.setString(1, userId);
							newOrderPS.setDouble(2, price);
							newOrderPS.setDouble(3, actualPayment);
							newOrderPS.setObject(4, LocalDateTime.now());
							newOrderPS.setString(5, "unpaid");
							// 事务：添加订单及详情
							connection.setAutoCommit(false);
							newOrderPS.executeUpdate();
							int orderId;
							try (ResultSet resultSet = newOrderPS.getGeneratedKeys()) {
								resultSet.first();
								orderId = resultSet.getInt(1);
							}
							if (orderId > 0) {
								// 添加订单成功，依据orderId添加详情
								for (Integer com_id : cart.keySet()) {
									Integer number = cart.get(com_id);
									orderMapPS.setInt(1, orderId);
									orderMapPS.setInt(2, com_id);
									orderMapPS.setInt(3, number);
									orderMapPS.addBatch();      // 批量提交
								}
								orderMapPS.executeBatch();
								connection.commit();            // 提交事务
								connection.setAutoCommit(true); // 恢复自动commit
								success = true;
							}
						} catch (SQLException e) {
							e.printStackTrace();
							if (!connection.getAutoCommit()) {
								connection.rollback();
							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					// 返回信息
					PrintWriter out = resp.getWriter();
					if (success) {  // 下订单成功
						out.println("<h2>Your order has been successfully generated.</h2>");
						out.println("<h3>The total price is ￥" + price + ".</h3>");
						if (actualPayment < price) {
							out.println("<h3>As the amount of consumption exceeds ￥" + DISCOUNT_THRESHOLD
									+ ", you can enjoy the " + DISCOUNT * 100 + "% discount.</h3>");
							out.println("<h3>Your actual payment is ￥" + actualPayment + ".</h3>");
						}
						out.println("<h3>Click <a href=" + resp.encodeURL(HOME_URI) + ">here</a> to return.</h3>");
						// 清空购物车
						cart.clear();
						session.setAttribute(CART_COOKIE, cart);
					} else {    // 下订单失败
						out.println("<h2>There are something wrong...</h2>");
						out.println("<h3>Your order was not successfully generated.</h3>");
						out.println("<h3>Click <a href=" + resp.encodeURL(HOME_URI) + ">here</a> to return.</h3>");
					}
				}
				break;
			default:
				System.out.println("未处理请求：" + req.getRequestURI());
		}
	}

	/**
	 * 跳转至登录页面
	 *
	 * @param req 请求
	 * @return 登录页面的HTML
	 */
	private String toLogIn(HttpServletRequest req) {
		String userId = "";
		for (Cookie cookie : req.getCookies()) {
			if (cookie.getName().equals(USER_ID_COOKIE)) {
				userId = cookie.getValue();
				break;
			}
		}
		return WebResourceLoader.loadHtml(this.getServletContext().getRealPath("login.html")).replace("$userId", userId);
	}

	/**
	 * 跳转至主页面
	 *
	 * @param userId 用户id
	 * @return 主页面的HTML
	 */
	private String toIndex(String userId, Map<Integer, Integer> cart) {
		String html = WebResourceLoader.loadHtml(this.getServletContext().getRealPath("index.html")).replace("$userId", userId);
		StringBuilder commoditySB = new StringBuilder();
		StringBuilder cartSB = new StringBuilder();
		double totalPrice = 0.0;
		// 访问数据库
		try (Connection connection = dataSource.getConnection();
		     PreparedStatement commodityStatement = connection.prepareStatement("SELECT * FROM `commodity` LIMIT ?");
		     PreparedStatement cartStatement = connection.prepareStatement("SELECT * FROM commodity WHERE com_id = ?")) {
			// 获取商品列表信息
			String commodityTemplate = "<tr><td>$name</td><td align=\"right\">$price</td><td>$comment</td><td><label><input type=\"number\" name=\"$com_id\" value=0></label></td></tr>";
			commodityStatement.setInt(1, 10);
			try (ResultSet resultSet = commodityStatement.executeQuery()) {
				while (resultSet.next()) {
					commoditySB.append(commodityTemplate
							.replace("$com_id", resultSet.getString("com_id"))
							.replace("$name", resultSet.getString("name"))
							.replace("$price", resultSet.getString("price"))
							.replace("$comment", resultSet.getString("comment")));
				}
			}
			// 获取购物车商品信息
			String cartTemplate = "<tr><td>$name</td><td align=\"right\">$unitPrice</td><td align=\"right\">$number</td><td align=\"right\">$total</td></tr>";
			for (Integer com_id : cart.keySet()) {
				cartStatement.setInt(1, com_id);
				try (ResultSet resultSet = cartStatement.executeQuery()) {
					if (resultSet.next()) {
						Integer number = cart.get(com_id);
						double unitPrice = resultSet.getDouble("price");
						double total = unitPrice * number;
						totalPrice += total;
						cartSB.append(cartTemplate
								.replace("$name", resultSet.getString("name"))
								.replace("$unitPrice", Double.toString(unitPrice))
								.replace("$number", number.toString())
								.replace("$total", Double.toString(total)));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 替换模板内容
		html = html.replace("$commodities", commoditySB.toString());
		html = html.replace("$cart", cartSB.toString());
		html = html.replace("$totalPrice", Double.toString(totalPrice));
		return html;
	}
}
