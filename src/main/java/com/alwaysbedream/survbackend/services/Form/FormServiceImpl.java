package com.alwaysbedream.survbackend.services.Form;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alwaysbedream.survbackend.entity.Form.Form;
import com.alwaysbedream.survbackend.entity.Form.FormResponse;
import com.alwaysbedream.survbackend.entity.Response.Response;
import com.alwaysbedream.survbackend.exceptions.EtAuthException;
import com.alwaysbedream.survbackend.repositories.FormRepository;
import com.alwaysbedream.survbackend.repositories.ResponseRepository;
import com.alwaysbedream.survbackend.repositories.UserRepository;
import com.alwaysbedream.survbackend.utils.Slug;
import com.alwaysbedream.survbackend.validation.UserIdValidation;
import com.alwaysbedream.survbackend.validation.Form.CreateForm;
import com.alwaysbedream.survbackend.validation.Form.Search;
import com.alwaysbedream.survbackend.validation.Form.SlugValidation;
import com.alwaysbedream.survbackend.validation.Form.UpdateForm;

@Service
@Transactional
public class FormServiceImpl implements FormService{

    @Autowired
    FormRepository formRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ResponseRepository responseRepository;
    
    @Override
    public Form createForm(CreateForm formData){
        // check if user id exists;
        Integer userCount = userRepository.getCountById(formData.getUser_id());
        if(userCount < 1){
            throw new EtAuthException("User with id " + formData.getUser_id() + " Not Found");
        }
        // generate form slug
        String slug = Slug.generateSlug().toLowerCase();
        // check if form slug already on database;
        Integer count = formRepository.getCountBySlug(slug);
        while(count > 0){
            // if slug exists generate new slug till not exists in database
            slug = Slug.generateSlug().toLowerCase();
            count = formRepository.getCountBySlug(slug);
        }
        Integer form_id = formRepository.createForm(formData.getUser_id(), formData.getTitle(), formData.getDescription(), slug);
        return formRepository.findById(form_id);
    }
    @Override
    public FormResponse readFormBySlug(SlugValidation data){
        // check if slug is valid
        Integer formCount = formRepository.getCountBySlug(data.getSlug());
        if(formCount < 1) throw new EtAuthException("Form with slug " + data.getSlug() + " Not Found");
        // if slug valid get the form data by that slug
        Form form = formRepository.findBySlug(data.getSlug());
        List<Response> responses = responseRepository.readResponseByFormId(form.getId());
        // return data as FromResponse Entity
        return new FormResponse(form, responses);
    }
    @Override
    public void updateForm(UpdateForm Data){
        // check if slug is valid
        Integer formCount = formRepository.getCountBySlug(Data.getSlug());
        if(formCount < 1) throw new EtAuthException("Form with slug " + Data.getSlug() + " Not Found");
        // get form data
        Form formData = formRepository.findBySlug(Data.getSlug());
        // update form by request data
        formRepository.updateForm(formData.getId(), Data.getTitle(), Data.getDescription());
    }
    @Override
    public List<FormResponse> readUserForms(UserIdValidation Data){
        // check if user ud exists;
        Integer userCount = userRepository.getCountById(Data.getUser_id());
        if( userCount < 1 ) throw new EtAuthException("User with id " + Data.getUser_id() + " Not Found");
        // get User Forms
        List<Form> formList = formRepository.readUserForms(Data.getUser_id());
        // create List variabel
        List<FormResponse> results = new ArrayList<FormResponse>();
        for(Form form : formList){
            List<Response> getResponse = responseRepository.readResponseByFormId(form.getId());
            FormResponse data = new FormResponse(form, getResponse);
            results.add(data);
        }
        return results;
    }
    @Override
    public List<FormResponse> searchForms(Search Data){
        // get all data by keyword
        List<Form> formList = formRepository.searchForms(Data.getUser_id(),Data.getKeyword());
        // create List variabel
        List<FormResponse> results = new ArrayList<FormResponse>();
        for(Form form : formList){
            List<Response> getResponse = responseRepository.readResponseByFormId(form.getId());
            FormResponse data = new FormResponse(form, getResponse);
            results.add(data);
        }
        return results;
    }
    @Override
    public void deleteForm(SlugValidation Data){
        // check if form exist
        Integer formCount = formRepository.getCountBySlug(Data.getSlug());
        if(formCount < 1) throw new EtAuthException("Form with slug " + Data.getSlug() + " Not Found");
        // find form data
        Form form = formRepository.findBySlug(Data.getSlug());
        // delete form by form id
        formRepository.deleteForm(form.getId());
    }
}
