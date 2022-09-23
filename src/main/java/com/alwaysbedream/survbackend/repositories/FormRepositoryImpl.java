package com.alwaysbedream.survbackend.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;

import com.alwaysbedream.survbackend.entity.Form.Form;
import com.alwaysbedream.survbackend.exceptions.EtAuthException;
import com.alwaysbedream.survbackend.exceptions.EtBadRequestException;

@Repository
public class FormRepositoryImpl implements FormRepository {
    // create sql query template
    private static final String SQL_CREATE = "INSERT INTO \"Forms\" (user_id , title , description , slug) VALUES(?,?,?,?)";
    private static final String SQL_COUNT_BY_SLUG = "SELECT COUNT(*) FROM \"Forms\" WHERE slug = ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM \"Forms\" WHERE id = ?";
    private static final String SQL_FIND_USER_FORMS = "SELECT * FROM \"Forms\" WHERE user_id = ? LIMIT 10";
    private static final String SQL_FIND_BY_SLUG = "SELECT * FROM \"Forms\" WHERE slug = ?";
    private static final String SQL_UPDATE = "UPDATE \"Forms\" SET title = ? , description = ? WHERE id = ?";
    private static final String SQL_SEARCH_FORMS = "SELECT * FROM \"Forms\" WHERE user_id = ? AND (title LIKE ? OR description LIKE ?)";
    private static final String SQL_DELETE = "DELETE FROM \"Forms\" WHERE id = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer createForm(Integer user_id , String title , String description , String slug) throws EtAuthException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE , Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, user_id);
                ps.setString(2, title);
                ps.setString(3, description);
                ps.setString(4, slug);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("id");
        }catch(Exception e){
            throw new EtAuthException("Internal Server Error , Failed Create Form !");
        }
    }
    @Override
    public Integer getCountBySlug(String slug){
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_SLUG, new Object[]{slug} , Integer.class);
    }
    @Override
    public Form findById(Integer id) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{id} , formRowMapper);
    }
    @Override
    public List<Form> readUserForms(Integer user_id){
        return jdbcTemplate.query(SQL_FIND_USER_FORMS , new Object[]{user_id} , formRowMapper);
    }
    @Override
    public Form findBySlug(String slug){
        return jdbcTemplate.queryForObject(SQL_FIND_BY_SLUG , new Object[]{slug} , formRowMapper);
    }
    @Override
    public void updateForm(Integer form_id , String title , String description) throws EtBadRequestException{
        try {
            jdbcTemplate.update(SQL_UPDATE , new Object[]{ title , description , form_id});
        }catch(Exception e){
            throw new EtBadRequestException(e.getMessage());
        }
    }
    @Override
    public List<Form> searchForms(Integer user_id , String keyword){
        return jdbcTemplate.query(SQL_SEARCH_FORMS , new Object[]{user_id , "%" + keyword + "%" , "%" + keyword + "%"} , formRowMapper);
    }
    @Override
    public void deleteForm(Integer form_id){
        try {
            jdbcTemplate.update(SQL_DELETE , new Object[]{form_id});
        }catch(Exception e){
            throw new EtBadRequestException(e.getMessage());
        }
    }
    private RowMapper<Form> formRowMapper = ((rs , rowNum) -> {
        return new Form(rs.getInt("id") , rs.getInt("user_id"), rs.getString("title") , rs.getString("description") , rs.getString("slug"));
    });
}
