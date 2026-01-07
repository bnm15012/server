-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Oct 11, 2025 at 06:56 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `studio`
--

-- --------------------------------------------------------

--
-- Table structure for table `activity`
--

CREATE TABLE `activity` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT 'system',
  `created_on` datetime(6) NOT NULL,
  `last_modified_on` datetime(6) NOT NULL,
  `version` bigint(20) NOT NULL DEFAULT 0,
  `activity_type` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `branch_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `activity`
--

INSERT INTO `activity` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `activity_type`, `description`, `branch_id`) VALUES
(1, 'system', '2025-08-17 11:36:46.000000', '2025-08-17 11:36:46.000000', 0, 'ZUMBA', '', 1),
(7, 'system', '2025-08-17 16:34:09.000000', '2025-08-17 16:34:09.000000', 0, 'DANCE', '', 2),
(8, 'system', '2025-08-17 16:45:44.000000', '2025-08-17 16:45:44.000000', 0, 'DANCE', '', 3),
(12, 'system', '2025-08-18 02:58:26.000000', '2025-08-18 02:58:26.000000', 0, 'YOGA', '', 3),
(13, 'system', '2025-08-18 02:58:32.000000', '2025-08-18 02:58:32.000000', 0, 'MARTIAL_ARTS', '', 3),
(14, 'system', '2025-08-18 16:32:17.000000', '2025-08-18 16:32:17.000000', 0, 'ZUMBA', '', 3),
(15, 'system', '2025-08-21 17:19:23.000000', '2025-08-21 17:19:23.000000', 0, 'ZUMBA', '', 2),
(16, 'system', '2025-08-21 18:12:24.000000', '2025-08-21 18:12:24.000000', 0, 'MARTIAL_ARTS', '', 2),
(17, 'system', '2025-08-22 15:39:01.000000', '2025-08-22 15:39:01.000000', 0, 'YOGA', '', 2),
(18, 'system', '2025-08-24 16:23:52.000000', '2025-08-24 16:23:52.000000', 0, 'FREESTYLE', '', 2);

-- --------------------------------------------------------

--
-- Table structure for table `activity_batch`
--

CREATE TABLE `activity_batch` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT 'system',
  `created_on` datetime(6) NOT NULL,
  `last_modified_on` datetime(6) NOT NULL,
  `version` bigint(20) NOT NULL DEFAULT 0,
  `days_per_week` int(11) DEFAULT NULL,
  `end_time` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `plan_type` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `start_time` varchar(255) NOT NULL,
  `activity_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `activity_batch`
--

INSERT INTO `activity_batch` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `days_per_week`, `end_time`, `name`, `plan_type`, `price`, `start_time`, `activity_id`) VALUES
(1, 'system', '2025-08-17 11:36:46.000000', '2025-08-17 11:40:39.000000', 1, 5, '12:00', 'batch 1', 'MONTHLY', 500, '00:00', 1),
(10, 'system', '2025-08-17 16:34:09.000000', '2025-08-24 16:21:05.000000', 2, 3, '00:00', 'Batch 1', 'MONTHLY', 100, '02:00', 7),
(12, 'system', '2025-08-17 16:45:44.000000', '2025-08-17 16:45:44.000000', 0, 3, '00:00', 'Batch 1', 'MONTHLY', 0, '00:00', 8),
(15, 'system', '2025-08-18 02:48:03.000000', '2025-08-18 02:48:03.000000', 0, 4, '00:00', 'Batch 2', 'MONTHLY', 0, '00:00', 8),
(16, 'system', '2025-08-18 02:48:03.000000', '2025-08-18 02:48:03.000000', 0, 5, '00:00', 'Batch 3', 'MONTHLY', 0, '00:00', 8),
(17, 'system', '2025-08-18 02:48:43.000000', '2025-08-18 02:48:43.000000', 0, 6, '00:00', 'Batch 4', 'MONTHLY', 0, '00:00', 8),
(18, 'system', '2025-08-18 02:48:43.000000', '2025-08-18 02:48:43.000000', 0, 3, '00:00', 'Batch 5', 'QUARTERLY', 0, '00:00', 8),
(19, 'system', '2025-08-18 02:49:12.000000', '2025-08-18 02:49:12.000000', 0, 4, '00:00', 'Batch 6', 'QUARTERLY', 0, '00:00', 8),
(20, 'system', '2025-08-18 02:49:12.000000', '2025-08-18 02:49:12.000000', 0, 3, '00:00', 'Batch 7', 'HALF_YEARLY', 0, '00:00', 8),
(21, 'system', '2025-08-18 02:49:12.000000', '2025-08-18 02:49:12.000000', 0, 3, '00:00', 'Batch 8', 'REGISTRATION', 0, '00:00', 8),
(22, 'system', '2025-08-18 02:49:12.000000', '2025-08-18 02:49:12.000000', 0, 3, '00:00', 'Batch 9', 'YEARLY', 0, '00:00', 8),
(25, 'system', '2025-08-18 02:58:26.000000', '2025-08-18 02:58:26.000000', 0, 3, '00:00', 'Batch 1', 'MONTHLY', 0, '00:00', 12),
(26, 'system', '2025-08-18 02:58:32.000000', '2025-08-18 02:58:32.000000', 0, 3, '00:00', 'Batch 1', 'MONTHLY', 0, '00:00', 13),
(27, 'system', '2025-08-18 16:32:17.000000', '2025-08-18 16:32:17.000000', 0, 3, '00:00', 'Batch 1', 'MONTHLY', 0, '00:00', 14),
(28, 'system', '2025-08-21 17:19:23.000000', '2025-08-21 18:08:30.000000', 4, 3, '00:00', 'Batch 1', 'REGISTRATION', 0, '00:00', 15),
(29, 'system', '2025-08-21 18:12:24.000000', '2025-08-21 18:12:24.000000', 0, 3, '00:00', 'Batch 1', 'HALF_YEARLY', 0, '00:00', 16),
(30, 'system', '2025-08-22 15:39:01.000000', '2025-08-22 15:39:08.000000', 1, 3, '00:00', 'Batch 1', 'test 1', 1000, '00:00', 17),
(31, 'system', '2025-08-22 16:13:12.000000', '2025-09-21 09:22:32.000000', 4, 3, '00:00', 'Kids batch 2', 'MONTHLY', 100, '00:00', 17),
(32, 'system', '2025-08-24 12:21:26.000000', '2025-09-21 09:22:32.000000', 3, 3, '00:00', 'Kids batch', 'MONTHLY', 200, '00:00', 17),
(33, 'system', '2025-08-24 16:23:52.000000', '2025-08-24 16:23:52.000000', 0, 3, '00:00', 'Batch 2', 'MONTHLY', 0, '00:00', 18),
(34, 'system', '2025-08-24 16:23:52.000000', '2025-08-24 16:23:52.000000', 0, 4, '00:00', 'Batch 2', 'MONTHLY', 0, '00:00', 18);

-- --------------------------------------------------------

--
-- Table structure for table `activity_membership_type`
--

CREATE TABLE `activity_membership_type` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT 'system',
  `created_on` datetime(6) NOT NULL,
  `last_modified_on` datetime(6) NOT NULL,
  `version` bigint(20) NOT NULL DEFAULT 0,
  `membership_type` varchar(255) NOT NULL,
  `studio_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `activity_membership_type`
--

INSERT INTO `activity_membership_type` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `membership_type`, `studio_id`) VALUES
(20, 'system', '2025-08-01 05:26:26.000000', '2025-08-01 05:26:26.000000', 0, 'MONTHLY', NULL),
(21, 'system', '2025-09-10 16:19:58.000000', '2025-09-10 16:19:58.000000', 0, 'monthly', 2),
(22, 'system', '2025-09-10 16:20:10.000000', '2025-09-10 16:20:10.000000', 0, 'year', 2);

-- --------------------------------------------------------

--
-- Table structure for table `bank_account`
--

CREATE TABLE `bank_account` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT 'system',
  `created_on` datetime(6) NOT NULL,
  `last_modified_on` datetime(6) NOT NULL,
  `version` bigint(20) NOT NULL DEFAULT 0,
  `account_number` varchar(255) DEFAULT NULL,
  `bank_name` varchar(255) DEFAULT NULL,
  `branch_name` varchar(255) DEFAULT NULL,
  `ifsc_code` varchar(255) DEFAULT NULL,
  `instructor_id` bigint(20) DEFAULT NULL,
  `upi_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE `booking` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT 'system',
  `created_on` datetime(6) NOT NULL,
  `last_modified_on` datetime(6) NOT NULL,
  `version` bigint(20) NOT NULL DEFAULT 0,
  `advance_amount` double NOT NULL,
  `advance_date` datetime(6) NOT NULL,
  `advance_mode` varchar(255) DEFAULT NULL,
  `balance_amount` double DEFAULT NULL,
  `booking_date` datetime(6) NOT NULL,
  `end_time` datetime(6) NOT NULL,
  `final_payment_date` datetime(6) DEFAULT NULL,
  `notes` text DEFAULT NULL,
  `payment_mode` varchar(255) NOT NULL,
  `payment_status` varchar(255) NOT NULL,
  `purpose` text NOT NULL,
  `start_time` datetime(6) NOT NULL,
  `total_amount` double NOT NULL,
  `branch_id` bigint(20) NOT NULL,
  `client_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `advance_amount`, `advance_date`, `advance_mode`, `balance_amount`, `booking_date`, `end_time`, `final_payment_date`, `notes`, `payment_mode`, `payment_status`, `purpose`, `start_time`, `total_amount`, `branch_id`, `client_id`) VALUES
(2, 'system', '2025-07-06 18:15:59.000000', '2025-07-06 12:45:59.000000', 0, 12, '2025-07-06 12:27:46.000000', 'CASH', 0, '2025-07-06 12:27:46.000000', '2025-07-06 15:00:00.000000', NULL, NULL, 'CASH', 'PENDING', 'Dance Practice', '2025-07-05 21:45:00.000000', 12, 2, 1),
(3, 'system', '2025-09-07 17:52:50.000000', '2025-09-07 17:52:50.000000', 0, 50, '2025-09-07 17:48:44.000000', 'CASH', 0, '2025-09-07 17:48:44.000000', '2025-09-08 18:30:00.000000', NULL, NULL, 'CASH', 'PENDING', 'test email', '2025-09-07 18:30:00.000000', 50, 2, 2),
(4, 'system', '2025-09-07 19:27:03.000000', '2025-09-07 19:27:03.000000', 0, 456, '2025-09-07 19:00:13.000000', 'CASH', 0, '2025-09-07 19:00:13.000000', '2025-09-09 18:30:00.000000', NULL, 'qwsd', 'CASH', 'COMPLETED', 'szdbckjsdc', '2025-09-09 18:30:00.000000', 456, 2, 3);

-- --------------------------------------------------------

--
-- Table structure for table `branch`
--

CREATE TABLE `branch` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT 'system',
  `created_on` datetime(6) NOT NULL,
  `last_modified_on` datetime(6) NOT NULL,
  `version` bigint(20) NOT NULL DEFAULT 0,
  `address` varchar(255) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT 1,
  `name` varchar(255) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `pincode` varchar(10) DEFAULT NULL,
  `state` varchar(100) DEFAULT NULL,
  `whatsapp_status` varchar(255) NOT NULL,
  `studio_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `branch`
--

INSERT INTO `branch` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `address`, `city`, `is_active`, `name`, `phone`, `pincode`, `state`, `whatsapp_status`, `studio_id`) VALUES
(1, 'system', '2025-08-15 23:36:48.000000', '2025-08-16 15:17:25.000000', 2, 'test', 'test', 0, 'b1', '1234567890', '123456', 't', 'INACTIVE', 2),
(2, 'system', '2025-05-24 23:15:38.000000', '2025-08-26 18:35:06.000000', 1, 'Talavchora Talavchora Talavchora Talavchora Talavchora Talavchora ', 'Chikhli', 1, 'MAIN BRANCH', '9409434932', '396521', 'Gujarat', 'LOGOUT', 2),
(3, 'system', '2025-06-01 20:51:11.000000', '2025-06-01 15:21:11.000000', 0, 'tet', 'hj', 1, 'Test', '1234567890', '678999', 'bjknj', 'LOGOUT', 2),
(4, 'system', '2025-08-10 16:30:29.000000', '2025-08-10 11:00:29.000000', 0, 'twst', 'test', 1, 'MAIN BRANCH', '1234567890', '123456', 'test', 'INACTIVE', 3),
(5, 'system', '2025-08-15 18:09:07.000000', '2025-10-04 16:04:21.000000', 2, 't', 't', 1, 'b4', '1234567890', '123456', 't', 'INACTIVE', 2),
(6, 'system', '2025-08-15 18:09:20.000000', '2025-08-16 15:17:30.000000', 1, 't', 't', 0, 'b5', '1234567890', '123456', 't', 'INACTIVE', 2);

-- --------------------------------------------------------

--
-- Table structure for table `bulk_upload`
--

CREATE TABLE `bulk_upload` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT 'system',
  `created_on` datetime(6) NOT NULL,
  `last_modified_on` datetime(6) NOT NULL,
  `version` bigint(20) NOT NULL DEFAULT 0,
  `completed_at` datetime(6) DEFAULT NULL,
  `entity_type` varchar(255) NOT NULL,
  `error_message` text DEFAULT NULL,
  `failed_records` int(11) NOT NULL,
  `file_name` varchar(255) NOT NULL,
  `file_url` varchar(255) NOT NULL,
  `processed_records` int(11) NOT NULL,
  `status` varchar(255) NOT NULL DEFAULT 'PENDING',
  `successful_records` int(11) NOT NULL,
  `total_records` int(11) NOT NULL,
  `branch_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bulk_upload`
--

INSERT INTO `bulk_upload` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `completed_at`, `entity_type`, `error_message`, `failed_records`, `file_name`, `file_url`, `processed_records`, `status`, `successful_records`, `total_records`, `branch_id`) VALUES
(1, 'system', '2025-08-06 21:59:48.000000', '2025-08-06 16:29:48.000000', 1, '2025-08-06 16:29:48.000000', 'STUDENT', 'Error processing bulk upload: Failed to download file from URL. Response code: 403', 0, 'test.csv', 'https://book-and-manage-invoices.s3.amazonaws.com/test.csv', 0, 'FAILED', 0, 0, 3),
(2, 'system', '2025-08-08 07:18:31.000000', '2025-08-08 01:48:30.000000', 0, NULL, 'STUDENT', NULL, 0, 'test.csv', 'https://book-and-manage-invoices.s3.amazonaws.com/test.csv', 0, 'FAILED', 0, 0, 3),
(3, 'system', '2025-08-09 00:33:52.000000', '2025-08-08 19:03:52.000000', 0, NULL, 'STUDENT', NULL, 0, 'test.csv', 'https://book-and-manage-invoices.s3.amazonaws.com/test.csv', 0, 'FAILED', 0, 0, 3),
(4, 'system', '2025-08-09 00:42:30.000000', '2025-08-08 19:12:30.000000', 0, NULL, 'STUDENT', NULL, 0, 'test.csv', 's3Bucket.fileUrl', 0, 'PENDING', 0, 2, 2),
(5, 'system', '2025-08-09 22:19:06.000000', '2025-08-09 16:49:06.000000', 0, NULL, 'STUDENT', NULL, 0, 'test.csv', 'https://book-and-manage-invoices.s3.amazonaws.com/BulkUpload-MAIN BRANCH.csv', 0, 'PENDING', 0, 2, 2),
(6, 'system', '2025-08-10 13:23:49.000000', '2025-08-10 07:53:49.000000', 1, '2025-08-10 07:53:49.000000', 'STUDENT', 'Error processing bulk upload: Failed to download file from URL. Response code: 505', 0, 'BulkUpload-MAIN BRANCH.csv', 'https://book-and-manage-invoices.s3.amazonaws.com/BulkUpload-MAIN BRANCH.csv', 0, 'FAILED', 0, 0, 2),
(7, 'system', '2025-08-10 13:28:51.000000', '2025-08-10 07:58:51.000000', 1, '2025-08-10 07:58:51.000000', 'INSTRUCTOR', 'Error processing bulk upload: Failed to download file from URL. Response code: 505', 0, 'BulkUpload-MAIN BRANCH.csv', 'https://book-and-manage-invoices.s3.amazonaws.com/BulkUpload-MAIN BRANCH.csv', 0, 'FAILED', 0, 0, 2);

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

CREATE TABLE `client` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT 'system',
  `created_on` datetime(6) NOT NULL,
  `last_modified_on` datetime(6) NOT NULL,
  `version` bigint(20) NOT NULL DEFAULT 0,
  `client_type` varchar(255) NOT NULL,
  `group_name` varchar(255) NOT NULL,
  `notes` text DEFAULT NULL,
  `poc_email` varchar(255) DEFAULT NULL,
  `poc_name` varchar(255) NOT NULL,
  `poc_phone` varchar(10) NOT NULL,
  `branch_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `client_type`, `group_name`, `notes`, `poc_email`, `poc_name`, `poc_phone`, `branch_id`) VALUES
(1, 'system', '2025-06-01 20:34:33.000000', '2025-08-29 19:01:14.000000', 2, 'GROUP', 'Test', 'test', 'dhruv20369@gmail.com', 'Dhruv Patel', '9409434932', 2),
(2, 'system', '2025-09-07 17:52:23.000000', '2025-09-07 18:05:50.000000', 1, 'INDIVIDUAL', 'mukund', '', 'mukundagarwala156@gmail.com', 'mukund', '7326027500', 2),
(3, 'system', '2025-09-07 19:26:32.000000', '2025-09-07 19:26:32.000000', 0, 'GROUP', 'xyz', '', 'bn@m.c ', 'xyz', 'yzgjn', 2),
(4, 'system', '2025-09-11 14:18:29.000000', '2025-09-11 14:18:29.000000', 0, 'GROUP', 'test add', '', 'test add', 'test add', 'test add', 2),
(5, 'system', '2025-10-04 12:27:57.000000', '2025-10-04 12:27:57.000000', 0, 'GROUP', 'test', '', 'test', 'test', 'test', 2);

-- --------------------------------------------------------

--
-- Table structure for table `conditions`
--

CREATE TABLE `conditions` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT 'system',
  `created_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `last_modified_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `version` bigint(20) NOT NULL DEFAULT 0,
  `template_content` text DEFAULT NULL,
  `template_name` varchar(255) NOT NULL,
  `template_subject` varchar(255) NOT NULL,
  `template_type` varchar(255) NOT NULL,
  `branch_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `enquiries`
--

CREATE TABLE `enquiries` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT 'system',
  `created_on` datetime(6) NOT NULL,
  `last_modified_on` datetime(6) NOT NULL,
  `version` bigint(20) NOT NULL DEFAULT 0,
  `contact` varchar(15) NOT NULL,
  `enquiry_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `purpose` varchar(255) NOT NULL,
  `name` varchar(100) NOT NULL,
  `branch_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `enquiries`
--

INSERT INTO `enquiries` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `contact`, `enquiry_date`, `purpose`, `name`, `branch_id`) VALUES
(2, 'system', '2025-08-23 11:59:16.000000', '2025-08-23 11:59:16.000000', 0, 'test', '2025-08-22 18:30:00', 'tezs', 'test', 2),
(3, 'system', '2025-08-23 12:22:43.000000', '2025-10-05 17:36:00.000000', 1, '123456789', '2025-08-22 18:30:00', 'test check', 'test edit by id', 2),
(4, 'system', '2025-08-23 12:42:08.000000', '2025-08-23 12:42:08.000000', 0, '1234567890', '2025-08-22 18:30:00', 'test ', 'DHRUV JITENDRAKUMAR PATEL', 2),
(6, 'system', '2025-08-23 15:58:02.000000', '2025-09-27 17:55:48.000000', 2, '1234567890', '2025-08-23 10:27:51', 'test 23', 'DHRUV JITENDRAKUMAR PATEL', 2),
(7, 'system', '2025-09-27 17:54:55.000000', '2025-09-27 17:54:55.000000', 0, '1234567890', '2025-09-27 12:24:46', 'Talavchora', 'dhruv4023', 2),
(8, 'system', '2025-09-27 17:57:03.000000', '2025-10-09 18:03:14.000000', 1, '3214567890', '2025-09-27 12:26:46', 'my tesing', 'test edit', 2),
(11, 'system', '2025-10-09 18:34:33.000000', '2025-10-09 18:34:33.000000', 0, 'test1', '2025-10-09 13:04:29', 'test1', 'test2', 2),
(12, 'system', '2025-10-10 16:27:35.000000', '2025-10-10 16:27:35.000000', 0, '1234567890', '2025-10-31 10:56:34', 'test', 'dhruv', 2),
(13, 'system', '2025-10-10 16:29:20.000000', '2025-10-10 16:29:20.000000', 0, '456789', '2025-10-22 10:59:09', 't', 'test', 2),
(14, 'system', '2025-10-10 20:13:12.000000', '2025-10-10 20:13:22.000000', 1, 'today', '2025-10-14 14:43:03', '', 'test', 2);

-- --------------------------------------------------------

--
-- Table structure for table `expense`
--

CREATE TABLE `expense` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT 'system',
  `created_on` datetime(6) NOT NULL,
  `last_modified_on` datetime(6) NOT NULL,
  `version` bigint(20) NOT NULL DEFAULT 0,
  `amount` double NOT NULL,
  `description` varchar(255) NOT NULL,
  `expense_category` varchar(255) NOT NULL,
  `expense_date` datetime(6) NOT NULL,
  `branch_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `expense`
--

INSERT INTO `expense` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `amount`, `description`, `expense_category`, `expense_date`, `branch_id`) VALUES
(1, 'system', '2025-06-01 20:35:59.000000', '2025-06-01 15:05:59.000000', 0, 100, 'test', 'ELECTRICITY', '2025-06-01 15:05:49.000000', 2),
(4, 'system', '2025-08-10 17:04:04.000000', '2025-08-10 11:34:04.000000', 0, 500, 'dfgh', 'ELECTRICITY', '2025-08-10 11:33:55.000000', 2),
(5, 'system', '2025-08-10 11:38:54.000000', '2025-08-10 11:38:54.000000', 0, 678, '456', 'ELECTRICITY', '2025-08-10 11:38:48.000000', 2),
(10, 'system', '2025-08-10 14:54:27.000000', '2025-08-10 14:54:27.000000', 0, 456, 'ert', 'ELECTRICITY', '2025-07-31 18:30:00.000000', 2),
(13, 'system', '2025-09-13 09:31:39.000000', '2025-09-13 09:31:39.000000', 0, 5, 'test', 'ELECTRICITY', '2025-09-13 09:31:35.000000', 2),
(14, 'system', '2025-09-13 09:32:32.000000', '2025-09-13 09:32:32.000000', 0, 68, 'fyg', 'ELECTRICITY', '2025-09-13 09:32:07.000000', 2),
(15, 'system', '2025-09-13 09:34:46.000000', '2025-10-11 03:54:28.000000', 2, 70, 'test err', 'MARKETING', '2025-09-13 09:34:39.000000', 2),
(16, 'system', '2025-09-13 09:35:33.000000', '2025-10-02 16:57:11.000000', 1, 70, 'test err', 'ELECTRICITY', '2025-09-13 09:35:22.000000', 2),
(18, 'system', '2025-10-02 16:48:08.000000', '2025-10-02 16:56:15.000000', 1, 101, 'test edit cud', 'ELECTRICITY', '2025-10-02 16:47:59.000000', 2),
(19, 'system', '2025-06-01 20:35:59.000000', '2025-06-01 15:05:59.000000', 0, 0, 'test', 'ELECTRICITY', '2025-06-01 15:05:49.000000', 2),
(20, 'system', '2025-06-02 10:15:00.000000', '2025-06-02 10:15:00.000000', 0, 200, 'Rent Payment', 'RENT', '2025-06-02 10:10:00.000000', 2),
(21, 'system', '2025-06-03 09:45:00.000000', '2025-06-03 09:45:00.000000', 0, 150, 'Stationery', 'SUPPLIES', '2025-06-03 09:40:00.000000', 2),
(22, 'system', '2025-06-04 11:20:00.000000', '2025-06-04 11:20:00.000000', 0, 5000, 'Salary', 'SALARY', '2025-06-04 11:10:00.000000', 2),
(23, 'system', '2025-06-05 13:00:00.000000', '2025-06-05 13:00:00.000000', 0, 1200, 'Electric Bill', 'ELECTRICITY', '2025-06-05 12:50:00.000000', 2),
(24, 'system', '2025-06-06 08:30:00.000000', '2025-06-06 08:30:00.000000', 0, 800, 'Marketing Ads', 'MARKETING', '2025-06-06 08:20:00.000000', 2),
(25, 'system', '2025-06-07 14:45:00.000000', '2025-06-07 14:45:00.000000', 0, 300, 'Cleaning', 'MAINTENANCE', '2025-06-07 14:40:00.000000', 2),
(26, 'system', '2025-06-08 16:10:00.000000', '2025-06-08 16:10:00.000000', 0, 250, 'Water Bill', 'OTHER', '2025-06-08 16:05:00.000000', 2),
(27, 'system', '2025-06-09 12:05:00.000000', '2025-06-09 12:05:00.000000', 0, 700, 'Repair', 'MAINTENANCE', '2025-06-09 12:00:00.000000', 2),
(28, 'system', '2025-06-10 09:15:00.000000', '2025-06-10 09:15:00.000000', 0, 1000, 'Rent Payment', 'RENT', '2025-06-10 09:10:00.000000', 2),
(29, 'system', '2025-06-11 10:30:00.000000', '2025-06-11 10:30:00.000000', 0, 650, 'Stationery', 'SUPPLIES', '2025-06-11 10:25:00.000000', 2),
(30, 'system', '2025-06-12 11:55:00.000000', '2025-06-12 11:55:00.000000', 0, 4800, 'Salary', 'SALARY', '2025-06-12 11:50:00.000000', 2),
(31, 'system', '2025-06-13 15:05:00.000000', '2025-06-13 15:05:00.000000', 0, 1300, 'Electric Bill', 'ELECTRICITY', '2025-06-13 15:00:00.000000', 2),
(32, 'system', '2025-06-14 14:40:00.000000', '2025-06-14 14:40:00.000000', 0, 900, 'Marketing Ads', 'MARKETING', '2025-06-14 14:35:00.000000', 2),
(33, 'system', '2025-06-15 16:25:00.000000', '2025-06-15 16:25:00.000000', 0, 450, 'Cleaning', 'MAINTENANCE', '2025-06-15 16:20:00.000000', 2),
(34, 'system', '2025-06-16 18:10:00.000000', '2025-06-16 18:10:00.000000', 0, 220, 'Water Bill', 'OTHER', '2025-06-16 18:05:00.000000', 2),
(35, 'system', '2025-06-17 08:35:00.000000', '2025-06-17 08:35:00.000000', 0, 600, 'Repair', 'MAINTENANCE', '2025-06-17 08:30:00.000000', 2),
(36, 'system', '2025-06-18 10:50:00.000000', '2025-06-18 10:50:00.000000', 0, 1100, 'Rent Payment', 'RENT', '2025-06-18 10:45:00.000000', 2),
(37, 'system', '2025-06-19 09:25:00.000000', '2025-06-19 09:25:00.000000', 0, 750, 'Stationery', 'SUPPLIES', '2025-06-19 09:20:00.000000', 2),
(38, 'system', '2025-06-20 12:40:00.000000', '2025-06-20 12:40:00.000000', 0, 5100, 'Salary', 'SALARY', '2025-06-20 12:35:00.000000', 2);

-- --------------------------------------------------------

--
-- Table structure for table `forms`
--

CREATE TABLE `forms` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT 'system',
  `created_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `last_modified_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `version` bigint(20) NOT NULL DEFAULT 0,
  `description` text DEFAULT NULL,
  `form_type` enum('INQUIRY','REGISTRATION') NOT NULL,
  `form_url` varchar(255) DEFAULT NULL,
  `google_form_id` varchar(255) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT 1,
  `title` varchar(255) NOT NULL,
  `branch_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `generic_template`
--

CREATE TABLE `generic_template` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT 'system',
  `created_on` datetime(6) NOT NULL,
  `last_modified_on` datetime(6) NOT NULL,
  `version` bigint(20) NOT NULL DEFAULT 0,
  `template_content` text DEFAULT NULL,
  `template_type` varchar(255) NOT NULL,
  `studio_id` bigint(20) NOT NULL,
  `template_subject` varchar(255) NOT NULL,
  `template_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `generic_template`
--

INSERT INTO `generic_template` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `template_content`, `template_type`, `studio_id`, `template_subject`, `template_name`) VALUES
(2, 'system', '2025-08-10 00:04:38.000000', '2025-08-30 10:48:10.000000', 15, 'I, {{instructor_name}}, hereby agree to serve as an Instructor at {{studio_studioName}} starting from {{instructorActivity_startDate}}}}}} to {{instructorActivity_endDate}}. I understand and agree to the following terms and conditions:\n\n1. Conduct classes as per the assigned schedule with dedication and discipline.\n2. Maintain professional behavior toward students, parents, and staff.\n3. Comply with the studio’s curriculum, policies, and dress code.\n4. Protect the confidentiality of student and studio-related information.\n5. Receive payment as mutually agreed by both parties.\n6. Allow either party to terminate this agreement with a 15-day written notice.\n7. Acknowledge that any breach of the terms may result in immediate termination.', 'INSTRUCTOR_CONTRACT_DANCE', 2, 'DANCE', 'Joining Contract'),
(6, 'system', '2025-08-10 14:49:01.000000', '2025-08-10 09:19:01.000000', 0, 'Terms and Conditions\n\nI, {{instructorData.name}}, hereby agree to serve as an Instructor at {{studio.studioName}} starting from {{getLocalDateTime(activityData.startDate)}} until further notice. I understand and agree to the following terms and conditions:\n\n1. Conduct classes as per the assigned schedule with dedication and discipline.\n2. Maintain professional behavior toward students, parents, and staff.\n3. Comply with the studio’s curriculum, policies, and dress code.\n4. Protect the confidentiality of student and studio-related information.\n5. Receive payment as mutually agreed by both parties.\n6. Allow either party to terminate this agreement with a 15-day written notice.\n7. Acknowledge that any breach of the terms may result in immediate termination.\n\nI confirm that the personal and bank details provided above are true and accurate to the best of my knowledge.\n\nInstructor Signature: _________________________\nDate: ____________\n\nAuthorized Studio Representative: _________________________\n{{studio.studioName}}\n{{currentBranch.name}}', 'STUDENT', 3, 'DANCE', 'Dance Instructor Contractor'),
(7, 'system', '2025-08-10 15:38:01.000000', '2025-08-10 10:08:01.000000', 0, 'Dear students,\n\nPlease note that the studio will be closed on {closure_date} due to {reason}. We apologize for any inconvenience.\n\nBest regards,\nStudio Test Team', 'COMMUNICATION', 2, 'Studio Closed Notice', 'Studio Closed Notice'),
(8, 'system', '2025-08-10 16:26:57.000000', '2025-08-30 10:49:08.000000', 2, 'Dance Contract test {{instructorActivity_endDate}}', 'INSTRUCTOR_CONTRACT_ZUMBA', 2, 'Dance COntract', 'Dance'),
(12, 'system', '2025-08-11 16:29:52.000000', '2025-08-11 16:30:09.000000', 1, 'tes', 'COMMUNICATION', 2, 'test', 'test'),
(15, 'system', '2025-08-11 16:31:41.000000', '2025-09-06 10:43:05.000000', 5, 'Herlo {{student_name}}, \n\nThis is test email sent to {{student_email}}.\n\nThanks\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'COMMUNICATION', 2, 'Testing email send to {{student_name}} from {{studio_studioName}}', 'test generic template'),
(17, 'system', '2025-08-25 16:22:03.000000', '2025-09-28 03:14:31.000000', 8, 'Client Responsibility – Any damages caused during the booking will be chargeable to the client.\nForce Majeure – We are not liable for cancellations/delays due to circumstances beyond our control.\nBooking Confirmation – Bookings are confirmed only after receipt of the advance payment.\nPayment Terms – Balance amount must be cleared on or before the service/event date.\nCancellation – Advance payment is non-refundable in case of cancell', 'COMMUNICATION', 2, 'T & C', 'Booking term');

-- --------------------------------------------------------

--
-- Table structure for table `genric_template`
--

CREATE TABLE `genric_template` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT 'system',
  `created_on` datetime(6) NOT NULL,
  `last_modified_on` datetime(6) NOT NULL,
  `version` bigint(20) NOT NULL DEFAULT 0,
  `template_content` text DEFAULT NULL,
  `template_name` varchar(255) NOT NULL,
  `template_subject` varchar(255) NOT NULL,
  `template_type` varchar(255) NOT NULL,
  `studio_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `instructor_activity_assignment`
--

CREATE TABLE `instructor_activity_assignment` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT 'system',
  `created_on` datetime(6) NOT NULL,
  `last_modified_on` datetime(6) NOT NULL,
  `version` bigint(20) NOT NULL DEFAULT 0,
  `activity_name` varchar(100) NOT NULL,
  `assigned_date` datetime(6) DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `start_date` datetime(6) NOT NULL,
  `instructor_id` bigint(20) DEFAULT NULL,
  `contract_document` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `instructor_activity_assignment`
--

INSERT INTO `instructor_activity_assignment` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `activity_name`, `assigned_date`, `end_date`, `start_date`, `instructor_id`, `contract_document`) VALUES
(5, 'system', '2025-08-17 02:26:06.000000', '2025-10-04 18:35:15.000000', 2, 'GYM', '2025-08-17 02:24:02.000000', '2025-08-17 18:30:00.000000', '2025-08-17 02:24:02.000000', 27, 'http://res.cloudinary.com/dvraa4zpz/image/upload/v1759602910/instructor_contract/bf24a243-d5e8-4dca-9641-736a74229266_341477483_172458159034973_6480249691395109525_n.jpg.jpg'),
(6, 'system', '2025-08-17 16:28:50.000000', '2025-08-30 10:37:57.000000', 2, 'ZUMBA', '2025-08-17 16:12:29.000000', '2026-08-28 18:30:00.000000', '2025-08-19 16:12:29.000000', 27, NULL),
(7, 'system', '2025-08-30 10:40:21.000000', '2025-08-30 10:40:21.000000', 0, 'DANCE', '2025-08-30 09:52:19.000000', '2025-08-30 18:30:00.000000', '2025-08-30 09:52:19.000000', 27, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `members`
--

CREATE TABLE `members` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT 'system',
  `created_on` datetime(6) NOT NULL,
  `last_modified_on` datetime(6) NOT NULL,
  `version` bigint(20) NOT NULL DEFAULT 0,
  `address` varchar(1024) DEFAULT NULL,
  `dob` timestamp NULL DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `emergency_contact_number` varchar(10) NOT NULL,
  `member_type` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `profile_image` varchar(255) DEFAULT NULL,
  `branch_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `members`
--

INSERT INTO `members` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `address`, `dob`, `email`, `emergency_contact_number`, `member_type`, `name`, `phone`, `profile_image`, `branch_id`) VALUES
(1, 'system', '2025-05-24 23:27:28.000000', '2025-08-31 06:27:13.000000', 9, '', '2025-08-30 13:00:00', 'dhruv20369@gmail.com', '1234567890', 'STUDENT', 'DHRUV JITENDRAKUMAR PATEL', '9409434932', NULL, 2),
(2, 'system', '2025-06-01 20:52:40.000000', '2025-09-22 16:54:59.000000', 1, 'Talavchora', '2025-06-05 13:00:00', 'dhruv2035@gmail.com', '1234567890', 'STUDENT', 'DHRUV JITENDRAKUMAR PATEL', '9409434932', NULL, 3),
(3, 'system', '2025-06-02 09:30:00.000000', '2025-08-28 17:26:09.000000', 2, '456 Blue Rd, Talavchora', '2003-07-09 18:30:00', 'student3@gmail.com', '', 'INSTRUCTOR', 'Priya Patel', '9234567890', NULL, 2),
(4, 'system', '2025-06-03 11:05:00.000000', '2025-07-16 15:10:12.000000', 3, '789 Yellow Ave, Talavchora', '2008-11-19 18:30:00', 'bookandmanage@gmail.com', '9345678901', 'STUDENT', 'Mukund Agrawal', '7326027500', NULL, 2),
(5, 'system', '2025-06-04 14:50:00.000000', '2025-10-04 16:48:44.000000', 2, '101 Pink Blvd, Talavchora', '2007-01-04 18:30:00', 'student5@gmail.com', '9456789012', 'STUDENT', 'Sneha Desai', '9409434932', NULL, 2),
(6, 'system', '2025-06-05 16:20:00.000000', '2025-10-04 16:49:02.000000', 2, '202 Orange Ln, Talavchora', '2006-08-24 18:30:00', 'student6@gmail.com', '9567890123', 'STUDENT', 'Yash Mehta', '9409434932', NULL, 2),
(7, 'system', '2025-06-06 09:45:00.000000', '2025-07-02 14:45:00.000000', 1, '303 Violet St, Talavchora', '2002-04-16 18:30:00', 'student7@gmail.com', '9678901234', 'STUDENT', 'Pooja Shah', '9678901234', NULL, 2),
(8, 'system', '2025-06-07 08:35:00.000000', '2025-07-02 14:50:00.000000', 1, '404 Indigo Dr, Talavchora', '2009-09-12 18:30:00', 'student8@gmail.com', '9789012345', 'STUDENT', 'Kunal Joshi', '9789012345', NULL, 2),
(9, 'system', '2025-06-08 12:10:00.000000', '2025-10-04 15:45:39.000000', 2, '505 White Cir, Talavchora', '2001-12-29 18:30:00', 'student9@gmail.com', '9890123456', 'STUDENT', 'Isha Verma', '1234567890', NULL, 2),
(10, 'system', '2025-06-09 17:25:00.000000', '2025-07-02 15:00:00.000000', 1, '606 Brown Way, Talavchora', '2004-06-07 18:30:00', 'student10@gmail.com', '9001234567', 'STUDENT', 'Rajiv Kumar', '9001234567', NULL, 2),
(11, 'system', '2025-06-10 13:40:00.000000', '2025-07-02 15:05:00.000000', 1, '707 Gray Rd, Talavchora', '2000-10-21 18:30:00', 'student11@gmail.com', '9112345678', 'STUDENT', 'Neha Reddy', '9112345678', NULL, 2),
(12, 'system', '2025-06-11 09:00:00.000000', '2025-07-02 15:10:00.000000', 1, '808 Silver Ln, Talavchora', '2002-05-13 18:30:00', 'student12@gmail.com', '9223456789', 'STUDENT', 'Divya Nair', '9223456789', NULL, 2),
(13, 'system', '2025-06-12 10:15:00.000000', '2025-07-02 15:15:00.000000', 1, '909 Gold St, Talavchora', '2003-09-22 18:30:00', 'student13@gmail.com', '9334567890', 'STUDENT', 'Harsh Rana', '9334567890', NULL, 2),
(14, 'system', '2025-06-13 11:45:00.000000', '2025-07-02 15:20:00.000000', 1, '111 Ruby Ave, Talavchora', '2005-11-10 18:30:00', 'student14@gmail.com', '9445678901', 'STUDENT', 'Megha Jain', '9445678901', NULL, 2),
(15, 'system', '2025-06-14 14:25:00.000000', '2025-07-02 15:25:00.000000', 1, '222 Emerald Blvd, Talavchora', '2006-01-28 18:30:00', 'student15@gmail.com', '9556789012', 'STUDENT', 'Nikhil Bansal', '9556789012', NULL, 2),
(16, 'system', '2025-06-15 16:55:00.000000', '2025-07-02 15:30:00.000000', 1, '333 Sapphire St, Talavchora', '2007-08-18 18:30:00', 'student16@gmail.com', '9667890123', 'STUDENT', 'Shruti Thakkar', '9667890123', NULL, 2),
(17, 'system', '2025-06-16 08:10:00.000000', '2025-07-02 15:35:00.000000', 1, '444 Topaz Rd, Talavchora', '2008-02-23 18:30:00', 'student17@gmail.com', '9778901234', 'STUDENT', 'Manav Joshi', '9778901234', NULL, 2),
(18, 'system', '2025-06-17 11:40:00.000000', '2025-07-02 15:40:00.000000', 1, '555 Amethyst Ln, Talavchora', '2004-10-11 18:30:00', 'student18@gmail.com', '9889012345', 'STUDENT', 'Riya Soni', '9889012345', NULL, 2),
(19, 'system', '2025-06-18 10:05:00.000000', '2025-07-02 15:45:00.000000', 1, '666 Pearl Cir, Talavchora', '2003-06-29 18:30:00', 'student19@gmail.com', '9990123456', 'STUDENT', 'Jay Patel', '9990123456', NULL, 2),
(20, 'system', '2025-06-19 15:30:00.000000', '2025-07-02 15:50:00.000000', 1, '777 Coral Way, Talavchora', '2002-09-13 18:30:00', 'student20@gmail.com', '9001234568', 'STUDENT', 'Sanya Arora', '9001234568', NULL, 2),
(21, 'system', '2025-06-20 14:10:00.000000', '2025-07-02 15:55:00.000000', 1, '888 Opal St, Talavchora', '2009-01-03 18:30:00', 'student21@gmail.com', '9112345679', 'STUDENT', 'Parth Goyal', '9112345679', NULL, 2),
(22, 'system', '2025-06-21 12:45:00.000000', '2025-07-02 16:00:00.000000', 1, '999 Jade Dr, Talavchora', '2005-12-16 18:30:00', 'student22@gmail.com', '9223456790', 'STUDENT', 'Tanya Mehta', '9223456790', NULL, 2),
(23, 'system', '2025-06-22 10:20:00.000000', '2025-07-02 16:05:00.000000', 1, '1010 Citrine Ln, Talavchora', '2006-03-10 18:30:00', 'student23@gmail.com', '9334567891', 'STUDENT', 'Deepak Rawal', '9334567891', NULL, 2),
(24, 'system', '2025-06-23 11:35:00.000000', '2025-07-02 16:10:00.000000', 1, '1111 Onyx Ave, Talavchora', '2001-07-24 18:30:00', 'student24@gmail.com', '9445678902', 'STUDENT', 'Ankita Solanki', '9445678902', NULL, 2),
(25, 'system', '2025-06-24 09:15:00.000000', '2025-07-02 16:15:00.000000', 1, '1212 Quartz Blvd, Talavchora', '2007-02-01 18:30:00', 'student25@gmail.com', '9556789013', 'STUDENT', 'Rahul Chauhan', '9556789013', NULL, 2),
(26, 'system', '2025-06-01 10:15:00.000000', '2025-07-02 14:20:00.000000', 1, '123 Green St, Talavchora', '2005-03-14 18:30:00', 'student2@gmail.com', '9123456780', 'STUDENT', 'Rohan Sharma', '9123456780', NULL, 2),
(27, 'system', '2025-08-03 20:19:09.000000', '2025-08-03 14:49:08.000000', 0, 'Talavchora', '2025-08-02 13:00:00', 'dhruv20345@gmail.com', '1234567890', 'INSTRUCTOR', 'DHRUV JITENDRAKUMAR PATEL', '9409434934', '', 2),
(28, 'system', '2025-08-10 16:31:47.000000', '2025-08-10 16:31:47.000000', 0, 'Talavchora', '2025-08-05 13:00:00', 'dhruv20345@gmail.com', '1234567890', 'STUDENT', 'xyz', '0940943493', NULL, 2),
(29, 'system', '2025-08-10 16:32:12.000000', '2025-08-10 16:32:12.000000', 0, 'Talavchora', '2025-08-05 13:00:00', 'dhruv2034125@gmail.com', '1234567890', 'STUDENT', 'xyz', '0940943493', NULL, 2),
(30, 'system', '2025-08-10 16:32:54.000000', '2025-08-10 16:32:54.000000', 0, 'Talavchora', '2025-08-05 13:00:00', 'abc123456789@x.x', '1234567890', 'STUDENT', 'xyz', '0940943493', NULL, 2),
(31, 'system', '2025-08-10 16:36:30.000000', '2025-08-10 16:36:30.000000', 0, 'Talavchora', '2025-08-11 13:00:00', 'dhruv20345@gmail.com', '0940943493', 'STUDENT', 'DHRUV JITENDRAKUMAR PATEL', '0940943493', NULL, 2),
(32, 'system', '2025-08-16 15:17:03.000000', '2025-08-16 15:17:03.000000', 0, 'Talavchora', '2025-08-14 13:00:00', 'dhruv2034512@gmail.com', '0940943493', 'INSTRUCTOR', 'DHRUV JITENDRAKUMAR PATEL', '0940943493', '', 1),
(33, 'system', '2025-08-19 01:39:27.000000', '2025-08-19 01:39:27.000000', 0, '', NULL, 'dhruv20saqs345@gmail.com', '', 'STUDENT', 'DHRUV JITENDRAKUMAR PATEL', '0940943493', NULL, 2),
(34, 'system', '2025-08-31 06:06:24.000000', '2025-08-31 06:06:24.000000', 0, '', NULL, 'abzxy50312@gmail.com', '', 'STUDENT', 'Abxyy', '9409434932', NULL, 2);

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE `message` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT 'system',
  `created_on` datetime(6) NOT NULL,
  `last_modified_on` datetime(6) NOT NULL,
  `version` bigint(20) NOT NULL DEFAULT 0,
  `content` text NOT NULL,
  `notification_type` varchar(30) NOT NULL,
  `send_to_all` bit(1) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `branch_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `message`
--

INSERT INTO `message` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `content`, `notification_type`, `send_to_all`, `title`, `branch_id`) VALUES
(1, 'system', '2025-07-16 20:40:34.000000', '2025-07-16 15:10:34.000000', 0, 'Dear students,\n\nPlease note that the studio will be closed on {closure_date} due to {reason}. We apologize for any inconvenience.\n\nBest regards,\nStudio Test Team', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(2, 'system', '2025-07-16 20:41:14.000000', '2025-07-16 15:11:14.000000', 0, 'Hello {student_name},\n\nThis is a reminder that your payment of ₹{amount_due} is pending. Please renew the membership plan to continue the  services\n\nThank you,\nStudio Test', 'WHATSAPP', b'0', 'Payment Reminder', 2),
(3, 'system', '2025-07-16 20:42:03.000000', '2025-07-16 15:12:03.000000', 0, 'Hello {student_name},\n\nThis is a reminder that your payment of ₹{amount_due} is pending. Please renew the membership plan to continue the  services\n\nThank you,\nStudio Test', 'EMAIL', b'0', 'Payment Reminder', 2),
(4, 'system', '2025-08-10 16:53:25.000000', '2025-08-10 11:23:25.000000', 0, 'Dear students,\n\nPlease note that the studio will be closed on {closure_date} due to {reason}. We apologize for any inconvenience.\n\nBest regards,\nStudio Test Team', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(5, 'system', '2025-08-10 16:54:15.000000', '2025-08-10 11:24:15.000000', 0, 'Dear students,\n\nPlease note that the studio will be closed on {closure_date} due to {reason}. We apologize for any inconvenience.\n\nBest regards,\nStudio Test Team', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(6, 'system', '2025-08-10 16:55:08.000000', '2025-08-10 11:25:08.000000', 0, 'Dear students,\n\nPlease note that the studio will be closed on {closure_date} due to {reason}. We apologize for any inconvenience.\n\nBest regards,\nStudio Test Team', 'WHATSAPP', b'0', 'Studio Closed Notice test', 2),
(7, 'system', '2025-08-10 11:39:20.000000', '2025-08-10 11:39:20.000000', 0, 'Dear students,\n\nPlease note that the studio will be closed on {closure_date} due to {reason}. We apologize for any inconvenience.\n\nBest regards,\nStudio Test Team', 'WHATSAPP', b'0', 'Studio Closed Notice test time', 2),
(8, 'system', '2025-08-11 17:16:51.000000', '2025-08-11 17:16:51.000000', 0, 'Dear students,\n\nPlease note that the studio will be closed on {closure_date} due to {reason}. We apologize for any inconvenience.\n\nBest regards,\nStudio Test Team', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(9, 'system', '2025-08-11 17:17:06.000000', '2025-08-11 17:17:06.000000', 0, 'Hello {student_name},\n\nThis is a reminder that your payment of ₹{amount_due} is pending. Please renew the membership plan to continue the  services\n\nThank you,\nStudio Test', 'WHATSAPP', b'0', 'Payment Reminder', 2),
(10, 'system', '2025-08-11 17:18:04.000000', '2025-08-11 17:18:04.000000', 0, 'Dear students,\n\nPlease note that the studio will be closed on {closure_date} due to {reason}. We apologize for any inconvenience.\n\nBest regards,\nStudio Test Team', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(11, 'system', '2025-08-27 17:39:24.000000', '2025-08-27 17:39:24.000000', 0, 'https://book-and-manage-invoices.s3.amazonaws.com/student-invoice.pdf', 'EMAIL', b'0', 'Invoice', 2),
(12, 'system', '2025-08-28 17:22:26.000000', '2025-08-28 17:22:26.000000', 0, 'Dear students,\n\nPlease note that the studio will be closed on {closure_date} due to {reason}. We apologize for any inconvenience.\n\nBest regards,\nStudio Test Team', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(13, 'system', '2025-08-28 17:23:42.000000', '2025-08-28 17:23:42.000000', 0, 'Dear students,\n\nPlease note that the studio will be closed on {closure_date} due to {reason}. We apologize for any inconvenience.\n\nBest regards,\nStudio Test Team', 'WHATSAPP', b'1', 'Studio Closed Notice', 2),
(14, 'system', '2025-08-28 17:27:33.000000', '2025-08-28 17:27:33.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(15, 'system', '2025-08-28 17:35:06.000000', '2025-08-28 17:35:06.000000', 0, 'Dear {name},\n\nPlease note that the studio will be closed on xyz date due to any reason. We apologize for any inconvenience.\n\nBest regards,\nRythmics Dance Studio Team', 'WHATSAPP', b'0', 'Studio Closed Notice', 3),
(16, 'system', '2025-08-28 17:35:22.000000', '2025-08-28 17:35:22.000000', 0, 'Dear {name},\n\nPlease note that the studio will be closed on xyz date due to any reason. We apologize for any inconvenience.\n\nBest regards,\nRythmics Dance Studio Team', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(17, 'system', '2025-08-28 17:39:18.000000', '2025-08-28 17:39:18.000000', 0, 'Dear {name},\\n\\nPlease note that the studio will be closed on xyz date due to any reason. We apologize for any inconvenience.\\n\\nBest regards,\\nRythmics Dance Studio Team', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(18, 'system', '2025-08-28 17:42:52.000000', '2025-08-28 17:42:52.000000', 0, 'Dear {name},\\n\\nPlease note that the studio will be closed on xyz date due to any reason. We apologize for any inconvenience.\\n\\nBest regards,\\nRythmics Dance Studio Team', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(19, 'system', '2025-08-28 17:48:18.000000', '2025-08-28 17:48:18.000000', 0, 'Dear {name},\\n\\nPlease note that the studio will be closed on xyz date due to any reason. We apologize for any inconvenience.\\n\\nBest regards,\\nRythmics Dance Studio Team', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(20, 'system', '2025-08-28 17:52:13.000000', '2025-08-28 17:52:13.000000', 0, 'Dear {name},\\n\\nPlease note that the studio will be closed on xyz date due to any reason. We apologize for any inconvenience.\\n\\nBest regards,\\nRythmics Dance Studio Team', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(21, 'system', '2025-08-28 17:53:58.000000', '2025-08-28 17:53:58.000000', 0, 'Dear {name},\\n\\nPlease note that the studio will be closed on xyz date due to any reason. We apologize for any inconvenience.\\n\\nBest regards,\\nRythmics Dance Studio Team', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(22, 'system', '2025-08-28 17:54:43.000000', '2025-08-28 17:54:43.000000', 0, 'Dear {name},\\n\\nPlease note that the studio will be closed on xyz date due to any reason. We apologize for any inconvenience.\\n\\nBest regards,\\nRythmics Dance Studio Team', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(23, 'system', '2025-08-28 17:55:35.000000', '2025-08-28 17:55:35.000000', 0, 'Dear {name},\\n\\nPlease note that the studio will be closed on xyz date due to any reason. We apologize for any inconvenience.\\n\\nBest regards,\\nRythmics Dance Studio Team', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(24, 'system', '2025-08-28 17:55:44.000000', '2025-08-28 17:55:44.000000', 0, 'Dear {name},\\n\\nPlease note that the studio will be closed on xyz date due to any reason. We apologize for any inconvenience.\\n\\nBest regards,\\nRythmics Dance Studio Team', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(25, 'system', '2025-08-28 17:56:00.000000', '2025-08-28 17:56:00.000000', 0, 'Dear {name},\\n\\nPlease note that the studio will be closed on xyz date due to any reason. We apologize for any inconvenience.\\n\\nBest regards,\\nRythmics Dance Studio Team', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(26, 'system', '2025-08-28 17:56:04.000000', '2025-08-28 17:56:04.000000', 0, 'Dear {name},\\n\\nPlease note that the studio will be closed on xyz date due to any reason. We apologize for any inconvenience.\\n\\nBest regards,\\nRythmics Dance Studio Team', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(27, 'system', '2025-08-28 17:57:58.000000', '2025-08-28 17:57:58.000000', 0, 'Dear {name},\\n\\nPlease note that the studio will be closed on xyz date due to any reason. We apologize for any inconvenience.\\n\\nBest regards,\\nRythmics Dance Studio Team', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(28, 'system', '2025-08-28 17:59:01.000000', '2025-08-28 17:59:01.000000', 0, 'Dear {name},\\n\\nPlease note that the studio will be closed on xyz date due to any reason. We apologize for any inconvenience.\\n\\nBest regards,\\nRythmics Dance Studio Team', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(29, 'system', '2025-08-28 18:00:56.000000', '2025-08-28 18:00:56.000000', 0, 'Dear {name},\\n\\nPlease note that the studio will be closed on xyz date due to any reason. We apologize for any inconvenience.\\n\\nBest regards,\\nRythmics Dance Studio Team', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(30, 'system', '2025-08-28 18:02:03.000000', '2025-08-28 18:02:03.000000', 0, 'Dear {name},\\n\\nPlease note that the studio will be closed on xyz date due to any reason. We apologize for any inconvenience.\\n\\nBest regards,\\nRythmics Dance Studio Team', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(31, 'system', '2025-08-28 18:03:59.000000', '2025-08-28 18:03:59.000000', 0, 'Test 1', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(32, 'system', '2025-08-28 18:04:30.000000', '2025-08-28 18:04:30.000000', 0, 'Test 1', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(33, 'system', '2025-08-28 18:09:56.000000', '2025-08-28 18:09:56.000000', 0, 'Test 1', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(34, 'system', '2025-08-28 18:10:39.000000', '2025-08-28 18:10:39.000000', 0, 'Test 1', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(35, 'system', '2025-08-28 18:13:47.000000', '2025-08-28 18:13:47.000000', 0, 'Test 1', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(36, 'system', '2025-08-28 18:16:04.000000', '2025-08-28 18:16:04.000000', 0, 'Test 1', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(37, 'system', '2025-08-28 18:17:55.000000', '2025-08-28 18:17:55.000000', 0, 'Test 1', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(38, 'system', '2025-08-28 18:20:08.000000', '2025-08-28 18:20:08.000000', 0, 'Test 1', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(39, 'system', '2025-08-28 18:22:54.000000', '2025-08-28 18:22:54.000000', 0, 'Test 1', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(40, 'system', '2025-08-28 18:25:05.000000', '2025-08-28 18:25:05.000000', 0, 'Test 1', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(41, 'system', '2025-08-28 18:36:09.000000', '2025-08-28 18:36:09.000000', 0, 'Test 1', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(42, 'system', '2025-08-28 18:37:30.000000', '2025-08-28 18:37:30.000000', 0, 'Test 1', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(43, 'system', '2025-08-28 18:48:30.000000', '2025-08-28 18:48:30.000000', 0, 'Test 1', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(44, 'system', '2025-08-28 18:49:27.000000', '2025-08-28 18:49:27.000000', 0, 'Test 1', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(45, 'system', '2025-08-28 18:49:37.000000', '2025-08-28 18:49:37.000000', 0, 'Test 1', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(46, 'system', '2025-08-28 18:51:49.000000', '2025-08-28 18:51:49.000000', 0, 'test', 'WHATSAPP', b'0', 'test', 2),
(47, 'system', '2025-08-28 18:59:17.000000', '2025-08-28 18:59:17.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(48, 'system', '2025-08-28 18:59:28.000000', '2025-08-28 18:59:28.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(49, 'system', '2025-08-28 19:07:41.000000', '2025-08-28 19:07:41.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(50, 'system', '2025-08-28 19:08:33.000000', '2025-08-28 19:08:33.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(51, 'system', '2025-08-28 19:11:03.000000', '2025-08-28 19:11:03.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(52, 'system', '2025-08-28 19:11:09.000000', '2025-08-28 19:11:09.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(53, 'system', '2025-08-28 19:13:27.000000', '2025-08-28 19:13:27.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(54, 'system', '2025-08-28 19:13:31.000000', '2025-08-28 19:13:31.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(55, 'system', '2025-08-28 19:13:35.000000', '2025-08-28 19:13:35.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(56, 'system', '2025-08-28 19:15:09.000000', '2025-08-28 19:15:09.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(57, 'system', '2025-08-28 19:15:13.000000', '2025-08-28 19:15:13.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(58, 'system', '2025-08-28 19:17:25.000000', '2025-08-28 19:17:25.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(59, 'system', '2025-08-28 19:17:28.000000', '2025-08-28 19:17:28.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(60, 'system', '2025-08-28 19:17:43.000000', '2025-08-28 19:17:43.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(61, 'system', '2025-08-28 19:22:18.000000', '2025-08-28 19:22:18.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(62, 'system', '2025-08-28 19:22:39.000000', '2025-08-28 19:22:39.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(63, 'system', '2025-08-28 19:23:31.000000', '2025-08-28 19:23:31.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(64, 'system', '2025-08-29 15:16:53.000000', '2025-08-29 15:16:53.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(65, 'system', '2025-08-29 15:17:22.000000', '2025-08-29 15:17:22.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(66, 'system', '2025-08-29 15:20:47.000000', '2025-08-29 15:20:47.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(67, 'system', '2025-08-29 15:22:05.000000', '2025-08-29 15:22:05.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(68, 'system', '2025-08-29 15:44:56.000000', '2025-08-29 15:44:56.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(69, 'system', '2025-08-29 15:45:05.000000', '2025-08-29 15:45:05.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(70, 'system', '2025-08-29 15:45:40.000000', '2025-08-29 15:45:40.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(71, 'system', '2025-08-29 15:47:19.000000', '2025-08-29 15:47:19.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(72, 'system', '2025-08-29 15:52:30.000000', '2025-08-29 15:52:30.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(73, 'system', '2025-08-29 16:03:05.000000', '2025-08-29 16:03:05.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(74, 'system', '2025-08-29 16:55:47.000000', '2025-08-29 16:55:47.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Booking Invoice', 2),
(75, 'system', '2025-08-29 16:56:06.000000', '2025-08-29 16:56:06.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Booking Invoice', 2),
(76, 'system', '2025-08-29 17:21:01.000000', '2025-08-29 17:21:01.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(77, 'system', '2025-08-29 17:21:48.000000', '2025-08-29 17:21:48.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(78, 'system', '2025-08-29 17:23:03.000000', '2025-08-29 17:23:03.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(79, 'system', '2025-08-29 17:25:10.000000', '2025-08-29 17:25:10.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(80, 'system', '2025-08-29 17:25:48.000000', '2025-08-29 17:25:48.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(81, 'system', '2025-08-29 19:00:20.000000', '2025-08-29 19:00:20.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Booking Invoice', 2),
(82, 'system', '2025-08-29 19:01:23.000000', '2025-08-29 19:01:23.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Booking Invoice', 2),
(83, 'system', '2025-08-29 19:03:45.000000', '2025-08-29 19:03:45.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Booking Invoice', 2),
(84, 'system', '2025-08-29 19:04:07.000000', '2025-08-29 19:04:07.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Booking Invoice', 2),
(85, 'system', '2025-08-29 19:05:11.000000', '2025-08-29 19:05:11.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Booking Invoice', 2),
(86, 'system', '2025-08-29 19:05:45.000000', '2025-08-29 19:05:45.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(87, 'system', '2025-08-29 19:30:24.000000', '2025-08-29 19:30:24.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(88, 'system', '2025-08-29 19:31:05.000000', '2025-08-29 19:31:05.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Booking Invoice', 2),
(89, 'system', '2025-08-29 19:32:03.000000', '2025-08-29 19:32:03.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Booking Invoice', 2),
(90, 'system', '2025-08-31 00:25:32.000000', '2025-08-31 00:25:32.000000', 0, 'https://book-and-manage-invoices.s3.amazonaws.com/student-invoice.pdf', 'EMAIL', b'0', 'Invoice', 2),
(91, 'system', '2025-08-31 00:26:33.000000', '2025-08-31 00:26:33.000000', 0, 'https://book-and-manage-invoices.s3.amazonaws.com/student-invoice.pdf', 'EMAIL', b'0', 'Invoice', 2),
(92, 'system', '2025-08-31 00:29:10.000000', '2025-08-31 00:29:10.000000', 0, 's3Bucket.fileUrl', 'EMAIL', b'0', 'Invoice', 2),
(93, 'system', '2025-08-31 00:32:11.000000', '2025-08-31 00:32:11.000000', 0, 's3Bucket.fileUrl', 'EMAIL', b'0', 'Invoice', 2),
(94, 'system', '2025-08-31 00:42:51.000000', '2025-08-31 00:42:51.000000', 0, 'https://book-and-manage-invoices.s3.amazonaws.com/student-invoice.pdf', 'EMAIL', b'0', 'Invoice', 2),
(95, 'system', '2025-08-31 00:44:34.000000', '2025-08-31 00:44:34.000000', 0, 'https://book-and-manage-invoices.s3.amazonaws.com/student-invoice.pdf', 'EMAIL', b'0', 'Invoice', 2),
(96, 'system', '2025-08-31 00:45:40.000000', '2025-08-31 00:45:40.000000', 0, 'https://book-and-manage-invoices.s3.amazonaws.com/student-invoice.pdf', 'EMAIL', b'0', 'Invoice', 2),
(97, 'system', '2025-08-31 00:46:03.000000', '2025-08-31 00:46:03.000000', 0, 'https://book-and-manage-invoices.s3.amazonaws.com/student-invoice.pdf', 'EMAIL', b'0', 'Invoice', 2),
(98, 'system', '2025-08-31 00:46:38.000000', '2025-08-31 00:46:38.000000', 0, 'https://book-and-manage-invoices.s3.amazonaws.com/student-invoice.pdf', 'EMAIL', b'0', 'Invoice', 2),
(99, 'system', '2025-08-31 00:56:59.000000', '2025-08-31 00:56:59.000000', 0, 'https://book-and-manage-invoices.s3.amazonaws.com/student-invoice.pdf', 'EMAIL', b'0', 'Invoice', 2),
(100, 'system', '2025-08-31 00:58:59.000000', '2025-08-31 00:58:59.000000', 0, 's3Bucket.fileUrl', 'EMAIL', b'0', 'Invoice', 2),
(101, 'system', '2025-08-31 01:00:13.000000', '2025-08-31 01:00:13.000000', 0, 's3Bucket.fileUrl', 'EMAIL', b'0', 'Invoice', 2),
(102, 'system', '2025-08-31 01:04:22.000000', '2025-08-31 01:04:22.000000', 0, 's3Bucket.fileUrl', 'EMAIL', b'0', 'Invoice', 2),
(103, 'system', '2025-08-31 01:08:10.000000', '2025-08-31 01:08:10.000000', 0, 's3Bucket.fileUrl', 'EMAIL', b'0', 'Invoice', 2),
(104, 'system', '2025-08-31 01:08:19.000000', '2025-08-31 01:08:19.000000', 0, 's3Bucket.fileUrl', 'EMAIL', b'0', 'Invoice', 2),
(105, 'system', '2025-08-31 01:09:30.000000', '2025-08-31 01:09:30.000000', 0, 's3Bucket.fileUrl', 'EMAIL', b'0', 'Invoice', 2),
(106, 'system', '2025-08-31 01:12:05.000000', '2025-08-31 01:12:05.000000', 0, 's3Bucket.fileUrl', 'EMAIL', b'0', 'Invoice', 2),
(107, 'system', '2025-08-31 01:12:11.000000', '2025-08-31 01:12:11.000000', 0, 's3Bucket.fileUrl', 'EMAIL', b'0', 'Invoice', 2),
(108, 'system', '2025-08-31 01:19:39.000000', '2025-08-31 01:19:39.000000', 0, 's3Bucket.fileUrl', 'EMAIL', b'0', 'Invoice', 2),
(109, 'system', '2025-08-31 01:36:08.000000', '2025-08-31 01:36:08.000000', 0, 's3Bucket.fileUrl', 'EMAIL', b'0', 'Invoice', 2),
(110, 'system', '2025-08-31 01:49:54.000000', '2025-08-31 01:49:54.000000', 0, 's3Bucket.fileUrl', 'EMAIL', b'0', 'Invoice', 2),
(111, 'system', '2025-08-31 01:50:24.000000', '2025-08-31 01:50:24.000000', 0, 's3Bucket.fileUrl', 'EMAIL', b'0', 'Invoice', 2),
(112, 'system', '2025-08-31 01:51:43.000000', '2025-08-31 01:51:43.000000', 0, 's3Bucket.fileUrl', 'EMAIL', b'0', 'Invoice', 2),
(113, 'system', '2025-08-31 04:18:14.000000', '2025-08-31 04:18:14.000000', 0, 'Test 1', 'EMAIL', b'0', 'Studio Closed Notice', 2),
(114, 'system', '2025-08-31 04:18:27.000000', '2025-08-31 04:18:27.000000', 0, 'Test 1', 'EMAIL', b'0', 'Studio Closed Notice', 2),
(115, 'system', '2025-08-31 04:20:03.000000', '2025-08-31 04:20:03.000000', 0, 'Test 1', 'EMAIL', b'0', 'Studio Closed Notice', 2),
(116, 'system', '2025-08-31 04:22:49.000000', '2025-08-31 04:22:49.000000', 0, 'Test 1', 'EMAIL', b'0', 'Studio Closed Notice', 2),
(117, 'system', '2025-08-31 04:24:23.000000', '2025-08-31 04:24:23.000000', 0, 'Test 1', 'EMAIL', b'0', 'Studio Closed Notice', 2),
(118, 'system', '2025-08-31 04:24:47.000000', '2025-08-31 04:24:47.000000', 0, 'Test 1', 'EMAIL', b'0', 'Studio Closed Notice', 2),
(119, 'system', '2025-08-31 04:27:37.000000', '2025-08-31 04:27:37.000000', 0, 'Test 1', 'EMAIL', b'0', 'Studio Closed Notice', 2),
(120, 'system', '2025-08-31 04:27:37.000000', '2025-08-31 04:27:37.000000', 0, 'Test 1', 'EMAIL', b'0', 'Studio Closed Notice', 2),
(121, 'system', '2025-08-31 04:29:59.000000', '2025-08-31 04:29:59.000000', 0, 'Test 1', 'EMAIL', b'0', 'Studio Closed Notice', 2),
(122, 'system', '2025-08-31 04:29:59.000000', '2025-08-31 04:29:59.000000', 0, 'Test 1', 'EMAIL', b'0', 'Studio Closed Notice', 2),
(123, 'system', '2025-08-31 04:33:15.000000', '2025-08-31 04:33:15.000000', 0, 'Test 1', 'EMAIL', b'0', 'Studio Closed Notice', 2),
(124, 'system', '2025-08-31 04:33:23.000000', '2025-08-31 04:33:23.000000', 0, 'Test 1', 'EMAIL', b'0', 'Studio Closed Notice', 2),
(125, 'system', '2025-08-31 04:34:07.000000', '2025-08-31 04:34:07.000000', 0, 'Test 1', 'EMAIL', b'0', 'Studio Closed Notice', 2),
(126, 'system', '2025-08-31 04:34:14.000000', '2025-08-31 04:34:14.000000', 0, 'Test 1', 'EMAIL', b'0', 'Studio Closed Notice', 2),
(127, 'system', '2025-08-31 04:39:06.000000', '2025-08-31 04:39:06.000000', 0, 'Invoice', 'EMAIL', b'0', 'Invoice', 2),
(128, 'system', '2025-08-31 04:39:52.000000', '2025-08-31 04:39:52.000000', 0, 'Invoice', 'EMAIL', b'0', 'Invoice', 2),
(129, 'system', '2025-08-31 04:40:48.000000', '2025-08-31 04:40:48.000000', 0, 'Invoice', 'EMAIL', b'0', 'Invoice', 2),
(130, 'system', '2025-08-31 04:42:35.000000', '2025-08-31 04:42:35.000000', 0, 'Invoice', 'EMAIL', b'0', 'Invoice', 2),
(131, 'system', '2025-08-31 04:42:40.000000', '2025-08-31 04:42:40.000000', 0, 'Invoice', 'EMAIL', b'0', 'Invoice', 2),
(132, 'system', '2025-08-31 04:45:47.000000', '2025-08-31 04:45:47.000000', 0, 'Invoice', 'EMAIL', b'0', 'Booking Invoice', 2),
(133, 'system', '2025-08-31 04:48:02.000000', '2025-08-31 04:48:02.000000', 0, 'Invoice', 'EMAIL', b'0', 'Booking Invoice', 2),
(134, 'system', '2025-08-31 04:48:02.000000', '2025-08-31 04:48:02.000000', 0, 'Invoice', 'EMAIL', b'0', 'Booking Invoice', 2),
(135, 'system', '2025-08-31 05:56:05.000000', '2025-08-31 05:56:05.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'EMAIL', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(136, 'system', '2025-08-31 05:56:05.000000', '2025-08-31 05:56:05.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'EMAIL', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(137, 'system', '2025-08-31 05:56:55.000000', '2025-08-31 05:56:55.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(138, 'system', '2025-08-31 05:58:20.000000', '2025-08-31 05:58:20.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'EMAIL', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(139, 'system', '2025-08-31 05:58:20.000000', '2025-08-31 05:58:20.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'EMAIL', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(140, 'system', '2025-08-31 06:00:35.000000', '2025-08-31 06:00:35.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'EMAIL', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(141, 'system', '2025-08-31 06:00:35.000000', '2025-08-31 06:00:35.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'EMAIL', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(142, 'system', '2025-08-31 06:01:42.000000', '2025-08-31 06:01:42.000000', 0, 'Hello {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'EMAIL', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(143, 'system', '2025-08-31 06:01:42.000000', '2025-08-31 06:01:42.000000', 0, 'Hello {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'EMAIL', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(144, 'system', '2025-08-31 06:03:34.000000', '2025-08-31 06:03:34.000000', 0, 'Hello {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'EMAIL', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}    helloooooooooo', 2),
(145, 'system', '2025-08-31 06:03:34.000000', '2025-08-31 06:03:34.000000', 0, 'Hello {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'EMAIL', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}    helloooooooooo', 2),
(146, 'system', '2025-08-31 06:09:50.000000', '2025-08-31 06:09:50.000000', 0, 'Invoice', 'EMAIL', b'0', 'Invoice', 2),
(147, 'system', '2025-08-31 06:09:50.000000', '2025-08-31 06:09:50.000000', 0, 'Invoice', 'EMAIL', b'0', 'Invoice', 2),
(148, 'system', '2025-09-06 10:32:24.000000', '2025-09-06 10:32:24.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'1', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(149, 'system', '2025-09-06 10:42:45.000000', '2025-09-06 10:42:45.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(150, 'system', '2025-09-06 10:43:23.000000', '2025-09-06 10:43:23.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(151, 'system', '2025-09-06 10:46:53.000000', '2025-09-06 10:46:53.000000', 0, 'Dear students,\r\n\r\nPlease note that the studio will be closed on {closure_date} due to {reason}. We apologize for any inconvenience.\r\n\r\nBest regards,\r\nStudio Test Team', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(152, 'system', '2025-09-06 10:49:13.000000', '2025-09-06 10:49:13.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(153, 'system', '2025-09-06 10:59:03.000000', '2025-09-06 10:59:03.000000', 0, 'Invoice', 'EMAIL', b'0', 'Invoice', 2),
(154, 'system', '2025-09-06 11:03:34.000000', '2025-09-06 11:03:34.000000', 0, 'Invoice', 'EMAIL', b'0', 'Booking Invoice', 2),
(155, 'system', '2025-09-06 11:04:05.000000', '2025-09-06 11:04:05.000000', 0, 'Invoice', 'EMAIL', b'0', 'Booking Invoice', 2),
(156, 'system', '2025-09-06 11:04:23.000000', '2025-09-06 11:04:23.000000', 0, 'Invoice', 'EMAIL', b'0', 'Booking Invoice', 2),
(157, 'system', '2025-09-06 11:07:14.000000', '2025-09-06 11:07:14.000000', 0, 'Invoice', 'EMAIL', b'0', 'Booking Invoice', 2),
(158, 'system', '2025-09-06 11:07:52.000000', '2025-09-06 11:07:52.000000', 0, 'Invoice', 'EMAIL', b'0', 'Booking Invoice', 2),
(159, 'system', '2025-09-06 11:13:04.000000', '2025-09-06 11:13:04.000000', 0, 'Invoice', 'EMAIL', b'0', 'Booking Invoice', 2),
(160, 'system', '2025-09-06 11:14:42.000000', '2025-09-06 11:14:42.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Booking Invoice', 2),
(161, 'system', '2025-09-06 11:14:45.000000', '2025-09-06 11:14:45.000000', 0, 'Invoice', 'EMAIL', b'0', 'Booking Invoice', 2),
(162, 'system', '2025-09-07 17:54:50.000000', '2025-09-07 17:54:50.000000', 0, 'Invoice', 'EMAIL', b'0', 'Booking Invoice', 2),
(163, 'system', '2025-09-07 18:04:41.000000', '2025-09-07 18:04:41.000000', 0, 'Invoice', 'EMAIL', b'0', 'Booking Invoice', 2),
(164, 'system', '2025-09-07 18:06:11.000000', '2025-09-07 18:06:11.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Booking Invoice', 2),
(165, 'system', '2025-09-07 18:06:55.000000', '2025-09-07 18:06:55.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Booking Invoice', 2),
(166, 'system', '2025-09-07 18:30:31.000000', '2025-09-07 18:30:31.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Booking Invoice', 2),
(167, 'system', '2025-09-07 18:52:33.000000', '2025-09-07 18:52:33.000000', 0, 'Invoice', 'EMAIL', b'0', 'Booking Invoice', 2),
(168, 'system', '2025-09-07 18:53:54.000000', '2025-09-07 18:53:54.000000', 0, 'Invoice', 'EMAIL', b'0', 'Booking Invoice', 2),
(169, 'system', '2025-09-07 19:08:40.000000', '2025-09-07 19:08:40.000000', 0, 'Invoice', 'EMAIL', b'0', 'Booking Invoice', 2),
(170, 'system', '2025-09-07 19:10:46.000000', '2025-09-07 19:10:46.000000', 0, 'Invoice', 'EMAIL', b'0', 'Booking Invoice', 2),
(171, 'system', '2025-09-07 19:11:19.000000', '2025-09-07 19:11:19.000000', 0, 'Invoice', 'EMAIL', b'0', 'Booking Invoice', 2),
(172, 'system', '2025-09-07 19:11:37.000000', '2025-09-07 19:11:37.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Booking Invoice', 2),
(173, 'system', '2025-09-07 20:33:51.000000', '2025-09-07 20:33:51.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(174, 'system', '2025-09-07 20:34:50.000000', '2025-09-07 20:34:50.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(175, 'system', '2025-09-07 20:50:03.000000', '2025-09-07 20:50:03.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'EMAIL', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(176, 'system', '2025-09-07 20:50:26.000000', '2025-09-07 20:50:26.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'EMAIL', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(177, 'system', '2025-09-07 20:55:21.000000', '2025-09-07 20:55:21.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'EMAIL', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(178, 'system', '2025-09-07 20:55:36.000000', '2025-09-07 20:55:36.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'EMAIL', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(179, 'system', '2025-09-21 12:13:17.000000', '2025-09-21 12:13:17.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(180, 'system', '2025-09-21 12:32:33.000000', '2025-09-21 12:32:33.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(181, 'system', '2025-09-22 15:09:31.000000', '2025-09-22 15:09:31.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(182, 'system', '2025-09-22 16:55:12.000000', '2025-09-22 16:55:12.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 3),
(183, 'system', '2025-09-23 19:13:56.000000', '2025-09-23 19:13:56.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(184, 'system', '2025-09-23 19:16:09.000000', '2025-09-23 19:16:09.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(185, 'system', '2025-09-23 19:18:11.000000', '2025-09-23 19:18:11.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(186, 'system', '2025-09-23 19:18:54.000000', '2025-09-23 19:18:54.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(187, 'system', '2025-09-24 17:52:24.000000', '2025-09-24 17:52:24.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(188, 'system', '2025-09-24 17:54:53.000000', '2025-09-24 17:54:53.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(189, 'system', '2025-09-24 17:56:13.000000', '2025-09-24 17:56:13.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(190, 'system', '2025-09-24 17:58:37.000000', '2025-09-24 17:58:37.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(191, 'system', '2025-09-24 17:59:59.000000', '2025-09-24 17:59:59.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(192, 'system', '2025-09-24 18:02:17.000000', '2025-09-24 18:02:17.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(193, 'system', '2025-09-24 18:03:31.000000', '2025-09-24 18:03:31.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(194, 'system', '2025-09-24 18:08:06.000000', '2025-09-24 18:08:06.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(195, 'system', '2025-09-24 18:10:24.000000', '2025-09-24 18:10:24.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(196, 'system', '2025-09-24 18:14:40.000000', '2025-09-24 18:14:40.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(197, 'system', '2025-09-24 18:18:53.000000', '2025-09-24 18:18:53.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(198, 'system', '2025-09-24 18:19:34.000000', '2025-09-24 18:19:34.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(199, 'system', '2025-09-24 18:19:49.000000', '2025-09-24 18:19:49.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(200, 'system', '2025-09-24 18:21:30.000000', '2025-09-24 18:21:30.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(201, 'system', '2025-09-24 18:24:31.000000', '2025-09-24 18:24:31.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(202, 'system', '2025-09-24 18:24:51.000000', '2025-09-24 18:24:51.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(203, 'system', '2025-09-24 18:27:48.000000', '2025-09-24 18:27:48.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(204, 'system', '2025-09-24 18:28:16.000000', '2025-09-24 18:28:16.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(205, 'system', '2025-09-24 18:37:15.000000', '2025-09-24 18:37:15.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(206, 'system', '2025-09-24 18:39:21.000000', '2025-09-24 18:39:21.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(207, 'system', '2025-09-24 18:42:23.000000', '2025-09-24 18:42:23.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(208, 'system', '2025-09-24 18:47:26.000000', '2025-09-24 18:47:26.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(209, 'system', '2025-09-24 18:50:13.000000', '2025-09-24 18:50:13.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(210, 'system', '2025-09-24 18:52:33.000000', '2025-09-24 18:52:33.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(211, 'system', '2025-09-24 18:58:31.000000', '2025-09-24 18:58:31.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(212, 'system', '2025-09-24 19:03:22.000000', '2025-09-24 19:03:22.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(213, 'system', '2025-09-24 19:04:25.000000', '2025-09-24 19:04:25.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(214, 'system', '2025-09-24 19:04:44.000000', '2025-09-24 19:04:44.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(215, 'system', '2025-09-24 19:05:55.000000', '2025-09-24 19:05:55.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(216, 'system', '2025-09-24 19:06:14.000000', '2025-09-24 19:06:14.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(217, 'system', '2025-09-24 19:08:43.000000', '2025-09-24 19:08:43.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(218, 'system', '2025-09-24 19:09:01.000000', '2025-09-24 19:09:01.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(219, 'system', '2025-09-24 19:10:51.000000', '2025-09-24 19:10:51.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(220, 'system', '2025-09-24 19:11:09.000000', '2025-09-24 19:11:09.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(229, 'system', '2025-09-26 16:56:20.000000', '2025-09-26 16:56:20.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(230, 'system', '2025-09-26 16:59:16.000000', '2025-09-26 16:59:16.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(231, 'system', '2025-09-26 16:59:50.000000', '2025-09-26 16:59:50.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(232, 'system', '2025-09-26 17:03:00.000000', '2025-09-26 17:03:00.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(233, 'system', '2025-09-26 17:03:17.000000', '2025-09-26 17:03:17.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(234, 'system', '2025-09-26 17:48:42.000000', '2025-09-26 17:48:42.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(235, 'system', '2025-09-26 17:50:03.000000', '2025-09-26 17:50:03.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(236, 'system', '2025-09-26 17:57:48.000000', '2025-09-26 17:57:48.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(237, 'system', '2025-09-26 17:59:33.000000', '2025-09-26 17:59:33.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(238, 'system', '2025-09-26 18:00:41.000000', '2025-09-26 18:00:41.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(239, 'system', '2025-09-26 18:03:09.000000', '2025-09-26 18:03:09.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(240, 'system', '2025-09-26 18:05:42.000000', '2025-09-26 18:05:42.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(241, 'system', '2025-09-26 18:11:12.000000', '2025-09-26 18:11:12.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(242, 'system', '2025-09-26 18:11:40.000000', '2025-09-26 18:11:40.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(243, 'system', '2025-09-26 18:12:41.000000', '2025-09-26 18:12:41.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(244, 'system', '2025-09-26 18:31:21.000000', '2025-09-26 18:31:21.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(245, 'system', '2025-09-26 18:33:03.000000', '2025-09-26 18:33:03.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Booking Invoice', 2),
(246, 'system', '2025-09-26 18:44:48.000000', '2025-09-26 18:44:48.000000', 0, 'tes', 'EMAIL', b'0', 'test', 2),
(247, 'system', '2025-09-26 18:47:23.000000', '2025-09-26 18:47:23.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(252, 'system', '2025-09-26 19:19:52.000000', '2025-09-26 19:19:52.000000', 0, 'tes', 'EMAIL', b'0', 'test', 2),
(253, 'system', '2025-09-26 19:20:47.000000', '2025-09-26 19:20:47.000000', 0, 'tes', 'EMAIL', b'0', 'test', 2),
(254, 'system', '2025-09-26 19:50:49.000000', '2025-09-26 19:50:49.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(255, 'system', '2025-09-26 19:51:07.000000', '2025-09-26 19:51:07.000000', 0, 'tes', 'EMAIL', b'0', 'test', 2),
(256, 'system', '2025-09-26 19:52:45.000000', '2025-09-26 19:52:45.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(257, 'system', '2025-09-26 19:55:46.000000', '2025-09-26 19:55:46.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(258, 'system', '2025-09-26 19:56:54.000000', '2025-09-26 19:56:54.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(259, 'system', '2025-09-26 19:57:27.000000', '2025-09-26 19:57:27.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(260, 'system', '2025-09-27 03:17:37.000000', '2025-09-27 03:17:37.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(261, 'system', '2025-09-27 16:40:08.000000', '2025-09-27 16:40:08.000000', 0, 'tes', 'WHATSAPP', b'1', 'test', 2),
(262, 'system', '2025-09-27 16:41:05.000000', '2025-09-27 16:41:05.000000', 0, 'tes', 'WHATSAPP', b'1', 'test', 2),
(263, 'system', '2025-09-27 16:56:28.000000', '2025-09-27 16:56:28.000000', 0, 'Dear students,\r\n\r\nPlease note that the studio will be closed on {closure_date} due to {reason}. We apologize for any inconvenience.\r\n\r\nBest regards,\r\nStudio Test Team', 'WHATSAPP', b'0', 'Studio Closed Notice', 2),
(264, 'system', '2025-09-27 16:59:01.000000', '2025-09-27 16:59:01.000000', 0, 'Dear students,\r\n\r\nPlease note that the studio will be closed on {closure_date} due to {reason}. We apologize for any inconvenience.\r\n\r\nBest regards,\r\nStudio Test Team', 'EMAIL', b'0', 'Studio Closed Notice', 2),
(265, 'system', '2025-09-27 17:01:19.000000', '2025-09-27 17:01:19.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(266, 'system', '2025-09-27 17:01:30.000000', '2025-09-27 17:01:30.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(267, 'system', '2025-09-27 17:09:45.000000', '2025-09-27 17:09:45.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'EMAIL', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(268, 'system', '2025-09-27 17:12:23.000000', '2025-09-27 17:12:23.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(269, 'system', '2025-09-27 17:14:05.000000', '2025-09-27 17:14:05.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(270, 'system', '2025-09-27 17:14:41.000000', '2025-09-27 17:14:41.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'EMAIL', b'1', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(271, 'system', '2025-09-27 17:15:33.000000', '2025-09-27 17:15:33.000000', 0, 'tes', 'EMAIL', b'1', 'test', 2),
(273, 'system', '2025-09-27 17:25:48.000000', '2025-09-27 17:25:48.000000', 0, 'Invoice', 'EMAIL', b'0', 'Invoice', 2),
(274, 'system', '2025-09-27 17:25:48.000000', '2025-09-27 17:25:48.000000', 0, 'Dear {student_name},\n\nThank you for registering for {activity_name}. PFA the link below for invoice details.\n\nLink -  {invoice_url}\n\nBest regards,\n{studio_name}', 'EMAIL', b'0', 'Membership Invoice Details', 2),
(275, 'system', '2025-09-27 17:28:14.000000', '2025-09-27 17:28:14.000000', 0, 'Invoice', 'EMAIL', b'0', 'Invoice', 2);
INSERT INTO `message` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `content`, `notification_type`, `send_to_all`, `title`, `branch_id`) VALUES
(276, 'system', '2025-09-27 17:28:15.000000', '2025-09-27 17:28:15.000000', 0, 'Dear {student_name},\n\nThank you for registering for {activity_name}. PFA the link below for invoice details.\n\nLink -  {invoice_url}\n\nBest regards,\n{studio_name}', 'EMAIL', b'0', 'Membership Invoice Details', 2),
(277, 'system', '2025-09-27 17:33:47.000000', '2025-09-27 17:33:47.000000', 0, 'Invoice', 'EMAIL', b'0', 'Booking Invoice', 2),
(278, 'system', '2025-09-27 17:33:47.000000', '2025-09-27 17:33:47.000000', 0, 'Dear {student_name},\n\nThank you for appointment booking. The attached documnent below for invoice details.\n\nBest regards,\n{studio_name}', 'EMAIL', b'0', 'Booking Invoice Details', 2),
(279, 'system', '2025-09-27 17:34:58.000000', '2025-09-27 17:34:58.000000', 0, 'Invoice', 'EMAIL', b'0', 'Booking Invoice', 2),
(280, 'system', '2025-09-27 17:34:58.000000', '2025-09-27 17:34:58.000000', 0, 'Dear {student_name},\n\nThank you for appointment booking. The attached documnent below for invoice details.\n\nBest regards,\n{studio_name}', 'EMAIL', b'0', 'Booking Invoice Details', 2),
(283, 'system', '2025-09-27 17:44:30.000000', '2025-09-27 17:44:33.000000', 1, 'Dear mukund,\n\nThank you for appointment booking. The attached documnent below for invoice details.\n\nBest regards,\nStudio Test', 'EMAIL', b'0', 'Booking Invoice', 2),
(284, 'system', '2025-09-27 17:44:58.000000', '2025-09-27 17:44:58.000000', 1, 'Dear Mukund Agrawal,\n\nThank you for registering for {activity_name}. PFA the link below for invoice details.\n\nLink -  {invoice_url}\n\nBest regards,\nStudio Test', 'EMAIL', b'0', 'Invoice', 2),
(285, 'system', '2025-09-27 17:45:00.000000', '2025-09-27 17:45:00.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(287, 'system', '2025-09-28 03:15:57.000000', '2025-09-28 03:15:57.000000', 1, 'Dear mukund,\n\nThank you for appointment booking. The attached documnent below for invoice details.\n\nBest regards,\nStudio Test', 'EMAIL', b'0', 'Booking Invoice', 2),
(288, 'system', '2025-09-28 03:20:09.000000', '2025-09-28 03:20:09.000000', 1, 'Dear mukund,\n\nThank you for appointment booking. The attached documnent below for invoice details.\n\nBest regards,\nStudio Test', 'EMAIL', b'0', 'Booking Invoice', 2),
(289, 'system', '2025-09-28 03:23:18.000000', '2025-09-28 03:23:18.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Booking Invoice', 2),
(291, 'system', '2025-09-28 03:24:52.000000', '2025-09-28 03:24:59.000000', 1, 'Dear Dhruv Patel,\n\nThank you for appointment booking. The attached documnent below for invoice details.\n\nBest regards,\nStudio Test', 'EMAIL', b'0', 'Booking Invoice', 2),
(292, 'system', '2025-09-28 09:22:24.000000', '2025-09-28 09:22:24.000000', 1, 'Dear DHRUV JITENDRAKUMAR PATEL,\n\nThank you for registering for {activity_name}. PFA the link below for invoice details.\n\nLink -  {invoice_url}\n\nBest regards,\nStudio Test', 'EMAIL', b'0', 'Invoice', 2),
(293, 'system', '2025-09-28 09:27:45.000000', '2025-09-28 09:27:52.000000', 1, 'Dear DHRUV JITENDRAKUMAR PATEL,\n\nThank you for registering for {activity_name}. PFA the link below for invoice details.\n\nLink -  {invoice_url}\n\nBest regards,\nStudio Test', 'EMAIL', b'0', 'Invoice', 2),
(294, 'system', '2025-09-28 17:36:51.000000', '2025-09-28 17:36:51.000000', 0, 'Client Responsibility – Any damages caused during the booking will be chargeable to the client.\r\nForce Majeure – We are not liable for cancellations/delays due to circumstances beyond our control.\r\nBooking Confirmation – Bookings are confirmed only after receipt of the advance payment.\r\nPayment Terms – Balance amount must be cleared on or before the service/event date.\r\nCancellation – Advance payment is non-refundable in case of cancell', 'WHATSAPP', b'0', 'T & C', 2),
(295, 'system', '2025-10-04 16:44:36.000000', '2025-10-04 16:44:36.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(296, 'system', '2025-10-04 16:45:27.000000', '2025-10-04 16:45:27.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(297, 'system', '2025-10-04 16:46:25.000000', '2025-10-04 16:46:25.000000', 0, 'Client Responsibility – Any damages caused during the booking will be chargeable to the client.\r\nForce Majeure – We are not liable for cancellations/delays due to circumstances beyond our control.\r\nBooking Confirmation – Bookings are confirmed only after receipt of the advance payment.\r\nPayment Terms – Balance amount must be cleared on or before the service/event date.\r\nCancellation – Advance payment is non-refundable in case of cancell', 'WHATSAPP', b'0', 'T & C', 2),
(298, 'system', '2025-10-04 16:49:26.000000', '2025-10-04 16:49:26.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(299, 'system', '2025-10-04 16:50:46.000000', '2025-10-04 16:50:46.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(300, 'system', '2025-10-04 16:52:44.000000', '2025-10-04 16:52:44.000000', 0, 'tes', 'WHATSAPP', b'0', 'test', 2),
(301, 'system', '2025-10-04 16:54:57.000000', '2025-10-04 16:54:57.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(302, 'system', '2025-10-04 16:57:13.000000', '2025-10-04 16:57:13.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(303, 'system', '2025-10-04 16:58:03.000000', '2025-10-04 16:58:03.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(304, 'system', '2025-10-04 17:02:34.000000', '2025-10-04 17:02:34.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(305, 'system', '2025-10-04 17:09:40.000000', '2025-10-04 17:09:40.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(306, 'system', '2025-10-11 09:37:59.000000', '2025-10-11 09:37:59.000000', 0, 'Your invoice', 'WHATSAPP', b'0', 'Invoice', 2),
(307, 'system', '2025-10-11 09:48:13.000000', '2025-10-11 09:48:13.000000', 0, 'Herlo {{student_name}}, \r\n\r\nThis is test email sent to {{student_email}}.\r\n\r\nThanks\r\nBest regards from {{branch_name}} powered by {{studio_studioName}}.', 'WHATSAPP', b'0', 'Testing email send to {{student_name}} from {{studio_studioName}}', 2),
(308, 'system', '2025-10-11 09:55:11.000000', '2025-10-11 09:55:11.000000', 0, 'Client Responsibility – Any damages caused during the booking will be chargeable to the client.\r\nForce Majeure – We are not liable for cancellations/delays due to circumstances beyond our control.\r\nBooking Confirmation – Bookings are confirmed only after receipt of the advance payment.\r\nPayment Terms – Balance amount must be cleared on or before the service/event date.\r\nCancellation – Advance payment is non-refundable in case of cancell', 'WHATSAPP', b'0', 'T & C', 2);

-- --------------------------------------------------------

--
-- Table structure for table `message_queue`
--

CREATE TABLE `message_queue` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT 'system',
  `created_on` datetime(6) NOT NULL,
  `last_modified_on` datetime(6) NOT NULL,
  `version` bigint(20) NOT NULL DEFAULT 0,
  `file_path` varchar(500) DEFAULT NULL,
  `notification_type` enum('EMAIL','WHATSAPP') NOT NULL,
  `retries` int(11) NOT NULL,
  `branch_id` bigint(20) NOT NULL,
  `member_id` bigint(20) NOT NULL,
  `message_id` bigint(20) NOT NULL,
  `recipient_id` bigint(20) DEFAULT NULL,
  `studio_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `message_recipient`
--

CREATE TABLE `message_recipient` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT 'system',
  `created_on` datetime(6) NOT NULL,
  `last_modified_on` datetime(6) NOT NULL,
  `version` bigint(20) NOT NULL DEFAULT 0,
  `contact` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `status` enum('FAILED','PENDING','SENT') NOT NULL,
  `message_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `message_recipient`
--

INSERT INTO `message_recipient` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `contact`, `name`, `reason`, `status`, `message_id`) VALUES
(2, 'system', '2025-09-26 19:19:52.000000', '2025-09-26 19:20:48.000000', 1, 'dhruv20369@gmail.com', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 252),
(3, 'system', '2025-09-26 19:19:52.000000', '2025-09-26 19:20:51.000000', 1, 'bookandmanage@gmail.com', 'Mukund Agrawal', NULL, 'SENT', 252),
(4, 'system', '2025-09-26 19:20:48.000000', '2025-09-26 19:20:58.000000', 1, 'student3@gmail.com', 'Priya Patel', NULL, 'SENT', 253),
(8, 'system', '2025-09-26 19:51:07.000000', '2025-09-26 19:51:07.000000', 1, 'dhruv20369@gmail.com', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 255),
(9, 'system', '2025-09-26 19:51:07.000000', '2025-09-26 19:51:14.000000', 1, 'student3@gmail.com', 'Priya Patel', NULL, 'SENT', 255),
(10, 'system', '2025-09-26 19:51:07.000000', '2025-09-26 19:51:21.000000', 1, 'bookandmanage@gmail.com', 'Mukund Agrawal', NULL, 'SENT', 255),
(13, 'system', '2025-09-26 19:55:46.000000', '2025-09-26 19:55:46.000000', 1, '9409434932', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 257),
(14, 'system', '2025-09-26 19:55:46.000000', '2025-09-26 19:55:57.000000', 1, '7326027500', 'Mukund Agrawal', NULL, 'SENT', 257),
(15, 'system', '2025-09-26 19:56:54.000000', '2025-09-26 19:56:54.000000', 1, '9409434932', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 258),
(16, 'system', '2025-09-26 19:56:54.000000', '2025-09-26 19:57:27.000000', 1, '9234567890', 'Priya Patel', NULL, 'SENT', 258),
(17, 'system', '2025-09-26 19:56:54.000000', '2025-09-26 19:57:35.000000', 1, '7326027500', 'Mukund Agrawal', NULL, 'SENT', 258),
(18, 'system', '2025-09-26 19:56:54.000000', '2025-09-26 19:57:45.000000', 1, '9456789012', 'Sneha Desai', NULL, 'SENT', 258),
(19, 'system', '2025-09-26 19:56:54.000000', '2025-09-26 19:57:58.000000', 1, '9567890123', 'Yash Mehta', NULL, 'SENT', 258),
(20, 'system', '2025-09-26 19:56:54.000000', '2025-09-26 19:58:14.000000', 1, '9678901234', 'Pooja Shah', NULL, 'SENT', 258),
(21, 'system', '2025-09-26 19:56:54.000000', '2025-09-26 19:58:32.000000', 1, '9789012345', 'Kunal Joshi', NULL, 'SENT', 258),
(22, 'system', '2025-09-26 19:57:27.000000', '2025-09-26 19:58:49.000000', 1, '9409434932', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 259),
(23, 'system', '2025-09-27 03:17:37.000000', '2025-09-27 03:18:49.000000', 1, '9409434932', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 260),
(24, 'system', '2025-09-27 03:17:37.000000', '2025-09-27 03:19:03.000000', 1, '7326027500', 'Mukund Agrawal', NULL, 'SENT', 260),
(25, 'system', '2025-09-27 16:40:08.000000', '2025-09-27 16:40:08.000000', 1, '9409434932', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 261),
(26, 'system', '2025-09-27 16:40:08.000000', '2025-09-27 16:40:17.000000', 1, '9234567890', 'Priya Patel', NULL, 'SENT', 261),
(27, 'system', '2025-09-27 16:40:08.000000', '2025-09-27 16:40:27.000000', 1, '7326027500', 'Mukund Agrawal', NULL, 'SENT', 261),
(28, 'system', '2025-09-27 16:40:08.000000', '2025-09-27 16:40:47.000000', 1, '9456789012', 'Sneha Desai', NULL, 'SENT', 261),
(29, 'system', '2025-09-27 16:40:08.000000', '2025-09-27 16:40:59.000000', 1, '9567890123', 'Yash Mehta', NULL, 'SENT', 261),
(30, 'system', '2025-09-27 16:40:08.000000', '2025-09-27 16:41:13.000000', 1, '9678901234', 'Pooja Shah', NULL, 'SENT', 261),
(31, 'system', '2025-09-27 16:40:08.000000', '2025-09-27 16:41:24.000000', 1, '9789012345', 'Kunal Joshi', NULL, 'SENT', 261),
(32, 'system', '2025-09-27 16:40:08.000000', '2025-09-27 16:41:45.000000', 1, '9890123456', 'Isha Verma', NULL, 'SENT', 261),
(33, 'system', '2025-09-27 16:40:08.000000', '2025-09-27 16:42:02.000000', 1, '9001234567', 'Rajiv Kumar', NULL, 'SENT', 261),
(34, 'system', '2025-09-27 16:40:08.000000', '2025-09-27 16:42:12.000000', 1, '9112345678', 'Neha Reddy', NULL, 'SENT', 261),
(35, 'system', '2025-09-27 16:40:08.000000', '2025-09-27 16:42:28.000000', 1, '9223456789', 'Divya Nair', NULL, 'SENT', 261),
(36, 'system', '2025-09-27 16:40:08.000000', '2025-09-27 16:42:50.000000', 1, '9334567890', 'Harsh Rana', NULL, 'SENT', 261),
(37, 'system', '2025-09-27 16:40:08.000000', '2025-09-27 16:43:07.000000', 1, '9445678901', 'Megha Jain', NULL, 'SENT', 261),
(38, 'system', '2025-09-27 16:40:08.000000', '2025-09-27 16:43:21.000000', 1, '9556789012', 'Nikhil Bansal', NULL, 'SENT', 261),
(39, 'system', '2025-09-27 16:40:08.000000', '2025-09-27 16:43:33.000000', 1, '9667890123', 'Shruti Thakkar', NULL, 'SENT', 261),
(40, 'system', '2025-09-27 16:40:08.000000', '2025-09-27 16:43:44.000000', 1, '9778901234', 'Manav Joshi', NULL, 'SENT', 261),
(41, 'system', '2025-09-27 16:40:08.000000', '2025-09-27 16:43:51.000000', 1, '9889012345', 'Riya Soni', NULL, 'SENT', 261),
(42, 'system', '2025-09-27 16:40:08.000000', '2025-09-27 16:44:02.000000', 1, '9990123456', 'Jay Patel', NULL, 'SENT', 261),
(43, 'system', '2025-09-27 16:40:08.000000', '2025-09-27 16:44:21.000000', 1, '9001234568', 'Sanya Arora', NULL, 'SENT', 261),
(44, 'system', '2025-09-27 16:40:08.000000', '2025-09-27 16:44:39.000000', 1, '9112345679', 'Parth Goyal', NULL, 'SENT', 261),
(45, 'system', '2025-09-27 16:40:08.000000', '2025-09-27 16:44:48.000000', 1, '9223456790', 'Tanya Mehta', NULL, 'SENT', 261),
(46, 'system', '2025-09-27 16:40:08.000000', '2025-09-27 16:45:06.000000', 1, '9334567891', 'Deepak Rawal', NULL, 'SENT', 261),
(47, 'system', '2025-09-27 16:40:08.000000', '2025-09-27 16:45:22.000000', 1, '9445678902', 'Ankita Solanki', NULL, 'SENT', 261),
(48, 'system', '2025-09-27 16:40:08.000000', '2025-09-27 16:45:34.000000', 1, '9556789013', 'Rahul Chauhan', NULL, 'SENT', 261),
(49, 'system', '2025-09-27 16:40:08.000000', '2025-09-27 16:45:49.000000', 1, '9123456780', 'Rohan Sharma', NULL, 'SENT', 261),
(50, 'system', '2025-09-27 16:40:08.000000', '2025-09-27 16:45:56.000000', 1, '0940943493', 'xyz', NULL, 'SENT', 261),
(51, 'system', '2025-09-27 16:40:08.000000', '2025-09-27 16:46:11.000000', 1, '0940943493', 'xyz', NULL, 'SENT', 261),
(52, 'system', '2025-09-27 16:40:08.000000', '2025-09-27 16:46:31.000000', 1, '0940943493', 'xyz', NULL, 'SENT', 261),
(53, 'system', '2025-09-27 16:40:08.000000', '2025-09-27 16:46:42.000000', 1, '0940943493', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 261),
(54, 'system', '2025-09-27 16:40:08.000000', '2025-09-27 16:47:03.000000', 1, '0940943493', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 261),
(55, 'system', '2025-09-27 16:40:08.000000', '2025-09-27 16:47:14.000000', 1, '9409434932', 'Abxyy', NULL, 'SENT', 261),
(56, 'system', '2025-09-27 16:41:05.000000', '2025-09-27 16:47:35.000000', 1, '9234567890', 'Priya Patel', NULL, 'SENT', 262),
(57, 'system', '2025-09-27 16:41:05.000000', '2025-09-27 16:47:54.000000', 1, '9409434934', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 262),
(58, 'system', '2025-09-27 16:56:28.000000', '2025-09-27 16:56:28.000000', 1, '9409434932', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 263),
(59, 'system', '2025-09-27 16:56:28.000000', '2025-09-27 16:56:50.000000', 1, '7326027500', 'Mukund Agrawal', NULL, 'SENT', 263),
(60, 'system', '2025-09-27 16:59:01.000000', '2025-09-27 16:59:02.000000', 1, 'dhruv20369@gmail.com', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 264),
(61, 'system', '2025-09-27 16:59:01.000000', '2025-09-27 16:59:07.000000', 1, 'bookandmanage@gmail.com', 'Mukund Agrawal', NULL, 'SENT', 264),
(62, 'system', '2025-09-27 17:01:19.000000', '2025-09-27 17:01:19.000000', 1, '9409434932', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 265),
(63, 'system', '2025-09-27 17:01:30.000000', '2025-09-27 17:01:30.000000', 1, '9409434932', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 266),
(64, 'system', '2025-09-27 17:09:45.000000', '2025-09-27 17:09:45.000000', 1, 'bookandmanage@gmail.com', 'Mukund Agrawal', NULL, 'SENT', 267),
(65, 'system', '2025-09-27 17:12:23.000000', '2025-09-27 17:12:23.000000', 1, '9409434932', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 268),
(66, 'system', '2025-09-27 17:12:23.000000', '2025-09-27 17:12:32.000000', 1, '7326027500', 'Mukund Agrawal', NULL, 'SENT', 268),
(67, 'system', '2025-09-27 17:14:05.000000', '2025-09-27 17:14:05.000000', 1, '9409434932', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 269),
(68, 'system', '2025-09-27 17:14:41.000000', '2025-09-27 17:14:41.000000', 1, 'student3@gmail.com', 'Priya Patel', NULL, 'SENT', 270),
(69, 'system', '2025-09-27 17:14:41.000000', '2025-09-27 17:14:44.000000', 1, 'dhruv20345@gmail.com', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 270),
(70, 'system', '2025-09-27 17:15:33.000000', '2025-09-27 17:15:33.000000', 1, 'dhruv20369@gmail.com', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 271),
(71, 'system', '2025-09-27 17:15:33.000000', '2025-09-27 17:15:37.000000', 1, 'bookandmanage@gmail.com', 'Mukund Agrawal', NULL, 'SENT', 271),
(72, 'system', '2025-09-27 17:15:33.000000', '2025-09-27 17:15:39.000000', 1, 'student5@gmail.com', 'Sneha Desai', NULL, 'SENT', 271),
(73, 'system', '2025-09-27 17:15:33.000000', '2025-09-27 17:15:41.000000', 1, 'student6@gmail.com', 'Yash Mehta', NULL, 'SENT', 271),
(74, 'system', '2025-09-27 17:15:33.000000', '2025-09-27 17:15:45.000000', 1, 'student7@gmail.com', 'Pooja Shah', NULL, 'SENT', 271),
(75, 'system', '2025-09-27 17:15:33.000000', '2025-09-27 17:15:51.000000', 1, 'student8@gmail.com', 'Kunal Joshi', NULL, 'SENT', 271),
(76, 'system', '2025-09-27 17:15:33.000000', '2025-09-27 17:15:57.000000', 1, 'student9@gmail.com', 'Isha Verma', NULL, 'SENT', 271),
(77, 'system', '2025-09-27 17:15:33.000000', '2025-09-27 17:16:02.000000', 1, 'student10@gmail.com', 'Rajiv Kumar', NULL, 'SENT', 271),
(78, 'system', '2025-09-27 17:15:33.000000', '2025-09-27 17:16:05.000000', 1, 'student11@gmail.com', 'Neha Reddy', NULL, 'SENT', 271),
(79, 'system', '2025-09-27 17:15:33.000000', '2025-09-27 17:16:08.000000', 1, 'student12@gmail.com', 'Divya Nair', NULL, 'SENT', 271),
(80, 'system', '2025-09-27 17:15:33.000000', '2025-09-27 17:16:11.000000', 1, 'student13@gmail.com', 'Harsh Rana', NULL, 'SENT', 271),
(81, 'system', '2025-09-27 17:15:33.000000', '2025-09-27 17:16:14.000000', 1, 'student14@gmail.com', 'Megha Jain', NULL, 'SENT', 271),
(82, 'system', '2025-09-27 17:15:33.000000', '2025-09-27 17:16:18.000000', 1, 'student15@gmail.com', 'Nikhil Bansal', NULL, 'SENT', 271),
(83, 'system', '2025-09-27 17:15:33.000000', '2025-09-27 17:16:22.000000', 1, 'student16@gmail.com', 'Shruti Thakkar', NULL, 'SENT', 271),
(84, 'system', '2025-09-27 17:15:33.000000', '2025-09-27 17:16:27.000000', 1, 'student17@gmail.com', 'Manav Joshi', NULL, 'SENT', 271),
(85, 'system', '2025-09-27 17:15:33.000000', '2025-09-27 17:16:31.000000', 1, 'student18@gmail.com', 'Riya Soni', NULL, 'SENT', 271),
(86, 'system', '2025-09-27 17:15:33.000000', '2025-09-27 17:16:35.000000', 1, 'student19@gmail.com', 'Jay Patel', NULL, 'SENT', 271),
(87, 'system', '2025-09-27 17:15:33.000000', '2025-09-27 17:16:38.000000', 1, 'student20@gmail.com', 'Sanya Arora', NULL, 'SENT', 271),
(88, 'system', '2025-09-27 17:15:33.000000', '2025-09-27 17:16:42.000000', 1, 'student21@gmail.com', 'Parth Goyal', NULL, 'SENT', 271),
(89, 'system', '2025-09-27 17:15:33.000000', '2025-09-27 17:16:44.000000', 1, 'student22@gmail.com', 'Tanya Mehta', NULL, 'SENT', 271),
(90, 'system', '2025-09-27 17:15:33.000000', '2025-09-27 17:16:50.000000', 1, 'student23@gmail.com', 'Deepak Rawal', NULL, 'SENT', 271),
(91, 'system', '2025-09-27 17:15:33.000000', '2025-09-27 17:16:57.000000', 1, 'student24@gmail.com', 'Ankita Solanki', NULL, 'SENT', 271),
(92, 'system', '2025-09-27 17:15:33.000000', '2025-09-27 17:17:03.000000', 1, 'student25@gmail.com', 'Rahul Chauhan', NULL, 'SENT', 271),
(93, 'system', '2025-09-27 17:15:33.000000', '2025-09-27 17:17:09.000000', 1, 'student2@gmail.com', 'Rohan Sharma', NULL, 'SENT', 271),
(94, 'system', '2025-09-27 17:15:33.000000', '2025-09-27 17:17:12.000000', 1, 'dhruv20345@gmail.com', 'xyz', NULL, 'SENT', 271),
(95, 'system', '2025-09-27 17:15:33.000000', '2025-09-27 17:17:15.000000', 1, 'dhruv2034125@gmail.com', 'xyz', NULL, 'SENT', 271),
(96, 'system', '2025-09-27 17:15:33.000000', '2025-09-27 17:17:18.000000', 1, 'abc123456789@x.x', 'xyz', NULL, 'SENT', 271),
(97, 'system', '2025-09-27 17:15:33.000000', '2025-09-27 17:17:22.000000', 1, 'dhruv20345@gmail.com', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 271),
(98, 'system', '2025-09-27 17:15:33.000000', '2025-09-27 17:17:25.000000', 1, 'dhruv20saqs345@gmail.com', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 271),
(99, 'system', '2025-09-27 17:15:33.000000', '2025-09-27 17:17:31.000000', 1, 'abzxy50312@gmail.com', 'Abxyy', NULL, 'SENT', 271),
(100, 'system', '2025-09-27 17:25:48.000000', '2025-09-27 17:25:55.000000', 1, 'dhruv20369@gmail.com', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 274),
(101, 'system', '2025-09-27 17:28:15.000000', '2025-09-27 17:28:15.000000', 1, 'dhruv20369@gmail.com', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 276),
(102, 'system', '2025-09-27 17:33:47.000000', '2025-09-27 17:33:47.000000', 1, '7326027500', 'mukund', NULL, 'SENT', 278),
(103, 'system', '2025-09-27 17:34:58.000000', '2025-09-27 17:34:58.000000', 1, '9409434932', 'Dhruv Patel', NULL, 'SENT', 280),
(105, 'system', '2025-09-27 17:44:33.000000', '2025-09-27 17:44:33.000000', 1, '7326027500', 'mukund', NULL, 'SENT', 283),
(106, 'system', '2025-09-27 17:44:58.000000', '2025-09-27 17:44:58.000000', 1, 'bookandmanage@gmail.com', 'Mukund Agrawal', NULL, 'SENT', 284),
(107, 'system', '2025-09-27 17:45:00.000000', '2025-09-27 17:45:01.000000', 1, '7326027500', 'Mukund Agrawal', NULL, 'SENT', 285),
(109, 'system', '2025-09-28 03:15:57.000000', '2025-09-28 03:15:57.000000', 1, '7326027500', 'mukund', NULL, 'SENT', 287),
(110, 'system', '2025-09-28 03:20:09.000000', '2025-09-28 03:20:09.000000', 1, '7326027500', 'mukund', NULL, 'SENT', 288),
(111, 'system', '2025-09-28 03:23:18.000000', '2025-09-28 03:23:18.000000', 1, '7326027500', 'mukund', NULL, 'SENT', 289),
(113, 'system', '2025-09-28 03:24:52.000000', '2025-09-28 03:24:59.000000', 1, '9409434932', 'Dhruv Patel', NULL, 'SENT', 291),
(114, 'system', '2025-09-28 09:22:24.000000', '2025-09-28 09:22:24.000000', 1, 'dhruv20369@gmail.com', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 292),
(115, 'system', '2025-09-28 09:27:45.000000', '2025-09-28 09:27:52.000000', 1, 'dhruv20369@gmail.com', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 293),
(116, 'system', '2025-09-28 17:36:51.000000', '2025-09-28 17:36:53.000000', 1, '9409434932', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 294),
(117, 'system', '2025-10-04 16:44:36.000000', '2025-10-04 16:44:43.000000', 1, '9409434932', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 295),
(118, 'system', '2025-10-04 16:45:27.000000', '2025-10-04 16:45:32.000000', 1, '9409434932', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 296),
(119, 'system', '2025-10-04 16:46:25.000000', '2025-10-04 16:46:30.000000', 1, '9409434932', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 297),
(120, 'system', '2025-10-04 16:49:26.000000', '2025-10-04 16:49:30.000000', 1, '9409434932', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 298),
(121, 'system', '2025-10-04 16:49:26.000000', '2025-10-04 16:49:51.000000', 1, '9409434932', 'Sneha Desai', NULL, 'SENT', 298),
(122, 'system', '2025-10-04 16:49:26.000000', '2025-10-04 16:50:01.000000', 1, '9409434932', 'Yash Mehta', NULL, 'SENT', 298),
(123, 'system', '2025-10-04 16:50:46.000000', '2025-10-04 16:50:49.000000', 1, '9409434932', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 299),
(124, 'system', '2025-10-04 16:50:46.000000', '2025-10-04 16:51:13.000000', 1, '9409434932', 'Sneha Desai', NULL, 'SENT', 299),
(125, 'system', '2025-10-04 16:50:46.000000', '2025-10-04 16:51:32.000000', 1, '9409434932', 'Yash Mehta', NULL, 'SENT', 299),
(126, 'system', '2025-10-04 16:52:44.000000', '2025-10-04 16:52:47.000000', 1, '9409434932', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 300),
(127, 'system', '2025-10-04 16:54:57.000000', '2025-10-04 16:55:00.000000', 1, '9409434932', 'Sneha Desai', NULL, 'SENT', 301),
(128, 'system', '2025-10-04 16:54:57.000000', '2025-10-04 16:55:25.000000', 1, '9409434932', 'Yash Mehta', NULL, 'SENT', 301),
(129, 'system', '2025-10-04 16:57:13.000000', '2025-10-04 16:57:17.000000', 1, '9409434932', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 302),
(130, 'system', '2025-10-04 16:57:13.000000', '2025-10-04 16:57:28.000000', 1, '9409434932', 'Sneha Desai', NULL, 'SENT', 302),
(131, 'system', '2025-10-04 16:57:13.000000', '2025-10-04 16:58:05.000000', 1, '9409434932', 'Yash Mehta', NULL, 'SENT', 302),
(132, 'system', '2025-10-04 16:58:03.000000', '2025-10-04 16:58:15.000000', 1, '9409434932', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 303),
(133, 'system', '2025-10-04 16:58:03.000000', '2025-10-04 16:58:31.000000', 1, '9409434932', 'Sneha Desai', NULL, 'SENT', 303),
(134, 'system', '2025-10-04 16:58:03.000000', '2025-10-04 16:58:49.000000', 1, '9409434932', 'Yash Mehta', NULL, 'SENT', 303),
(135, 'system', '2025-10-04 17:02:34.000000', '2025-10-04 17:02:38.000000', 1, '9409434932', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 304),
(136, 'system', '2025-10-04 17:02:34.000000', '2025-10-04 17:02:50.000000', 1, '9409434932', 'Sneha Desai', NULL, 'SENT', 304),
(137, 'system', '2025-10-04 17:02:34.000000', '2025-10-04 17:03:06.000000', 1, '9409434932', 'Yash Mehta', NULL, 'SENT', 304),
(138, 'system', '2025-10-04 17:09:40.000000', '2025-10-04 17:09:44.000000', 1, '9409434932', 'Sneha Desai', NULL, 'SENT', 305),
(139, 'system', '2025-10-04 17:09:40.000000', '2025-10-04 17:10:00.000000', 1, '9409434932', 'Yash Mehta', NULL, 'SENT', 305),
(140, 'system', '2025-10-11 09:37:59.000000', '2025-10-11 09:38:06.000000', 1, '9409434932', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 306),
(141, 'system', '2025-10-11 09:48:13.000000', '2025-10-11 09:48:14.000000', 1, '9409434932', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 307),
(142, 'system', '2025-10-11 09:55:11.000000', '2025-10-11 09:55:15.000000', 1, '9409434932', 'DHRUV JITENDRAKUMAR PATEL', NULL, 'SENT', 308);

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT 'system',
  `created_on` datetime(6) NOT NULL,
  `last_modified_on` datetime(6) NOT NULL,
  `version` bigint(20) NOT NULL DEFAULT 0,
  `actual_amount` double DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `payee_id` bigint(20) DEFAULT NULL,
  `payee_type` varchar(255) NOT NULL,
  `payment_date` datetime(6) DEFAULT NULL,
  `payment_type` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `branch_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `actual_amount`, `amount`, `payee_id`, `payee_type`, `payment_date`, `payment_type`, `status`, `branch_id`) VALUES
(1, 'system', '2025-05-24 23:27:36.000000', '2025-05-24 17:57:36.000000', 0, 200, 200, 1, 'STUDENT', '2025-05-24 17:56:58.000000', 'CASH', 'COMPLETED', 2),
(2, 'system', '2025-06-01 20:33:58.000000', '2025-06-01 15:03:58.000000', 0, 200, 200, 2, 'STUDENT', '2025-06-01 15:03:20.000000', 'CASH', 'COMPLETED', 2),
(4, 'system', '2025-06-01 20:52:54.000000', '2025-06-01 15:22:54.000000', 0, 100, 100, 3, 'STUDENT', '2025-06-01 15:18:55.000000', 'CASH', 'COMPLETED', 3),
(5, 'system', '2025-06-01 22:27:50.000000', '2025-06-01 16:57:50.000000', 0, 200, 300, 4, 'STUDENT', '2025-06-01 16:57:10.000000', 'CASH', 'COMPLETED', 2),
(6, 'system', '2025-07-01 21:33:32.000000', '2025-07-01 16:03:32.000000', 0, 200, 200, 5, 'STUDENT', '2025-07-01 16:00:40.000000', 'CASH', 'COMPLETED', 2),
(9, 'system', '2025-08-03 23:07:25.000000', '2025-08-03 17:37:25.000000', 0, 200, 200, 7, 'STUDENT', '2025-08-03 17:36:18.000000', 'CASH', 'COMPLETED', 2),
(10, 'system', '2025-08-09 01:00:25.000000', '2025-08-08 19:30:25.000000', 0, 200, 200, 8, 'STUDENT', '2025-08-08 19:12:38.000000', 'CASH', 'COMPLETED', 2),
(12, 'system', '2025-08-15 17:15:58.000000', '2025-08-15 17:15:58.000000', 0, 0, 0, 10, 'STUDENT', '2025-08-15 17:11:42.000000', 'CASH', 'COMPLETED', 2),
(13, 'system', '2025-08-17 14:02:21.000000', '2025-08-17 14:02:21.000000', 0, 0, 0, 11, 'STUDENT', '2025-08-17 13:59:51.000000', 'CASH', 'COMPLETED', 2),
(14, 'system', '2025-08-17 14:38:16.000000', '2025-08-17 14:38:16.000000', 0, 0, 100, 12, 'STUDENT', '2025-08-17 14:33:11.000000', 'CASH', 'COMPLETED', 2),
(15, 'system', '2025-08-17 14:51:11.000000', '2025-08-17 14:51:11.000000', 0, 100, 50, 13, 'STUDENT', '2025-08-17 14:33:11.000000', 'CASH', 'COMPLETED', 2),
(16, 'system', '2025-08-17 16:28:07.000000', '2025-08-17 16:28:07.000000', 0, 100, 100, 14, 'STUDENT', '2025-08-17 16:12:29.000000', 'CASH', 'COMPLETED', 2),
(17, 'system', '2025-08-17 16:42:46.000000', '2025-08-17 16:42:46.000000', 0, 0, 50, 15, 'STUDENT', '2025-08-17 16:35:36.000000', 'CASH', 'COMPLETED', 2),
(18, 'system', '2025-08-19 15:53:42.000000', '2025-08-19 15:53:42.000000', 0, 100, 70, 16, 'STUDENT', '2025-08-19 15:25:11.000000', 'CASH', 'COMPLETED', 2),
(19, 'system', '2025-08-22 15:59:16.000000', '2025-08-22 15:59:16.000000', 0, 1000, 1000, 17, 'STUDENT', '2025-08-22 15:43:40.000000', 'CASH', 'COMPLETED', 2),
(20, 'system', '2025-08-22 16:16:48.000000', '2025-08-22 16:16:48.000000', 0, 0, 0, 18, 'STUDENT', '2025-08-22 16:11:50.000000', 'CASH', 'COMPLETED', 2),
(21, 'system', '2025-08-22 16:17:00.000000', '2025-08-22 16:17:00.000000', 0, 0, 0, 19, 'STUDENT', '2025-08-22 16:16:40.000000', 'CASH', 'COMPLETED', 2),
(22, 'system', '2025-08-26 18:22:15.000000', '2025-08-26 18:22:15.000000', 0, 100, 100, 20, 'STUDENT', '2025-08-26 17:35:56.000000', 'CASH', 'COMPLETED', 2),
(23, 'system', '2025-08-26 18:22:23.000000', '2025-08-26 18:22:23.000000', 0, 0, 0, 21, 'STUDENT', '2025-08-26 17:35:56.000000', 'CASH', 'COMPLETED', 2),
(24, 'system', '2025-08-26 18:22:31.000000', '2025-08-26 18:22:31.000000', 0, 0, 0, 22, 'STUDENT', '2025-08-26 17:35:56.000000', 'CASH', 'COMPLETED', 2),
(25, 'system', '2025-08-26 18:22:38.000000', '2025-08-26 18:22:38.000000', 0, 0, 0, 23, 'STUDENT', '2025-08-26 17:35:56.000000', 'CASH', 'COMPLETED', 2),
(26, 'system', '2025-08-26 18:24:26.000000', '2025-08-26 18:24:26.000000', 0, 100, 100, 24, 'STUDENT', '2025-08-26 17:35:56.000000', 'CASH', 'COMPLETED', 2),
(27, 'system', '2025-08-26 18:24:35.000000', '2025-08-26 18:24:35.000000', 0, 0, 0, 25, 'STUDENT', '2025-08-26 17:35:56.000000', 'CASH', 'COMPLETED', 2),
(28, 'system', '2025-08-27 15:33:19.000000', '2025-08-27 15:33:19.000000', 0, 100, 100, 26, 'STUDENT', '2025-08-27 15:23:06.000000', 'CASH', 'COMPLETED', 2),
(29, 'system', '2025-08-27 15:33:26.000000', '2025-08-27 15:33:26.000000', 0, 0, 0, 27, 'STUDENT', '2025-08-27 15:23:06.000000', 'CASH', 'COMPLETED', 2),
(30, 'system', '2025-08-27 15:33:34.000000', '2025-08-27 15:33:34.000000', 0, 0, 0, 28, 'STUDENT', '2025-08-27 15:23:06.000000', 'CASH', 'COMPLETED', 2),
(31, 'system', '2025-08-28 19:23:26.000000', '2025-08-28 19:23:26.000000', 0, 100, 100, 29, 'STUDENT', '2025-08-28 19:12:14.000000', 'CASH', 'COMPLETED', 2),
(33, 'system', '2025-09-07 19:27:03.000000', '2025-09-07 19:42:13.000000', 1, 456, 456, 4, 'BOOKING', '2025-09-07 19:00:13.000000', 'UPI', 'COMPLETED', 2),
(34, 'system', '2025-09-20 14:05:39.000000', '2025-09-20 14:44:25.000000', 1, 100, 100, 30, 'STUDENT', '2025-09-29 14:02:44.000000', 'CASH', 'COMPLETED', 2),
(37, 'system', '2025-09-20 14:08:27.000000', '2025-09-20 14:43:31.000000', 1, 0, 0, 33, 'STUDENT', '2025-09-30 14:06:41.000000', 'CASH', 'COMPLETED', 2),
(38, 'system', '2025-09-20 14:12:30.000000', '2025-09-20 14:44:44.000000', 1, 1000, 1000, 34, 'STUDENT', '2025-09-30 18:30:00.000000', 'CASH', 'COMPLETED', 2),
(39, 'system', '2025-09-20 14:46:35.000000', '2025-09-20 14:46:35.000000', 0, 100, 100, 35, 'STUDENT', NULL, 'CASH', 'PENDING', 2),
(40, 'system', '2025-09-21 07:56:20.000000', '2025-09-21 07:56:20.000000', 0, 0, 0, 36, 'STUDENT', '2025-09-21 07:56:20.000000', 'CASH', 'COMPLETED', 2),
(41, 'system', '2025-09-21 08:00:19.000000', '2025-09-21 08:00:19.000000', 0, 100, 100, 37, 'STUDENT', '2025-09-21 07:56:20.000000', 'CASH', 'PENDING', 2),
(42, 'system', '2025-09-21 08:03:10.000000', '2025-09-21 08:03:22.000000', 1, 0, 0, 38, 'STUDENT', '2025-09-30 07:56:20.000000', 'CASH', 'COMPLETED', 2),
(43, 'system', '2025-09-22 16:54:41.000000', '2025-09-22 16:54:41.000000', 0, 0, 0, 39, 'STUDENT', '2025-09-22 16:54:41.000000', 'CASH', 'COMPLETED', 3),
(44, 'system', '2025-10-04 15:45:54.000000', '2025-10-04 15:45:54.000000', 0, 100, 100, 40, 'STUDENT', '2025-10-04 15:45:54.000000', 'CASH', 'COMPLETED', 2);

-- --------------------------------------------------------

--
-- Table structure for table `plan`
--

CREATE TABLE `plan` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT 'system',
  `created_on` datetime(6) NOT NULL,
  `last_modified_on` datetime(6) NOT NULL,
  `version` bigint(20) NOT NULL DEFAULT 0,
  `amount` double NOT NULL,
  `country_code` varchar(255) NOT NULL,
  `disabled_features` varchar(255) DEFAULT NULL,
  `enabled_features` varchar(255) DEFAULT NULL,
  `plan_type` varchar(255) NOT NULL,
  `sms_quota` double NOT NULL,
  `popular` bit(1) NOT NULL,
  `description` varchar(255) NOT NULL,
  `remind_before_days` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `plan`
--

INSERT INTO `plan` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `amount`, `country_code`, `disabled_features`, `enabled_features`, `plan_type`, `sms_quota`, `popular`, `description`, `remind_before_days`) VALUES
(1, 'system', '2025-04-12 13:54:22.000000', '2025-04-12 13:54:22.000000', 0, 0, 'IN', 'Client booking,Statistics & Analysis,Sales Report', 'Student Details,Payment Details', 'TRIAL', 0, b'0', 'Perfect for just getting started', 0),
(2, 'system', '2025-04-12 13:54:22.000000', '2025-04-12 13:54:22.000000', 0, 1499, 'IN', 'Client booking,Statistics & Analysis,Sales Report', 'Student Details,Payment Details', 'MONTHLY', 0, b'0', 'Perfect for just getting started', 0),
(4, 'system', '2025-04-12 13:55:42.000000', '2025-04-12 13:55:43.000000', 0, 7999, 'IN', 'Sales Report', 'Student Details,Payment Details,Client booking,Statistics & Analysis', 'HALF_YEARLY', 0, b'1', 'Ideal for growing studios with advanced needs', 0),
(5, 'system', '2025-04-12 13:54:43.000000', '2025-04-12 13:54:43.000000', 0, 13999, 'IN', '', 'Student Details,Payment Details,Client booking,Statistics & Analysis,Sales Report', 'YEARLY', 0, b'0', 'Most saving plan.', 0),
(6, 'system', '2025-04-12 13:54:22.000000', '2025-04-12 13:54:22.000000', 0, 0, 'US', 'Client booking,Statistics & Analysis,Sales Report', 'Student Details,Payment Details', 'TRIAL', 0, b'0', '', 0),
(7, 'system', '2025-04-12 13:54:22.000000', '2025-04-12 13:54:22.000000', 0, 17, 'US', 'Client booking,Statistics & Analysis,Sales Report', 'Student Details,Payment Details', 'MONTHLY', 0, b'0', '', 0),
(8, 'system', '2025-04-12 13:55:42.000000', '2025-04-12 13:55:43.000000', 0, 45, 'US', 'Sales Report', 'Student Details,Payment Details,Client booking,Statistics & Analysis', 'HALF_YEARLY', 0, b'1', '', 0),
(9, 'system', '2025-04-12 13:55:26.000000', '2025-04-12 13:55:26.000000', 0, 90, 'US', 'Statistics & Analysis,Sales Report', 'Student Details,Payment Details,Client booking', 'QUARTERLY', 0, b'0', '', 0),
(10, 'system', '2025-04-12 13:54:43.000000', '2025-04-12 13:54:43.000000', 0, 180, 'US', '', 'Student Details,Payment Details,Client booking,Statistics & Analysis,Sales Report', 'YEARLY', 0, b'0', '', 0),
(11, 'system', '2025-04-12 13:54:43.000000', '2025-04-12 13:54:43.000000', 0, 5999, 'IN', '', '', 'AMC', 0, b'0', '', 30);

-- --------------------------------------------------------

--
-- Table structure for table `student_activity_assignment`
--

CREATE TABLE `student_activity_assignment` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT 'system',
  `created_on` datetime(6) NOT NULL,
  `last_modified_on` datetime(6) NOT NULL,
  `version` bigint(20) NOT NULL DEFAULT 0,
  `activity_amount` double NOT NULL,
  `activity_name` varchar(100) NOT NULL,
  `days_per_week` int(11) NOT NULL,
  `membership_end_date` datetime(6) NOT NULL,
  `membership_start_date` datetime(6) NOT NULL,
  `membership_type` varchar(255) NOT NULL,
  `registration_date` datetime(6) NOT NULL,
  `student_id` bigint(20) DEFAULT NULL,
  `batch_name` varchar(255) NOT NULL DEFAULT 'default batch',
  `batch_time` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student_activity_assignment`
--

INSERT INTO `student_activity_assignment` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `activity_amount`, `activity_name`, `days_per_week`, `membership_end_date`, `membership_start_date`, `membership_type`, `registration_date`, `student_id`, `batch_name`, `batch_time`) VALUES
(7, 'system', '2025-08-03 23:07:24.000000', '2025-08-03 17:37:24.000000', 0, 200, 'GYM', 5, '2025-09-02 17:36:18.000000', '2025-08-03 17:36:18.000000', 'MONTHLY', '2025-08-03 17:36:18.000000', 6, 'default batch', ''),
(8, 'system', '2025-08-09 01:00:25.000000', '2025-08-08 19:30:25.000000', 0, 200, 'GYM', 5, '2025-09-07 19:12:38.000000', '2025-08-08 19:12:38.000000', 'MONTHLY', '2025-08-08 19:12:38.000000', 3, 'default batch', ''),
(10, 'system', '2025-08-15 17:15:58.000000', '2025-08-15 17:15:58.000000', 0, 0, 'GYM', 5, '2025-09-14 17:11:42.000000', '2025-08-15 17:11:42.000000', 'MONTHLY', '2025-08-15 17:11:42.000000', 3, 'Default Batch', '00:00 - 00:00'),
(11, 'system', '2025-08-17 14:02:20.000000', '2025-08-17 14:02:20.000000', 0, 0, 'ZUMBA', 3, '2025-09-16 13:59:51.000000', '2025-08-17 13:59:51.000000', 'MONTHLY', '2025-08-17 13:59:51.000000', 1, 'Batch 1', '09:00 - 10:00'),
(12, 'system', '2025-08-17 14:38:16.000000', '2025-08-17 14:38:16.000000', 0, 0, 'ZUMBA', 3, '2025-09-16 14:33:11.000000', '2025-08-17 14:33:11.000000', 'MONTHLY', '2025-08-17 14:33:11.000000', 1, 'Batch 1', '09:00 - 10:00'),
(13, 'system', '2025-08-17 14:51:11.000000', '2025-08-17 14:51:11.000000', 0, 100, 'GYM', 6, '2025-09-16 14:33:11.000000', '2025-08-17 14:33:11.000000', 'MONTHLY', '2025-08-17 14:33:11.000000', 1, 'Batch 1', '09:00 - 10:00'),
(14, 'system', '2025-08-17 16:28:07.000000', '2025-08-17 16:28:07.000000', 0, 100, 'GYM', 6, '2025-09-16 16:12:29.000000', '2025-08-17 16:12:29.000000', 'MONTHLY', '2025-08-17 16:12:29.000000', 1, 'Batch 1', '09:00 - 10:00'),
(15, 'system', '2025-08-17 16:42:46.000000', '2025-08-17 16:42:46.000000', 0, 0, 'DANCE', 2, '2025-09-16 16:35:36.000000', '2025-08-17 16:35:36.000000', 'MONTHLY', '2025-08-17 16:35:36.000000', 1, 'Batch 2', '00:00 - 00:00'),
(16, 'system', '2025-08-19 15:53:42.000000', '2025-08-19 15:53:42.000000', 0, 100, 'DANCE', 3, '2025-09-18 15:25:11.000000', '2025-08-19 15:25:11.000000', 'MONTHLY', '2025-08-19 15:25:11.000000', 3, 'Batch 1', '00:00 - 00:00'),
(17, 'system', '2025-08-22 15:59:15.000000', '2025-08-22 15:59:15.000000', 0, 1000, 'YOGA', 3, '2025-09-29 18:30:00.000000', '2025-08-31 15:43:40.000000', 'test 1', '2025-08-22 15:43:40.000000', 1, 'Batch 1', '00:00 - 00:00'),
(18, 'system', '2025-08-22 16:16:48.000000', '2025-08-22 17:18:20.000000', 1, 0, 'YOGA', 3, '2025-09-30 16:11:50.000000', '2025-08-22 16:11:50.000000', 'training + Monthly membership', '2025-08-22 16:11:50.000000', 7, 'Batch 2', '00:00 - 00:00'),
(19, 'system', '2025-08-22 16:17:00.000000', '2025-08-22 16:17:00.000000', 0, 0, 'ZUMBA', 3, '2025-08-22 16:16:40.000000', '2025-08-22 16:16:40.000000', 'REGISTRATION', '2025-08-22 16:16:40.000000', 7, 'Batch 1', '00:00 - 00:00'),
(20, 'system', '2025-08-26 18:22:15.000000', '2025-08-26 18:22:15.000000', 0, 100, 'DANCE', 3, '2025-09-25 17:35:56.000000', '2025-08-26 17:35:56.000000', 'MONTHLY', '2025-08-26 17:35:56.000000', 6, 'Batch 1', '02:00 - 00:00'),
(21, 'system', '2025-08-26 18:22:23.000000', '2025-08-26 18:22:23.000000', 0, 0, 'MARTIAL_ARTS', 3, '2026-02-22 17:35:56.000000', '2025-08-26 17:35:56.000000', 'HALF_YEARLY', '2025-08-26 17:35:56.000000', 6, 'Batch 1', '00:00 - 00:00'),
(22, 'system', '2025-08-26 18:22:30.000000', '2025-08-26 18:22:30.000000', 0, 0, 'ZUMBA', 3, '2025-08-26 17:35:56.000000', '2025-08-26 17:35:56.000000', 'REGISTRATION', '2025-08-26 17:35:56.000000', 6, 'Batch 1', '00:00 - 00:00'),
(23, 'system', '2025-08-26 18:22:38.000000', '2025-08-26 18:22:38.000000', 0, 0, 'ZUMBA', 3, '2025-08-26 17:35:56.000000', '2025-08-26 17:35:56.000000', 'REGISTRATION', '2025-08-26 17:35:56.000000', 6, 'Batch 1', '00:00 - 00:00'),
(24, 'system', '2025-08-26 18:24:26.000000', '2025-08-26 18:24:26.000000', 0, 100, 'DANCE', 3, '2025-09-25 17:35:56.000000', '2025-08-26 17:35:56.000000', 'MONTHLY', '2025-08-26 17:35:56.000000', 6, 'Batch 1', '02:00 - 00:00'),
(25, 'system', '2025-08-26 18:24:34.000000', '2025-08-26 18:24:34.000000', 0, 0, 'MARTIAL_ARTS', 3, '2026-02-22 17:35:56.000000', '2025-08-26 17:35:56.000000', 'HALF_YEARLY', '2025-08-26 17:35:56.000000', 6, 'Batch 1', '00:00 - 00:00'),
(26, 'system', '2025-08-27 15:33:19.000000', '2025-08-27 15:33:19.000000', 0, 100, 'DANCE', 3, '2025-09-26 15:23:06.000000', '2025-08-27 15:23:06.000000', 'MONTHLY', '2025-08-27 15:23:06.000000', 8, 'Batch 1', '02:00 - 00:00'),
(27, 'system', '2025-08-27 15:33:26.000000', '2025-08-27 15:33:26.000000', 0, 0, 'MARTIAL_ARTS', 3, '2026-02-23 15:23:06.000000', '2025-08-27 15:23:06.000000', 'HALF_YEARLY', '2025-08-27 15:23:06.000000', 8, 'Batch 1', '00:00 - 00:00'),
(28, 'system', '2025-08-27 15:33:34.000000', '2025-08-27 15:33:34.000000', 0, 0, 'MARTIAL_ARTS', 3, '2026-02-23 15:23:06.000000', '2025-08-27 15:23:06.000000', 'HALF_YEARLY', '2025-08-27 15:23:06.000000', 8, 'Batch 1', '00:00 - 00:00'),
(29, 'system', '2025-08-28 19:23:26.000000', '2025-08-28 19:23:26.000000', 0, 100, 'DANCE', 3, '2025-09-27 19:12:14.000000', '2025-08-28 19:12:14.000000', 'MONTHLY', '2025-08-28 19:12:14.000000', 4, 'Batch 1', '02:00 - 00:00'),
(30, 'system', '2025-09-20 14:05:39.000000', '2025-09-20 14:05:39.000000', 0, 100, 'DANCE', 3, '2025-10-20 14:02:44.000000', '2025-09-20 14:02:44.000000', 'MONTHLY', '2025-09-20 14:02:44.000000', 1, 'Batch 1', '02:00 - 00:00'),
(33, 'system', '2025-09-20 14:08:26.000000', '2025-09-20 14:08:26.000000', 0, 0, 'YOGA', 3, '2025-10-20 14:07:49.000000', '2025-09-20 14:07:49.000000', 'training + Monthly membership', '2025-09-20 14:07:49.000000', 1, 'Kids batch', '00:00 - 00:00'),
(34, 'system', '2025-09-20 14:12:30.000000', '2025-09-20 14:52:01.000000', 2, 1000, 'YOGA', 3, '2025-10-31 14:11:44.000000', '2025-09-23 14:11:44.000000', 'test 1', '2025-09-20 14:11:44.000000', 1, 'Batch 1', '00:00 - 00:00'),
(35, 'system', '2025-09-20 14:46:35.000000', '2025-09-20 14:51:25.000000', 2, 100, 'DANCE', 3, '2025-10-31 14:43:52.000000', '2025-10-01 14:43:52.000000', 'MONTHLY', '2025-09-20 14:43:52.000000', 1, 'Batch 1', '02:00 - 00:00'),
(36, 'system', '2025-09-21 07:56:20.000000', '2025-09-21 07:56:20.000000', 0, 0, 'ZUMBA', 3, '2025-09-21 07:55:55.000000', '2025-09-21 07:55:55.000000', 'REGISTRATION', '2025-09-09 07:55:55.000000', 3, 'Batch 1', '00:00 - 00:00'),
(37, 'system', '2025-09-21 08:00:19.000000', '2025-09-21 08:00:19.000000', 0, 100, 'DANCE', 3, '2025-10-21 07:55:55.000000', '2025-09-21 07:55:55.000000', 'MONTHLY', '2025-09-09 07:55:55.000000', 3, 'Batch 1', '02:00 - 00:00'),
(38, 'system', '2025-09-21 08:03:10.000000', '2025-09-21 08:03:10.000000', 0, 0, 'MARTIAL_ARTS', 3, '2026-03-20 07:59:55.000000', '2025-09-21 07:59:55.000000', 'HALF_YEARLY', '2025-09-21 07:59:55.000000', 3, 'Batch 1', '00:00 - 00:00'),
(39, 'system', '2025-09-22 16:54:41.000000', '2025-09-22 16:54:41.000000', 0, 0, 'YOGA', 3, '2025-10-22 15:11:35.000000', '2025-09-22 15:11:35.000000', 'MONTHLY', '2025-09-22 15:11:35.000000', 2, 'Batch 1', '00:00 - 00:00'),
(40, 'system', '2025-10-04 15:45:54.000000', '2025-10-04 15:45:54.000000', 0, 100, 'DANCE', 3, '2025-11-03 15:45:27.000000', '2025-10-04 15:45:27.000000', 'MONTHLY', '2025-10-04 15:45:27.000000', 9, 'Batch 1', '02:00 - 00:00');

-- --------------------------------------------------------

--
-- Table structure for table `studio`
--

CREATE TABLE `studio` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT 'system',
  `created_on` datetime(6) NOT NULL,
  `last_modified_on` datetime(6) NOT NULL,
  `version` bigint(20) NOT NULL DEFAULT 0,
  `configuration` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`configuration`)),
  `contact` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `logo` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `pass_code` varchar(255) DEFAULT NULL,
  `gst_number` varchar(255) DEFAULT NULL,
  `amc_enabled` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `studio`
--

INSERT INTO `studio` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `configuration`, `contact`, `email`, `location`, `logo`, `name`, `pass_code`, `gst_number`, `amc_enabled`) VALUES
(2, 'system', '2025-05-24 23:15:38.000000', '2025-10-04 19:05:59.000000', 64, '{\"BRANCH\":true,\"CLIENT\":true,\"BOOKINGS\":true,\"INSTRUCTOR\":true,\"STUDENT\":true,\"ACTIVITY\":true,\"COMMUNICATION\":true,\"PAYMENTS\":true,\"EXPENSE\":true,\"ANALYSIS\":true,\"REPORTS\":true,\"BATCH\":true,\"PAYMENT_DATE\":true,\"ENQUIRY\":true,\"MEMBERSHIP_PLAN_TABLE\":true,\"TEMPLATES\":true}', '9409434932', 'dhruv20345@gmail.com', 'Chikhli', 'http://res.cloudinary.com/dvraa4zpz/image/upload/v1759604756/studio/6854b440-23e7-40e3-8aa5-8874d9b4226b_341477483_172458159034973_6480249691395109525_n.jpg.jpg', 'Studio Test', 'lpfhgmujrttltgio', 'GST123', 0),
(3, 'system', '2025-08-10 16:30:29.000000', '2025-08-10 11:00:29.000000', 0, '{\n  \"BRANCH\": true,\n  \"CLIENT\": false,\n  \"BOOKINGS\": false,\n  \"INSTRUCTOR\": true,\n  \"STUDENT\": true,\n  \"ACTIVITY\": true,\n  \"COMMUNICATION\": true,\n  \"PAYMENTS\": true,\n  \"EXPENSE\": true,\n  \"ANALYSIS\": false,\n  \"REPORTS\": true,\n  \"BATCH\": true,\n  \"BULK_UPLOAD\": true,\n  \"PAYMENT_DATE\": true,\n  \"ENQUIRY\": true,\n  \"MEMBERSHIP_PLAN_TABLE\": false,\n  \"TEMPLATES\": false\n}\n', '1234567890', 'dhruv20369@gmail.com', 'test', NULL, 'test', NULL, NULL, 0);

-- --------------------------------------------------------

--
-- Table structure for table `subscription`
--

CREATE TABLE `subscription` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT 'system',
  `created_on` datetime(6) NOT NULL,
  `last_modified_on` datetime(6) NOT NULL,
  `version` bigint(20) NOT NULL DEFAULT 0,
  `end_date` datetime(6) NOT NULL,
  `order_id` varchar(255) NOT NULL,
  `payment_id` varchar(255) DEFAULT NULL,
  `price` decimal(38,2) NOT NULL,
  `renewal_date` datetime(6) DEFAULT NULL,
  `start_date` datetime(6) NOT NULL,
  `status` varchar(255) NOT NULL,
  `subscription_plan` varchar(255) NOT NULL,
  `studio_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `subscription`
--

INSERT INTO `subscription` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `end_date`, `order_id`, `payment_id`, `price`, `renewal_date`, `start_date`, `status`, `subscription_plan`, `studio_id`) VALUES
(1, 'system', '2025-05-24 23:26:21.000000', '2025-05-24 17:56:48.000000', 1, '2025-06-24 00:00:00.000000', 'order_QYr9BVuKprEXod', 'pay_QYr9RcEohutMP4', 1499.00, NULL, '2025-05-24 00:00:00.000000', 'ACTIVE', 'MONTHLY', 2),
(2, 'system', '2025-07-01 21:29:45.000000', '2025-07-01 16:00:09.000000', 1, '2025-08-01 00:00:00.000000', 'order_QnrSakuC2J6jmn', 'pay_QnrSnVrSHjy4hU', 1499.00, NULL, '2025-07-01 00:00:00.000000', 'ACTIVE', 'MONTHLY', 2),
(4, 'system', '2025-09-13 09:47:03.000000', '2025-09-13 09:47:35.000000', 1, '2026-10-04 00:00:00.000000', 'order_RH2doK3pizHrJu', 'pay_RH2e9Zch0JiILh', 5999.00, NULL, '2025-08-01 00:00:00.000000', 'ACTIVE', 'MONTHLY', 2),
(5, 'system', '2025-10-05 08:50:29.000000', '2025-10-05 08:50:29.000000', 0, '2026-11-04 00:00:00.000000', 'order_RPjQi0TbObpWrg', NULL, 1499.00, NULL, '2026-10-04 00:00:00.000000', 'CREATED', 'MONTHLY', 2);

-- --------------------------------------------------------

--
-- Table structure for table `template`
--

CREATE TABLE `template` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT 'system',
  `created_on` datetime(6) NOT NULL,
  `last_modified_on` datetime(6) NOT NULL,
  `version` bigint(20) NOT NULL DEFAULT 0,
  `body` text NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `template_type` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `template`
--

INSERT INTO `template` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `body`, `name`, `subject`, `template_type`) VALUES
(13, 'system', '2025-09-28 09:43:45.000000', '2025-09-28 09:43:45.000000', 0, 'Dear {studio_name},\n\nCongratulations! Your studio has been successfully registered.\nWe are thrilled to have you join us and look forward to helping your studio grow and connect with more dance enthusiasts.\n\nIf you have any questions or need assistance, please do not hesitate to reach out to our support team.\n\nWe have created a user with a dummy password below, please update as per your convenience.\n\nUsername: {username}\nPassword: {password}\n\nBest regards,\nBook & Manage Team', 'NEW_STUDIO_EMAIL', 'Welcome to Book & Manage !!', 'EMAIL'),
(14, 'system', '2025-09-28 09:43:45.000000', '2025-09-28 09:43:45.000000', 0, 'Hi {studio_name},\n\nYour details have been updated. Please check the dashboard to view the changes.\n\nBest regards,\nBook & Manage Team', 'UPDATE_STUDIO_EMAIL', 'Updated Studio Details', 'EMAIL'),
(15, 'system', '2025-09-28 09:43:45.000000', '2025-09-28 09:43:45.000000', 0, 'Dear {student_name},\n\nYour registration has been completed! We are thrilled to welcome you to {studio_name}.\n\nIf you have any questions or need assistance, please don’t hesitate to reach out to us. We’re here to help!\n\nLooking forward to seeing you in our classes!\n\nBest regards,\n{studio_name} Team', 'NEW_STUDENT_EMAIL', 'Welcome to Book & Manage !!', 'EMAIL'),
(16, 'system', '2025-09-28 09:43:45.000000', '2025-09-28 09:43:45.000000', 0, 'Dear {student_name},\n\nThis is a gentle reminder that your subscription is due for renewal. Please renew your {activity_type} membership to continue enjoying our services.\n\nBest regards,\n{studio_name} Team', 'SUBSCRIPTION_RENEWAL_REMINDER', 'Subscription Renewal Reminder', 'EMAIL'),
(17, 'system', '2025-09-28 09:43:45.000000', '2025-09-28 09:43:45.000000', 0, 'Dear students,\n\nPlease note that the studio will be closed on {closure_date} due to {reason}. We apologize for any inconvenience.\n\nBest regards,\n{studio_name} Team', 'STUDIO_CLOSED_NOTICE', 'Studio Closed Notice', 'SMS'),
(18, 'system', '2025-09-28 09:43:45.000000', '2025-09-28 09:43:45.000000', 0, 'Hello {student_name},\n\nThis is a reminder that your payment of ₹{amount_due} is pending. Please renew the membership plan to continue the  services\n\nThank you,\n{studio_name}', 'PAYMENT_REMINDER', 'Payment Reminder', 'SMS'),
(19, 'system', '2025-09-28 09:43:45.000000', '2025-09-28 09:43:45.000000', 0, 'Dear {user_name},\n\nCongratulations! You has been successfully registered.\nWe are thrilled to have you join us and look forward to helping your studio grow and connect with more dance enthusiasts.\n\nIf you have any questions or need assistance, please do not hesitate to reach out to our support team.\n\nWe have created a user with a dummy password below, please update as per your convenience.\n\nUsername: {username}\nPassword: {password}\n\nBest regards,\nBook & Manage Team', 'NEW_USER_EMAIL', 'Welcome to Book & Manage !!', 'EMAIL'),
(20, 'system', '2025-09-28 09:43:45.000000', '2025-09-28 09:43:45.000000', 0, 'Dear {user_name},\n\nYour details have been updated. Please check the dashboard to view the changes.\n\nBest regards,\nBook & Manage Team', 'UPDATE_USER_EMAIL', 'Updated User Details', 'EMAIL'),
(21, 'system', '2025-09-28 09:43:45.000000', '2025-09-28 09:43:45.000000', 0, 'Dear {student_name},\n\nThank you for registering for {activity_name}. The attached documnent below for invoice details.\n\nBest regards,\n{studio_name}', 'MEMBERSHIP_INVOICE', 'Membership Invoice Details', 'EMAIL'),
(22, 'system', '2025-09-28 09:43:45.000000', '2025-09-28 09:43:45.000000', 0, 'Dear {student_name},\n\nThank you for appointment booking. The attached documnent below for invoice details.\n\nBest regards,\n{studio_name}', 'BOOKING_INVOICE', 'Booking Invoice Details', 'EMAIL');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT 'system',
  `created_on` datetime(6) NOT NULL,
  `last_modified_on` datetime(6) NOT NULL,
  `version` bigint(20) NOT NULL DEFAULT 0,
  `email` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(10) NOT NULL,
  `profile_image` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `branch_id` bigint(20) DEFAULT NULL,
  `studio_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `email`, `enabled`, `name`, `password`, `phone`, `profile_image`, `role`, `branch_id`, `studio_id`) VALUES
(2, 'system', '2025-05-24 23:15:38.000000', '2025-09-28 17:22:09.000000', 1, 'dhruv20345@gmail.com', b'1', 'dhruv4023', '$2a$10$T7vDClpB5mD067ptJKcvxeBLK3UL4QLxqDlrSD3f0NmbuxzFyrJGe', '9409434932', NULL, 'ADMIN', 1, 2),
(4, 'system', '2025-08-10 16:30:29.000000', '2025-08-10 11:00:29.000000', 0, 'dhruv20369@gmail.com', b'1', 'test', '$2a$10$rxtvjf4QzDEPFsriSCGWpux4DQKsrbltjpgbjkFfpd16TuheFbRaS', '1234567890', NULL, 'ADMIN', 4, 3),
(13, 'system', '2025-09-21 17:27:07.000000', '2025-09-21 17:29:47.000000', 1, 'test@mail.com', b'1', 'test1', '$2a$10$qG.JeIZjMOU28ZtmqvLjqebAFOWaE7ZhrcPTuTvA5I2AgFt4E1pGW', '1234567890', NULL, 'MANAGER', 2, 2),
(18, 'system', '2025-10-04 16:00:22.000000', '2025-10-04 16:00:22.000000', 0, 'test@mail.com', b'1', 'test4', '$2a$10$fpZflc7McNUosSDEg44AnOPgs.8vRwiTzZyYWnGaBkyegi8wNFCuC', '1234567890', NULL, 'MANAGER', 3, 2),
(19, 'system', '2025-10-04 16:03:14.000000', '2025-10-04 16:03:14.000000', 0, 'test5@mail.com', b'1', 'test5', '$2a$10$9pmryZ9Tgt2xHH0ayzN8iehPfnqX/XwewjRvCpyQqI2KR0PsOcA8y', '1234567890', NULL, 'MANAGER', 3, 2),
(20, 'system', '2025-10-04 16:04:38.000000', '2025-10-04 16:04:38.000000', 0, 'test6@mail.com', b'1', 'test6', '$2a$10$Xjr2uHop80IL.UTo956fkeZdCU9tIwnEhW0oOECuG7pN2SP3/YLzK', '1234567890', NULL, 'MANAGER', 5, 2);

-- --------------------------------------------------------

--
-- Table structure for table `user_access`
--

CREATE TABLE `user_access` (
  `id` bigint(20) NOT NULL,
  `activity` int(11) NOT NULL,
  `analysis` int(11) NOT NULL,
  `communication` int(11) NOT NULL,
  `enquiry` int(11) NOT NULL,
  `expense` int(11) NOT NULL,
  `payments` int(11) NOT NULL,
  `reports` int(11) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_access`
--

INSERT INTO `user_access` (`id`, `activity`, `analysis`, `communication`, `enquiry`, `expense`, `payments`, `reports`, `user_id`) VALUES
(5, 1, 1, 1, 1, 1, 1, 1, 2),
(6, 1, 1, 1, 1, 1, 1, 1, 4),
(8, 0, 0, 1, 0, 1, 0, 0, 13),
(9, 0, 0, 1, 0, 1, 1, 0, 18),
(10, 0, 0, 1, 0, 1, 1, 0, 19),
(11, 0, 0, 1, 0, 1, 1, 0, 20);

-- --------------------------------------------------------

--
-- Table structure for table `whatsapp_web_session`
--

CREATE TABLE `whatsapp_web_session` (
  `client_id` varchar(255) NOT NULL,
  `creds` text NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `last_modified_on` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `whatsapp_web_session`
--

INSERT INTO `whatsapp_web_session` (`client_id`, `creds`, `created_at`, `last_modified_on`) VALUES
('2', '{\"noiseKey\":{\"private\":{\"type\":\"Buffer\",\"data\":[240,83,211,236,91,210,162,205,169,83,252,181,60,131,154,158,74,20,127,216,148,14,222,71,151,37,182,178,236,53,173,100]},\"public\":{\"type\":\"Buffer\",\"data\":[197,148,161,204,196,168,143,70,190,251,247,94,148,75,250,253,91,59,233,15,8,116,43,132,3,148,119,190,3,245,118,74]}},\"pairingEphemeralKeyPair\":{\"private\":{\"type\":\"Buffer\",\"data\":[128,244,55,144,78,249,151,237,233,76,26,52,69,142,132,52,224,70,70,237,157,20,135,127,150,51,210,87,38,230,225,117]},\"public\":{\"type\":\"Buffer\",\"data\":[97,199,78,208,128,116,114,88,90,226,187,74,224,117,83,139,153,125,172,53,75,178,146,105,85,88,225,149,120,247,155,18]}},\"signedIdentityKey\":{\"private\":{\"type\":\"Buffer\",\"data\":[120,222,210,143,223,146,248,224,106,53,28,151,56,31,183,247,148,109,165,238,204,153,56,10,53,98,133,227,177,158,238,110]},\"public\":{\"type\":\"Buffer\",\"data\":[116,29,41,53,49,242,204,49,251,59,231,187,22,167,41,65,50,226,50,117,15,132,69,209,108,228,103,113,234,228,171,28]}},\"signedPreKey\":{\"keyPair\":{\"private\":{\"type\":\"Buffer\",\"data\":[32,168,113,60,235,218,116,106,51,24,41,139,17,81,198,179,5,14,137,250,141,126,238,64,217,120,130,41,105,113,206,94]},\"public\":{\"type\":\"Buffer\",\"data\":[216,53,157,54,77,235,131,89,13,144,14,23,201,239,3,187,42,208,23,129,171,77,45,100,182,114,251,87,78,183,17,121]}},\"signature\":{\"type\":\"Buffer\",\"data\":[214,150,33,61,185,149,103,41,182,9,147,80,154,40,176,177,44,241,238,49,44,236,189,176,185,227,244,223,181,224,178,93,13,166,191,85,4,74,148,119,4,161,213,79,127,233,11,212,187,102,195,39,146,115,237,94,185,209,17,30,116,83,52,133]},\"keyId\":1},\"registrationId\":25,\"advSecretKey\":\"YSxZnJORUWStF6+QVlKHJmz7j2IcmGocz03NrXRc260=\",\"processedHistoryMessages\":[],\"nextPreKeyId\":31,\"firstUnuploadedPreKeyId\":31,\"accountSyncCounter\":0,\"accountSettings\":{\"unarchiveChats\":true},\"registered\":true,\"account\":{\"details\":\"CLfW5boBEKHyxcYGGBkgACgA\",\"accountSignatureKey\":\"IhXle0GhjYw9SBeq35OppYCDxRFcTvzxPikT/a04Lkk=\",\"accountSignature\":\"YnhobBRxjSAmJDUv2pxMhG+bwMj/h/betV2sG+zUp3TseNvzj0sBve5H7DbOQzOBtp0MWpl7tf9vzYnf06fQAQ==\",\"deviceSignature\":\"Q5u8P7Owg0QKmNfeVzswxH6ysmetg3qBzj0rkiHCnMmHXpehRGhQyylVNdWerp+IpjktHIKYXEums6DssliVgA==\"},\"me\":{\"id\":\"919409434932:33@s.whatsapp.net\",\"lid\":\"228535377600732:33@lid\"},\"signalIdentities\":[{\"identifier\":{\"name\":\"919409434932:33@s.whatsapp.net\",\"deviceId\":0},\"identifierKey\":{\"type\":\"Buffer\",\"data\":[5,34,21,229,123,65,161,141,140,61,72,23,170,223,147,169,165,128,131,197,17,92,78,252,241,62,41,19,253,173,56,46,73]}}],\"platform\":\"android\",\"routingInfo\":{\"type\":\"Buffer\",\"data\":[8,13,8,5]},\"lastAccountSyncTimestamp\":1758558673,\"lastPropHash\":\"3fYwCK\",\"myAppStateKeyId\":\"AAAAABhw\"}', '2025-09-22 17:25:00', '2025-09-22 17:25:00'),
('3', '{\"noiseKey\":{\"private\":{\"type\":\"Buffer\",\"data\":[240,83,211,236,91,210,162,205,169,83,252,181,60,131,154,158,74,20,127,216,148,14,222,71,151,37,182,178,236,53,173,100]},\"public\":{\"type\":\"Buffer\",\"data\":[197,148,161,204,196,168,143,70,190,251,247,94,148,75,250,253,91,59,233,15,8,116,43,132,3,148,119,190,3,245,118,74]}},\"pairingEphemeralKeyPair\":{\"private\":{\"type\":\"Buffer\",\"data\":[128,244,55,144,78,249,151,237,233,76,26,52,69,142,132,52,224,70,70,237,157,20,135,127,150,51,210,87,38,230,225,117]},\"public\":{\"type\":\"Buffer\",\"data\":[97,199,78,208,128,116,114,88,90,226,187,74,224,117,83,139,153,125,172,53,75,178,146,105,85,88,225,149,120,247,155,18]}},\"signedIdentityKey\":{\"private\":{\"type\":\"Buffer\",\"data\":[120,222,210,143,223,146,248,224,106,53,28,151,56,31,183,247,148,109,165,238,204,153,56,10,53,98,133,227,177,158,238,110]},\"public\":{\"type\":\"Buffer\",\"data\":[116,29,41,53,49,242,204,49,251,59,231,187,22,167,41,65,50,226,50,117,15,132,69,209,108,228,103,113,234,228,171,28]}},\"signedPreKey\":{\"keyPair\":{\"private\":{\"type\":\"Buffer\",\"data\":[32,168,113,60,235,218,116,106,51,24,41,139,17,81,198,179,5,14,137,250,141,126,238,64,217,120,130,41,105,113,206,94]},\"public\":{\"type\":\"Buffer\",\"data\":[216,53,157,54,77,235,131,89,13,144,14,23,201,239,3,187,42,208,23,129,171,77,45,100,182,114,251,87,78,183,17,121]}},\"signature\":{\"type\":\"Buffer\",\"data\":[214,150,33,61,185,149,103,41,182,9,147,80,154,40,176,177,44,241,238,49,44,236,189,176,185,227,244,223,181,224,178,93,13,166,191,85,4,74,148,119,4,161,213,79,127,233,11,212,187,102,195,39,146,115,237,94,185,209,17,30,116,83,52,133]},\"keyId\":1},\"registrationId\":25,\"advSecretKey\":\"YSxZnJORUWStF6+QVlKHJmz7j2IcmGocz03NrXRc260=\",\"processedHistoryMessages\":[],\"nextPreKeyId\":61,\"firstUnuploadedPreKeyId\":61,\"accountSyncCounter\":0,\"accountSettings\":{\"unarchiveChats\":true},\"registered\":true,\"account\":{\"details\":\"CLfW5boBEKHyxcYGGBkgACgA\",\"accountSignatureKey\":\"IhXle0GhjYw9SBeq35OppYCDxRFcTvzxPikT/a04Lkk=\",\"accountSignature\":\"YnhobBRxjSAmJDUv2pxMhG+bwMj/h/betV2sG+zUp3TseNvzj0sBve5H7DbOQzOBtp0MWpl7tf9vzYnf06fQAQ==\",\"deviceSignature\":\"Q5u8P7Owg0QKmNfeVzswxH6ysmetg3qBzj0rkiHCnMmHXpehRGhQyylVNdWerp+IpjktHIKYXEums6DssliVgA==\"},\"me\":{\"id\":\"919409434932:33@s.whatsapp.net\",\"lid\":\"228535377600732:33@lid\"},\"signalIdentities\":[{\"identifier\":{\"name\":\"919409434932:33@s.whatsapp.net\",\"deviceId\":0},\"identifierKey\":{\"type\":\"Buffer\",\"data\":[5,34,21,229,123,65,161,141,140,61,72,23,170,223,147,169,165,128,131,197,17,92,78,252,241,62,41,19,253,173,56,46,73]}}],\"platform\":\"android\",\"routingInfo\":{\"type\":\"Buffer\",\"data\":[8,13,8,5]},\"lastAccountSyncTimestamp\":1758558673,\"lastPropHash\":\"3fYwCK\",\"myAppStateKeyId\":\"AAAAABhw\"}', '2025-09-22 17:04:34', '2025-09-22 17:19:04');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `activity`
--
ALTER TABLE `activity`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `activityType_branch_key` (`activity_type`,`branch_id`),
  ADD KEY `fk_activity_branch_id` (`branch_id`);

--
-- Indexes for table `activity_batch`
--
ALTER TABLE `activity_batch`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name_plan_type_activity_id_days_per_week_key` (`name`,`plan_type`,`activity_id`,`days_per_week`),
  ADD KEY `fk_activity_activity_id` (`activity_id`);

--
-- Indexes for table `activity_membership_type`
--
ALTER TABLE `activity_membership_type`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `membership_type_studio_key` (`membership_type`,`studio_id`),
  ADD KEY `fk_activity_membership_type_studio_id` (`studio_id`);

--
-- Indexes for table `bank_account`
--
ALTER TABLE `bank_account`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_booking_branch_id` (`branch_id`),
  ADD KEY `fk_booking_client_id` (`client_id`);

--
-- Indexes for table `branch`
--
ALTER TABLE `branch`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uq_branch_name_studio` (`name`,`studio_id`),
  ADD KEY `fk_branch_studio_id` (`studio_id`);

--
-- Indexes for table `bulk_upload`
--
ALTER TABLE `bulk_upload`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKgsvulphmkclwmu65dihntw2de` (`branch_id`);

--
-- Indexes for table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `groupname_email_key` (`group_name`,`poc_email`),
  ADD KEY `fk_client_branch_id` (`branch_id`);

--
-- Indexes for table `conditions`
--
ALTER TABLE `conditions`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `templateType_templateName_branch_key` (`template_type`,`template_name`,`branch_id`);

--
-- Indexes for table `enquiries`
--
ALTER TABLE `enquiries`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_enquiry_branch_id` (`branch_id`);

--
-- Indexes for table `expense`
--
ALTER TABLE `expense`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_expense_branch_id` (`branch_id`);

--
-- Indexes for table `forms`
--
ALTER TABLE `forms`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_form_branch_id` (`branch_id`);

--
-- Indexes for table `generic_template`
--
ALTER TABLE `generic_template`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `templateType_templateName_studio_key` (`template_type`,`template_name`,`studio_id`),
  ADD KEY `fk_condition_studio_id` (`studio_id`);

--
-- Indexes for table `genric_template`
--
ALTER TABLE `genric_template`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `templateType_templateName_studio_key` (`template_type`,`template_name`,`studio_id`);

--
-- Indexes for table `instructor_activity_assignment`
--
ALTER TABLE `instructor_activity_assignment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_instructor_id` (`instructor_id`);

--
-- Indexes for table `members`
--
ALTER TABLE `members`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_name_email_membertype_branch` (`name`,`email`,`member_type`,`branch_id`),
  ADD KEY `fk_member_branch_id` (`branch_id`);

--
-- Indexes for table `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_message_branch_id` (`branch_id`);

--
-- Indexes for table `message_queue`
--
ALTER TABLE `message_queue`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_message_queue_branch_id` (`branch_id`),
  ADD KEY `fk_message_queue_member_id` (`member_id`),
  ADD KEY `fk_message_queue_message_id` (`message_id`),
  ADD KEY `fk_message_queue_recipient_id` (`recipient_id`),
  ADD KEY `fk_message_queue_studio_id` (`studio_id`);

--
-- Indexes for table `message_recipient`
--
ALTER TABLE `message_recipient`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_recipient_message_id` (`message_id`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_payment_branch_id` (`branch_id`);

--
-- Indexes for table `plan`
--
ALTER TABLE `plan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `student_activity_assignment`
--
ALTER TABLE `student_activity_assignment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_student_id` (`student_id`);

--
-- Indexes for table `studio`
--
ALTER TABLE `studio`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name_location_key` (`name`,`location`);

--
-- Indexes for table `subscription`
--
ALTER TABLE `subscription`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_subscription_studio_id` (`studio_id`);

--
-- Indexes for table `template`
--
ALTER TABLE `template`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name_key` (`name`),
  ADD KEY `fk_branch_id` (`branch_id`),
  ADD KEY `fk_studio_id` (`studio_id`);

--
-- Indexes for table `user_access`
--
ALTER TABLE `user_access`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_jq49nvmp64sni8uhc348nvbgk` (`user_id`);

--
-- Indexes for table `whatsapp_web_session`
--
ALTER TABLE `whatsapp_web_session`
  ADD PRIMARY KEY (`client_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `activity`
--
ALTER TABLE `activity`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `activity_batch`
--
ALTER TABLE `activity_batch`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT for table `activity_membership_type`
--
ALTER TABLE `activity_membership_type`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `bank_account`
--
ALTER TABLE `bank_account`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `booking`
--
ALTER TABLE `booking`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `branch`
--
ALTER TABLE `branch`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `bulk_upload`
--
ALTER TABLE `bulk_upload`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `client`
--
ALTER TABLE `client`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `conditions`
--
ALTER TABLE `conditions`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `enquiries`
--
ALTER TABLE `enquiries`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `expense`
--
ALTER TABLE `expense`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT for table `forms`
--
ALTER TABLE `forms`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `generic_template`
--
ALTER TABLE `generic_template`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `genric_template`
--
ALTER TABLE `genric_template`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `instructor_activity_assignment`
--
ALTER TABLE `instructor_activity_assignment`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `members`
--
ALTER TABLE `members`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT for table `message`
--
ALTER TABLE `message`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=309;

--
-- AUTO_INCREMENT for table `message_queue`
--
ALTER TABLE `message_queue`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=128;

--
-- AUTO_INCREMENT for table `message_recipient`
--
ALTER TABLE `message_recipient`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=143;

--
-- AUTO_INCREMENT for table `payment`
--
ALTER TABLE `payment`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT for table `plan`
--
ALTER TABLE `plan`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `student_activity_assignment`
--
ALTER TABLE `student_activity_assignment`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT for table `studio`
--
ALTER TABLE `studio`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `subscription`
--
ALTER TABLE `subscription`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `template`
--
ALTER TABLE `template`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `user_access`
--
ALTER TABLE `user_access`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `activity`
--
ALTER TABLE `activity`
  ADD CONSTRAINT `fk_activity_branch_id` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`id`);

--
-- Constraints for table `activity_batch`
--
ALTER TABLE `activity_batch`
  ADD CONSTRAINT `fk_activity_activity_id` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`);

--
-- Constraints for table `activity_membership_type`
--
ALTER TABLE `activity_membership_type`
  ADD CONSTRAINT `fk_activity_membership_type_studio_id` FOREIGN KEY (`studio_id`) REFERENCES `studio` (`id`);

--
-- Constraints for table `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `fk_booking_branch_id` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`id`),
  ADD CONSTRAINT `fk_booking_client_id` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`);

--
-- Constraints for table `branch`
--
ALTER TABLE `branch`
  ADD CONSTRAINT `fk_branch_studio_id` FOREIGN KEY (`studio_id`) REFERENCES `studio` (`id`);

--
-- Constraints for table `bulk_upload`
--
ALTER TABLE `bulk_upload`
  ADD CONSTRAINT `FKgsvulphmkclwmu65dihntw2de` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`id`);

--
-- Constraints for table `client`
--
ALTER TABLE `client`
  ADD CONSTRAINT `fk_client_branch_id` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`id`);

--
-- Constraints for table `enquiries`
--
ALTER TABLE `enquiries`
  ADD CONSTRAINT `fk_enquiry_branch_id` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`id`);

--
-- Constraints for table `expense`
--
ALTER TABLE `expense`
  ADD CONSTRAINT `fk_expense_branch_id` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`id`);

--
-- Constraints for table `forms`
--
ALTER TABLE `forms`
  ADD CONSTRAINT `fk_form_branch_id` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`id`);

--
-- Constraints for table `generic_template`
--
ALTER TABLE `generic_template`
  ADD CONSTRAINT `fk_condition_studio_id` FOREIGN KEY (`studio_id`) REFERENCES `studio` (`id`);

--
-- Constraints for table `instructor_activity_assignment`
--
ALTER TABLE `instructor_activity_assignment`
  ADD CONSTRAINT `fk_instructor_id` FOREIGN KEY (`instructor_id`) REFERENCES `members` (`id`);

--
-- Constraints for table `members`
--
ALTER TABLE `members`
  ADD CONSTRAINT `fk_member_branch_id` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`id`);

--
-- Constraints for table `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `fk_message_branch_id` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`id`);

--
-- Constraints for table `message_queue`
--
ALTER TABLE `message_queue`
  ADD CONSTRAINT `fk_message_queue_branch_id` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`id`),
  ADD CONSTRAINT `fk_message_queue_member_id` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`),
  ADD CONSTRAINT `fk_message_queue_message_id` FOREIGN KEY (`message_id`) REFERENCES `message` (`id`),
  ADD CONSTRAINT `fk_message_queue_recipient_id` FOREIGN KEY (`recipient_id`) REFERENCES `message_recipient` (`id`),
  ADD CONSTRAINT `fk_message_queue_studio_id` FOREIGN KEY (`studio_id`) REFERENCES `studio` (`id`);

--
-- Constraints for table `message_recipient`
--
ALTER TABLE `message_recipient`
  ADD CONSTRAINT `fk_recipient_message_id` FOREIGN KEY (`message_id`) REFERENCES `message` (`id`);

--
-- Constraints for table `payment`
--
ALTER TABLE `payment`
  ADD CONSTRAINT `fk_payment_branch_id` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`id`);

--
-- Constraints for table `student_activity_assignment`
--
ALTER TABLE `student_activity_assignment`
  ADD CONSTRAINT `fk_student_id` FOREIGN KEY (`student_id`) REFERENCES `members` (`id`);

--
-- Constraints for table `subscription`
--
ALTER TABLE `subscription`
  ADD CONSTRAINT `fk_subscription_studio_id` FOREIGN KEY (`studio_id`) REFERENCES `studio` (`id`);

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `fk_branch_id` FOREIGN KEY (`branch_id`) REFERENCES `branch` (`id`),
  ADD CONSTRAINT `fk_studio_id` FOREIGN KEY (`studio_id`) REFERENCES `studio` (`id`);

--
-- Constraints for table `user_access`
--
ALTER TABLE `user_access`
  ADD CONSTRAINT `fk_user_access_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
