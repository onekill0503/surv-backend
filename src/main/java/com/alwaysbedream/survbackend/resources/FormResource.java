package com.alwaysbedream.survbackend.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alwaysbedream.survbackend.entity.Form.FormResponse;
import com.alwaysbedream.survbackend.services.Form.FormService;
import com.alwaysbedream.survbackend.validation.UserIdValidation;
import com.alwaysbedream.survbackend.validation.Form.CreateForm;
import com.alwaysbedream.survbackend.validation.Form.Search;
import com.alwaysbedream.survbackend.validation.Form.SlugValidation;
import com.alwaysbedream.survbackend.validation.Form.UpdateForm;

@RestController
@RequestMapping("/")
public class FormResource {
    @Autowired
    FormService formService;

    @PutMapping("/form")
    public ResponseEntity<Map<String , String>> createForm(@RequestBody @Valid CreateForm formMap) {
        // call from service
        formService.createForm(formMap);
        // create server response data
        Map<String , String> map = new HashMap<>();
        map.put("message" , "Successfully Create Form");
        return new ResponseEntity<>(map , HttpStatus.OK);
    }
    @GetMapping("/form/{slug}")
    public ResponseEntity<Map<String ,FormResponse>> readForm(@PathVariable("slug") String slug){
        @Valid SlugValidation slugValidated = new SlugValidation(slug);
        // get formData using formService with slug parameter.
        FormResponse formData = formService.readFormBySlug(slugValidated);
        // create server response data
        Map<String, FormResponse> map = new HashMap<>();
        map.put("data", formData);
        return new ResponseEntity<>(map , HttpStatus.OK);
    }
    @PostMapping("/form")
    public ResponseEntity<Map<String, List<FormResponse>>> readUserForms(@RequestBody @Valid UserIdValidation formMap){
        // call form service to get list form by user_id
        List<FormResponse> forms = formService.readUserForms(formMap);
        // create server response data
        Map<String, List<FormResponse>> map = new HashMap<>();
        map.put("data", forms);
        return new ResponseEntity<>(map , HttpStatus.OK);
    }
    @PatchMapping("/form")
    public ResponseEntity<Map<String, String>> updateForm(@RequestBody @Valid UpdateForm formMap){
        // update the current data using req data
        formService.updateForm(formMap);
        // create server response data
        Map<String, String> map = new HashMap<>();
        map.put("message", "Successfully Update Form");
        return new ResponseEntity<>(map , HttpStatus.OK); 
    }
    @PostMapping("/forms/search")
    public ResponseEntity<Map<String,List<FormResponse>>> searchForm(@RequestBody @Valid Search formMap){
        // get search data
        List<FormResponse> results = formService.searchForms(formMap);
        // create server response data
        Map<String, List<FormResponse>> map = new HashMap<>();
        map.put("data", results);
        return new ResponseEntity<>(map , HttpStatus.OK); 
    }
    @DeleteMapping("/form/{slug}")
    public ResponseEntity<Map<String ,String>> deleteForm(@PathVariable("slug") String slug){
        @Valid SlugValidation slugValidated = new SlugValidation(slug);
        // delete the form
        formService.deleteForm(slugValidated);
        // create server response data
        Map<String, String> map = new HashMap<>();
        map.put("message", "Successfully Delete Form");
        return new ResponseEntity<>(map , HttpStatus.OK); 
    }
}
