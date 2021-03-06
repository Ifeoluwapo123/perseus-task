package com.perseus.task.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.perseus.task.validator.annotation.ValidContact;
import com.perseus.task.validator.annotation.ValidContactType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ValidContact
@AllArgsConstructor
@NoArgsConstructor
public class UserContactRequest implements Serializable {

    @NotNull(message = "Please enter user id")
    private Long userId;

    @NotNull(message = "Please enter contact-type")
    @ValidContactType
    private String contactType;

    private String phoneNumber;

    @Email(message = "Please enter valid email")
    private String email;
}
