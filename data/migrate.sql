INSERT INTO invoice_token (token, created_at, expires_at, student_assignment_id)
SELECT
    UNHEX(REPLACE(UUID(), '-', '')),
    NOW(),
    DATE_ADD(NOW(), INTERVAL 30 DAY),
    id
FROM student_activity_assignment
WHERE membership_end_date > NOW();


INSERT INTO invoice_token (token, created_at, expires_at, booking_id)
SELECT
    UNHEX(REPLACE(UUID(), '-', '')),
    NOW(),
    DATE_ADD(NOW(), INTERVAL 30 DAY),
    id
FROM booking
WHERE booking.end_time > NOW();
