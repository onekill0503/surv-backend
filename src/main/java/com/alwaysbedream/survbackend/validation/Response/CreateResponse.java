package com.alwaysbedream.survbackend.validation.Response;

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
public class CreateResponse {
    @NotNull(message = "user_id is required")
    private Integer user_id;

    @NotNull(message = "slug is required")
    @Size(min = 3,  message = "slug is to short (min 3 character)")
    private String slug;
    
    @NotNull(message = "name is required")
    @Size(min = 3,  message = "name is to short (min 3 character)")
    private String name;
    
    @NotNull(message = "age is required")
    private Integer age;

    @NotNull(message = "hobby is required")
    @Size(min = 3,  message = "hobby is to short (min 3 character)")
    private String hobby;

    @NotNull(message = "job is required")
    @Size(min = 3,  message = "job is to short (min 3 character)")
    private String job;
}
