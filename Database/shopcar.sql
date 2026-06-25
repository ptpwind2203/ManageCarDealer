-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jun 25, 2026 at 04:56 AM
-- Server version: 8.4.3
-- PHP Version: 8.3.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `shopcar`
--

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE `accounts` (
  `UserID` int NOT NULL,
  `FullName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `Account` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `BirthDate` date NOT NULL,
  `Email` varchar(255) NOT NULL,
  `NumberPhone` varchar(20) DEFAULT NULL,
  `Address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `roleID` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `accounts`
--

INSERT INTO `accounts` (`UserID`, `FullName`, `Account`, `Password`, `BirthDate`, `Email`, `NumberPhone`, `Address`, `roleID`) VALUES
(1, 'Phạm Thanh Phong', 'Ptpwind123', 'ptpwind123@', '2005-03-22', 'ptpwind2203@gmail.com', '0798464305', 'hồ chí minh', 1),
(2, 'Thanh Phong Phạm', 'ptpwind112', '$2a$10$UflFq.jwl5CidDRpXootL.PjA0v9JCfRDaG2PJHQjanxAw0aFsm8e', '2026-04-15', 'troknhok12@gmail.com', '0798464305', '53/18/10/ Đs 4 Bình Hưng Hòa B Bình Tân Tp. Hồ Chí Minh', 1),
(3, 'Trần Nguyễn Thanh Tuấn', 'tuan123', '$2a$10$X.aO0Pc88Eqjpk5xYZc00eaAmt0pi6EiZmnqhFG2QAYI4WX04ejGS', '2026-04-16', 'tuan@gmail.com', '0123698745', 'A5/59c/7h5 ấp 1b Xã Vĩnh Lộc A, Huyện Bình Chánh, TP. Hồ Chí Minh', 1),
(4, 'Thanh Phong Phạm', 'ptpwind1', '$2a$10$oIlZD6nXcVARRqOhuprH.ewrrHccj5gg9j0DJTtESgdvhEpTMXOFu', '2026-04-22', 'troknhok012@gmail.com', '0912121212', 'A5/59c/7h5 ấp 1b Xã Vĩnh Lộc A, Huyện Bình Chánh, TP. Hồ Chí Minh', 1);

-- --------------------------------------------------------

--
-- Table structure for table `cardetail`
--

CREATE TABLE `cardetail` (
  `CarID` int NOT NULL,
  `ColorID` int NOT NULL,
  `VersionID` int NOT NULL,
  `Quantity` int NOT NULL,
  `Price` decimal(20,3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `cardetail`
--

INSERT INTO `cardetail` (`CarID`, `ColorID`, `VersionID`, `Quantity`, `Price`) VALUES
(1, 2, 3, 12, 3000000000.000),
(1, 3, 2, 122, 1500000000.000),
(1, 3, 3, 5, 12112112112.000),
(1, 5, 2, 5, 1500000000.000),
(1, 9, 5, 12, 1900000000.000),
(13, 2, 3, 1, 40000000000.000),
(13, 3, 2, 3, 41000000000.000),
(13, 4, 3, 4, 40000000000.000),
(14, 3, 4, 7, 16000000000.000),
(14, 4, 4, 3, 16000000000.000),
(15, 1, 4, 7, 20000000000.000),
(15, 7, 3, 1, 21000000000.000),
(16, 7, 2, 11, 35000000000.000),
(17, 2, 4, 100, 21000000000.000),
(18, 1, 1, 7, 10000000000.000),
(18, 4, 5, 4, 10000000000.000),
(19, 1, 1, 5, 3500000000.000),
(19, 9, 4, 2, 3700000000.000),
(19, 10, 2, 4, 3600000000.000),
(20, 1, 3, 2, 2600000000.000),
(20, 2, 2, 3, 2800000000.000),
(21, 2, 1, 3, 3000000000.000),
(21, 3, 5, 1, 3200000000.000),
(21, 10, 1, 3, 3150000000.000),
(22, 3, 2, 2, 2550000000.000),
(22, 4, 2, 4, 2500000000.000),
(23, 4, 4, 10, 4100000000.000),
(23, 5, 4, 1, 4000000000.000),
(24, 5, 5, 2, 2900000000.000),
(24, 6, 3, 5, 2700000000.000),
(25, 6, 2, 4, 3050000000.000),
(25, 7, 1, 3, 3100000000.000),
(26, 7, 3, 3, 1400000000.000),
(26, 8, 4, 2, 1500000000.000),
(27, 8, 5, 1, 4300000000.000),
(27, 9, 5, 1, 4200000000.000);

-- --------------------------------------------------------

--
-- Table structure for table `cars`
--

CREATE TABLE `cars` (
  `CarID` int NOT NULL,
  `CarName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `CarImage` text,
  `EngineCapacity` varchar(255) DEFAULT NULL,
  `Description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `CompanyCar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `cars`
--

INSERT INTO `cars` (`CarID`, `CarName`, `CarImage`, `EngineCapacity`, `Description`, `CompanyCar`) VALUES
(1, 'Ford GT', 'Ford_GT', '3.5L V6 twin-turbo', 'Siêu xe Mỹ lấy cảm hứng từ xe đua Le Mans, nổi bật với thiết kế khí động học và hiệu suất cao', 'Ford'),
(13, 'Ferrari SF90 Stradale', 'Ferrari_SF90_Stradale', '4.0L V8 twin-turbo (kết hợp hybrid)', 'Siêu xe hybrid đầu tiên của Ferrari, sở hữu hiệu năng cực mạnh và công nghệ hiện đại.', 'Ferrari'),
(14, 'Ferrari 488 GTB', 'Ferrari_488_GTB', '3.9L V8 twin-turbo', 'Mẫu xe nổi bật với khả năng tăng tốc nhanh và thiết kế khí động học tinh tế.', 'Ferrari'),
(15, 'Ferrari Roma', 'Ferrari_Roma', '3.9L V8 twin-turbo', 'Xe GT sang trọng mang phong cách cổ điển, phù hợp cả đi phố và đường dài.', 'Ferrari'),
(16, 'Lamborghini Aventador', 'Lamborghini_Aventador', '6.5L V12', 'Siêu xe biểu tượng với thiết kế hầm hố và sức mạnh vượt trội.', 'Lamborghini'),
(17, 'Lamborghini Huracán', 'Lamborghini_Huracan', '5.2L V10', 'Mẫu xe thể thao nổi tiếng với hiệu năng cao và dễ điều khiển hơn Aventador.', 'Lamborghini '),
(18, 'Porsche 911 Turbo S', 'Porsche_911', '3.8L flat-6 twin-turbo', 'Xe thể thao huyền thoại với khả năng tăng tốc cực nhanh và độ ổn định cao.', 'Porsche'),
(19, 'Porsche Taycan Turbo S', 'Porsche_Taycan', 'Điện (EV)', 'Xe điện hiệu suất cao, kết hợp công nghệ hiện đại và khả năng tăng tốc ấn tượng.', 'Porsche'),
(20, 'Mercedes-Benz AMG GT', 'Mercedes_AMG', '4.0L V8 biturbo', 'Xe thể thao sang trọng với thiết kế mạnh mẽ và nội thất cao cấp.', 'Mercedes-Benz'),
(21, 'Mercedes-Benz S-Class', 'Mercedes_S_Class', '4.0L V8', 'Sedan hạng sang hàng đầu với công nghệ tiên tiến và trải nghiệm đẳng cấp.', 'Mercedes-Benz'),
(22, 'Nissan GT-R', 'Nissan_GTR', '3.8L V6 twin-turbo', '“Godzilla” của làng xe Nhật, nổi tiếng với hiệu năng cao và công nghệ dẫn động tiên tiến.', 'Nissan'),
(23, 'Audi R8', 'Audi_R8', '5.2L V10', 'Siêu xe nổi bật với động cơ V10 mạnh mẽ và thiết kế hiện đại.', 'Audi'),
(24, 'Audi RS7', 'Audi_RS7', '4.0L V8 twin-turbo', 'Sedan hiệu suất cao, kết hợp giữa tốc độ và sự tiện nghi.', 'Audi'),
(25, 'BMW M8 Coupe', 'BMW_M8', '4.4L V8 twin-turbo', 'Xe coupe thể thao sang trọng với hiệu năng mạnh mẽ và nội thất cao cấp.', 'BMW'),
(26, 'BMW i8', 'BMW_i8', 'Kappa 1.2L MPi', 'mẫu xe thể thao plug-in hybrid mang tính biểu tượng với thiết kế cánh bướm tương lai', 'BMW'),
(27, 'Maserati MC20', 'Maserati_MC20', '3.0L V6 twin-turbo', 'Siêu xe hiện đại với thiết kế Ý tinh tế và hiệu năng cao.', 'Maserati'),
(28, 'Maserati Quattroporte', 'Maserati_Quattroporte', '3.8L V8', 'Sedan hạng sang mang phong cách thể thao đặc trưng của Ý.', 'Maserati'),
(29, 'Lexus IS350', 'Lexus_IS350', 'động cơ V6 3.5L', 'Mẫu xe dòng sedan hạng sang cỡ nhỏ, nổi bật với thiết kế thể thao, sắc sảo và cảm giác lái phấn khích, hướng đến nhóm khách hàng trẻ tuổi.', 'Lexus');

-- --------------------------------------------------------

--
-- Table structure for table `colors`
--

CREATE TABLE `colors` (
  `ColorID` int NOT NULL,
  `ColorName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `colors`
--

INSERT INTO `colors` (`ColorID`, `ColorName`) VALUES
(1, 'Trắng'),
(2, 'Đen'),
(3, 'Đỏ'),
(4, 'Vàng'),
(5, 'Xanh lục'),
(6, 'Xanh lam'),
(7, 'Xám'),
(8, 'Tím'),
(9, 'Hồng'),
(10, 'Cam');

-- --------------------------------------------------------

--
-- Table structure for table `orderdetail`
--

CREATE TABLE `orderdetail` (
  `OrderID` int NOT NULL,
  `CarID` int NOT NULL,
  `QuantityBuy` int NOT NULL,
  `TotalPrice` decimal(20,3) NOT NULL,
  `Note` text,
  `Version` varchar(255) NOT NULL,
  `Color` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `orderdetail`
--

INSERT INTO `orderdetail` (`OrderID`, `CarID`, `QuantityBuy`, `TotalPrice`, `Note`, `Version`, `Color`) VALUES
(1, 14, 1, 16000000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Đặc Biệt', 'Đỏ'),
(2, 14, 1, 16000000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Đặc Biệt', 'Đỏ'),
(3, 14, 2, 32000000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Đặc Biệt', 'Đỏ'),
(4, 14, 2, 32000000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Đặc Biệt', 'Đỏ'),
(5, 15, 2, 40000000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Đặc Biệt', 'Trắng'),
(5, 16, 1, 35000000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Thể Thao', 'Xám'),
(6, 15, 2, 40000000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Đặc Biệt', 'Trắng'),
(6, 16, 1, 35000000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Thể Thao', 'Xám'),
(7, 15, 2, 40000000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Đặc Biệt', 'Trắng'),
(7, 16, 1, 35000000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Thể Thao', 'Xám'),
(8, 15, 2, 40000000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Đặc Biệt', 'Trắng'),
(8, 16, 1, 35000000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Thể Thao', 'Xám'),
(9, 15, 2, 40000000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Đặc Biệt', 'Trắng'),
(9, 16, 1, 35000000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Thể Thao', 'Xám'),
(9, 13, 1, 40000000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Tiêu Chuẩn', 'Vàng'),
(9, 23, 1, 4100000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Đặc Biệt', 'Vàng'),
(10, 22, 1, 2550000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Thể Thao', 'Đỏ'),
(11, 22, 1, 2550000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Thể Thao', 'Đỏ'),
(12, 1, 1, 1550000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Tiêu Chuẩn', 'Đỏ'),
(13, 1, 1, 1550000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Tiêu Chuẩn', 'Đỏ'),
(14, 24, 1, 2700000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Tiêu Chuẩn', 'Xanh lam'),
(15, 13, 1, 40000000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Tiêu Chuẩn', 'Vàng'),
(16, 15, 1, 21000000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Tiêu Chuẩn', 'Xám'),
(17, 16, 8, 280000000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Thể Thao', 'Xám'),
(18, 16, 3, 105000000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Thể Thao', 'Xám'),
(19, 13, 2, 80000000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Tiêu Chuẩn', 'Đen'),
(20, 21, 1, 3200000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Sang Trọng', 'Đỏ'),
(21, 14, 1, 16000000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Đặc Biệt', 'Đỏ'),
(22, 15, 1, 20000000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Đặc Biệt', 'Trắng'),
(23, 17, 1, 21000000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Đặc Biệt', 'Đen'),
(24, 16, 2, 70000000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Thể Thao', 'Xám'),
(24, 13, 1, 40000000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Tiêu Chuẩn', 'Đen'),
(25, 13, 1, 40000000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Tiêu Chuẩn', 'Vàng'),
(26, 22, 1, 2550000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Thể Thao', 'Đỏ'),
(26, 23, 1, 4000000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Đặc Biệt', 'Xanh lục'),
(27, 15, 2, 40000000000.000, 'CARDEALER HÂN HẠNH XIN CẢM ƠN BẠN', 'Đặc Biệt', 'Trắng');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `OrderID` int NOT NULL,
  `CustomerID` int DEFAULT NULL,
  `CustomerName` varchar(255) NOT NULL,
  `NumberPhone` varchar(20) NOT NULL,
  `CreatedAt` datetime NOT NULL,
  `TotalPrice` decimal(20,3) NOT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `Address` text NOT NULL,
  `Status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`OrderID`, `CustomerID`, `CustomerName`, `NumberPhone`, `CreatedAt`, `TotalPrice`, `Email`, `Address`, `Status`) VALUES
(1, NULL, 'Thanh Phong Phạm', '0798464305', '2026-04-17 17:49:02', 16000000000.000, 'troknhok12@gmail.com', '53/18/10/ Đs 4 Bình Hưng Hòa B Bình Tân Tp. Hồ Chí Minh', NULL),
(2, NULL, 'Thanh Phong Phạm', '0798464305', '2026-04-17 17:50:35', 16000000000.000, 'troknhok12@gmail.com', '53/18/10/ Đs 4 Bình Hưng Hòa B Bình Tân Tp. Hồ Chí Minh', NULL),
(3, NULL, 'Thanh Phong Phạm', '0798464305', '2026-04-17 17:51:54', 32000000000.000, 'troknhok12@gmail.com', '53/18/10/ Đs 4 Bình Hưng Hòa B Bình Tân Tp. Hồ Chí Minh', NULL),
(4, NULL, 'Thanh Phong Phạm', '0798464305', '2026-04-17 17:54:34', 32000000000.000, 'troknhok12@gmail.com', '53/18/10/ Đs 4 Bình Hưng Hòa B Bình Tân Tp. Hồ Chí Minh', NULL),
(5, NULL, 'Thanh Phong Phạm', '0912121212', '2026-04-17 18:03:34', 75000000000.000, 'troknhok012@gmail.com', 'A5/59c/7h5 ấp 1b Xã Vĩnh Lộc A, Huyện Bình Chánh, TP. Hồ Chí Minh', NULL),
(6, NULL, 'Thanh Phong Phạm', '0912121212', '2026-04-17 18:05:11', 75000000000.000, 'troknhok012@gmail.com', 'A5/59c/7h5 ấp 1b Xã Vĩnh Lộc A, Huyện Bình Chánh, TP. Hồ Chí Minh', NULL),
(7, NULL, 'Thanh Phong Phạm', '0912121212', '2026-04-17 18:07:37', 75000000000.000, 'troknhok012@gmail.com', 'A5/59c/7h5 ấp 1b Xã Vĩnh Lộc A, Huyện Bình Chánh, TP. Hồ Chí Minh', NULL),
(8, NULL, 'Thanh Phong Phạm', '0912121212', '2026-04-17 18:10:02', 75000000000.000, 'troknhok012@gmail.com', 'A5/59c/7h5 ấp 1b Xã Vĩnh Lộc A, Huyện Bình Chánh, TP. Hồ Chí Minh', NULL),
(9, NULL, 'Thanh Phong Phạm', '0912121212', '2026-04-17 18:24:34', 119100000000.000, 'troknhok012@gmail.com', 'A5/59c/7h5 ấp 1b Xã Vĩnh Lộc A, Huyện Bình Chánh, TP. Hồ Chí Minh', NULL),
(10, NULL, 'Thanh Phong Phạm', '0912121212', '2026-04-17 18:28:44', 2550000000.000, 'troknhok012@gmail.com', 'A5/59c/7h5 ấp 1b Xã Vĩnh Lộc A, Huyện Bình Chánh, TP. Hồ Chí Minh', NULL),
(11, NULL, 'Thanh Phong Phạm', '0912121212', '2026-04-17 18:30:30', 2550000000.000, 'troknhok012@gmail.com', 'A5/59c/7h5 ấp 1b Xã Vĩnh Lộc A, Huyện Bình Chánh, TP. Hồ Chí Minh', NULL),
(12, NULL, 'Thanh Phong Phạm', '0912121212', '2026-04-17 18:32:19', 1550000000.000, 'troknhok012@gmail.com', 'A5/59c/7h5 ấp 1b Xã Vĩnh Lộc A, Huyện Bình Chánh, TP. Hồ Chí Minh', NULL),
(13, NULL, 'Thanh Phong Phạm', '0912121212', '2026-04-17 18:32:37', 1550000000.000, 'troknhok012@gmail.com', 'A5/59c/7h5 ấp 1b Xã Vĩnh Lộc A, Huyện Bình Chánh, TP. Hồ Chí Minh', NULL),
(14, NULL, 'Thanh Phong Phạm', '0912121212', '2026-04-17 18:35:26', 2700000000.000, 'troknhok012@gmail.com', 'A5/59c/7h5 ấp 1b Xã Vĩnh Lộc A, Huyện Bình Chánh, TP. Hồ Chí Minh', NULL),
(15, NULL, 'Thanh Phong Phạm', '0912121212', '2026-04-17 18:42:00', 40000000000.000, 'troknhok012@gmail.com', 'A5/59c/7h5 ấp 1b Xã Vĩnh Lộc A, Huyện Bình Chánh, TP. Hồ Chí Minh', NULL),
(16, NULL, 'Thanh Phong Phạm', '0912121212', '2026-04-17 18:46:40', 21000000000.000, 'troknhok012@gmail.com', 'A5/59c/7h5 ấp 1b Xã Vĩnh Lộc A, Huyện Bình Chánh, TP. Hồ Chí Minh', NULL),
(17, NULL, 'Thanh Phong Phạm', '0798464305', '2026-04-17 19:04:49', 280000000000.000, 'troknhok12@gmail.com', '53/18/10/ Đs 4 Bình Hưng Hòa B Bình Tân Tp. Hồ Chí Minh', NULL),
(18, NULL, 'Thanh Phong Phạm', '0798464305', '2026-04-17 19:05:58', 105000000000.000, 'troknhok12@gmail.com', '53/18/10/ Đs 4 Bình Hưng Hòa B Bình Tân Tp. Hồ Chí Minh', NULL),
(19, NULL, 'Thanh Phong Phạm', '0798464305', '2026-04-17 19:09:30', 80000000000.000, 'troknhok12@gmail.com', '53/18/10/ Đs 4 Bình Hưng Hòa B Bình Tân Tp. Hồ Chí Minh', NULL),
(20, NULL, 'Thanh Phong Phạm', '0798464305', '2026-04-17 19:10:58', 3200000000.000, 'troknhok12@gmail.com', '53/18/10/ Đs 4 Bình Hưng Hòa B Bình Tân Tp. Hồ Chí Minh', NULL),
(21, NULL, 'Thanh Phong Phạm', ' 0981818112', '2026-04-17 19:30:24', 16000000000.000, 'troknhok12@gmail.com', ' hcm', NULL),
(22, NULL, 'Thanh Phong Phạm', ' 0981818112', '2026-04-17 19:34:00', 20000000000.000, 'troknhok12@gmail.com', ' hcm', NULL),
(23, NULL, 'Thanh Phong Phạm', ' 0981818112', '2026-04-17 19:34:48', 21000000000.000, 'troknhok12@gmail.com', 'hcms', NULL),
(24, NULL, 'Thanh Phong Phạm', '0912121212', '2026-04-17 20:09:16', 110000000000.000, 'troknhok012@gmail.com', 'A5/59c/7h5 ấp 1b Xã Vĩnh Lộc A, Huyện Bình Chánh, TP. Hồ Chí Minh', NULL),
(25, NULL, 'Thanh Phong Phạm', '0912121212', '2026-04-17 22:37:54', 40000000000.000, 'troknhok012@gmail.com', 'A5/59c/7h5 ấp 1b Xã Vĩnh Lộc A, Huyện Bình Chánh, TP. Hồ Chí Minh', NULL),
(26, NULL, 'Thanh Phong Phạm', '0912121212', '2026-04-17 23:11:17', 6550000000.000, 'troknhok012@gmail.com', 'A5/59c/7h5 ấp 1b Xã Vĩnh Lộc A, Huyện Bình Chánh, TP. Hồ Chí Minh', NULL),
(27, NULL, 'Thanh Phong Phạm', '0912121212', '2026-04-23 06:57:29', 40000000000.000, 'troknhok012@gmail.com', 'A5/59c/7h5 ấp 1b Xã Vĩnh Lộc A, Huyện Bình Chánh, TP. Hồ Chí Minh', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `roleID` int NOT NULL,
  `roleName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`roleID`, `roleName`) VALUES
(1, 'ADMIN'),
(2, 'STAFF'),
(3, 'CUSTOMER');

-- --------------------------------------------------------

--
-- Table structure for table `versions`
--

CREATE TABLE `versions` (
  `VersionID` int NOT NULL,
  `VersionName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `versions`
--

INSERT INTO `versions` (`VersionID`, `VersionName`) VALUES
(1, 'Cao Cấp'),
(2, 'Thể Thao'),
(3, 'Tiêu Chuẩn'),
(4, 'Đặc Biệt'),
(5, 'Sang Trọng');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accounts`
--
ALTER TABLE `accounts`
  ADD PRIMARY KEY (`UserID`),
  ADD UNIQUE KEY `Account` (`Account`),
  ADD KEY `roleID` (`roleID`);

--
-- Indexes for table `cardetail`
--
ALTER TABLE `cardetail`
  ADD PRIMARY KEY (`CarID`,`ColorID`,`VersionID`);

--
-- Indexes for table `cars`
--
ALTER TABLE `cars`
  ADD PRIMARY KEY (`CarID`);

--
-- Indexes for table `colors`
--
ALTER TABLE `colors`
  ADD PRIMARY KEY (`ColorID`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`OrderID`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`roleID`);

--
-- Indexes for table `versions`
--
ALTER TABLE `versions`
  ADD PRIMARY KEY (`VersionID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accounts`
--
ALTER TABLE `accounts`
  MODIFY `UserID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `cars`
--
ALTER TABLE `cars`
  MODIFY `CarID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `OrderID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `roleID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
