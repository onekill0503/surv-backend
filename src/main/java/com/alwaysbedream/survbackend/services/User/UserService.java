package com.alwaysbedream.survbackend.services.User;

import com.alwaysbedream.survbackend.entity.User.User;
import com.alwaysbedream.survbackend.exceptions.EtAuthException;
import com.alwaysbedream.survbackend.exceptions.EtBadRequestException;

public interface UserService {
    User validateUser(String email,String password) throws EtAuthException;
    User registerUser(String firstname, String lastname,String email,String password) throws EtAuthException;
    User updateData(Integer user_id , String firstname , String lastname , String email) throws EtBadRequestException;
    void changePassword(Integer user_id , String new_password , String old_password) throws EtBadRequestException;
    Object parseJWTToken(String token) throws EtBadRequestException;
}
