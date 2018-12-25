package listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;

import static servlet.MainServlet.USER_ID_COOKIE;

/**
 * 在线人数统计监听器
 * <br>
 * created on 2018/12/23
 *
 * @author 巽
 **/
@WebListener
public class OnlineNumberListener implements HttpSessionListener, HttpSessionAttributeListener, ServletContextListener {
	private Integer totalNumber = 0;
	private Integer loggedInNumber = 0;
	private Integer visitorNumber = 0;

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		System.out.println("Listen in the creation of session: " + se.getSession().getId());
		HttpSession session = se.getSession();
		ServletContext sc = session.getServletContext();
		sc.setAttribute("totalNumber", ++totalNumber);
		sc.setAttribute("visitorNumber", ++visitorNumber);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		System.out.println("Listen in the destroy of session: " + se.getSession().getId());
		HttpSession session = se.getSession();
		ServletContext sc = session.getServletContext();
		sc.setAttribute("totalNumber", --totalNumber);
		if (session.getAttribute(USER_ID_COOKIE) == null) {
			sc.setAttribute("visitorNumber", --visitorNumber);
		} else {
			sc.setAttribute("loggedInNumber", --loggedInNumber);
		}
	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent se) {
		if (se.getName().equals(USER_ID_COOKIE)) {
			System.out.println("Listen in the addition of session userId attribute: " + se.getSession().getId());
			HttpSession session = se.getSession();
			ServletContext sc = session.getServletContext();
			sc.setAttribute("loggedInNumber", ++loggedInNumber);
			sc.setAttribute("visitorNumber", --visitorNumber);
		}
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent se) {
		if (se.getName().equals(USER_ID_COOKIE)) {
			System.out.println("Listen in the remove of session userId attribute: " + se.getSession().getId());
			HttpSession session = se.getSession();
			ServletContext sc = session.getServletContext();
			sc.setAttribute("loggedInNumber", --loggedInNumber);
			sc.setAttribute("visitorNumber", ++visitorNumber);
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("Listen in the initial of ServletContext.");
		ServletContext sc = sce.getServletContext();
		sc.setAttribute("totalNumber", 0);
		sc.setAttribute("loggedInNumber", 0);
		sc.setAttribute("visitorNumber", 0);
	}
}
