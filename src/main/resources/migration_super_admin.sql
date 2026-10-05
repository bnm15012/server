-- Super Admin Migration
-- Password: SuperAdmin@123

INSERT INTO studio (name, location, logo, email, pass_code, contact, gst_number, 
                    configuration, amc_enabled, created_on, last_modified_on, created_by)
SELECT 'BookAndManage', 'Platform', '', 'bookandmanage@gmail.com', '', '', '', '{}', false, UTC_TIMESTAMP(), UTC_TIMESTAMP(), 'system'
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM studio WHERE name = 'BookAndManage');

INSERT INTO branch (name, studio_id, address, city, state, pincode, phone, 
                    is_active, whatsapp_status, created_on, last_modified_on, created_by)
SELECT 'Platform', s.id, '', '', '', '', '', 
       true, 'INACTIVE', UTC_TIMESTAMP(), UTC_TIMESTAMP(), 'system'
FROM studio s
WHERE s.name = 'BookAndManage'
AND NOT EXISTS (
    SELECT 1 FROM branch b WHERE b.studio_id = s.id AND b.name = 'Platform'
);

INSERT INTO user (name, email, phone, password, enabled, role, 
                  studio_id, branch_id, profile_image,
                  created_on, last_modified_on, created_by)
SELECT 'superadmin', 'bookandmanage@gmail.com', '0000000000',
       '$2y$10$o/br7bRx6pwsDDb2FXZttur2zj5pPxtsCkdiLuHtuuQHppKU79/eq',
       true, 'SUPER_ADMIN',
       s.id, b.id, NULL,
       UTC_TIMESTAMP(), UTC_TIMESTAMP(), 'system'
FROM studio s
JOIN branch b ON b.studio_id = s.id AND b.name = 'Platform'
WHERE s.name = 'BookAndManage'
AND NOT EXISTS (SELECT 1 FROM user WHERE name = 'superadmin');

INSERT INTO user_access (user_id, activity, communication, payments, 
                         expense, analysis, reports, enquiry)
SELECT u.id, 1, 1, 1, 1, 1, 1, 1
FROM user u
WHERE u.name = 'superadmin'
AND NOT EXISTS (SELECT 1 FROM user_access ua WHERE ua.user_id = u.id);
