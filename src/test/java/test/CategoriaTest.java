package test;

import static org.junit.jupiter.api.Assertions.*;
import com.parrilla.chalaperu.dao.impl.CategoriaDAOImpl;
import com.parrilla.chalaperu.model.Categoria;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

public class CategoriaTest {

    @InjectMocks
    private CategoriaDAOImpl categoriaDAO;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarTodos() throws SQLException {
        List<Categoria> lista = categoriaDAO.ListarTodos();
        assertTrue(lista.size() > 0);
    }

    @Test
    void testBuscarPorId() throws SQLException {
        int id = 3;
        Categoria obj = categoriaDAO.BuscarPorId(id);
        assertNotNull(obj);
        assertEquals(id, obj.getIdCateg());
    }

    /*
    @Test
    void testGuardar() throws Exception {
        Categoria categoria = new Categoria();
        categoria.setNombreCateg("Postres");
        categoria.setEstado(1);

        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1); // Simula éxito en la inserción

        boolean resultado = categoriaDAO.Guardar(categoria);

        assertTrue(resultado);
    }
     */
    @Test
    void testActualizar() throws Exception {
        Categoria obj = new Categoria();
        obj.setIdCateg(100);
        obj.setNombreCateg("Postres");
        obj.setEstado(1);

        boolean resultado = categoriaDAO.Actualizar(obj);

        // assertTrue(resultado);
        assertFalse(resultado); // No actualizo
    }

    @Test
    void testEliminar() throws Exception {
        int idEliminar = 100;
        boolean resultado = categoriaDAO.Eliminar(idEliminar);
        assertFalse(resultado);
    }

    @Test
    void testExisteCategoria() throws SQLException {
        String nombre = "Carnes Rojas";
        int id = 0;
        int resultado = categoriaDAO.ExisteCategoria(nombre, id);
        assertEquals(1, resultado);
    }
}
