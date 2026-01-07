CREATE DATABASE IF NOT EXISTS studio;

SET GLOBAL event_scheduler = ON;

CREATE EVENT delete_old_messages
ON SCHEDULE EVERY 1 WEEK
DO
  DELETE
    FROM message_queue
   WHERE updated_at < NOW() - INTERVAL 7 DAY;


