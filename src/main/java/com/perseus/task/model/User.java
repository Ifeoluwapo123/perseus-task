package com.perseus.task.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "email_id", referencedColumnName = "id")
    private List<Email> emails;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "phone_number_id", referencedColumnName = "id")
    private List<PhoneNumber> phoneNumbers;
}
