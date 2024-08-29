package com.example.service.integration_app.entity;

import com.example.service.integration_app.model.EntityModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "entities")
public class DatabaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String name;

    private Instant date;

    public static DatabaseEntity from(EntityModel model ) {
        return new DatabaseEntity(model.getId(), model.getName(), model.getDate());
    }
}
