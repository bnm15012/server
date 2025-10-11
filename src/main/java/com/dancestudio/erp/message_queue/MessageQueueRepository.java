package com.dancestudio.erp.message_queue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageQueueRepository extends JpaRepository<MessageQueue, Long> {
    @Query(value = "SELECT mq.* FROM message_queue mq " +
            "INNER JOIN ( " +
            "    SELECT branch_id, MIN(id) AS min_id " +
            "    FROM message_queue " +
            "    WHERE notification_type = :type AND file_path IS NOT NULL " +
            "    GROUP BY branch_id " +
            "    LIMIT 5 " +
            ") sub ON mq.id = sub.min_id", nativeQuery = true)
    List<MessageQueue> findAllWithFileOnePerBranch(@Param("type") String type);

    @Query(value = "SELECT mq.* FROM message_queue mq " +
            "INNER JOIN ( " +
            "    SELECT branch_id, MIN(id) AS min_id " +
            "    FROM message_queue " +
            "    WHERE notification_type = :type " +
            "    GROUP BY branch_id " +
            "    LIMIT 5 " +
            ") sub ON mq.id = sub.min_id", nativeQuery = true)
    List<MessageQueue> findAllOnePerBranch(@Param("type") String type);

}
