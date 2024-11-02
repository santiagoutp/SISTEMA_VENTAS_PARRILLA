package com.parrilla.chalaperu.controller;

import com.parrilla.chalaperu.model.Usuario;
import com.parrilla.chalaperu.service.UsuarioService;
import com.parrilla.chalaperu.util.ConstantesApp;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "AuthController", urlPatterns = {"/auth"})
public class AuthController extends HttpServlet {

    private UsuarioService usuarioService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        usuarioService = new UsuarioService();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");

        switch (accion) {
            case "autentificarse":
                Autentificarse(request, response);
                break;
            case "logout":
                Logout(request, response);
                break;
            case "main":
                Main(request, response);
                break;
        }
    }

    protected void Main(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Usuario obj = (Usuario) request.getSession().getAttribute("usuario");

        if (obj == null) {
            request.getSession().setAttribute("error", "Debe iniciar sesión primero");
            response.sendRedirect("index.jsp");
            return;
        }

        request.setAttribute("usuario", obj);
        request.getRequestDispatcher("views/pagMain.jsp").forward(request, response);
    }

    protected void Autentificarse(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Usuario obj = usuarioService.Login(username, password);

        if (obj != null) {
            boolean esAdmin = obj.getIdRol() == ConstantesApp.ROL_ADMIN;
            if (obj.getEstado() == 1) {
                request.getSession().setAttribute("usuario", obj);
                request.getSession().setAttribute("esAdmin", esAdmin);
                response.sendRedirect("auth?accion=main");
                return;
            } else {
                request.getSession().setAttribute("error", "Lo sentimos! Su cuenta se encuentra inactiva. Por favor comuniquese con el administrador.");
            }
        } else {
            request.getSession().setAttribute("error", "Usuario y/o contraseña incorrecto");
        }

        request.setAttribute("username", username);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    protected void Logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getSession().removeAttribute("usuario");
         request.getSession().removeAttribute("esAdmin");
        response.sendRedirect("index.jsp");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
