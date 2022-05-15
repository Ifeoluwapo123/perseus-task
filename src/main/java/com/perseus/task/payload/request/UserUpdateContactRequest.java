package com.perseus.task.payload.request;

import com.perseus.task.validator.annotation.ValidContactType;
import com.perseus.task.validator.annotation.ValidUpdateContact;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ValidUpdateContact
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateContactRequest implements Serializable {
    @NotNull(message = "Please enter user id")
    private Long userId;

    @NotNull(message = "Please enter contact-type")
    @ValidContactType
    private String contactType;

    private String oldPhoneNumber;

    private String newPhoneNumber;

    @Email(message = "Please enter valid email")
    private String oldEmailContact;

    @Email(message = "Please enter valid email")
    private String newEmailContact;
}
