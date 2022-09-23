package com.alwaysbedream.survbackend.services.Form;

import java.util.List;

import com.alwaysbedream.survbackend.entity.Form.Form;
import com.alwaysbedream.survbackend.entity.Form.FormResponse;
import com.alwaysbedream.survbackend.exceptions.EtBadRequestException;
import com.alwaysbedream.survbackend.exceptions.EtResourceNotFoundException;
import com.alwaysbedream.survbackend.validation.UserIdValidation;
import com.alwaysbedream.survbackend.validation.Form.CreateForm;
import com.alwaysbedream.survbackend.validation.Form.Search;
import com.alwaysbedream.survbackend.validation.Form.SlugValidation;
import com.alwaysbedream.survbackend.validation.Form.UpdateForm;

public interface FormService {
    Form createForm(CreateForm formData) throws EtBadRequestException;
    List<FormResponse> readUserForms(UserIdValidation user_id) throws EtResourceNotFoundException;
    FormResponse readFormBySlug(SlugValidation slug) throws EtResourceNotFoundException;
    void updateForm(UpdateForm formData) throws EtBadRequestException;
    List<FormResponse> searchForms(Search formData) throws EtResourceNotFoundException;
    void deleteForm(SlugValidation slug) throws EtBadRequestException;
}