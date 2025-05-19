package org.example;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import org.hibernate.envers.Audited;

@Entity
@Audited
public class Message extends PanacheEntity {
    public String message;
}