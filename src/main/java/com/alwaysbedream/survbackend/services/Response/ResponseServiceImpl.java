package com.alwaysbedream.survbackend.services.Response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alwaysbedream.survbackend.entity.Form.Form;
import com.alwaysbedream.survbackend.entity.Response.Response;
import com.alwaysbedream.survbackend.entity.Response.ResponseForm;
import com.alwaysbedream.survbackend.exceptions.EtAuthException;
import com.alwaysbedream.survbackend.exceptions.EtBadRequestException;
import com.alwaysbedream.survbackend.repositories.FormRepository;
import com.alwaysbedream.survbackend.repositories.ResponseRepository;
import com.alwaysbedream.survbackend.repositories.UserRepository;

@Service
@Transactional
public class ResponseServiceImpl implements ResponseService{

    @Autowired
    ResponseRepository responseRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FormRepository formRepository;

    @Override
    public void deleteResponse(Integer owner_id , Integer user_id , String slug) {
        // simple validation
        if(user_id == null || slug == null) throw new EtAuthException("There's Missing Field");
        // check if user ID and form ID is exists
        Integer userCount = userRepository.getCountById(user_id);
        Integer formCount = formRepository.getCountBySlug(slug);
        if(userCount < 1 || formCount < 1) throw new EtAuthException("User or Form ID is Invalid");
        // get Form Data
        Form formData = formRepository.findBySlug(slug);
        // check if user the owner of form or not.
        if(owner_id != formData.getUser_id()) throw new EtAuthException("You are not the Owner of the form");
        System.out.println(formData.getId());
        // get the response data;
        Response responseData = responseRepository.findByUserAndFormId(user_id, formData.getId());
        
        // call the delete function
        responseRepository.deleteResponse(responseData.getId());
    }

    @Override
    public List<ResponseForm> readResponseByUserId(Integer user_id) {
        // create List Data using Response entity and get the data from responseRepository
        List<Response> responses = responseRepository.readResponseByUserId(user_id);
        // create new list data using ResponseForm entity
        List<ResponseForm> results = new ArrayList<ResponseForm>();
        // fetch each data and put it into new ResponseForm Entity then pushed into list
        for(Response response : responses){
            Form form = formRepository.findById(response.getForm_id());
            ResponseForm data = new ResponseForm(form, response);
            results.add(data);
        }
        // return result list
        return results;
    }

    @Override
    public Response createResponse(Integer user_id , String slug, String name, Integer age, String hobby, String job) {
        // Simple validation BY Me
        if(user_id == null || slug == null || name == null || age == null || hobby == null || job == null ) throw new EtAuthException("There's Missing Field");
        // check if User and Form exists
        Integer userCount = userRepository.getCountById(user_id);
        Integer formCount = formRepository.getCountBySlug(slug);
        if(userCount < 1 || formCount < 1) throw new EtAuthException("User or Form ID is Invalid");
        // get Form Data
        Form formData = formRepository.findBySlug(slug);
        // is user owner of the form ?
        if(user_id == formData.getUser_id()) throw new EtBadRequestException("You are the Owner of the Form !");
        // check if user already submit the response or not
        Integer responseCount = responseRepository.getCountByUserAndFormId(user_id, formData.getId());
        if(responseCount > 0) throw new EtAuthException("You cant submit a response more than one!");
        // create new response and store the response id into response_id variable
        System.out.println(job);
        Integer response_id = responseRepository.createResponse(user_id, formData.getId() , name, age, hobby, job);
        return responseRepository.findById(response_id);
    }
}
