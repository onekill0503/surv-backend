package com.alwaysbedream.survbackend.services.Response;

import java.util.List;

import com.alwaysbedream.survbackend.entity.Response.Response;
import com.alwaysbedream.survbackend.entity.Response.ResponseForm;
import com.alwaysbedream.survbackend.exceptions.EtBadRequestException;
import com.alwaysbedream.survbackend.exceptions.EtResourceNotFoundException;
import com.alwaysbedream.survbackend.validation.UserIdValidation;
import com.alwaysbedream.survbackend.validation.Response.CreateResponse;
import com.alwaysbedream.survbackend.validation.Response.DeleteResponse;

public interface ResponseService {
    void deleteResponse(DeleteResponse responseData) throws EtBadRequestException;
    List<ResponseForm> readResponseByUserId(UserIdValidation user_id) throws EtResourceNotFoundException;
    Response createResponse(CreateResponse responseData) throws EtBadRequestException;
}
