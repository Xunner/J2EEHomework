import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 主Servlet，测试连通性，Hello world性质
 * <br>
 * created on 2018/12/12
 *
 * @author 巽
 **/
@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	private String message;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<h1>" + message + "</h1>");
	}

	@Override
	public void init() throws ServletException {
		super.init();
		message = "Hello, world. This message is from MainServlet.";
	}
}
