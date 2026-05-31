INSERT INTO student_invoice_token (token, created_at, expires_at, student_assignment_id)
SELECT
    UNHEX(REPLACE(UUID(), '-', '')),
    NOW(),
    DATE_ADD(NOW(), INTERVAL 30 DAY),
    id
FROM student_activity_assignment
WHERE membership_end_date > NOW();
