package com.perseus.task.model;

import lombok.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@ToString
@Entity
@Setter
@Getter
@Builder
@Table(name = "phone_number")
@NoArgsConstructor
@AllArgsConstructor
public class Email extends BaseModel{

    @Column(nullable = false, unique = true)
    private String email;
}
