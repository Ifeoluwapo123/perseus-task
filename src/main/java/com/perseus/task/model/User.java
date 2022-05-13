package com.perseus.task.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Table(name = "users", indexes = {
        @Index(name = "fn_index", columnList = "firstName"),
        @Index(name = "ln_index", columnList = "lastName")
})
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseModel{

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String firstName;

    @OneToMany
    @Column(name = "email_id")
    private List<Email> emails;

    @OneToMany
    @Column(name = "phone_number_id")
    private List<PhoneNumber> phoneNumbers;
}
