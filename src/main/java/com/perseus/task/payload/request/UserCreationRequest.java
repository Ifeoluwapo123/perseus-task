package com.perseus.task.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreationRequest implements Serializable {

    @Min(value = 3, message = "please enter a valid last name. Nick name not supported")
    @NotNull(message = "please enter your last name")
    private String lastName;

    @Min(value = 3, message = "please enter a valid last name. Nick name not supported")
    @NotNull(message = "please enter your first name")
    private String firstName;

    @Email(message = "please enter a valid email")
    @NotNull(message = "please enter your email")
    private String email;

    @NotNull(message = "please enter your phoneNumber")
    private String phoneNumber;
}
