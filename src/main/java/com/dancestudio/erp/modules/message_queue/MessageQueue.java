package com.dancestudio.erp.modules.message_queue;

import com.dancestudio.erp.entity.BaseEntity;
import com.dancestudio.erp.entity.Branch;
import com.dancestudio.erp.entity.Member;
import com.dancestudio.erp.entity.Message;
import com.dancestudio.erp.entity.MessageRecipient;
import com.dancestudio.erp.entity.Studio;
import com.dancestudio.erp.enums.NotificationType;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "message_queue")
@EqualsAndHashCode(callSuper = true)
public class MessageQueue extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "message_id", nullable = false, foreignKey = @ForeignKey(name = "fk_message_queue_message_id"))
    private Message message;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false, foreignKey = @ForeignKey(name = "fk_message_queue_member_id"))
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "branch_id", nullable = false, foreignKey = @ForeignKey(name = "fk_message_queue_branch_id"))
    private Branch branch;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "studio_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_message_queue_studio_id"))
    private Studio studio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipient_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_message_queue_recipient_id"))
    private MessageRecipient recipient;

    @Column(name = "notification_type", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @Column(name = "file_path", nullable = true, length = 500)
    private String filePath;

    private int retries = 0;
}
