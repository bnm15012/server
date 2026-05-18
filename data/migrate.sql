
BEGIN;
-- student_activity_assignment --
INSERT INTO member_active_status (member_id, earliest_start_date, latest_end_date)
SELECT 
    sa.student_id AS member_id,
    MIN(sa.membership_start_date) AS earliest_start_date,
    MAX(sa.membership_end_date) AS latest_end_date
FROM student_activity_assignment AS sa
JOIN members ON sa.student_id = members.id
WHERE members.member_type = 'STUDENT'
GROUP BY sa.student_id
ON DUPLICATE KEY UPDATE
    earliest_start_date = VALUES(earliest_start_date),
    latest_end_date = VALUES(latest_end_date);



-- student_activity_assignment --
INSERT INTO member_active_status (member_id, earliest_start_date, latest_end_date)
SELECT 
    sa.instructor_id AS member_id,
    MIN(sa.start_date) AS earliest_start_date,
    MAX(sa.end_date) AS latest_end_date
FROM instructor_activity_assignment AS sa
JOIN members ON sa.instructor_id = members.id
WHERE members.member_type = 'INSTRUCTOR'
GROUP BY sa.instructor_id
ON DUPLICATE KEY UPDATE
    earliest_start_date = VALUES(earliest_start_date),
    latest_end_date = VALUES(latest_end_date);




UPDATE payment SET transaction_type = 'CREDIT' WHERE transaction_type IS NULL;


INSERT INTO payment_student_activity (
    id,
    actual_amount,
    student_activity_assignment
)
SELECT
    p.id,
    p.actual_amount,
    p.payee_id
FROM payment p
WHERE p.payee_type = 'STUDENT';

INSERT INTO payment_booking (
    id,
    booking_id
)
SELECT
    p.id,
    p.payee_id
FROM payment p
WHERE p.payee_type = 'BOOKING';

ALTER TABLE payment
DROP COLUMN actual_amount,
DROP COLUMN payee_id,
DROP COLUMN payee_type;


UPDATE instructor_activity_assignment SET end_date = NULL WHERE end_date < '1000-01-01';

UPDATE member_active_status
SET latest_end_date = NULL
WHERE latest_end_date < '1000-01-01';

COMMIT;


-- done 
