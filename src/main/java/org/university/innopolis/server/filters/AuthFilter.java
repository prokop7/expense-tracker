package org.university.innopolis.server.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.university.innopolis.server.services.AuthenticationService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFilter extends GenericFilterBean {
    private static final String TOKEN_HEADER = "Authorization";

    private final AuthenticationService authService;

    @Autowired
    public AuthFilter(AuthenticationService authService) {
        this.authService = authService;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws
            ServletException,
            IOException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        String authHeader = request.getHeader(TOKEN_HEADER);

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else if (authHeader == null) {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.sendError(401, "Missing Authorization header");
            return;
        } else {
            authHeader = authHeader.substring(7);
            if (!authService.isAuthorized(authHeader)) {
                response.setHeader("Access-Control-Allow-Origin", "*");
                response.sendError(401, "Invalid token");
                return;
            }
        }

        chain.doFilter(req, res);
    }
}