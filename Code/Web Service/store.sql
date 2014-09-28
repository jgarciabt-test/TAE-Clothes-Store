-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Sep 28, 2014 at 07:36 PM
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
-- Table structure for table `store`
--

CREATE TABLE IF NOT EXISTS `store` (
`sto_id` int(11) NOT NULL,
  `sto_name` varchar(40) NOT NULL,
  `sto_address` varchar(200) NOT NULL,
  `sto_pc` varchar(10) NOT NULL,
  `sto_city` varchar(20) NOT NULL,
  `sto_phone` varchar(20) NOT NULL,
  `sto_lat` double NOT NULL,
  `sto_lng` double NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `store`
--

INSERT INTO `store` (`sto_id`, `sto_name`, `sto_address`, `sto_pc`, `sto_city`, `sto_phone`, `sto_lat`, `sto_lng`) VALUES
(1, 'TAE Store Kilburn', '35 Brondesbury Road', 'NW6 6BS', 'London', '+44 111 222 3333', 51.536385, -0.19897),
(2, 'TAE Store Anson', '32, Anson Road', 'NW2 3UU', 'London', '+44 333 222 1111', 51.554172, -0.215673),
(3, 'TAE Store Metropolitan', '11, Settles Street', 'E1 1JW', 'London', '+44 222 333 1111', 51.515598, -0.064644),
(4, 'TAE Store Canary Wharf', '4, Hertsmere Road', 'E14 4AL', 'London', '+44 555 666 2222', 51.507574, -0.024562),
(5, 'TAE Store Middlesex', '109-117 Middlesex Street', 'E1 7JF', 'London', '+44 333 444 1111', 51.517536, -0.078013);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `store`
--
ALTER TABLE `store`
 ADD PRIMARY KEY (`sto_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `store`
--
ALTER TABLE `store`
MODIFY `sto_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
