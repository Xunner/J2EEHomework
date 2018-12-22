import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录Servlet
 * <br>
 * created on 2018/12/12
 *
 * @author 巽
 **/
@WebServlet(urlPatterns = "/")
public class MainServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		System.out.println("get request: " + req.getRequestURI());
		HttpSession session = req.getSession(false);
		if (session == null) {
			resp.setContentType("text/html");
			resp.getWriter().println(WebResourceLoader.loadHtml(this.getServletContext().getRealPath("login.html")));
		} else {
			switch (req.getRequestURI()) {
				case "/logout":
					req.getSession().invalidate();
					resp.getWriter().println(WebResourceLoader.loadHtml(this.getServletContext().getRealPath("login.html")));
					break;
				case "/":   // 已登录用户请求主页
					resp.getWriter().println(WebResourceLoader.loadHtml(this.getServletContext().getRealPath("index.html")).replace("$userId", (String) session.getAttribute("userId")));
					break;
				default:
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
			case "/login":
				String userId = req.getParameter("userId");
				String password = req.getParameter("password");
				System.out.println("userId: " + userId + ", password: " + password);
				resp.setContentType("text/html");
				resp.getWriter().println(WebResourceLoader.loadHtml(this.getServletContext().getRealPath("index.html")).replace("$userId", userId));
				HttpSession session = req.getSession(true);
				session.setAttribute("userId", userId);
				break;
			case "/buy":
				break;
			default:
		}
	}
}
