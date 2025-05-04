package com.dancestudio.erp.entity;

import com.dancestudio.erp.enums.MessageStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "message_recipient")
@EqualsAndHashCode(callSuper = true)
public class MessageRecipient extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", nullable = false, foreignKey = @ForeignKey(name = "fk_recipient_message_id"))
    private Message message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, foreignKey = @ForeignKey(name = "fk_recipient_member_id"))
    private Member member;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MessageStatus status = MessageStatus.PENDING;

    @Column(name = "reason")
    private String reason;

}
