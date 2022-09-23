package com.alwaysbedream.survbackend.services.Form;

import java.util.List;

import com.alwaysbedream.survbackend.entity.Form.Form;
import com.alwaysbedream.survbackend.entity.Form.FormResponse;
import com.alwaysbedream.survbackend.exceptions.EtBadRequestException;
import com.alwaysbedream.survbackend.exceptions.EtResourceNotFoundException;

public interface FormService {
    Form createForm(Integer user_id , String title , String description) throws EtBadRequestException;
    List<FormResponse> readUserForms(Integer user_id) throws EtResourceNotFoundException;
    FormResponse readFormBySlug(String slug) throws EtResourceNotFoundException;
    void updateForm(String title , String description , String slug) throws EtBadRequestException;
    List<FormResponse> searchForms(Integer user_id,String keyword) throws EtResourceNotFoundException;
    void deleteForm(String slug) throws EtBadRequestException;
}
