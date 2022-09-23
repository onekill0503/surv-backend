package com.alwaysbedream.survbackend.validation.Form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class SlugValidation {
    public SlugValidation(String slug) {
        this.slug = slug;
	}

	@NotNull(message = "slug is required")
    @Size(min = 3 , message = "slug is to short (min 3 character)")
    private String slug;
}
