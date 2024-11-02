package com.parrilla.chalaperu.controller;

import com.parrilla.chalaperu.model.Categoria;
import com.parrilla.chalaperu.service.CategoriaService;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "CategoriaController", urlPatterns = {"/categoria"})
public class CategoriaController extends HttpServlet {

    private final String pagListar = "/views/pagGestCategoria.jsp";
    private final String pagNuevo = "/views/pagNuevaCategoria.jsp";
    private CategoriaService categoriaService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        categoriaService = new CategoriaService();
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
            default:
                throw new AssertionError();
        }
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean result = categoriaService.Eliminar(id);

            if (result) {
                request.getSession().setAttribute("success", "Categoría con id " + id + " eliminada!");
            } else {
                request.getSession().setAttribute("error", "No se pudo eliminar la categoría");
            }
        } catch (Exception ex) {
            request.getSession().setAttribute("error", ex.getMessage());
        }

        response.sendRedirect("categoria?accion=listar");
    }

    private void editar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Categoria categoria = categoriaService.BuscarPorId(id);

        if (categoria != null) {
            request.setAttribute("categoria", categoria);
            request.getRequestDispatcher(pagNuevo).forward(request, response);
        } else {
            request.getSession().setAttribute("error", "No se encontró la categoría con ID " + id);
            response.sendRedirect("categoria?accion=listar");
        }
    }

    private void guardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Categoria obj = new Categoria();
        try {
            obj.setIdCateg(Integer.parseInt(request.getParameter("id")));
            obj.setNombreCateg(request.getParameter("nombre"));
            obj.setEstado(Integer.parseInt(request.getParameter("estado")));

            boolean result;
            if (obj.getIdCateg() == 0) {
                result = categoriaService.Guardar(obj);
            } else {
                result = categoriaService.Actualizar(obj);
            }

            if (result) {
                request.getSession().setAttribute("success", obj.getIdCateg() == 0 ? "Categoría registrada" : "Categoría actualizada");
                response.sendRedirect("categoria?accion=listar");
                return;
            }
            request.getSession().setAttribute("error", "No se pudo guardar los datos.");
        } catch (Exception ex) {
            request.getSession().setAttribute("error", ex.getMessage());
        }

        request.setAttribute("categoria", obj);
        request.getRequestDispatcher(pagNuevo).forward(request, response);
    }

    private void nuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("categoria", new Categoria());
        request.getRequestDispatcher(pagNuevo).forward(request, response);
    }

    protected void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("categorias", categoriaService.ListarTodos());
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
