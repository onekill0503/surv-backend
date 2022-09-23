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
import com.alwaysbedream.validation.User.Login;
import com.alwaysbedream.validation.User.Register;
import com.alwaysbedream.validation.User.changePassword;
import com.alwaysbedream.validation.User.updateData;

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
    public User validateUser(Login userData){
        // create email pattern validation using regex
        Pattern pattern = Pattern.compile(Constants.EMAIL_VALIDATION_REGEX);
        // making email string to lowercase
        if(userData.getEmail() != null) userData.setEmail(userData.getEmail().toLowerCase());
        // check email format
        if(!pattern.matcher(userData.getEmail()).matches()){
            throw new EtAuthException("Invalid Email Format");
        }
        // return user data from userRepository
        return userRepository.findEmailAndPassword(userData.getEmail(), userData.getPassword());
    }
    @Override
    public User registerUser(Register userData){
        // create email pattern validation
        Pattern pattern = Pattern.compile(Constants.EMAIL_VALIDATION_REGEX);
        // making email string to lowercase
        if(userData.getEmail() != null) userData.setEmail(userData.getEmail().toLowerCase());
        // check email format
        if(!pattern.matcher(userData.getEmail()).matches()){
            throw new EtAuthException("Invalid Email Format");
        }
        // check if email not registered yet
        Integer count = userRepository.getCountByEmail(userData.getEmail());
        if(count > 0){
            throw new EtAuthException("Email already in use");
        }
        // create user then return the user data by id
        Integer user_id = userRepository.createUser(userData.getFirstname(), userData.getLastname(), userData.getEmail(), userData.getPassword());
        return userRepository.findById(user_id);
    }
    @Override
    public User updateData(updateData userData)
            throws EtBadRequestException {
        // check if user exists
        Integer userCount = userRepository.getCountById(userData.getUser_id());
        if(userCount < 1) throw new EtBadRequestException("User with ID " + userData.getUser_id() + " is Not Found");
        // get current user Data
        User user = userRepository.findById(userData.getUser_id());
        if(!userData.getEmail().equals(user.getEmail())){
            // check if email used or not
            Integer emailCount = userRepository.getCountByEmail(userData.getEmail());
            if(emailCount > 0) throw new EtBadRequestException("Email " + userData.getEmail() + " already used in another account");
        }
        // update the user data
        userRepository.updateUser(userData.getUser_id(), userData.getFirstname(), userData.getLastname(), userData.getEmail());
        return userRepository.findById(userData.getUser_id());
    }
    @Override
    public void changePassword(changePassword userData) {
        // check if user exists
        Integer userCount = userRepository.getCountById(userData.getUser_id());
        if(userCount < 1) throw new EtBadRequestException("User with ID " + userData.getUser_id() + " is Not Found");
        // get User Data
        User user = userRepository.findById(userData.getUser_id());
        if(!BCrypt.checkpw(userData.getOld_password(), user.getPassword())){
            throw new EtAuthException("Old Password is Wrong !");
        }
        // create hashed password by new password;
        String hashedPassword = BCrypt.hashpw(userData.getNew_password(), BCrypt.gensalt(10));
        // update password
        userRepository.changePassword(userData.getUser_id(), hashedPassword);
    }
}
