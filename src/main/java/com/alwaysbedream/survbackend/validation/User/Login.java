package com.alwaysbedream.survbackend.validation.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class Login {
    @NotNull(message = "password cant be empty")
    @Min(value = 3 , message = "Password to Short (min 3 digits)")
    private String password;
    
    @NotNull(message = "email cant be empty")
    @Email(message = "Invalid Email Format")
    private String email;
}
