package com.perseus.task.model;

import lombok.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@Builder
@Table(name = "phone_number")
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumber extends BaseModel{

    @Column(nullable = false)
    private String phoneNumber;
}
