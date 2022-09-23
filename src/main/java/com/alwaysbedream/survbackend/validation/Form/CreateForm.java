package com.alwaysbedream.survbackend.validation.Form;

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
public class CreateForm {
    @NotNull(message = "user_id is required")
    private Integer user_id;
    
    @NotNull(message = "title is required")
    @Size(min = 3 , message = "title is to short (min 3 character)")
    private String title;
    
    @NotNull(message = "description is required")
    @Size(min = 10 , message = "description is to short (min 10 character)")
    private String description;
}
