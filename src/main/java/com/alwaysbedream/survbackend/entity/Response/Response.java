package com.alwaysbedream.survbackend.entity.Response;

public class Response {
    private Integer id;
    private Integer form_id;
    private Integer user_id;
    private String name;
    private Integer age;
    private String hobby;
    private String job;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getForm_id() {
        return this.form_id;
    }

    public void setForm_id(Integer form_id) {
        this.form_id = form_id;
    }

    public Integer getUser_id() {
        return this.user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getHobby() {
        return this.hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getJob() {
        return this.job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Response(Integer id, Integer form_id, Integer user_id, String name, Integer age, String hobby,
            String job) {
        this.id = id;
        this.form_id = form_id;
        this.user_id = user_id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.job = job;
    }
}
