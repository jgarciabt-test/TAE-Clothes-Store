-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Oct 02, 2014 at 10:35 AM
-- Server version: 5.6.20
-- PHP Version: 5.5.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `TAE_STORE`
--

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE IF NOT EXISTS `products` (
`prod_id` int(11) NOT NULL,
  `prod_name` varchar(50) NOT NULL,
  `prod_desc` text NOT NULL,
  `prod_detail` text,
  `prod_price` float NOT NULL,
  `prod_offer` tinyint(1) NOT NULL,
  `prod_main_pic` int(11) DEFAULT NULL,
  `prod_cat` int(11) DEFAULT NULL,
  `prod_state` int(11) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`prod_id`, `prod_name`, `prod_desc`, `prod_detail`, `prod_price`, `prod_offer`, `prod_main_pic`, `prod_cat`, `prod_state`) VALUES
(1, 'Brand Shirt', 'Fit : Tailored and tight\r\nLong-sleeved twill shirt with button-down collar.\r\nNo pockets.', NULL, 15.55, 0, 5, 3, 1),
(3, 'Highstone Shirt', 'Fit : Comfortable and tight fitting.\r\nDenim long-sleeved shirt.\r\nOne pocket.', NULL, 30.95, 1, 6, 3, 1),
(4, 'Nakle Shirt', 'Fit : Comfortable and tight fitting.\r\nLong-sleeved shirt with indigo checks.\r\nTwo pockets.\r\nContrasting lining.', NULL, 32, 0, 7, 3, 1),
(5, 'Oxford Shirt', 'Long-sleeved Oxford shirt with button-down collar.\r\nNo pockets.\r\nContrasting lining.', NULL, 36, 1, 8, 3, 1),
(6, 'Twill Check Shirt', 'Fit : Comfortable and tight fitting.\r\nLong-sleeved shirt in twill fabric.\r\nOne pocket.\r\nContrasting lining.', NULL, 26, 1, 9, 3, 1),
(7, 'Otro', 'blablabla', NULL, 12, 0, 1, 1, 1),
(8, 'zapatitos', 'desc', NULL, 11, 1, 1, 5, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `products`
--
ALTER TABLE `products`
 ADD PRIMARY KEY (`prod_id`), ADD KEY `prod_main_pic` (`prod_main_pic`), ADD KEY `prod_cat` (`prod_cat`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
MODIFY `prod_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=9;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `products`
--
ALTER TABLE `products`
ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`prod_main_pic`) REFERENCES `picture` (`pic_id`),
ADD CONSTRAINT `products_ibfk_2` FOREIGN KEY (`prod_cat`) REFERENCES `categories` (`cat_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
