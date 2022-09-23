package com.alwaysbedream.validation.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
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
public class updateData {
    @NotNull(message = "User ID is required")
    @NotEmpty(message = "User ID can't be empty")
    private Integer user_id;

    @NotNull(message = "Firstname is required")
    @NotEmpty(message = "Firstname can't be empty")
    @Min(value = 3 , message = "Firstname to short (min 3 digits)")
    private String firstname;
    
    @NotNull(message = "Lastname is required")
    @NotEmpty(message = "Lastname can't be empty")
    @Min(value = 3 , message = "Lastname to short (min 3 digits)")
    private String lastname;
    
    @NotNull(message = "Email is required")
    @NotEmpty(message = "Email can't be empty")
    @Email(message = "Invalid Email Format")
    private String email;
}
