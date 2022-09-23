package com.alwaysbedream.survbackend.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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
import com.alwaysbedream.survbackend.validation.UserIdValidation;
import com.alwaysbedream.survbackend.validation.Response.CreateResponse;
import com.alwaysbedream.survbackend.validation.Response.DeleteResponse;

@RestController
@RequestMapping("/")
public class ResponseResource {
    @Autowired
    ResponseService responseService;
    
    @PutMapping("/response")
    public ResponseEntity<Map<String ,String>> createResponse(@RequestBody @Valid CreateResponse responseMap){
        responseService.createResponse(responseMap);
        Map<String , String> map = new HashMap<>();
        map.put("message" , "Successfully Submit your Response");
        return new ResponseEntity<>(map , HttpStatus.OK);
    }
    @PostMapping("/response")
    public ResponseEntity<Map<String , List<ResponseForm>>> readUserResponses(@RequestBody @Valid UserIdValidation responseMap){
        List<ResponseForm> results = responseService.readResponseByUserId(responseMap);
        Map<String , List<ResponseForm>> map = new HashMap<>();
        map.put("data" , results);
        return new ResponseEntity<>(map , HttpStatus.OK);
    }
    @DeleteMapping("/response")
    public ResponseEntity<Map<String ,String>> deleteResponse(@RequestBody @Valid DeleteResponse responseMap){
        responseService.deleteResponse(responseMap);
        Map<String , String> map = new HashMap<>();
        map.put("message" , "Successfully Delete the Response");
        return new ResponseEntity<>(map , HttpStatus.OK);
    }
}
