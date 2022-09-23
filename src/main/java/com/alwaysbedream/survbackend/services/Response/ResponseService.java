package com.alwaysbedream.survbackend.services.Response;

import java.util.List;

import com.alwaysbedream.survbackend.entity.Response.Response;
import com.alwaysbedream.survbackend.entity.Response.ResponseForm;
import com.alwaysbedream.survbackend.exceptions.EtBadRequestException;
import com.alwaysbedream.survbackend.exceptions.EtResourceNotFoundException;

public interface ResponseService {
    void deleteResponse(Integer owner_id , Integer id , String slug) throws EtBadRequestException;
    List<ResponseForm> readResponseByUserId(Integer user_id) throws EtResourceNotFoundException;
    Response createResponse(Integer user_id , String slug , String name , Integer age , String hobby , String job) throws EtBadRequestException;
}
