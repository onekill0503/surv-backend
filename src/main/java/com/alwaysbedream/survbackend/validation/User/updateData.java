package com.alwaysbedream.survbackend.validation.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
public class updateData {
    @NotNull(message = "User ID is required")
    private Integer user_id;

    @NotNull(message = "Firstname is required")
    @Size(min = 3 , message = "Firstname to short (min 3 digits)")
    private String firstname;
    
    @NotNull(message = "Lastname is required")
    @Size(min = 3 , message = "Lastname to short (min 3 digits)")
    private String lastname;
    
    @NotNull(message = "Email is required")
    @Email(message = "Invalid Email Format")
    private String email;
}
