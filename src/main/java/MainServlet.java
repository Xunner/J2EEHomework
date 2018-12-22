import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * 登录Servlet
 * <br>
 * created on 2018/12/12
 *
 * @author 巽
 **/
@WebServlet(urlPatterns = {"/home", "/login", "/logout", "/buy", "/"})
public class MainServlet extends HttpServlet {
	private DataSource dataSource;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		System.out.println("get request: " + req.getRequestURI());
		HttpSession session = req.getSession(false);
		PrintWriter out = resp.getWriter();
		if (session == null) {  // 首次打开网站的用户：跳转至登录页面
			resp.setContentType("text/html");
			out.println(this.toLogIn(req));
		} else {    // 已登录用户
			switch (req.getRequestURI()) {
				case "/logout": // 注销
					req.getSession().invalidate();
					out.println(this.toLogIn(req));
					break;
				case "/":   // 已登录用户请求访问主页
					out.println(this.toIndex((String) session.getAttribute("userId")));
					break;
				default:
					System.out.println("未处理请求：" + req.getRequestURI());
			}
		}
//		System.out.println(req.getContextPath());   //
//		System.out.println(req.getServletPath());   // /login
//		System.out.println(req.getPathInfo());      // null
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		System.out.println("post request: " + req.getRequestURI());
		switch (req.getRequestURI()) {
			case "/login":  // 登录
//				System.out.println("userId: " + userId + ", password: " + password);
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
							resp.setContentType("text/html");
							resp.getWriter().println(this.toIndex(userId));
							HttpSession session = req.getSession(true);
							session.setAttribute("userId", userId);
							Cookie cookie = new Cookie("userId", userId);
							resp.addCookie(cookie);
						} else {    // 登录失败
							System.out.println("user failed in log-in: " + userId);
							resp.setContentType("text/html");
							PrintWriter out = resp.getWriter();
							out.println("<h1>Log in failed.</h1>");
							out.println("<h3>User id dose not exist, or password is wrong.</h2>");
							out.println("<h3>Input user id: " + userId + "</h2>");
							out.println("<h3>Click <a href=" + resp.encodeURL("/login") + ">here</a> to return.</h2>");
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			case "/buy":    // 下订单
				break;
			default:
				System.out.println("未处理请求：" + req.getRequestURI());
		}
	}

	private String toLogIn(HttpServletRequest req) {
		String userId = "";
		for (Cookie cookie : req.getCookies()) {
			if (cookie.getName().equals("userId")) {
				userId = cookie.getValue();
				break;
			}
		}
		return WebResourceLoader.loadHtml(this.getServletContext().getRealPath("login.html")).replace("$userId", userId);
	}

	private String toIndex(String userId) {
		return WebResourceLoader.loadHtml(this.getServletContext().getRealPath("index.html")).replace("$userId", userId);
	}

	@Override
	public void init() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/J2EEHomework");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
}
