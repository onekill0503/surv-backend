package com.alwaysbedream.survbackend.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import com.alwaysbedream.survbackend.utils.Constants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class AuthFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        // checking the header
        String authHeader = httpRequest.getHeader("Authorization");
        if(authHeader != null){
            String[] authHeaderArr = authHeader.split("Bearer");
            if(authHeaderArr.length > 1 && authHeaderArr[1] != null){
                String token = authHeaderArr[1];
                try{
                    Claims claims = Jwts.parser().setSigningKey(Constants.API_SECRET_KEY).parseClaimsJws(token).getBody();
                    httpRequest.setAttribute("USERID", Integer.parseInt(claims.get("user_id").toString()));
                }catch(Exception e){
                    httpResponse.sendError(HttpStatus.FORBIDDEN.value() , "Invalid/Expired Token");
                    return;
                }
            }else{
                httpResponse.sendError(HttpStatus.FORBIDDEN.value() , "Authorization must be Bearer [token]");
                return;
            }
        }else{
            httpResponse.sendError(HttpStatus.FORBIDDEN.value() , "Authorization is Missing");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    } 
    
}