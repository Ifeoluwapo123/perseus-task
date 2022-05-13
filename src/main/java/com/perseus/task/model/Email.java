package com.perseus.task.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Builder
@Table(name = "phone_number")
@NoArgsConstructor
@AllArgsConstructor
public class Email extends BaseModel{

    @Column(nullable = false, unique = true)
    private String email;


//    private User user;
}
