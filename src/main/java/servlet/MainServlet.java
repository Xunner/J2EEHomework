package servlet;

import com.alibaba.fastjson.JSON;
import enums.Result;
import factory.ServiceFactory;
import po.CommodityPO;
import service.CommodityService;
import service.OrderService;
import service.UserService;
import util.WebResourceLoader;
import vo.HomeVO;
import vo.PayVO;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主Servlet
 * <br>
 * created on 2018/12/12
 *
 * @author 巽
 **/
@WebServlet(urlPatterns = {"/home", "/login", "/logout", "/add-to-cart", "/pay", "*.ajax"})
public class MainServlet extends HttpServlet {
	public final static double DISCOUNT_THRESHOLD = 200.0; // 折扣门槛
	public final static double DISCOUNT = 0.7; // 折扣
	public final static String USER_ID_COOKIE = "userId";   // cookie名字，下同
	private final static String CART_COOKIE = "cart";
	private final static String HOME_URI = "/home";         // 与注解中的URL pattern一致，下同
	private final static String HOME_AJAX = "/home.ajax";
	private final static String LOGIN_URI = "/login";
	private final static String LOGOUT_URI = "/logout";
	private final static String ADD_TO_CART_URI = "/add-to-cart";
	private final static String PAY_URI = "/pay";
	private UserService userService = ServiceFactory.getUserService();
	private OrderService orderService = ServiceFactory.getOrderService();
	private CommodityService commodityService = ServiceFactory.getCommodityService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		HttpSession session = req.getSession(true);
		PrintWriter out = resp.getWriter();
		if (req.getRequestURI().equals(LOGIN_URI)) { // 请求访问登录页面
			System.out.println("get request: " + req.getRequestURI());
			out.println(this.toLogIn(req));
		} else if (session.getAttribute(USER_ID_COOKIE) == null) {  // 首次打开网站的用户：跳转至登录页面
//			this.getServletContext().getRequestDispatcher(LOGIN_URI).forward(req, resp);
			resp.sendRedirect(LOGIN_URI);
		} else {    // 已登录用户
			System.out.println("get request: " + req.getRequestURI());
			switch (req.getRequestURI()) {
				case LOGOUT_URI: // 注销
					req.getSession().removeAttribute(USER_ID_COOKIE);
//					this.getServletContext().getRequestDispatcher(LOGIN_URI).forward(req, resp);
					resp.sendRedirect(LOGIN_URI);
					break;
				case HOME_URI:   // 已登录用户请求访问主页
					out.println(WebResourceLoader.loadHtml(this.getServletContext().getRealPath("index.html")));
					break;
				case HOME_AJAX: // 异步请求主页动态数据
					ServletContext sc = this.getServletContext();
					Map<String, Object> ret = new HashMap<>();
					ret.put("userId", session.getAttribute(USER_ID_COOKIE));
					ret.put("totalNumber", sc.getAttribute("totalNumber").toString());
					ret.put("loggedInNumber", sc.getAttribute("loggedInNumber").toString());
					ret.put("visitorNumber", sc.getAttribute("visitorNumber").toString());
					// 获取购物车商品信息
					Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute(CART_COOKIE);
					List<CommodityPO> commoditiesInCart = commodityService.getByIds(cart.keySet());
					List<HomeVO> retCart = new ArrayList<>();
					for (CommodityPO commodity : commoditiesInCart) {
						retCart.add(new HomeVO(commodity.getComId(), commodity.getName(), commodity.getPrice(), cart.get(commodity.getComId())));
					}
					ret.put("cart", retCart);
					// 获取商品列表信息
					List<CommodityPO> commodities = commodityService.getAll();
					ret.put("commodities", commodities);

					out.write(JSON.toJSONString(ret));
					break;
				default:
					System.out.println("未处理请求：" + req.getRequestURI());
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		System.out.println("post request: " + req.getRequestURI());
		HttpSession session;
		switch (req.getRequestURI()) {
			case LOGIN_URI:  // 登录
				String userId = req.getParameter("userId");
				String password = req.getParameter("password");
				Result result = userService.logIn(userId, password);
				System.out.println("Servlet: result=" + result);
				if (result == Result.SUCCESS) {  // 登录成功
					System.out.println("\tuser succeed in log-in: " + userId);
					session = req.getSession(true);
					Map<Integer, Integer> cart = new HashMap<>();
					session.setAttribute(CART_COOKIE, cart); // 新建购物车
					session.setAttribute(USER_ID_COOKIE, userId);
					Cookie cookie = new Cookie(USER_ID_COOKIE, userId);
					resp.addCookie(cookie);
					resp.sendRedirect(HOME_URI);
				} else {    // 登录失败
					System.out.println("\tuser failed in log-in: " + userId);
					PrintWriter out = resp.getWriter();
					out.println("<h1>Log in failed.</h1>");
					out.println("<h3>user id dose not exist, or password is wrong.</h2>");
					out.println("<h3>Input user id: " + userId + "</h2>");
					out.println("<h3>Click <a href=" + resp.encodeURL(LOGIN_URI) + ">here</a> to return.</h2>");
				}
				break;
			case ADD_TO_CART_URI:    // 加入购物车
				session = req.getSession(true);
				if (session.getAttribute(USER_ID_COOKIE) == null) {  // 首次打开网站的用户：跳转至登录页面
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
				session = req.getSession(true);
				if (session.getAttribute(USER_ID_COOKIE) == null) {  // 首次打开网站的用户：跳转至登录页面
					resp.sendRedirect(LOGIN_URI);
				} else {
					Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute(CART_COOKIE);
					PayVO payVO = orderService.generateOrder((String) session.getAttribute(USER_ID_COOKIE), cart);
					// 返回信息
					PrintWriter out = resp.getWriter();
					if (payVO.result == Result.SUCCESS) {  // 下订单成功
						out.println("<h2>Your order has been successfully generated.</h2>");
						out.println("<h3>The total price is ￥" + payVO.price + ".</h3>");
						if (payVO.actualPayment < payVO.price) {
							out.println("<h3>As the amount of consumption exceeds ￥" + DISCOUNT_THRESHOLD
									+ ", you can enjoy the " + DISCOUNT * 100 + "% discount.</h3>");
							out.println("<h3>Your actual payment is ￥" + payVO.actualPayment + ".</h3>");
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
				System.out.println("\t未处理请求：" + req.getRequestURI());
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
		ServletContext sc = this.getServletContext();
		return WebResourceLoader.loadHtml(this.getServletContext().getRealPath("login.html"))
				.replace("$userId", userId)
				.replace("$totalNumber", sc.getAttribute("totalNumber").toString())
				.replace("$loggedInNumber", sc.getAttribute("loggedInNumber").toString())
				.replace("$visitorNumber", sc.getAttribute("visitorNumber").toString());
	}
}
