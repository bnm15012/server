CREATE DATABASE IF NOT EXISTS studio;

SET GLOBAL event_scheduler = ON;

CREATE EVENT delete_old_messages
ON SCHEDULE EVERY 1 WEEK
DO
  DELETE
    FROM message_queue
   WHERE updated_at < NOW() - INTERVAL 7 DAY;



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
