package com.alwaysbedream.survbackend.resources;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alwaysbedream.survbackend.entity.User.User;
import com.alwaysbedream.survbackend.exceptions.EtBadRequestException;
import com.alwaysbedream.survbackend.services.User.UserService;
import com.alwaysbedream.survbackend.utils.JWTUtil;

@RestController
@RequestMapping("/account")
public class UserResource {
    
    @Autowired
    UserService userService;

    @PutMapping("/register")
    public ResponseEntity<Map<String , String>> registerUser(@RequestBody Map<String, Object> userMap) {
        // get all request data
        String firstname = (String) userMap.get("firstname");
        String lastname = (String) userMap.get("lastname");
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        // call user services
        userService.registerUser(firstname , lastname , email,password);
        // create response data
        Map<String , String> map = new HashMap<>();
        map.put("message", "User Successfully Registered");
        return new ResponseEntity<>(map , HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> userMap) {
        // get all request data
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        // call user service
        User user = userService.validateUser(email,password);
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
    public ResponseEntity<Map<String, String>> updateUserData(@RequestBody Map<String, Object> userMap){
        // get request data
        Integer user_id = (Integer) userMap.get("user_id");
        String firstname = (String) userMap.get("firstname");
        String lastname = (String) userMap.get("lastname");
        String email = (String) userMap.get("email");
        // update the user data
        User user = userService.updateData(user_id, firstname, lastname, email);
        Map<String , String> map = new HashMap<>();
        map.put("message" , "Successfully Update User Data !");
        map.put("token" , JWTUtil.generateToken(user));
        return new ResponseEntity<>(map , HttpStatus.OK);
    }
    @PostMapping("changepassword")
    public ResponseEntity<Map<String, String>> changeUserPassword(@RequestBody Map<String, Object> userMap) {
        Integer user_id = (Integer) userMap.get("user_id");
        String old_password = (String) userMap.get("old_password");
        String new_password = (String) userMap.get("new_password");
        // start to update the password
        userService.changePassword(user_id, new_password, old_password);
        // prepare the response data;
        Map<String , String> map = new HashMap<>();
        map.put("message" , "Successfully Update Your Password!");
        return new ResponseEntity<>(map , HttpStatus.OK);
    }
}
