package com.alwaysbedream.survbackend.validation.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class changePassword {
    @NotNull(message = "User ID is Required")
    private Integer user_id;

    @NotNull(message = "Old Password is required")
    @Size(min = 3 , message = "Old Password to short (min 3 digits)")
    private String old_password;
    
    @NotNull(message = "New Password is required")
    @Size(min = 3 , message = "New Password to short (min 3 digits)")
    private String new_password;
}