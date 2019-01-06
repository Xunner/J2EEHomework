package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 编码过滤器
 * <br>
 * created on 2018/12/23
 *
 * @author 巽
 **/
@WebFilter(filterName = "EncodeFilter", urlPatterns = {"/*"})
public class EncodeFilter implements Filter {
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//		System.out.println("Filter works");
		if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
			HttpServletRequest request = (HttpServletRequest) servletRequest;
			HttpServletResponse response = (HttpServletResponse) servletResponse;
//			System.out.println("    intercept: " + request.getRequestURI());
			if (request.getRequestURI().contains(".ajax")) {
				request.setCharacterEncoding("utf-8");
				response.setCharacterEncoding("utf-8");
				response.setContentType("application/json;charset=utf-8");
			} else if (!request.getRequestURI().contains("static")) {
//				System.out.println("    modify encode to utf-8");
				request.setCharacterEncoding("utf-8");
				response.setContentType("text/html;charset=utf-8");
			}
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}
}
