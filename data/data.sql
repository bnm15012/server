-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 02, 2025 at 05:35 PM
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
  `created_by` varchar(50) DEFAULT NULL,
  `created_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `last_modified_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `version` bigint(20) NOT NULL DEFAULT 0,
  `activity_type` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `membership_plans` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`membership_plans`)),
  `studio_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `activity`
--

INSERT INTO `activity` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `activity_type`, `description`, `membership_plans`, `studio_id`) VALUES
(1, 'system', '2025-02-02 10:16:52', '2025-02-02 10:16:52', 0, 'YOGA', 'yoga se hoga !', '[{\"membershipType\":\"MONTHLY\",\"amount\":1000.0}]', 1);

-- --------------------------------------------------------

--
-- Table structure for table `bank_account`
--

CREATE TABLE `bank_account` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `created_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `last_modified_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `version` bigint(20) NOT NULL DEFAULT 0,
  `account_number` varchar(255) DEFAULT NULL,
  `bank_name` varchar(255) DEFAULT NULL,
  `branch_name` varchar(255) DEFAULT NULL,
  `ifsc_code` varchar(255) DEFAULT NULL,
  `upi_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bank_account`
--

INSERT INTO `bank_account` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `account_number`, `bank_name`, `branch_name`, `ifsc_code`, `upi_id`) VALUES
(1, 'system', '2025-02-02 10:17:56', '2025-02-02 10:17:56', 0, '123456789', 'BOB', 'Dhubri', 'BARB0DBSAMR', 'johndoe@upi');

-- --------------------------------------------------------

--
-- Table structure for table `expense`
--

CREATE TABLE `expense` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `created_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `last_modified_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `version` bigint(20) NOT NULL DEFAULT 0,
  `amount` double NOT NULL,
  `description` varchar(255) NOT NULL,
  `expense_category` varchar(255) DEFAULT NULL,
  `expense_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `studio_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `instructor`
--

CREATE TABLE `instructor` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `created_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `last_modified_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `version` bigint(20) NOT NULL DEFAULT 0,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `profile_image` varchar(255) DEFAULT NULL,
  `bank_account_id` bigint(20) DEFAULT NULL,
  `studio_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `instructor`
--

INSERT INTO `instructor` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `email`, `name`, `phone`, `profile_image`, `bank_account_id`, `studio_id`) VALUES
(1, 'system', '2025-02-02 10:17:56', '2025-02-02 10:17:56', 0, 'abc@mail.com', 'John Doe', '1234567890', '', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `instructor_activity_assignment`
--

CREATE TABLE `instructor_activity_assignment` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `created_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `last_modified_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `version` bigint(20) NOT NULL DEFAULT 0,
  `assigned_date` date DEFAULT NULL,
  `end_date` date NOT NULL,
  `start_date` date NOT NULL,
  `activity_id` bigint(20) NOT NULL,
  `instructor_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `instructor_activity_assignment`
--

INSERT INTO `instructor_activity_assignment` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `assigned_date`, `end_date`, `start_date`, `activity_id`, `instructor_id`) VALUES
(1, 'system', '2025-02-02 11:48:18', '2025-02-02 11:48:18', 0, '2025-02-02', '2025-02-28', '2025-02-08', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `created_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `last_modified_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `version` bigint(20) NOT NULL DEFAULT 0,
  `amount` double DEFAULT NULL,
  `payee_id` bigint(20) DEFAULT NULL,
  `payee_type` varchar(255) NOT NULL,
  `payment_date` date DEFAULT NULL,
  `payment_type` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `studio_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `amount`, `payee_id`, `payee_type`, `payment_date`, `payment_type`, `status`, `studio_id`) VALUES
(1, 'system', '2025-02-02 11:49:37', '2025-02-02 11:49:37', 0, 100, 0, 'INSTRUCTOR', '2025-02-02', 'CASH', 'PENDING', 1);

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `created_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `last_modified_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `version` bigint(20) NOT NULL DEFAULT 0,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `profile_image` varchar(255) DEFAULT NULL,
  `studio_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `email`, `name`, `phone`, `profile_image`, `studio_id`) VALUES
(1, 'system', '2025-02-02 11:48:48', '2025-02-02 11:48:48', 0, 'dhruv20345@gmail.com', 'dhrruv', '9409434932', NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `student_activity_assignment`
--

CREATE TABLE `student_activity_assignment` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `created_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `last_modified_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `version` bigint(20) NOT NULL DEFAULT 0,
  `membership_end_date` date NOT NULL,
  `membership_start_date` date NOT NULL,
  `membership_type` varchar(255) NOT NULL,
  `registration_date` date NOT NULL,
  `activity_id` bigint(20) NOT NULL,
  `student_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student_activity_assignment`
--

INSERT INTO `student_activity_assignment` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `membership_end_date`, `membership_start_date`, `membership_type`, `registration_date`, `activity_id`, `student_id`) VALUES
(1, 'system', '2025-02-02 11:49:18', '2025-02-02 11:49:18', 0, '2025-02-28', '2025-02-13', 'MONTHLY', '2025-02-02', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `studio`
--

CREATE TABLE `studio` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `created_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `last_modified_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `version` bigint(20) NOT NULL DEFAULT 0,
  `contact` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `logo` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `studio`
--

INSERT INTO `studio` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `contact`, `location`, `logo`, `name`) VALUES
(1, 'system', '2025-02-02 09:34:26', '2025-02-02 09:34:26', 0, '', 'Gandhinagar', NULL, 'test');

-- --------------------------------------------------------

--
-- Table structure for table `subscription_plan`
--

CREATE TABLE `subscription_plan` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `created_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `last_modified_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `version` bigint(20) NOT NULL DEFAULT 0,
  `end_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `price` decimal(38,2) NOT NULL,
  `renewal_date` timestamp NULL DEFAULT current_timestamp(),
  `start_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `status` varchar(255) NOT NULL,
  `subscription_plan` varchar(255) NOT NULL,
  `studio_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `template`
--

CREATE TABLE `template` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `created_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `last_modified_on` timestamp NOT NULL DEFAULT current_timestamp(),
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
(1, 'system', '2024-11-09 18:58:59', '2024-11-09 18:58:59', 0, 'Dear {studio_name},\n\nCongratulations! Your studio has been successfully registered.\nWe are thrilled to have you join us and look forward to helping your studio grow and connect with more dance enthusiasts.\n\nIf you have any questions or need assistance, please do not hesitate to reach out to our support team.\n\nWe have created a user with a dummy password below, please update as per your convenience.\n\nUsername: {username}\nPassword: {password}\n\nBest regards,\nBook & Manage Team', 'NEW_STUDIO_EMAIL', 'Welcome to Book & Manage !!', 'EMAIL'),
(2, 'system', '2024-11-09 19:34:39', '2024-11-09 19:34:39', 0, 'Hi {studio_name},\n\nYour details have been updated. Please check the dashboard to view the changes.\n\nBest regards,\nBook & Manage Team', 'UPDATE_STUDIO_EMAIL', 'Updated Studio Details', 'EMAIL'),
(3, 'system', '2024-11-09 20:53:35', '2024-11-09 20:53:35', 0, 'Dear {student_name},\n\nYour registration has been completed! We are thrilled to welcome you to {studio_name}.\n\nIf you have any questions or need assistance, please don’t hesitate to reach out to us. We’re here to help!\n\nLooking forward to seeing you in our classes!\n\nBest regards,\n{studio_name} Team', 'NEW_STUDENT_EMAIL', 'Welcome to Book & Manage !!', 'EMAIL'),
(4, 'system', '2024-11-13 20:52:10', '2024-11-13 20:52:10', 0, 'Dear {student_name},\n\nThis is a gentle reminder that your subscription is due for renewal. Please renew your {activity_type} membership to continue enjoying our services.\n\nBest regards,\n{studio_name} Team', 'SUBSCRIPTION_RENEWAL_REMINDER', 'Subscription Renewal Reminder', 'EMAIL');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `created_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `last_modified_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `version` bigint(20) NOT NULL DEFAULT 0,
  `email` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(10) NOT NULL,
  `profile_image` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `studio_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `created_by`, `created_on`, `last_modified_on`, `version`, `email`, `enabled`, `name`, `password`, `phone`, `profile_image`, `role`, `studio_id`) VALUES
(1, 'system', '2025-02-02 09:34:26', '2025-02-02 09:34:26', 0, 'dhruv20345@gmail.com', b'1', 'dhruv4023', '$2a$10$ynSWVWElJFASOwWdQcD4P.HNgJSgyCeTJioGJw9krvj0yngBbypXC', '', NULL, 'MANAGER', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `activity`
--
ALTER TABLE `activity`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `activityType_studio_key` (`activity_type`,`studio_id`),
  ADD KEY `fk_activity_studio_id` (`studio_id`);

--
-- Indexes for table `bank_account`
--
ALTER TABLE `bank_account`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `expense`
--
ALTER TABLE `expense`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_expense_studio_id` (`studio_id`);

--
-- Indexes for table `instructor`
--
ALTER TABLE `instructor`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name_email_key` (`name`,`email`),
  ADD KEY `fk_instructor_bank_account_id` (`bank_account_id`),
  ADD KEY `fk_instructor_studio_id` (`studio_id`);

--
-- Indexes for table `instructor_activity_assignment`
--
ALTER TABLE `instructor_activity_assignment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_iaa_activity_id` (`activity_id`),
  ADD KEY `fk_instructor_id` (`instructor_id`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_payment_studio_id` (`studio_id`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name_email_key` (`name`,`email`),
  ADD KEY `fk_student_studio_id` (`studio_id`);

--
-- Indexes for table `student_activity_assignment`
--
ALTER TABLE `student_activity_assignment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_saa_activity_id` (`activity_id`),
  ADD KEY `fk_student_id` (`student_id`);

--
-- Indexes for table `studio`
--
ALTER TABLE `studio`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name_location_key` (`name`,`location`);

--
-- Indexes for table `subscription_plan`
--
ALTER TABLE `subscription_plan`
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
  ADD KEY `fk_studio_id` (`studio_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `activity`
--
ALTER TABLE `activity`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `bank_account`
--
ALTER TABLE `bank_account`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `expense`
--
ALTER TABLE `expense`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `instructor`
--
ALTER TABLE `instructor`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `instructor_activity_assignment`
--
ALTER TABLE `instructor_activity_assignment`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `payment`
--
ALTER TABLE `payment`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `student`
--
ALTER TABLE `student`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `student_activity_assignment`
--
ALTER TABLE `student_activity_assignment`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `studio`
--
ALTER TABLE `studio`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `subscription_plan`
--
ALTER TABLE `subscription_plan`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `template`
--
ALTER TABLE `template`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `activity`
--
ALTER TABLE `activity`
  ADD CONSTRAINT `fk_activity_studio_id` FOREIGN KEY (`studio_id`) REFERENCES `studio` (`id`);

--
-- Constraints for table `expense`
--
ALTER TABLE `expense`
  ADD CONSTRAINT `fk_expense_studio_id` FOREIGN KEY (`studio_id`) REFERENCES `studio` (`id`);

--
-- Constraints for table `instructor`
--
ALTER TABLE `instructor`
  ADD CONSTRAINT `fk_instructor_bank_account_id` FOREIGN KEY (`bank_account_id`) REFERENCES `bank_account` (`id`),
  ADD CONSTRAINT `fk_instructor_studio_id` FOREIGN KEY (`studio_id`) REFERENCES `studio` (`id`);

--
-- Constraints for table `instructor_activity_assignment`
--
ALTER TABLE `instructor_activity_assignment`
  ADD CONSTRAINT `fk_iaa_activity_id` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`),
  ADD CONSTRAINT `fk_instructor_id` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`id`);

--
-- Constraints for table `payment`
--
ALTER TABLE `payment`
  ADD CONSTRAINT `fk_payment_studio_id` FOREIGN KEY (`studio_id`) REFERENCES `studio` (`id`);

--
-- Constraints for table `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `fk_student_studio_id` FOREIGN KEY (`studio_id`) REFERENCES `studio` (`id`);

--
-- Constraints for table `student_activity_assignment`
--
ALTER TABLE `student_activity_assignment`
  ADD CONSTRAINT `fk_saa_activity_id` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`),
  ADD CONSTRAINT `fk_student_id` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`);

--
-- Constraints for table `subscription_plan`
--
ALTER TABLE `subscription_plan`
  ADD CONSTRAINT `fk_subscription_studio_id` FOREIGN KEY (`studio_id`) REFERENCES `studio` (`id`);

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `fk_studio_id` FOREIGN KEY (`studio_id`) REFERENCES `studio` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
