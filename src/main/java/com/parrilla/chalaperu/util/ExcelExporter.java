package com.parrilla.chalaperu.util;

import com.parrilla.chalaperu.model.Cliente;
import com.parrilla.chalaperu.model.Usuario;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExporter {

    public void exportarUsuarios(List<Usuario> lista, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=reporte_usuarios.xlsx");

        String[] columnas = {"ID","Rol", "Nombres", "Apellidos", "Username", "Estado"};

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet hoja = workbook.createSheet("Clientes");

            CellStyle cabeceraEstilo = crearEstiloCabecera(workbook);
            crearCabecera(hoja, cabeceraEstilo, columnas);

            CellStyle dataCellStyle = crearEstiloDatos(workbook);

            int initRow = 1;
            for (Usuario c : lista) {
                Row fila = hoja.createRow(initRow++);
                String[] datos = {
                    String.valueOf(c.getIdUsuario()),
                    c.getNombreRol(),
                    c.getNombres(),
                    c.getApellidos(),
                    c.getUsername(),
                    c.getEstado() == 1 ? "Activo": "Inactivo"
                };
                crearFilaDatosGenerico(fila, datos, dataCellStyle);
            }

            ajustarAnchoColumnas(hoja, columnas.length);

            ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
            workbook.write(outByteStream);
            response.getOutputStream().write(outByteStream.toByteArray());
            response.getOutputStream().flush();
        }
    }
    
    public void exportarClientes(List<Cliente> lista, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=reporte_clientes.xlsx");

        String[] columnas = {"ID", "Nombres", "Apellidos", "DNI", "Telefono", "Dirección"};

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet hoja = workbook.createSheet("Clientes");

            CellStyle cabeceraEstilo = crearEstiloCabecera(workbook);
            crearCabecera(hoja, cabeceraEstilo, columnas);

            CellStyle dataCellStyle = crearEstiloDatos(workbook);

            int initRow = 1;
            for (Cliente c : lista) {
                Row fila = hoja.createRow(initRow++);
                String[] datos = {
                    String.valueOf(c.getIdCliente()),
                    c.getNombre(),
                    c.getApellidos(),
                    c.getDni(),
                    c.getTelefono(),
                    c.getDireccion()
                };
                crearFilaDatosGenerico(fila, datos, dataCellStyle);
            }

            ajustarAnchoColumnas(hoja, columnas.length);

            ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
            workbook.write(outByteStream);
            response.getOutputStream().write(outByteStream.toByteArray());
            response.getOutputStream().flush();
        }
    }

    private void crearFilaDatosGenerico(Row fila, String[] datos, CellStyle dataCellStyle) {
        for (int i = 0; i < datos.length; i++) {
            Cell cell = fila.createCell(i);
            cell.setCellValue(datos[i]);
            cell.setCellStyle(dataCellStyle);
        }
    }

    private CellStyle crearEstiloCabecera(XSSFWorkbook workbook) {
        CellStyle cabeceraEstilo = workbook.createCellStyle();
        cabeceraEstilo.setFillForegroundColor(IndexedColors.OLIVE_GREEN.getIndex());
        cabeceraEstilo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cabeceraEstilo.setBorderBottom(BorderStyle.THIN);
        cabeceraEstilo.setBorderLeft(BorderStyle.THIN);
        cabeceraEstilo.setBorderRight(BorderStyle.THIN);
        cabeceraEstilo.setBorderTop(BorderStyle.THIN);
        cabeceraEstilo.setAlignment(HorizontalAlignment.CENTER);
        cabeceraEstilo.setVerticalAlignment(VerticalAlignment.CENTER);
        cabeceraEstilo.setWrapText(true);

        Font fuenteCabecera = workbook.createFont();
        fuenteCabecera.setFontName("Arial");
        fuenteCabecera.setBold(true);
        fuenteCabecera.setFontHeightInPoints((short) 12);
        fuenteCabecera.setColor(IndexedColors.WHITE.getIndex());
        cabeceraEstilo.setFont(fuenteCabecera);

        return cabeceraEstilo;
    }

    private void crearCabecera(XSSFSheet hoja, CellStyle cabeceraEstilo, String[] columnas) {
        Row filaCabecera = hoja.createRow(0);
        for (int i = 0; i < columnas.length; i++) {
            Cell cell = filaCabecera.createCell(i);
            cell.setCellStyle(cabeceraEstilo);
            cell.setCellValue(columnas[i]);
        }
    }

    private CellStyle crearEstiloDatos(XSSFWorkbook workbook) {
        CellStyle dataCellStyle = workbook.createCellStyle();
        dataCellStyle.setWrapText(true);
        dataCellStyle.setBorderBottom(BorderStyle.THIN);
        dataCellStyle.setBorderLeft(BorderStyle.THIN);
        dataCellStyle.setBorderRight(BorderStyle.THIN);
        dataCellStyle.setBorderTop(BorderStyle.THIN);
        return dataCellStyle;
    }

    private void ajustarAnchoColumnas(XSSFSheet hoja, int numColumnas) {
        for (int i = 0; i < numColumnas; i++) {
            hoja.autoSizeColumn(i);
            if (hoja.getColumnWidth(i) < 256 * 20) {
                hoja.setColumnWidth(i, 256 * 20);
            }
        }
    }
}
