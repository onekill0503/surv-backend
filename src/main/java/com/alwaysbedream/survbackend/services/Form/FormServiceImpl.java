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
import com.alwaysbedream.survbackend.utils.Slug;;

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
    public Form createForm(Integer user_id , String title , String description){
        // simple validation by me XD
        if(user_id == null || title == null || description == null) {
            throw new EtAuthException("There's Missing Field !");
        }
        // check if user id exists;
        Integer userCount = userRepository.getCountById(user_id);
        if(userCount < 1){
            throw new EtAuthException("User with id " + user_id + " Not Found");
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
        Integer form_id = formRepository.createForm(user_id, title, description, slug);
        return formRepository.findById(form_id);
    }
    @Override
    public FormResponse readFormBySlug(String slug){
        // simple validation
        if(slug == null) throw new EtAuthException("There's Missing Field");
        // check if slug is valid
        Integer formCount = formRepository.getCountBySlug(slug);
        if(formCount < 1) throw new EtAuthException("Form with slug " + slug + " Not Found");
        // if slug valid get the form data by that slug
        Form form = formRepository.findBySlug(slug);
        List<Response> responses = responseRepository.readResponseByFormId(form.getId());
        // return data as FromResponse Entity
        return new FormResponse(form, responses);
    }
    @Override
    public void updateForm(String title , String description , String slug){
        // simple validation
        if(slug == null || title == null || description == null) {
            throw new EtAuthException("There's Missing Field !");
        }
        // check if slug is valid
        Integer formCount = formRepository.getCountBySlug(slug);
        if(formCount < 1) throw new EtAuthException("Form with slug " + slug + " Not Found");
        // get form data
        Form formData = formRepository.findBySlug(slug);
        // update form by request data
        formRepository.updateForm(formData.getId(), title, description);
    }
    @Override
    public List<FormResponse> readUserForms(Integer user_id){
        // simple validation
        if(user_id == null) throw new EtAuthException("There's Missing Field !");
        // check if user ud exists;
        Integer userCount = userRepository.getCountById(user_id);
        if( userCount < 1 ) throw new EtAuthException("User with id " + user_id + " Not Found");
        // get User Forms
        List<Form> formList = formRepository.readUserForms(user_id);
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
    public List<FormResponse> searchForms(Integer user_id , String keyword){
        // get all data by keyword
        List<Form> formList = formRepository.searchForms(user_id,keyword);
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
    public void deleteForm(String slug){
        // simple validation
        if(slug == null) throw new EtAuthException("There's Missing Field !");
        // check if form exist
        Integer formCount = formRepository.getCountBySlug(slug);
        if(formCount < 1) throw new EtAuthException("Form with slug " + slug + " Not Found");
        // find form data
        Form form = formRepository.findBySlug(slug);
        // delete form by form id
        formRepository.deleteForm(form.getId());
    }
}
