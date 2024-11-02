package com.parrilla.chalaperu.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static String RUTA_IMAGEN = "src\\main\\webapp\\assets\\img\\producto\\";
    public static String RUTA_APP_IMAGEN = "assets\\img\\consultas\\";
    public static String RUTA_REPORTE = "src\\main\\java\\reportes\\";

    public static String guardarArchivo(String ruta, Part archivoPart, String nombreArchivo) {
        try {
            File archivo = new File(ruta);
            if (!archivo.exists()) {
                archivo.mkdirs();
                logger.info("Directorio creado: {}", ruta);
            }

            Path path = Paths.get(ruta + nombreArchivo);
            archivo = new File(path.toAbsolutePath().toString());

            try (InputStream input = archivoPart.getInputStream()) {
                Files.copy(input, archivo.toPath(), StandardCopyOption.REPLACE_EXISTING);
                logger.info("Archivo guardado: {}", archivo.getAbsolutePath());
            }
        } catch (Exception ex) {
            logger.error("Error al guardar archivo: {}", ex.getMessage(), ex);
            return "";
        }
        return nombreArchivo;
    }

    public static String RutaAbsoluta(HttpServletRequest request, String carpeta) {
        String ruta = request.getServletContext().getRealPath("");
        return ruta.substring(0, ruta.indexOf("target")) + carpeta;
    }

    public static String RutaAbsoluta(HttpServletRequest request) {
        String ruta = request.getServletContext().getRealPath("");
        return ruta.substring(0, ruta.indexOf("target"));
    }

    public static String NomImagen() {
        String uniqueId = UUID.randomUUID().toString();
        long timestamp = System.currentTimeMillis();
        return String.format("img-%s-%d.jpg", uniqueId, timestamp);
    }

}
