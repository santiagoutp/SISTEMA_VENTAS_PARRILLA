package com.parrilla.chalaperu.controller;

import com.google.gson.Gson;
import com.parrilla.chalaperu.model.DetalleVenta;
import com.parrilla.chalaperu.model.Producto;
import com.parrilla.chalaperu.model.Usuario;
import com.parrilla.chalaperu.model.Venta;
import com.parrilla.chalaperu.service.ClienteService;
import com.parrilla.chalaperu.service.ProductoService;
import com.parrilla.chalaperu.service.UsuarioService;
import com.parrilla.chalaperu.service.VentaService;
import com.parrilla.chalaperu.util.CarritoUtil;
import com.parrilla.chalaperu.util.ConstantesApp;
import jakarta.servlet.ServletConfig;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "VentaController", urlPatterns = {"/venta"})
public class VentaController extends HttpServlet {

    private final String pagNuevo = "/views/pagNuevaVenta.jsp";
    private final String pagConsulta = "/views/pagConsultaVenta.jsp";
    private ClienteService clienteService;
    private UsuarioService usuarioService;
    private ProductoService productoService;
    private VentaService ventaService;
    private CarritoUtil carritoUtil;

    @Override
    public void init(ServletConfig config) throws ServletException {
        clienteService = new ClienteService();
        usuarioService = new UsuarioService();
        productoService = new ProductoService();
        ventaService = new VentaService();
        carritoUtil = new CarritoUtil();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");

        switch (accion) {
            case "nueva":
                NuevaVenta(request, response);
                break;
            case "listarDetalle":
                ListarDetalle(request, response);
                break;
            case "agregarItemDetalle":
                AgregarItemDetalle(request, response);
                break;
            case "eliminarItemDetalle":
                EliminarItemDetalle(request, response);
                break;
            case "eliminarDetalle":
                EliminarDetalle(request, response);
                break;
            case "procesar":
                ProcesarVenta(request, response);
                break;
            case "consulta":
                ConsultaVenta(request, response);
                break;
            case "consulta_comprobante":
                ConsultaComprobanteVenta(request, response);
                break;
            case "anular":
                AnularVenta(request, response);
                break;
            default:
                throw new AssertionError();
        }
    }

    protected void AnularVenta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String inicio = request.getParameter("inicio");
        String fin = request.getParameter("fin");
        try {

            int id = Integer.parseInt(request.getParameter("id"));

            boolean result = ventaService.AnularVenta(id);

            if (result) {
                request.getSession().setAttribute("success", "La venta con ID " + id + " se anuló de forma correcta.");
            } else {
                request.getSession().setAttribute("error", "No se pudo anular venta");
            }

        } catch (Exception ex) {
            request.getSession().setAttribute("error", ex.getMessage());
        }
        response.sendRedirect("venta?accion=consulta&inicio=" + inicio + "&fin=" + fin);
    }

    protected void ConsultaComprobanteVenta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        int id = Integer.parseInt(request.getParameter("id"));
        Venta obj = ventaService.BuscarPorId(id);

        PrintWriter out = response.getWriter();
        out.print(new Gson().toJson(obj));
        out.flush();
    }

    protected void ConsultaVenta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Venta> lista;
        String inicio = "", fin = "";

        if (request.getParameter("inicio") != null && request.getParameter("fin") != null) {
            inicio = request.getParameter("inicio");
            fin = request.getParameter("fin");
            lista = ventaService.ConsultarVentasPorFecha(inicio, fin);
        } else {
            lista = new ArrayList<>();
        }

        request.setAttribute("ventas", lista);
        request.setAttribute("inicio", inicio);
        request.setAttribute("fin", fin);
        request.getRequestDispatcher(pagConsulta).forward(request, response);
    }

    protected void ProcesarVenta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<DetalleVenta> lista = carritoUtil.ObtenerSesion(request);
            double subTotal, igv, total;

            subTotal = carritoUtil.SubTotal(lista);
            igv = carritoUtil.IGV(lista);
            total = carritoUtil.Total(subTotal, igv);

            Venta objVenta = new Venta();
            objVenta.setDetalles(lista);
            objVenta.setValorPagar(total);
            objVenta.setIdCliente(Integer.parseInt(request.getParameter("lbIdCliente")));
            objVenta.setIdVendedor(Integer.parseInt(request.getParameter("vendedor")));
            objVenta.setEstado(1);

            boolean result = ventaService.Guardar(objVenta);

            if (result) {
                request.getSession().setAttribute("success", "Venta registrada!");
                carritoUtil.RemoverCarrito(request);
            } else {
                request.getSession().setAttribute("error", "Lo sentimos! No se pudo registrar venta");
            }
        } catch (Exception ex) {
            request.getSession().setAttribute("error", ex.getMessage());
        }

        response.sendRedirect("venta?accion=nueva");
    }

    protected void NuevaVenta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Usuario objUsu = (Usuario) request.getSession().getAttribute("usuario");

        List<Usuario> listaVendedores;
        int idVendSelected = 0;
        if (objUsu.getIdRol() == ConstantesApp.ROL_ADMIN) {
            listaVendedores = usuarioService.listarVendedoresActivos();
        } else {
            idVendSelected = objUsu.getIdUsuario();
            listaVendedores = usuarioService.listarVendedoresActivosPorId(objUsu.getIdUsuario());
        }

        request.setAttribute("productos", productoService.ListarTodosActivosDisponibles());
        request.setAttribute("clientes", clienteService.ListarTodos());
        request.setAttribute("vendedores", listaVendedores);
        request.setAttribute("vendedorSelected", idVendSelected);
        request.getRequestDispatcher(pagNuevo).forward(request, response);
    }

    protected void ListarDetalle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        Map<String, Object> resultado = new HashMap<>();

        List<DetalleVenta> lista = carritoUtil.ObtenerSesion(request);
        double subTotal, igv, total;

        subTotal = carritoUtil.SubTotal(lista);
        igv = carritoUtil.IGV(lista);
        total = carritoUtil.Total(subTotal, igv);

        resultado.put("data", lista);
        resultado.put("subTotal", subTotal);
        resultado.put("igv", igv);
        resultado.put("total", total);
        PrintWriter out = response.getWriter();
        out.print(new Gson().toJson(resultado));
        out.flush();
    }

    protected void AgregarItemDetalle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        Map<String, Object> resultado = new HashMap<>();

        try {
            int idProd = Integer.parseInt(request.getParameter("id"));
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));

            Producto objProd = productoService.BuscarPorId(idProd);

            if (objProd != null) {
                DetalleVenta objDet = new DetalleVenta();
                objDet.setCantidad(cantidad);
                objDet.setProducto(objProd);

                carritoUtil.AgregarCarrito(request, objDet);
                resultado.put("msg", "OK");
            } else {
                resultado.put("msg", "No se encontro producto");
            }
        } catch (Exception ex) {
            resultado.put("msg", ex.getMessage());
        }

        PrintWriter out = response.getWriter();
        out.print(new Gson().toJson(resultado));
        out.flush();
    }

    protected void EliminarItemDetalle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        Map<String, Object> resultado = new HashMap<>();

        try {
            int indice = Integer.parseInt(request.getParameter("index"));

            carritoUtil.RemoverItemCarrito(request, indice);
            resultado.put("msg", "OK");
        } catch (Exception ex) {
            resultado.put("msg", ex.getMessage());
        }

        PrintWriter out = response.getWriter();
        out.print(new Gson().toJson(resultado));
        out.flush();
    }

    protected void EliminarDetalle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        Map<String, Object> resultado = new HashMap<>();

        try {
            carritoUtil.RemoverCarrito(request);
            resultado.put("msg", "OK");
        } catch (Exception ex) {
            resultado.put("msg", ex.getMessage());
        }

        PrintWriter out = response.getWriter();
        out.print(new Gson().toJson(resultado));
        out.flush();
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
