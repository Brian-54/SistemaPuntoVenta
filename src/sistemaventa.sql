-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 20-12-2020 a las 02:24:14
-- Versión del servidor: 10.4.8-MariaDB
-- Versión de PHP: 7.3.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `sistemaventa`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `id` int(11) NOT NULL,
  `dni` int(20) NOT NULL,
  `nombre` varchar(150) NOT NULL,
  `telefono` int(15) NOT NULL,
  `direccion` varchar(200) NOT NULL,
  `razon` varchar(200) NOT NULL,
  `fecha` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`id`, `dni`, `nombre`, `telefono`, `direccion`, `razon`, `fecha`) VALUES
(1, 1, 'Alberto', 34534534, 'Prados #234', 'Comerciante', '2020-11-01 22:49:45'),
(9, 2, 'Brian', 34534534, 'Prados #234', 'Tienda Electronica', '2020-11-02 19:26:44'),
(10, 3, 'Xavier', 34534534, 'Prados #234', 'Tienda de Tennis', '2020-11-02 19:26:51'),
(13, 4, 'Alexis', 345353456, 'Iztacalco #3232', 'Tienda de Tecnologia', '2020-12-18 21:38:57');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `config`
--

CREATE TABLE `config` (
  `id` int(11) NOT NULL,
  `nombre` varchar(200) NOT NULL,
  `ruc` varchar(20) NOT NULL,
  `telefono` int(20) NOT NULL,
  `direccion` varchar(200) NOT NULL,
  `razon` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `config`
--

INSERT INTO `config` (`id`, `nombre`, `ruc`, `telefono`, `direccion`, `razon`) VALUES
(1, 'CienciasUPIICSA', '1', 57666741, 'Estado de Mexico', 'Instituto Politecnico Nacional');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle`
--

CREATE TABLE `detalle` (
  `id` int(11) NOT NULL,
  `cod_producto` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `id_venta` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `detalle`
--

INSERT INTO `detalle` (`id`, `cod_producto`, `cantidad`, `precio`, `id_venta`) VALUES
(1, 1, 2, '1450.00', 1),
(2, 2, 1, '300.00', 1),
(3, 1, 2, '1450.00', 6),
(4, 2, 1, '300.00', 6),
(5, 1, 2, '1450.00', 7),
(6, 2, 1, '300.00', 7),
(7, 1, 2, '1450.00', 8),
(8, 2, 5, '300.00', 9),
(9, 1, 1, '1450.00', 10),
(10, 2, 5, '300.00', 10),
(11, 1, 6, '1450.00', 11),
(12, 2, 3, '300.00', 11),
(13, 1, 1, '1450.00', 12),
(14, 2, 5, '300.00', 12),
(15, 2, 2, '300.00', 13),
(16, 1, 5, '1450.00', 13),
(17, 1, 2, '1450.00', 14),
(18, 2, 3, '300.00', 14),
(19, 2, 6, '300.00', 15),
(20, 1, 7, '1450.00', 15),
(21, 1, 4, '1450.00', 16),
(22, 2, 3, '300.00', 16),
(23, 3, 2, '1255.64', 16),
(24, 1, 3, '1450.00', 17),
(25, 2, 2, '300.00', 17),
(26, 3, 1, '1255.64', 17),
(27, 1, 1, '1450.00', 18),
(28, 2, 3, '300.00', 18),
(29, 3, 1, '1255.64', 18),
(30, 1, 2, '1450.00', 19),
(31, 3, 2, '1255.64', 19),
(32, 1, 4, '1450.00', 20),
(33, 2, 1, '300.00', 20),
(34, 3, 1, '1255.64', 20),
(35, 4, 2, '250.00', 20),
(36, 1, 2, '1450.00', 21),
(37, 4, 2, '250.00', 21),
(38, 1, 2, '1450.00', 22),
(39, 2, 1, '300.00', 22),
(40, 3, 1, '1255.64', 23),
(41, 3, 1, '1255.64', 24),
(42, 1, 3, '1450.00', 25),
(43, 3, 1, '1255.64', 25),
(44, 2, 5, '300.00', 26),
(45, 4, 5, '250.00', 27),
(46, 2, 1, '300.00', 27),
(47, 1, 3, '1450.00', 27),
(48, 4, 2, '250.00', 28),
(49, 2, 1, '300.00', 29),
(50, 1, 2, '1450.00', 29);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `id` int(11) NOT NULL,
  `codigo` varchar(30) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `proveedor` varchar(100) NOT NULL,
  `stock` int(11) NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `fecha` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`id`, `codigo`, `nombre`, `proveedor`, `stock`, `precio`, `fecha`) VALUES
(2, '1', 'Botas', 'Keboo', 2, '1450.00', '2020-12-07 15:00:31'),
(3, '2', 'Memoria ram 4Gb', 'Intel', 2, '300.00', '2020-12-07 15:04:02'),
(4, '3', 'Procesador i5', 'Intel', 7, '1255.64', '2020-12-18 21:41:03'),
(5, '4', 'Audifonos', 'Sony', 9, '250.00', '2020-12-19 02:54:24');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedor`
--

CREATE TABLE `proveedor` (
  `id` int(11) NOT NULL,
  `ruc` int(20) NOT NULL,
  `nombre` varchar(200) NOT NULL,
  `telefono` int(15) NOT NULL,
  `direccion` varchar(200) NOT NULL,
  `razon` varchar(200) NOT NULL,
  `fecha` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `proveedor`
--

INSERT INTO `proveedor` (`id`, `ruc`, `nombre`, `telefono`, `direccion`, `razon`, `fecha`) VALUES
(1, 1, 'Nike', 234234, 'Bosques de Berbera', ' Nike California S. DE R.L. DE C.V.', '2020-12-06 22:26:06'),
(3, 2, 'Jordan', 234234, 'Bosques de Berbera', 'Urbana Jordan S.A. de C.V.', '2020-12-06 22:46:48'),
(6, 3, 'Intel', 234234, 'Bosques deLuzaka', 'Intel Tecnologia de Mexico S.A. de C.V.', '2020-12-06 23:56:44'),
(7, 4, 'Keboo', 345345345, 'Col Industrial 2048', 'Keboo S.A. de C.V.', '2020-12-07 14:22:42'),
(8, 5, 'Sony', 345345346, 'Col Centro', 'Sony De Mexico S.A. de C.V', '2020-12-18 21:40:06'),
(9, 6, 'Nike', 234234, 'Bosques de Berbera', ' Nike Mexico S. de R.L. de C.V.', '2020-12-19 03:20:45');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `correo` varchar(100) NOT NULL,
  `pass` varchar(100) NOT NULL,
  `rol` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `nombre`, `correo`, `pass`, `rol`) VALUES
(1, 'Brian Murillo', 'musovespo54@gmail.com', '1234', 'Administrador'),
(3, 'Juan', 'juan@gmail.com', '12345', 'Asistente'),
(4, 'Oswaldo', 'oswaldo54@gmail.com', '5453', 'Asistente'),
(6, 'Vendedor', 'Prueba@gmail.com', '123', 'Asistente');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE `ventas` (
  `id` int(11) NOT NULL,
  `cliente` varchar(100) NOT NULL,
  `vendedor` varchar(100) NOT NULL,
  `total` decimal(10,2) NOT NULL,
  `fecha` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `ventas`
--

INSERT INTO `ventas` (`id`, `cliente`, `vendedor`, `total`, `fecha`) VALUES
(12, 'Xavier', 'Murillo 54', '2950.00', '16/12/2020'),
(13, 'Alberto', 'Murillo 54', '7850.00', '16/12/2020'),
(14, 'Alberto', 'Murillo 54', '3800.00', '17/12/2020'),
(15, 'Xavier', 'Murillo 54', '11950.00', '17/12/2020'),
(16, 'Brian', 'Murillo 54', '9211.28', '17/12/2020'),
(17, 'Alberto', 'Murillo 54', '6205.64', '18/12/2020'),
(18, 'Alexis', 'Murillo 54', '3605.64', '18/12/2020'),
(19, 'Alexis', 'Brian', '5411.28', '19/12/2020'),
(20, 'Alberto', 'Brian', '7855.64', '19/12/2020'),
(21, 'Brian', 'Brian', '3400.00', '19/12/2020'),
(22, 'Alexis', 'Brian Murillo', '3200.00', '19/12/2020'),
(23, 'Alberto', 'Brian Murillo', '1255.64', '19/12/2020'),
(24, 'Alberto', 'Brian Murillo', '1255.64', '19/12/2020'),
(25, 'Brian', 'Brian Murillo', '5605.64', '19/12/2020'),
(26, 'Alexis', 'Brian Murillo', '1500.00', '19/12/2020'),
(27, 'Alexis', 'Brian Murillo', '5900.00', '19/12/2020'),
(28, 'Alberto', 'Prueba', '500.00', '19/12/2020'),
(29, 'Alberto', 'Prueba', '3200.00', '19/12/2020');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `config`
--
ALTER TABLE `config`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `detalle`
--
ALTER TABLE `detalle`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `config`
--
ALTER TABLE `config`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `detalle`
--
ALTER TABLE `detalle`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `ventas`
--
ALTER TABLE `ventas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
