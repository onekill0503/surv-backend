package com.alwaysbedream.survbackend.repositories;

import com.alwaysbedream.survbackend.entity.User.User;
import com.alwaysbedream.survbackend.exceptions.EtAuthException;

public interface UserRepository {
    Integer createUser(String firstname, String lastname,String email, String password) throws EtAuthException;
    User findEmailAndPassword(String email, String password) throws EtAuthException;
    User findById(Integer user_id);
    Integer getCountByEmail(String email);
    Integer getCountById(Integer user_id);
    void updateUser(Integer user_id , String firstname , String lastname , String email);
    void changePassword(Integer user_id , String password);
}
