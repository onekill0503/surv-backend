package com.alwaysbedream.survbackend.services.User;

import com.alwaysbedream.survbackend.entity.User.User;
import com.alwaysbedream.survbackend.exceptions.EtAuthException;
import com.alwaysbedream.survbackend.exceptions.EtBadRequestException;
import com.alwaysbedream.survbackend.validation.User.Login;
import com.alwaysbedream.survbackend.validation.User.Register;
import com.alwaysbedream.survbackend.validation.User.changePassword;
import com.alwaysbedream.survbackend.validation.User.updateData;

public interface UserService {
    User validateUser(Login userData) throws EtAuthException;
    User registerUser(Register userData) throws EtAuthException;
    User updateData(updateData userData) throws EtBadRequestException;
    void changePassword(changePassword userData) throws EtBadRequestException;
    Object parseJWTToken(String token) throws EtBadRequestException;
}
