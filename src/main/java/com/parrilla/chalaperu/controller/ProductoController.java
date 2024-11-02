package com.parrilla.chalaperu.controller;

import com.parrilla.chalaperu.model.Producto;
import com.parrilla.chalaperu.service.CategoriaService;
import com.parrilla.chalaperu.service.ProductoService;
import com.parrilla.chalaperu.util.FileUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

@WebServlet(name = "ProductoController", urlPatterns = {"/producto"})
@MultipartConfig
public class ProductoController extends HttpServlet {
    
    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);
    private final String pagListar = "/views/pagGestProducto.jsp";
    private final String pagNuevo = "/views/pagNuevoProducto.jsp";
    private ProductoService productoService;
    private CategoriaService categoriaService;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        productoService = new ProductoService();
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
    
    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("productos", productoService.ListarTodos());
        request.getRequestDispatcher(pagListar).forward(request, response);
    }
    
    private void nuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("producto", new Producto());
        request.setAttribute("categorias", categoriaService.ListarTodos());
        request.getRequestDispatcher(pagNuevo).forward(request, response);
    }
    
    private void guardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Producto obj = new Producto();
        try {
            obj.setIdProducto(Integer.parseInt(request.getParameter("id")));
            obj.setNombre(request.getParameter("nombre"));
            obj.setDescripcion(request.getParameter("descripcion"));
            obj.setPrecio(Double.parseDouble(request.getParameter("precio")));
            obj.setStock(Integer.parseInt(request.getParameter("stock")));
            obj.setEstado(Integer.parseInt(request.getParameter("estado")));
            obj.setIdCateg(Integer.parseInt(request.getParameter("idCateg")));
            obj.setPorcentajeIgv(Double.parseDouble(request.getParameter("porcentajeIgv")));
            
            Part archivoPart = request.getPart("imagen");
            if (archivoPart.getSize() != 0) {
                String ruta = FileUtil.RutaAbsoluta(request, FileUtil.RUTA_IMAGEN);
                String imagen = FileUtil.guardarArchivo(ruta, archivoPart, FileUtil.NomImagen());
                obj.setImagen(imagen);
            } else {
                obj.setImagen(null);
            }
            
            boolean result = (obj.getIdProducto() == 0) ? productoService.Guardar(obj) : productoService.Actualizar(obj);
            
            if (result) {
                request.getSession().setAttribute("success", obj.getIdProducto() == 0 ? "Producto registrado" : "Producto actualizado");
                response.sendRedirect("producto?accion=listar");
                return;
            }
            request.getSession().setAttribute("error", "No se pudo guardar los datos.");
        } catch (Exception ex) {
            request.getSession().setAttribute("error", ex.getMessage());
            logger.error("Error: {}", ex.getMessage(), ex);
        }
        
        request.setAttribute("producto", obj);
        request.setAttribute("categorias", categoriaService.ListarTodos());
        request.getRequestDispatcher(pagNuevo).forward(request, response);
    }
    
    public String RutaAbsoluta(String carpeta) {
        String archivo = getServletContext().getRealPath("");
        
        String ruta = String.valueOf(archivo + carpeta);
        ruta = ruta.replace("\\build", "");
        
        return ruta;
    }
    
    private void editar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Producto producto = productoService.BuscarPorId(id);
        
        if (producto != null) {
            request.setAttribute("producto", producto);
            request.setAttribute("categorias", categoriaService.ListarTodos());
            request.getRequestDispatcher(pagNuevo).forward(request, response);
        } else {
            request.getSession().setAttribute("error", "No se encontró el producto con ID " + id);
            response.sendRedirect("producto?accion=listar");
        }
    }
    
    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean result = productoService.Eliminar(id);
            
            if (result) {
                request.getSession().setAttribute("success", "Producto con id " + id + " eliminado!");
            } else {
                request.getSession().setAttribute("error", "No se pudo eliminar el producto");
            }
        } catch (Exception ex) {
            request.getSession().setAttribute("error", ex.getMessage());
        }
        
        response.sendRedirect("producto?accion=listar");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
