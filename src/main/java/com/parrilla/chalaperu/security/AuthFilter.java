package com.parrilla.chalaperu.security;

import com.parrilla.chalaperu.model.Usuario;
import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        Usuario usuarioLogeado = (Usuario) httpRequest.getSession().getAttribute("usuario");

        if (usuarioLogeado != null) {
            chain.doFilter(request, response);
        } else {
            httpRequest.getSession().setAttribute("error", "Acceso denegado: Inicie sesión.");
            httpResponse.sendRedirect("index.jsp");
        }
    }

    @Override
    public void destroy() {
    }
}
