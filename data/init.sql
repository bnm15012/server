-- Ensure the database exists
CREATE DATABASE IF NOT EXISTS studio;
USE studio;

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
