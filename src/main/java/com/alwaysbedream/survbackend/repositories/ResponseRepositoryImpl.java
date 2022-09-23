package com.alwaysbedream.survbackend.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.alwaysbedream.survbackend.entity.Response.Response;
import com.alwaysbedream.survbackend.exceptions.EtAuthException;
import com.alwaysbedream.survbackend.exceptions.EtBadRequestException;

@Repository
public class ResponseRepositoryImpl implements ResponseRepository{
    // create sql query tempalte
    private static final String SQL_CREATE = "INSERT INTO \"Responses\" (user_id , form_id , name , age , hobby , job) VALUES(?,?,?,?,?,?)";
    private static final String SQL_FIND_BY_FORMID = "SELECT * FROM \"Responses\" WHERE form_id = ?";
    private static final String SQL_FIND_BY_USERID = "SELECT * FROM \"Responses\" WHERE user_id = ?";
    private static final String SQL_DELETE = "DELETE FROM \"Responses\" WHERE id = ?";
    private static final String SQL_FIND_USER_FORM_ID = "SELECT * FROM \"Responses\" WHERE user_id = ? AND form_id = ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM \"Responses\" WHERE id = ?";
    private static final String SQL_COUNT_USER_FORM_ID = "SELECT COUNT(*) FROM \"Responses\" WHERE user_id = ? AND form_id = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Response> readResponseByFormId(Integer form_id){
        return jdbcTemplate.query(SQL_FIND_BY_FORMID , new Object[]{form_id} , responseRowMapper);
    }

    @Override
    public List<Response> readResponseByUserId(Integer user_id){
        return jdbcTemplate.query(SQL_FIND_BY_USERID , new Object[]{user_id} , responseRowMapper);
    }

    @Override
    public Integer createResponse(Integer user_id , Integer form_id , String name , Integer age , String hobby , String job){
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE , Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, user_id);
                ps.setInt(2, form_id);
                ps.setString(3, name);
                ps.setInt(4, age);
                ps.setString(5, hobby);
                ps.setString(6, job);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("id");
        }catch(Exception e){
            throw new EtAuthException(e.getMessage());
        }
    }

    @Override
    public void deleteResponse(Integer id){
        try {
            jdbcTemplate.update(SQL_DELETE , new Object[]{id});
        }catch(Exception e){
            throw new EtBadRequestException(e.getMessage());
        }
    }
    
    @Override
    public Response findByUserAndFormId(Integer user_id, Integer form_id) {
        return jdbcTemplate.queryForObject(SQL_FIND_USER_FORM_ID , new Object[]{user_id , form_id} , responseRowMapper);
    }
    public Integer getCountByUserAndFormId(Integer user_id , Integer form_id){
        return jdbcTemplate.queryForObject(SQL_COUNT_USER_FORM_ID , new Object[]{user_id , form_id} , Integer.class);
    }
    
    @Override
    public Response findById(Integer id){
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{id} , responseRowMapper);
    }
    
    private RowMapper<Response> responseRowMapper = ((rs , rowNum) -> {
        return new Response(rs.getInt("id") , rs.getInt("form_id") , rs.getInt("user_id") , rs.getString("name") , rs.getInt("age") , rs.getString("hobby") , rs.getString("job"));
    });
}
