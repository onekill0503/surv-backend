package com.alwaysbedream.survbackend.validation.User;

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
public class Retrive {
    @NotNull(message = "token is required")
    private String token;
}
