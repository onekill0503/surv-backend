package com.alwaysbedream.validation.User;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
    @NotEmpty(message = "User ID can't be empty")
    private Integer user_id;

    @NotNull(message = "Old Password is required")
    @NotEmpty(message = "Old Password can't be empty")
    @Min(value = 3 , message = "Old Password to short (min 3 digits)")
    private String old_password;
    
    @NotNull(message = "New Password is required")
    @NotEmpty(message = "New Password can't be empty")
    @Min(value = 3 , message = "New Password to short (min 3 digits)")
    private String new_password;
}
