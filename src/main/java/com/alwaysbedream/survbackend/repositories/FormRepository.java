package com.alwaysbedream.survbackend.repositories;

import java.util.List;

import com.alwaysbedream.survbackend.entity.Form.Form;
import com.alwaysbedream.survbackend.exceptions.EtAuthException;
import com.alwaysbedream.survbackend.exceptions.EtResourceNotFoundException;

public interface FormRepository {
    Integer createForm(Integer user_id , String title , String description , String slug) throws EtAuthException;
    Integer getCountBySlug(String slug);
    Form findById(Integer id);
    List<Form> readUserForms(Integer user_id) throws EtResourceNotFoundException;
    Form findBySlug(String slug);
    void updateForm(Integer form_id , String title , String description);
    List<Form> searchForms(Integer user_id , String keyword);
    void deleteForm(Integer form_id);
}
