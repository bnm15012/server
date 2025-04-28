package com.dancestudio.erp.entity;

import com.dancestudio.erp.enums.MessageType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "message")
@EqualsAndHashCode(callSuper = true)
public class Message extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false, foreignKey = @ForeignKey(name = "fk_message_branch_id"))
    private Branch branch;

    @Column(name = "send_to_all")
    private Boolean sendToAll = false;

    @Column(name = "title")
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 30)
    private MessageType type;

    @Column(name = "scheduled_at")
    private java.util.Date scheduledAt;
}
