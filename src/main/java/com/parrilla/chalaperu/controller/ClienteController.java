package com.parrilla.chalaperu.controller;

import com.parrilla.chalaperu.model.Cliente;
import com.parrilla.chalaperu.service.ClienteService;
import com.parrilla.chalaperu.util.ExcelExporter;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet(name = "ClienteController", urlPatterns = {"/cliente"})
public class ClienteController extends HttpServlet {

    private final String pagListar = "/views/pagGestCliente.jsp";
    private final String pagNuevo = "/views/pagNuevoCliente.jsp";
    private ClienteService clienteService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        clienteService = new ClienteService();
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
                exportarClientesExcel(request, response);
                break;
            default:
                throw new AssertionError();
        }
    }

    protected void exportarClientesExcel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Cliente> lista = clienteService.ListarTodos();
            ExcelExporter exporter = new ExcelExporter();
            exporter.exportarClientes(lista, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean result = clienteService.Eliminar(id);

            if (result) {
                request.getSession().setAttribute("success", "Cliente con ID " + id + " eliminado!");
            } else {
                request.getSession().setAttribute("error", "No se pudo eliminar el cliente");
            }
        } catch (Exception ex) {
            request.getSession().setAttribute("error", ex.getMessage());
        }

        response.sendRedirect("cliente?accion=listar");
    }

    private void editar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Cliente cliente = clienteService.BuscarPorId(id);

        if (cliente != null) {
            request.setAttribute("cliente", cliente);
            request.getRequestDispatcher(pagNuevo).forward(request, response);
        } else {
            request.getSession().setAttribute("error", "No se encontró el cliente con ID " + id);
            response.sendRedirect("cliente?accion=listar");
        }
    }

    private void guardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cliente obj = new Cliente();
        try {
            obj.setIdCliente(Integer.parseInt(request.getParameter("id")));
            obj.setNombre(request.getParameter("nombre"));
            obj.setApellidos(request.getParameter("apellidos"));
            obj.setDni(request.getParameter("dni"));
            obj.setTelefono(request.getParameter("telefono"));
            obj.setDireccion(request.getParameter("direccion"));
            obj.setEstado(Integer.parseInt(request.getParameter("estado")));

            boolean result;
            if (obj.getIdCliente() == 0) {
                result = clienteService.Guardar(obj);
            } else {
                result = clienteService.Actualizar(obj);
            }

            if (result) {
                request.getSession().setAttribute("success", obj.getIdCliente() == 0 ? "Cliente registrado" : "Cliente actualizado");
                response.sendRedirect("cliente?accion=listar");
                return;
            }
            request.getSession().setAttribute("error", "No se pudo guardar los datos.");
        } catch (Exception ex) {
            request.getSession().setAttribute("error", ex.getMessage());
        }

        request.setAttribute("cliente", obj);
        request.getRequestDispatcher(pagNuevo).forward(request, response);
    }

    private void nuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("cliente", new Cliente());
        request.getRequestDispatcher(pagNuevo).forward(request, response);
    }

    protected void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("clientes", clienteService.ListarTodos());
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
