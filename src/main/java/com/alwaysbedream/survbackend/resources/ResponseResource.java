package com.alwaysbedream.survbackend.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alwaysbedream.survbackend.entity.Response.ResponseForm;
import com.alwaysbedream.survbackend.services.Response.ResponseService;

@RestController
@RequestMapping("/")
public class ResponseResource {
    @Autowired
    ResponseService responseService;
    
    @PutMapping("/response")
    public ResponseEntity<Map<String ,String>> createResponse(@RequestBody Map<String , Object> responseMap){
        Integer user_id = (Integer) responseMap.get("user_id");
        String slug = (String) responseMap.get("form_slug");
        String name = (String) responseMap.get("name");
        Integer age = (Integer) responseMap.get("age");
        String hobby = (String) responseMap.get("hobby");
        String job = (String) responseMap.get("job");

        responseService.createResponse(user_id, slug, name, age, hobby, job);
        Map<String , String> map = new HashMap<>();
        map.put("message" , "Successfully Submit your Response");
        return new ResponseEntity<>(map , HttpStatus.OK);
    }
    @PostMapping("/response")
    public ResponseEntity<Map<String , List<ResponseForm>>> readUserResponses(@RequestBody Map<String , Object> responseMap){
        Integer user_id = (Integer) responseMap.get("user_id");
        List<ResponseForm> results = responseService.readResponseByUserId(user_id);
        Map<String , List<ResponseForm>> map = new HashMap<>();
        map.put("data" , results);
        return new ResponseEntity<>(map , HttpStatus.OK);
    }
    @DeleteMapping("/response")
    public ResponseEntity<Map<String ,String>> deleteResponse(@RequestBody Map<String , Object> responseMap){
        Integer owner_id = (Integer) responseMap.get("owner_id");
        Integer user_id = (Integer) responseMap.get("user_id");
        String slug = (String) responseMap.get("slug");
        responseService.deleteResponse(owner_id, user_id, slug);
        Map<String , String> map = new HashMap<>();
        map.put("message" , "Successfully Delete the Response");
        return new ResponseEntity<>(map , HttpStatus.OK);
    }
}
