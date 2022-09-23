package com.alwaysbedream.survbackend.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@RestController
@RequestMapping("/")
public class FormResource {
    @Autowired
    FormService formService;

    @PutMapping("/form")
    public ResponseEntity<Map<String , String>> createForm(@RequestBody Map<String , Object> formMap) {
        // get request data and store into variable
        Integer user_id = (Integer) formMap.get("user_id");
        String title = (String) formMap.get("title");
        String description = (String) formMap.get("description");
        // call from service
        formService.createForm(user_id, title, description);
        // create server response data
        Map<String , String> map = new HashMap<>();
        map.put("message" , "Successfully Create Form");
        return new ResponseEntity<>(map , HttpStatus.OK);
    }
    @GetMapping("/form/{slug}")
    public ResponseEntity<Map<String ,FormResponse>> readForm(@PathVariable("slug") String slug){
        // get formData using formService with slug parameter.
        FormResponse formData = formService.readFormBySlug(slug);
        // create server response data
        Map<String, FormResponse> map = new HashMap<>();
        map.put("data", formData);
        return new ResponseEntity<>(map , HttpStatus.OK);
    }
    @PostMapping("/form")
    public ResponseEntity<Map<String, List<FormResponse>>> readUserForms(@RequestBody Map<String , Object> formMap){
        // get request data
        Integer user_id = (Integer) formMap.get("user_id");
        // call form service to get list form by user_id
        List<FormResponse> forms = formService.readUserForms(user_id);
        // create server response data
        Map<String, List<FormResponse>> map = new HashMap<>();
        map.put("data", forms);
        return new ResponseEntity<>(map , HttpStatus.OK);
    }
    @PatchMapping("/form")
    public ResponseEntity<Map<String, String>> updateForm(@RequestBody Map<String , Object> formMap){
        // request data
        String slug = (String) formMap.get("slug");
        String title = (String) formMap.get("title");
        String description = (String) formMap.get("description");
        // update the current data using req data
        formService.updateForm(title, description, slug);
        // create server response data
        Map<String, String> map = new HashMap<>();
        map.put("message", "Successfully Update Form");
        return new ResponseEntity<>(map , HttpStatus.OK); 
    }
    @PostMapping("/forms/search")
    public ResponseEntity<Map<String,List<FormResponse>>> searchForm(@RequestBody Map<String , Object> formMap){
        // request data
        Integer user_id = (Integer) formMap.get("user_id");
        String keyword = (String) formMap.get("keyword");
        // get search data
        List<FormResponse> results = formService.searchForms(user_id,keyword);
        // create server response data
        Map<String, List<FormResponse>> map = new HashMap<>();
        map.put("data", results);
        return new ResponseEntity<>(map , HttpStatus.OK); 
    }
    @DeleteMapping("/form/{slug}")
    public ResponseEntity<Map<String ,String>> deleteForm(@PathVariable("slug") String slug){
        // delete the form
        formService.deleteForm(slug);
        // create server response data
        Map<String, String> map = new HashMap<>();
        map.put("message", "Successfully Delete Form");
        return new ResponseEntity<>(map , HttpStatus.OK); 
    }
}
