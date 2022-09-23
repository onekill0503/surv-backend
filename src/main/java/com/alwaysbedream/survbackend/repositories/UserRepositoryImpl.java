package com.alwaysbedream.survbackend.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.alwaysbedream.survbackend.entity.User.User;
import com.alwaysbedream.survbackend.exceptions.EtAuthException;
import com.alwaysbedream.survbackend.exceptions.EtBadRequestException;

@Repository
public class UserRepositoryImpl implements UserRepository{

    // create sql query tempalte
    private static final String SQL_CREATE = "INSERT INTO \"Users\" (firstname,lastname,email,password) VALUES(?,?,?,?)";
    private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM \"Users\" WHERE email = ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM \"Users\" WHERE id = ?";
    private static final String SQL_FIND_BY_EMAIL = "SELECT * FROM \"Users\" WHERE email = ?";
    private static final String SQL_COUNT_BY_ID = "SELECT COUNT(*) FROM \"Users\" WHERE id = ?";
    private static final String SQL_UPDATE_DATA = "UPDATE \"Users\" SET firstname = ? , lastname = ? , email = ? WHERE id = ?";
    private static final String SQL_UPDATE_PASSWORD = "UPDATE \"Users\" SET password = ? WHERE id = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer createUser(String firstname, String lastname,String email, String password) throws EtAuthException {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, firstname);
                ps.setString(2, lastname);
                ps.setString(3, email);
                ps.setString(4, hashedPassword);
                return ps;
            } , keyHolder);
            return (Integer) keyHolder.getKeys().get("id");
        }catch(Exception e){
            throw new EtAuthException("Internal Server Error, FAILED CREATE ACCOUNT.");
        }
    }
    @Override
    public User findEmailAndPassword(String email , String password) throws EtAuthException {
        try {
            // running query
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[]{email} , userRowMapper);
            if(!BCrypt.checkpw(password, user.getPassword())){
                throw new EtAuthException("Invalid Email / Password");
            }
            return user;
        }catch(Exception e){
            throw new EtAuthException("Invalid Email / Password");
        }
    }
    @Override
    public User findById(Integer user_id) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{user_id} , userRowMapper);
    }
    @Override
    public Integer getCountByEmail(String email) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, new Object[]{email}, Integer.class);
    }
    @Override
    public Integer getCountById(Integer user_id){
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_ID , new Object[]{user_id} , Integer.class);
    }

    @Override
    public void updateUser(Integer user_id, String firstname, String lastname, String email) {
        try {
            jdbcTemplate.update(SQL_UPDATE_DATA, new Object[]{firstname , lastname , email , user_id});
        }catch(Exception e){
            throw new EtBadRequestException(e.getMessage());
        }
        
    }
    @Override
    public void changePassword(Integer user_id, String password) {
        try {
            jdbcTemplate.update(SQL_UPDATE_PASSWORD, new Object[]{password , user_id});
        }catch(Exception e){
            throw new EtBadRequestException(e.getMessage());
        }
    }

    private RowMapper<User> userRowMapper = ((rs,rowNum) -> {
        return new User(rs.getInt("id"), rs.getString("firstname"),rs.getString("lastname"),rs.getString("email"),rs.getString("password"));
    });
}
