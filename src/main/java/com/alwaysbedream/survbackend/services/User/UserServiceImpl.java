package com.alwaysbedream.survbackend.services.User;

import java.util.regex.Pattern;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alwaysbedream.survbackend.entity.User.User;
import com.alwaysbedream.survbackend.exceptions.EtAuthException;
import com.alwaysbedream.survbackend.exceptions.EtBadRequestException;
import com.alwaysbedream.survbackend.repositories.UserRepository;
import com.alwaysbedream.survbackend.utils.Constants;
import com.alwaysbedream.survbackend.utils.JWTUtil;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    @Autowired
    UserRepository userRepository;
    
    @Override
    public Object parseJWTToken(String token){
        Object parsedToken = new Object();
        try {
            parsedToken = JWTUtil.retriveToken(token);
        }catch(Exception e){
            throw new EtBadRequestException("Invalid / Expired Token");
        }
        return parsedToken;
    }

    @Override
    public User validateUser(String email,String password){
        // create email pattern validation using regex
        Pattern pattern = Pattern.compile(Constants.EMAIL_VALIDATION_REGEX);
        // making email string to lowercase
        if(email != null) email = email.toLowerCase();
        // check email format
        if(!pattern.matcher(email).matches()){
            throw new EtAuthException("Invalid Email Format");
        }
        // return user data from userRepository
        return userRepository.findEmailAndPassword(email, password);
    }
    @Override
    public User registerUser(String firstname, String lastname,String email,String password){
        // create email pattern validation
        Pattern pattern = Pattern.compile(Constants.EMAIL_VALIDATION_REGEX);
        // making email string to lowercase
        if(email != null) email = email.toLowerCase();
        // check email format
        if(!pattern.matcher(email).matches()){
            throw new EtAuthException("Invalid Email Format");
        }
        // check if email not registered yet
        Integer count = userRepository.getCountByEmail(email);
        if(count > 0){
            throw new EtAuthException("Email already in use");
        }
        // create user then return the user data by id
        Integer user_id = userRepository.createUser(firstname, lastname, email, password);
        return userRepository.findById(user_id);
    }
    @Override
    public User updateData(Integer user_id, String firstname, String lastname, String email)
            throws EtBadRequestException {
        // simple validation
        if(user_id == null || firstname == null || lastname == null || email == null){
            throw new EtAuthException("There's Missing Field");
        }
        // check if user exists
        Integer userCount = userRepository.getCountById(user_id);
        if(userCount < 1) throw new EtBadRequestException("User with ID " + user_id + " is Not Found");
        // get current user Data
        User user = userRepository.findById(user_id);
        if(!email.equals(user.getEmail())){
            // check if email used or not
            Integer emailCount = userRepository.getCountByEmail(email);
            if(emailCount > 0) throw new EtBadRequestException("Email " + email + " already used in another account");
        }
        // update the user data
        userRepository.updateUser(user_id, firstname, lastname, email);
        return userRepository.findById(user_id);
    }
    @Override
    public void changePassword(Integer user_id, String new_password, String old_password) {
        // simple validation
        if(user_id == null || new_password == null || old_password == null){
            throw new EtAuthException("There's Missing Field");
        }
        // check if user exists
        Integer userCount = userRepository.getCountById(user_id);
        if(userCount < 1) throw new EtBadRequestException("User with ID " + user_id + " is Not Found");
        // get User Data
        User user = userRepository.findById(user_id);
        if(!BCrypt.checkpw(old_password, user.getPassword())){
            throw new EtAuthException("Old Password is Wrong !");
        }
        // create hashed password by new password;
        String hashedPassword = BCrypt.hashpw(new_password, BCrypt.gensalt(10));
        // update password
        userRepository.changePassword(user_id, hashedPassword);
    }
}
