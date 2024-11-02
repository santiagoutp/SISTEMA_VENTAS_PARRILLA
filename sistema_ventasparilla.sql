-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 02-11-2024 a las 15:11:47
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `sistema_ventasparilla`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_anular_venta` (IN `p_id_venta` INT)   BEGIN
    UPDATE tb_cabecera_venta
    SET estado = 0
    WHERE id_venta = p_id_venta;

    UPDATE tb_producto p
    JOIN tb_detalle_venta dv ON p.id_producto = dv.id_producto
    SET p.stock = p.stock + dv.cantidad
    WHERE dv.id_venta = p_id_venta;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insertar_detalle_venta` (IN `p_id_venta` INT, IN `p_id_producto` INT, IN `p_cantidad` INT, IN `p_precio_unitario` DOUBLE, IN `p_subtotal` DOUBLE, IN `p_porcentaje_igv` DOUBLE, IN `p_total_pagar` DOUBLE)   BEGIN

    INSERT INTO tb_detalle_venta(id_venta, id_producto, cantidad, precio_unitario, subtotal, porcentaje_igv, total_pagar) 
    VALUES (p_id_venta, p_id_producto, p_cantidad, p_precio_unitario, p_subtotal, p_porcentaje_igv, p_total_pagar);

	UPDATE tb_producto
	SET stock = stock - p_cantidad
	WHERE id_producto = p_id_producto;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_cabecera_venta`
--

CREATE TABLE `tb_cabecera_venta` (
  `id_venta` int(11) NOT NULL,
  `id_cliente` int(11) NOT NULL,
  `valor_pagar` double(10,2) NOT NULL,
  `fecha_venta` datetime NOT NULL,
  `estado` int(1) NOT NULL,
  `id_vendedor` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_cabecera_venta`
--

INSERT INTO `tb_cabecera_venta` (`id_venta`, `id_cliente`, `valor_pagar`, `fecha_venta`, `estado`, `id_vendedor`) VALUES
(2, 3, 108.61, '2024-11-02 01:54:54', 0, 2),
(3, 5, 64.75, '2024-11-02 01:59:10', 0, 2),
(4, 3, 116.96, '2024-11-02 02:05:35', 0, 3),
(5, 10, 158.73, '2024-11-02 02:10:20', 1, 4),
(6, 10, 139.24, '2024-11-02 07:04:39', 1, 2),
(7, 2, 83.54, '2024-11-02 07:26:29', 1, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_categoria`
--

CREATE TABLE `tb_categoria` (
  `id_categ` int(11) NOT NULL,
  `nombre_categ` varchar(100) NOT NULL,
  `estado` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_categoria`
--

INSERT INTO `tb_categoria` (`id_categ`, `nombre_categ`, `estado`) VALUES
(1, 'Carnes Rojas', 1),
(2, 'Aves', 1),
(3, 'Embutidos', 1),
(4, 'Guarniciones', 1),
(5, 'Salsas y Aderezos', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_cliente`
--

CREATE TABLE `tb_cliente` (
  `id_Cliente` int(11) NOT NULL,
  `nombres` varchar(30) NOT NULL,
  `apellidos` varchar(30) NOT NULL,
  `dni` varchar(15) NOT NULL,
  `telefono` varchar(15) NOT NULL,
  `direccion` varchar(150) NOT NULL,
  `estado` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_cliente`
--

INSERT INTO `tb_cliente` (`id_Cliente`, `nombres`, `apellidos`, `dni`, `telefono`, `direccion`, `estado`) VALUES
(1, 'Juan', 'Pérez López', '12345678', '987654321', 'Av. José Larco 812, Miraflores', 1),
(2, 'María', 'González Torres', '23456789', '976543210', 'Calle Lima 400, Barranco', 1),
(3, 'Luis', 'Martínez Rodríguez', '34567890', '965432109', 'Calle Pizarro 123, San Isidro', 1),
(4, 'Ana', 'Rodríguez Gómez', '45678901', '954321098', 'Av. Arequipa 850, Lima', 1),
(5, 'Pedro', 'López Fernández', '56789012', '943210987', 'Calle Huancavelica 210, La Victoria', 1),
(6, 'Luisa', 'Sánchez Jiménez', '67890123', '932109876', 'Av. Brasil 600, Breña', 1),
(7, 'Jorge', 'Díaz Mendoza', '78901234', '921098765', 'Calle Cañete 25, San Miguel', 1),
(8, 'Sofía', 'Hernández Silva', '89012345', '910987654', 'Av. Universitaria 4000, San Borja', 1),
(9, 'Fernando', 'Martín Salazar', '90123456', '909876543', 'Calle Santa Rosa 350, Pueblo Libre', 1),
(10, 'Claudia', 'Jiménez Ortiz', '01234567', '898765432', 'Calle Los Angeles 202, Surquillo', 1),
(11, 'Ricardo', 'Vásquez Valdivia', '12345678', '887654321', 'Av. Los Incas 1150, La Molina', 1),
(12, 'Valeria', 'Ríos Fernández', '23456789', '876543210', 'Calle Ocoña 330, Centro de Lima', 1),
(13, 'Carlos', 'Torres Córdova', '34567890', '865432109', 'Calle Ramón Castilla 345, San Juan de Lurigancho', 1),
(14, 'Gabriela', 'Morales López', '45678901', '854321098', 'Av. San Martín 1220, Miraflores', 1),
(15, 'Andrés', 'Salazar Vera', '56789012', '843210987', 'Calle José Gálvez 501, Chorrillos', 1),
(16, 'Cristina', 'Pacheco Suárez', '67890123', '832109876', 'Calle Juan de Arona 157, San Isidro', 1),
(17, 'Diego', 'Córdoba Pérez', '78901234', '821098765', 'Av. 28 de Julio 200, Miraflores', 1),
(18, 'Marta', 'Muñoz Aguirre', '89012345', '810987654', 'Calle Salaverry 950, San Isidro', 1),
(19, 'Nicolás', 'Quispe Ramos', '90123456', '809876543', 'Calle Tacna 100, Cercado de Lima', 1),
(20, 'Patricia', 'Cruz Soto', '01234567', '698765432', 'Calle González Prada 850, La Victoria', 1),
(21, 'Esteban', 'Rocca Carrillo', '12345678', '687654321', 'Av. Venezuela 345, Lima', 1),
(22, 'Julia', 'Rivera Arévalo', '23456789', '676543210', 'Calle La Paz 90, San Miguel', 1),
(23, 'Roberto', 'Soto Fernández', '34567890', '765432109', 'Calle San Francisco 1100, La Perla', 1),
(24, 'Inés', 'Silva Quiroz', '45678901', '754321098', 'Av. San Felipe 750, San Borja', 1),
(25, 'Ángel', 'Núñez Castañeda', '56789012', '743210987', 'Calle Huaylas 300, Villa María del Triunfo', 1),
(26, 'Patricia', 'Céspedes Lazo', '67890123', '732109876', 'Calle Prueba 200, San Juan de Miraflores', 1),
(27, 'Eduardo', 'Benítez Herrera', '78901234', '721098765', 'Av. José Olaya 750, Pueblo Libre', 1),
(28, 'Teresa', 'Bermúdez Salas', '89012345', '710987654', 'Calle Camaná 400, Centro de Lima', 1),
(29, 'Victor', 'Duran Ruiz', '90123456', '709876543', 'Calle María Parado de Bellido 200, La Victoria', 1),
(30, 'Adriana', 'Pizarro Villanueva', '01234567', '698765432', 'Calle Cuadra 155, Miraflores', 1),
(31, 'Claudio', 'Córdova Gutiérrez', '12345678', '687654321', 'Calle Barranco 250, Barranco', 1),
(32, 'Elena', 'Salinas Espinoza', '23456789', '676543210', 'Av. Los Abetos 1500, La Molina', 1),
(33, 'Hugo', 'Orellana Pinto', '34567890', '665432109', 'Calle Unión 800, San Martín de Porres', 1),
(34, 'Mónica', 'Camacho Vera', '45678901', '654321098', 'Calle Chacra Ríos 170, Surco', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_detalle_venta`
--

CREATE TABLE `tb_detalle_venta` (
  `id_detalle_venta` int(11) NOT NULL,
  `id_venta` int(11) NOT NULL,
  `id_producto` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio_unitario` double(10,2) NOT NULL,
  `subtotal` double(10,2) NOT NULL,
  `porcentaje_igv` double(10,2) NOT NULL,
  `total_pagar` double(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_detalle_venta`
--

INSERT INTO `tb_detalle_venta` (`id_detalle_venta`, `id_venta`, `id_producto`, `cantidad`, `precio_unitario`, `subtotal`, `porcentaje_igv`, `total_pagar`) VALUES
(1, 2, 3, 1, 28.00, 28.00, 18.00, 33.04),
(2, 2, 1, 2, 25.00, 50.00, 18.00, 59.00),
(3, 3, 5, 3, 15.50, 46.50, 18.00, 54.87),
(4, 4, 3, 3, 28.00, 84.00, 18.00, 99.12),
(5, 5, 2, 2, 30.00, 60.00, 18.00, 70.80),
(6, 5, 8, 3, 18.00, 54.00, 18.00, 63.72),
(7, 6, 2, 3, 30.00, 90.00, 18.00, 106.20),
(8, 6, 10, 2, 5.00, 10.00, 18.00, 11.80),
(9, 7, 1, 2, 25.00, 50.00, 18.00, 59.00),
(10, 7, 10, 2, 5.00, 10.00, 18.00, 11.80);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_producto`
--

CREATE TABLE `tb_producto` (
  `id_Producto` int(11) NOT NULL,
  `id_categ` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `stock` int(11) NOT NULL,
  `precio` double(10,2) NOT NULL,
  `descripcion` varchar(200) NOT NULL,
  `porcentaje_igv` double(10,2) NOT NULL,
  `estado` int(11) NOT NULL,
  `imagen` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_producto`
--

INSERT INTO `tb_producto` (`id_Producto`, `id_categ`, `nombre`, `stock`, `precio`, `descripcion`, `porcentaje_igv`, `estado`, `imagen`) VALUES
(1, 1, 'Churrasco', 40, 25.00, 'Delicioso churrasco marinado y asado al punto perfecto', 18.00, 1, 'img-aa03662d-d27b-4b2e-95ef-d65dc09f9660-1730506702865.jpg'),
(2, 1, 'Costillas de Cerdo', 25, 30.00, 'Costillas de cerdo a la BBQ, tiernas y jugosas', 18.00, 1, 'img-b9cfde0b-a4f8-40f8-9a68-f4e11b7e6a92-1730506811147.jpg'),
(3, 1, 'Picanha', 21, 28.00, 'Picanha asada, muy jugosa y llena de sabor', 18.00, 1, 'img-41f4cb7b-d725-4d07-9e5d-af4a6f87ee74-1730506986904.jpg'),
(4, 1, 'Salchichas', 50, 10.00, 'Salchichas a la parrilla, perfectas para compartir', 18.00, 1, 'img-5700bcc5-74d8-42e8-b6a6-196c660a937d-1730507017344.jpg'),
(5, 1, 'Anticuchos', 38, 15.50, 'Anticuchos de corazón de res, servidos con papas y salsa de ají', 18.00, 1, 'img-63233c8f-04b5-4e94-af0f-7c624ea8575f-1730507049476.jpg'),
(6, 2, 'Pollo a la Parrilla', 40, 20.00, 'Pollo marinado y asado a la parrilla, servido con ensalada', 18.00, 1, 'img-595fc90d-1d04-4780-be88-5a9c43ec4848-1730507089057.jpg'),
(7, 2, 'Mollejas', 25, 22.00, 'Mollejas de pollo a la parrilla, crujientes y sabrosas', 18.00, 1, 'img-6da04dc3-1634-4588-a4c4-1f1999fa7256-1730507128412.jpg'),
(8, 3, 'Brochetas de Carne', 27, 18.00, 'Brochetas mixtas de carne y verduras a la parrilla', 18.00, 1, 'img-a74f553e-be8b-448a-946f-8979e97f80d5-1730507163764.jpg'),
(9, 4, 'Guarnición de Papas Fritas', 50, 8.00, 'Papas fritas crujientes, perfectas para acompañar', 18.00, 1, 'img-01439b90-ce60-4960-8529-1ee9b766e0c8-1730507192216.jpg'),
(10, 5, 'Salsa Barbacoa', 56, 5.00, 'Salsa barbacoa casera, ideal para carnes asadas', 18.00, 1, 'img-3e391eb6-e888-42da-b991-9dcee0853c78-1730507239994.jpg');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_rol`
--

CREATE TABLE `tb_rol` (
  `id_rol` int(11) NOT NULL,
  `nombre_rol` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_rol`
--

INSERT INTO `tb_rol` (`id_rol`, `nombre_rol`) VALUES
(1, 'Administrador'),
(2, 'Vendedor');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_usuario`
--

CREATE TABLE `tb_usuario` (
  `id_usuario` int(11) NOT NULL,
  `nombres` varchar(30) NOT NULL,
  `apellidos` varchar(30) NOT NULL,
  `username` varchar(15) NOT NULL,
  `password` varchar(15) NOT NULL,
  `telefono` varchar(15) NOT NULL,
  `estado` int(11) NOT NULL,
  `id_rol` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_usuario`
--

INSERT INTO `tb_usuario` (`id_usuario`, `nombres`, `apellidos`, `username`, `password`, `telefono`, `estado`, `id_rol`) VALUES
(1, 'Juan', 'Pérez', 'admin', '123456', '965412365', 1, 1),
(2, 'María', 'González', 'mgonzalez', '123456', '965412365', 1, 2),
(3, 'Luis', 'Ramírez', 'lramirez', '123456', '965412369', 1, 2),
(4, 'Ana', 'Martínez', 'amartinez', '123456', '965478965', 1, 2),
(5, 'Carlos', 'López', 'clopez', '123456', '962001597', 1, 1),
(6, 'Laura', 'Fernández', 'lfernandez', '123456', '963222590', 0, 2);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `tb_cabecera_venta`
--
ALTER TABLE `tb_cabecera_venta`
  ADD PRIMARY KEY (`id_venta`),
  ADD KEY `id_vendedor` (`id_vendedor`),
  ADD KEY `id_cliente` (`id_cliente`);

--
-- Indices de la tabla `tb_categoria`
--
ALTER TABLE `tb_categoria`
  ADD PRIMARY KEY (`id_categ`);

--
-- Indices de la tabla `tb_cliente`
--
ALTER TABLE `tb_cliente`
  ADD PRIMARY KEY (`id_Cliente`);

--
-- Indices de la tabla `tb_detalle_venta`
--
ALTER TABLE `tb_detalle_venta`
  ADD PRIMARY KEY (`id_detalle_venta`),
  ADD KEY `id_producto` (`id_producto`),
  ADD KEY `id_venta` (`id_venta`);

--
-- Indices de la tabla `tb_producto`
--
ALTER TABLE `tb_producto`
  ADD PRIMARY KEY (`id_Producto`),
  ADD KEY `id_categ` (`id_categ`);

--
-- Indices de la tabla `tb_rol`
--
ALTER TABLE `tb_rol`
  ADD PRIMARY KEY (`id_rol`),
  ADD UNIQUE KEY `nombre_rol` (`nombre_rol`);

--
-- Indices de la tabla `tb_usuario`
--
ALTER TABLE `tb_usuario`
  ADD PRIMARY KEY (`id_usuario`),
  ADD KEY `id_rol` (`id_rol`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `tb_cabecera_venta`
--
ALTER TABLE `tb_cabecera_venta`
  MODIFY `id_venta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `tb_categoria`
--
ALTER TABLE `tb_categoria`
  MODIFY `id_categ` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `tb_cliente`
--
ALTER TABLE `tb_cliente`
  MODIFY `id_Cliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT de la tabla `tb_detalle_venta`
--
ALTER TABLE `tb_detalle_venta`
  MODIFY `id_detalle_venta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `tb_producto`
--
ALTER TABLE `tb_producto`
  MODIFY `id_Producto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `tb_usuario`
--
ALTER TABLE `tb_usuario`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `tb_cabecera_venta`
--
ALTER TABLE `tb_cabecera_venta`
  ADD CONSTRAINT `tb_cabecera_venta_ibfk_1` FOREIGN KEY (`id_vendedor`) REFERENCES `tb_usuario` (`id_usuario`),
  ADD CONSTRAINT `tb_cabecera_venta_ibfk_2` FOREIGN KEY (`id_cliente`) REFERENCES `tb_cliente` (`id_Cliente`);

--
-- Filtros para la tabla `tb_detalle_venta`
--
ALTER TABLE `tb_detalle_venta`
  ADD CONSTRAINT `tb_detalle_venta_ibfk_1` FOREIGN KEY (`id_producto`) REFERENCES `tb_producto` (`id_Producto`),
  ADD CONSTRAINT `tb_detalle_venta_ibfk_2` FOREIGN KEY (`id_venta`) REFERENCES `tb_cabecera_venta` (`id_venta`);

--
-- Filtros para la tabla `tb_producto`
--
ALTER TABLE `tb_producto`
  ADD CONSTRAINT `tb_producto_ibfk_1` FOREIGN KEY (`id_categ`) REFERENCES `tb_categoria` (`id_categ`);

--
-- Filtros para la tabla `tb_usuario`
--
ALTER TABLE `tb_usuario`
  ADD CONSTRAINT `tb_usuario_ibfk_1` FOREIGN KEY (`id_rol`) REFERENCES `tb_rol` (`id_rol`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
