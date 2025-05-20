package org.example;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.hibernate.envers.Audited;

import java.time.Instant;

@Entity
@Audited
public class Message extends PanacheEntity {
    public String message;

    private Instant modifiedDate;

    @PrePersist
    @PreUpdate
    public void setModifiedDate() {
        this.modifiedDate = Instant.now(); // Set current timestamp
    }

    // Getters and Setters for MODIFIED_DATE
    public Instant getModifiedDate() {
        return modifiedDate;
    }
    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}