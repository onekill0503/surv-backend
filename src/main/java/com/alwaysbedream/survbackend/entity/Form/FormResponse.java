package com.alwaysbedream.survbackend.entity.Form;

import java.util.List;

import com.alwaysbedream.survbackend.entity.Response.Response;

public class FormResponse {
    private Form formData;
    private List<Response> formResponse;

    public Form getFormData() {
        return this.formData;
    }

    public void setFormData(Form formData) {
        this.formData = formData;
    }

    public List<Response> getFormResponse() {
        return this.formResponse;
    }

    public void setFormResponse(List<Response> formResponse) {
        this.formResponse = formResponse;
    }

    public FormResponse(Form formData, List<Response> formResponse) {
        this.formData = formData;
        this.formResponse = formResponse;
    }
}
