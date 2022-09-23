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
public class DeleteResponse {
    @NotNull(message = "owner_id is required")
    private Integer owner_id;

    @NotNull(message = "user_id is required")
    private Integer user_id;

    @NotNull(message = "slug is required")
    @Size(min = 3 , message = "slug is to short (min 3 character)")
    private String slug;
}