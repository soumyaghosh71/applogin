package com.example.applogin.dao.impl;

import com.example.applogin.bean.User;
import com.example.applogin.constants.Constants;
import com.example.applogin.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDaoImpl extends JdbcDaoSupport implements UserDao {

    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public User getUserById(String userId) {

        String sql = "SELECT cast(aes_decrypt(unhex(`user_password`), 'secret') as char(50)) FROM applogin.user where user_id=?";

        List<User> users =  getJdbcTemplate().query(sql, new Object[]{userId}, new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setUserId(userId);
                user.setPassword(rs.getString(1));
                return user;
            }

        });
        if(users.isEmpty())
            return null;
        else if (users.size() == 1)
            return users.get(0);
        else
            return null;
    }

    @Override
    public String createNewUser(User user) {

        if (getUserById(user.getUserId()) != null) {
            return Constants.REGISTER_FAILED_DUPLICATE_USERID;
        }

        String sql = "insert into user (user_id, user_password) values(?, hex(aes_encrypt(?, 'secret')))";
        int count = getJdbcTemplate().update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, user.getUserId());
                ps.setString(2, user.getPassword());
                return ps;
            }
        });

        if (count == 1) {
            return Constants.REGISTER_SUCCESS;
        }
        return Constants.REGISTER_FAILED;
    }
}