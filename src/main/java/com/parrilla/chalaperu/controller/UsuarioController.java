package com.parrilla.chalaperu.controller;

import com.parrilla.chalaperu.model.Usuario;
import com.parrilla.chalaperu.service.RolService;
import com.parrilla.chalaperu.service.UsuarioService;
import com.parrilla.chalaperu.util.ExcelExporter;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet(name = "UsuarioController", urlPatterns = {"/usuario"})
public class UsuarioController extends HttpServlet {

    private final String pagListar = "/views/pagGestUsuario.jsp";
    private final String pagNuevo = "/views/pagNuevoUsuario.jsp";
    private RolService rolService;
    private UsuarioService usuarioService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        rolService = new RolService();
        usuarioService = new UsuarioService();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");

        switch (accion) {
            case "listar":
                listar(request, response);
                break;
            case "nuevo":
                nuevo(request, response);
                break;
            case "guardar":
                guardar(request, response);
                break;
            case "editar":
                editar(request, response);
                break;
            case "eliminar":
                eliminar(request, response);
                break;
            case "exportar_excel":
                exportarUsuariosExcel(request, response);
                break;
            default:
                throw new AssertionError();
        }
    }

    protected void exportarUsuariosExcel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Usuario> lista = usuarioService.ListarTodos();
            ExcelExporter exporter = new ExcelExporter();
            exporter.exportarUsuarios(lista, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));

            boolean result = usuarioService.Eliminar(id);

            if (result) {
                request.getSession().setAttribute("success", "Usuario con id " + id + " eliminado!");
            } else {
                request.getSession().setAttribute("error", "No se pudo eliminar usuario");
            }
        } catch (Exception ex) {
            request.getSession().setAttribute("error", ex.getMessage());
        }

        response.sendRedirect("usuario?accion=listar");
    }

    private void editar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        Usuario obj = usuarioService.BuscarPorId(id);

        if (obj != null) {
            request.setAttribute("usuario", obj);
            request.setAttribute("roles", rolService.ListarTodos());
            request.getRequestDispatcher(pagNuevo).forward(request, response);
        } else {
            request.getSession().setAttribute("error", "No se encontro usuario con ID " + id);
            response.sendRedirect("usuario?accion=listar");
        }
    }

    private void guardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Usuario obj = new Usuario();
        try {
            obj.setIdUsuario(Integer.parseInt(request.getParameter("id")));
            obj.setNombres(request.getParameter("nombres"));
            obj.setApellidos(request.getParameter("apellidos"));
            obj.setUsername(request.getParameter("username"));
            obj.setPassword(request.getParameter("password"));
            obj.setTelefono(request.getParameter("telefono"));
            obj.setEstado(Integer.parseInt(request.getParameter("estado")));
            obj.setIdRol(Integer.parseInt(request.getParameter("rol")));
            boolean result;

            if (obj.getIdUsuario() == 0) {
                result = usuarioService.Guardar(obj);
            } else {
                result = usuarioService.Actualizar(obj);
            }

            if (result) {
                request.getSession().setAttribute("success", obj.getIdUsuario() == 0 ? "Usuario registrado" : "Usuario actualizado");
                response.sendRedirect("usuario?accion=listar");
                return;
            }
            request.getSession().setAttribute("error", "No se pudo guardar datos.");

        } catch (Exception ex) {
            request.getSession().setAttribute("error", ex.getMessage());
        }

        request.setAttribute("usuario", obj);
        request.setAttribute("roles", rolService.ListarTodos());
        request.getRequestDispatcher(pagNuevo).forward(request, response);
    }

    private void nuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("usuario", new Usuario());
        request.setAttribute("roles", rolService.ListarTodos());
        request.getRequestDispatcher(pagNuevo).forward(request, response);
    }

    protected void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("usuarios", usuarioService.ListarTodos());
        request.getRequestDispatcher(pagListar).forward(request, response);
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
