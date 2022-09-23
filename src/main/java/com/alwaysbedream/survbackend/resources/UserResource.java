package com.alwaysbedream.survbackend.resources;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alwaysbedream.survbackend.entity.User.User;
import com.alwaysbedream.survbackend.exceptions.EtBadRequestException;
import com.alwaysbedream.survbackend.services.User.UserService;
import com.alwaysbedream.survbackend.utils.JWTUtil;
import com.alwaysbedream.survbackend.validation.User.Login;
import com.alwaysbedream.survbackend.validation.User.Register;
import com.alwaysbedream.survbackend.validation.User.changePassword;
import com.alwaysbedream.survbackend.validation.User.updateData;

@RestController
@Validated
@RequestMapping("/account")
public class UserResource {
    
    @Autowired
    UserService userService;

    @PutMapping("/register")
    public ResponseEntity<Map<String , String>> registerUser(@RequestBody @Valid Register userMap) {
        // call user services
        userService.registerUser(userMap);
        // create response data
        Map<String , String> map = new HashMap<>();
        map.put("message", "User Successfully Registered");
        return new ResponseEntity<>(map , HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody @Valid Login userMap) {
        // call user service
        User user = userService.validateUser(userMap);
        // create response data
        Map<String , String> map = new HashMap<>();
        map.put("message" , "Successfully Logged In !");
        map.put("token" , JWTUtil.generateToken(user));
        return new ResponseEntity<>(map , HttpStatus.OK);
    }
    @PostMapping("/retrive")
    public ResponseEntity<Map<String, Object>> retriveJWTToken(@RequestBody Map<String , Object> userMap) throws EtBadRequestException{
        String token = (String) userMap.get("token");
        // making response by parsed jwt token
        Map<String , Object> map = new HashMap<>();
        // create response data by parsed JWT Token
        map.put("data" , userService.parseJWTToken(token));
        return new ResponseEntity<>(map , HttpStatus.OK);
    }
    @PostMapping("/update")
    public ResponseEntity<Map<String, String>> updateUserData(@RequestBody @Valid updateData userMap){
        // update the user data
        User user = userService.updateData(userMap);
        Map<String , String> map = new HashMap<>();
        map.put("message" , "Successfully Update User Data !");
        map.put("token" , JWTUtil.generateToken(user));
        return new ResponseEntity<>(map , HttpStatus.OK);
    }
    @PostMapping("changepassword")
    public ResponseEntity<Map<String, String>> changeUserPassword(@RequestBody changePassword userMap) {
        // start to update the password
        userService.changePassword(userMap);
        // prepare the response data;
        Map<String , String> map = new HashMap<>();
        map.put("message" , "Successfully Update Your Password!");
        return new ResponseEntity<>(map , HttpStatus.OK);
    }
}
