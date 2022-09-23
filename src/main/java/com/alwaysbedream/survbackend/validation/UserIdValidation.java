package com.alwaysbedream.survbackend.validation;

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
public class UserIdValidation {
    @NotNull(message = "user_id is required")
    private Integer user_id;
}
