package com.alwaysbedream.survbackend.repositories;

import java.util.List;

import com.alwaysbedream.survbackend.entity.Response.Response;
import com.alwaysbedream.survbackend.exceptions.EtAuthException;
import com.alwaysbedream.survbackend.exceptions.EtBadRequestException;

public interface ResponseRepository {
    List<Response> readResponseByFormId(Integer form_id);
    List<Response> readResponseByUserId(Integer user_id);
    Integer createResponse(Integer user_id , Integer form_id , String name, Integer age , String hobby , String job) throws EtAuthException;
    Integer getCountByUserAndFormId (Integer user_id , Integer form_id);
    void deleteResponse(Integer id) throws EtBadRequestException;
    Response findByUserAndFormId(Integer user_id , Integer form_id);
    Response findById(Integer id);
}
