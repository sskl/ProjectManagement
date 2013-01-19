package tr.edu.ankara.blm489.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tr.edu.ankara.blm489.controls.MainControl;
import tr.edu.ankara.blm489.models.User;

/**
 * Servlet Filter implementation class MainFilter
 */
@WebFilter("*.xhtml")
public class MainFilter implements Filter {

    /**
     * Default constructor. 
     */
    public MainFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        HttpServletResponse res = (HttpServletResponse) response;

        User user = (User) session.getAttribute("sessUser");
        
        String uri = req.getRequestURI();
        
        if (uri.contains("project"))
        	MainControl.setActivePage(0);
        if (uri.contains("task"))
        	MainControl.setActivePage(1);
        if (uri.contains("team"))
        	MainControl.setActivePage(2);
        if (uri.contains("admin"))
        	MainControl.setActivePage(3);
        
        if (user != null && uri.endsWith("login.xhtml")) {
        	res.sendRedirect(req.getContextPath() +  "/project/index.xhtml");
        } else {
        	chain.doFilter(request, response);
        }
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
