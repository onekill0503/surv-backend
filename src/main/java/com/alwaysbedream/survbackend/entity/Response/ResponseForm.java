package com.alwaysbedream.survbackend.entity.Response;

import com.alwaysbedream.survbackend.entity.Form.Form;

public class ResponseForm {
    private Form formData;
    private Response formResponse;

    public Form getFormData() {
        return this.formData;
    }

    public void setFormData(Form formData) {
        this.formData = formData;
    }

    public Response getFormResponse() {
        return this.formResponse;
    }

    public void setFormResponse(Response formResponse) {
        this.formResponse = formResponse;
    }

    public ResponseForm(Form formData, Response formResponse) {
        this.formData = formData;
        this.formResponse = formResponse;
    }
}
