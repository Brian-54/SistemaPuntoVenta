-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 19-12-2020 a las 04:06:39
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
(1, 1, 'Alberto', 34534534, 'Prados #234', 'Seguro Social', '2020-11-01 22:49:45'),
(9, 2, 'Brian', 34534534, 'Prados #234', 'IMSS', '2020-11-02 19:26:44'),
(10, 3, 'Xavier', 34534534, 'Prados #234', 'IMSS', '2020-11-02 19:26:51');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `config`
--

CREATE TABLE `config` (
  `id` int(11) NOT NULL,
  `nombre` varchar(200) NOT NULL,
  `ruc` varchar(20) NOT NULL,
  `telefono` int(15) NOT NULL,
  `direccion` varchar(200) NOT NULL,
  `razon` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `config`
--

INSERT INTO `config` (`id`, `nombre`, `ruc`, `telefono`, `direccion`, `razon`) VALUES
(1, 'Brian', '1', 57666741, 'Bosques de Berbera #13', 'IMSS');

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
(20, 1, 7, '1450.00', 15);

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
(2, '1', 'Botas', 'Keboo', 3, '1450.00', '2020-12-07 15:00:31'),
(3, '2', 'Memoria ram 4Gb', 'Intel', 4, '300.00', '2020-12-07 15:04:02');

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
(1, 1, 'Nike', 234234, 'Bosques de Berbera', 'IMSS', '2020-12-06 22:26:06'),
(3, 2, 'Jordan', 234234, 'Bosques de Berbera', 'IMSS', '2020-12-06 22:46:48'),
(6, 3, 'Intel', 234234, 'Bosques deLuzaka', '', '2020-12-06 23:56:44'),
(7, 4, 'Keboo', 345345345, 'Col Industrial 2048', 'IMSS', '2020-12-07 14:22:42');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `correo` varchar(100) NOT NULL,
  `pass` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `nombre`, `correo`, `pass`) VALUES
(1, 'Brian', 'musovespo54@gmail.com', '1234'),
(3, 'Juan', 'juan@gmail.com', '12345'),
(4, 'Oswaldo', 'oswaldo54@gmail.com', '5453');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE `ventas` (
  `id` int(11) NOT NULL,
  `cliente` varchar(100) NOT NULL,
  `vendedor` varchar(100) NOT NULL,
  `total` decimal(10,2) NOT NULL,
  `fecha` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `ventas`
--

INSERT INTO `ventas` (`id`, `cliente`, `vendedor`, `total`, `fecha`) VALUES
(1, 'Alberto', 'UPIICSA', '3800.00', '2020-12-10 17:14:48'),
(2, 'Xavier', 'UPIICSA', '4100.00', '2020-12-10 17:35:07'),
(3, 'Brian', 'UPIICSA', '3500.00', '2020-12-10 17:38:34'),
(4, 'Alberto', 'UPIICSA', '1450.00', '2020-12-10 17:40:17'),
(5, 'Alberto', 'UPIICSA', '3200.00', '2020-12-10 17:41:46'),
(6, '', 'UPIICSA', '3200.00', '2020-12-11 18:48:44'),
(7, 'Brian', 'UPIICSA', '3200.00', '2020-12-11 18:57:15'),
(8, 'Alberto', 'UPIICSA', '2900.00', '2020-12-11 19:07:07'),
(9, 'Brian', 'UPIICSA', '1500.00', '2020-12-11 19:07:52'),
(10, 'Xavier', 'UPIICSA', '2950.00', '2020-12-11 19:18:25'),
(11, 'Brian', 'Murillo 54', '9600.00', '2020-12-18 19:28:36'),
(12, 'Xavier', 'Murillo 54', '2950.00', '2020-12-18 19:38:46'),
(13, 'Alberto', 'Murillo 54', '7850.00', '2020-12-18 20:31:18'),
(14, 'Alberto', 'Murillo 54', '3800.00', '2020-12-18 20:34:48'),
(15, 'Xavier', 'Murillo 54', '11950.00', '2020-12-18 20:36:33');

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `config`
--
ALTER TABLE `config`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `detalle`
--
ALTER TABLE `detalle`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `ventas`
--
ALTER TABLE `ventas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
