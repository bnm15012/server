Swagger UI URL - http://localhost:7000/studio-service/swagger-ui/index.html

Install java 17

Install and run mySQL DB on local

Host - 127.0.0.1
DB name - studio
userName - root
password - Lace*5465*1234
port - 3306


INSERT INTO `template` (`created_by`, `created_on`, `last_modified_on`, `version`, `body`, `name`, `subject`, `template_type`)
VALUES
('system', '2024-11-10 00:28:59', '2024-11-10 00:28:59', '0', 'Dear {studio_name},\n\nCongratulations! Your studio has been successfully registered.\nWe are thrilled to have you join us and look forward to helping your studio grow and connect with more dance enthusiasts.\n\nIf you have any questions or need assistance, please do not hesitate to reach out to our support team.\n\nWe have created a user with a dummy password below, please update as per your convenience.\n\nUsername: {username}\nPassword: {password}\n\nBest regards,\nBook & Manage Team', 'NEW_STUDIO_EMAIL', 'Welcome to Book & Manage !!', 'EMAIL'),
('system', '2024-11-10 01:04:39', '2024-11-10 01:04:39', '0', 'Hi {studio_name},\n\nYour details have been updated. Please check the dashboard to view the changes.\n\nBest regards,\nBook & Manage Team', 'UPDATE_STUDIO_EMAIL', 'Updated Studio Details', 'EMAIL'),
('system', '2024-11-10 02:23:35', '2024-11-10 02:23:35', '0', 'Dear {student_name},\n\nYour registration has been completed! We are thrilled to welcome you to {studio_name}.\n\nIf you have any questions or need assistance, please don’t hesitate to reach out to us. We’re here to help!\n\nLooking forward to seeing you in our classes!\n\nBest regards,\n{studio_name} Team', 'NEW_STUDENT_EMAIL', 'Welcome to Book & Manage !!', 'EMAIL'),
('system', '2024-11-14 02:22:10', '2024-11-14 02:22:10', '0', 'Dear {student_name},\n\nThis is a gentle reminder that your subscription is due for renewal. Please renew your {activity_type} membership to continue enjoying our services.\n\nBest regards,\n{studio_name} Team', 'SUBSCRIPTION_RENEWAL_REMINDER', 'Subscription Renewal Reminder', 'EMAIL');
