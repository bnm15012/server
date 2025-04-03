package com.dancestudio.erp.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import com.dancestudio.erp.util.DateUtil;

import java.util.Date;

@MappedSuperclass
@Data
public abstract class BaseEntity {
    private static final long serialVersionUID = -1700105192677945854L;
    private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    protected Long id;

    @Column(name = "created_by", updatable = false, length = 50)
    protected String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name = "created_on", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name = "last_modified_on", nullable = false, insertable = true, updatable = true, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date lastModifiedOn;

    @Version
    @Column(name = "version", nullable = false, columnDefinition = "bigint DEFAULT 0")
    protected Long version = 0L;

    public BaseEntity(Long id, String createdBy) {
        this.id = id;
        this.createdBy = createdBy;
    }

    public BaseEntity(Long id, String createdBy, Date createdOn) {
        this.id = id;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
    }

    public BaseEntity() {
    }

    @PrePersist
    protected void onCreate() {
        this.lastModifiedOn = this.createdOn = this.createdOn == null ? DateUtil.getCurrentDateUTC() : this.createdOn;
        this.createdBy = this.createdBy != null && !this.createdBy.equals("") ? this.createdBy : "system";
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastModifiedOn = DateUtil.getCurrentDateUTC();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BaseEntity [id=").append(this.id).append(", createdBy=").append(this.createdBy).append(", createdOn=").append(this.createdOn).append(", lastModifiedOn=").append(this.lastModifiedOn).append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
}
