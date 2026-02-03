package hibernate.filter;

import hibernate.util.UrlPath;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

import static hibernate.util.UrlPath.LOGIN;
import static hibernate.util.UrlPath.REGISTRATION;


@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    private static final Set<String> PUBLIC_PATH = Set.of(LOGIN, REGISTRATION);


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var uri = ((HttpServletRequest) servletRequest).getRequestURI();
        if (isPublicPath(uri) || isUserLoggedIn(servletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendRedirect(LOGIN);
        }
    }

    private boolean isPublicPath(String uri) {
        return PUBLIC_PATH.stream().anyMatch(e -> uri.startsWith(e));
    }

    private boolean isUserLoggedIn(ServletRequest servletRequest) {
        var user = ((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        return user != null;
    }


}
