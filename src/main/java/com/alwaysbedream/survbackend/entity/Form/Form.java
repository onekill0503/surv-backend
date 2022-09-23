package com.alwaysbedream.survbackend.entity.Form;

public class Form {
    private Integer id;
    private Integer user_id;
    private String title;
    private String description;
    private String slug;

    public Integer getUser_id() {
        return this.user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
    
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlug() {
        return this.slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Form(Integer id, Integer user_id, String title, String description, String slug) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.slug = slug;
    }
}
