Swagger UI URL - http://localhost:7000/studio-service/swagger-ui/index.html

Install java 17

Install and run mySQL DB on local

Host - 127.0.0.1

DB name - studio

userName - root

password - Lace*5465*1234

port - 3306


CREATE TABLE `activity` (
`id` bigint NOT NULL AUTO_INCREMENT,
`created_by` varchar(50) DEFAULT NULL,
`created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`last_modified_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`version` bigint NOT NULL DEFAULT '0',
`activity_type` varchar(50) NOT NULL,
`description` varchar(255) DEFAULT NULL,
`studio_id` bigint NOT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `bank_account` (
`id` bigint NOT NULL AUTO_INCREMENT,
`created_by` varchar(50) DEFAULT NULL,
`created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`last_modified_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`version` bigint NOT NULL DEFAULT '0',
`account_number` varchar(255) DEFAULT NULL,
`bank_name` varchar(255) DEFAULT NULL,
`branch_name` varchar(255) DEFAULT NULL,
`ifsc_code` varchar(255) DEFAULT NULL,
`instructor_id` bigint DEFAULT NULL,
`upi_id` varchar(255) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE `instructor` (
`id` bigint NOT NULL AUTO_INCREMENT,
`created_by` varchar(50) DEFAULT NULL,
`created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`last_modified_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`version` bigint NOT NULL DEFAULT '0',
`bank_acocunt_id` bigint DEFAULT NULL,
`email` varchar(255) NOT NULL,
`name` varchar(255) NOT NULL,
`phone` varchar(10) NOT NULL,
`profile_image` varchar(255) DEFAULT NULL,
`status` varchar(50) NOT NULL,
`studio_id` bigint NOT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `instructor_activity_assignment` (
`id` bigint NOT NULL AUTO_INCREMENT,
`created_by` varchar(50) DEFAULT NULL,
`created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`last_modified_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`version` bigint NOT NULL DEFAULT '0',
`assigned_date` date DEFAULT NULL,
`activity_id` bigint DEFAULT NULL,
`instructor_id` bigint DEFAULT NULL,
PRIMARY KEY (`id`),
KEY `FKn4unu0x4o8vork3x73mu5ai4n` (`activity_id`),
KEY `FKfeunluwamplp3fi87y2ap0fx7` (`instructor_id`),
CONSTRAINT `FKfeunluwamplp3fi87y2ap0fx7` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`id`),
CONSTRAINT `FKn4unu0x4o8vork3x73mu5ai4n` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `membership_fee` (
`id` bigint NOT NULL AUTO_INCREMENT,
`created_by` varchar(50) DEFAULT NULL,
`created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`last_modified_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`version` bigint NOT NULL DEFAULT '0',
`fee_amount` double DEFAULT NULL,
`membership_type` varchar(50) NOT NULL,
`studio_id` bigint DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `payment` (
`id` bigint NOT NULL AUTO_INCREMENT,
`created_by` varchar(50) DEFAULT NULL,
`created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`last_modified_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`version` bigint NOT NULL DEFAULT '0',
`amount` double DEFAULT NULL,
`payee_id` bigint DEFAULT NULL,
`payee_type` varchar(50) NOT NULL,
`payment_date` date DEFAULT NULL,
`payment_type` varchar(50) NOT NULL,
`status` varchar(50) NOT NULL,
`studio_id` bigint DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `student` (
`id` bigint NOT NULL AUTO_INCREMENT,
`created_by` varchar(50) DEFAULT NULL,
`created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`last_modified_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`version` bigint NOT NULL DEFAULT '0',
`email` varchar(255) NOT NULL,
`membership_end_date` date NOT NULL,
`membership_start_date` date NOT NULL,
`membership_type` varchar(50) NOT NULL,
`name` varchar(255) NOT NULL,
`phone` varchar(10) NOT NULL,
`profile_image` varchar(255) DEFAULT NULL,
`registration_date` date DEFAULT NULL,
`status` varchar(50) NOT NULL,
`studio_id` bigint NOT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `student_activity_assignment` (
`id` bigint NOT NULL AUTO_INCREMENT,
`created_by` varchar(50) DEFAULT NULL,
`created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`last_modified_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`version` bigint NOT NULL DEFAULT '0',
`registration_date` date DEFAULT NULL,
`status` varchar(50) NOT NULL,
`activity_id` bigint DEFAULT NULL,
`student_id` bigint DEFAULT NULL,
PRIMARY KEY (`id`),
KEY `FKbv4dx6nv9ly8aavpxx87dpiqi` (`activity_id`),
KEY `FK9kaljjdo082w38nm5otp5xqyt` (`student_id`),
CONSTRAINT `FK9kaljjdo082w38nm5otp5xqyt` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
CONSTRAINT `FKbv4dx6nv9ly8aavpxx87dpiqi` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `studio` (
`id` bigint NOT NULL AUTO_INCREMENT,
`created_by` varchar(50) DEFAULT NULL,
`created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`last_modified_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`version` bigint NOT NULL DEFAULT '0',
`contact` varchar(255) DEFAULT NULL,
`location` varchar(255) DEFAULT NULL,
`logo` varchar(255) DEFAULT NULL,
`name` varchar(255) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user` (
`id` bigint NOT NULL AUTO_INCREMENT,
`created_by` varchar(50) DEFAULT NULL,
`created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`last_modified_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
`version` bigint NOT NULL DEFAULT '0',
`email` varchar(255) NOT NULL,
`name` varchar(255) DEFAULT NULL,
`password` varchar(255) DEFAULT NULL,
`phone` varchar(10) NOT NULL,
`role` varchar(255) DEFAULT NULL,
`studio_id` bigint DEFAULT NULL,
PRIMARY KEY (`id`),
UNIQUE KEY `UK7o518x1j1i4woesf8gxbaii15` (`name`,`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;