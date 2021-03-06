package org.si.simple_login.repository.impl;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import org.si.simple_login.domain.User;
import org.si.simple_login.repository.UserAuthenticationDAO;
import org.si.simple_login.repository.exceptions.EmptyFieldException;
import org.si.simple_login.repository.exceptions.NonUniqueUserNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UserAuthenticationDAOSQL implements UserAuthenticationDAO {

    // stores the user name during the session; the string serves as the key for the value
    public static final String AUTHENTICATED_USER_NAME = "authenticatedUserName";

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserAuthenticationDAOSQL(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder){

        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkAuthentication(User userRequest){

        boolean result = false;

        List<User> users = jdbcTemplate.query(

                "SELECT * FROM user_login WHERE user_name = ?",
                new String[]{userRequest.getUserName()},
                (rs, rowNum) -> new User(rs.getString("user_name"),
                                         rs.getString("password"))
        );

        if(users.size() != 0){

            User user = users.get(0);

            if(passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {

                result = true;
                VaadinSession.getCurrent().setAttribute(AUTHENTICATED_USER_NAME, user.getUserName());
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNewUser(User userRequest) throws Exception {

        String userName = userRequest.getUserName();
        String email = userRequest.getEmail();
        String password = userRequest.getPassword();
        String role = "USER";

        // All form fields are required; if any of them is empty, throw an exception.
        if(userName.equals("")|| email.equals("") || password.equals("")){

            throw new EmptyFieldException();
        }

        try{
            // If the specified user_name (PK) is available for a new user,
            // insert the user object with encoded password; otherwise, rethrow an exception.
            userRequest.setPassword(passwordEncoder.encode(password));
            jdbcTemplate.update(

                "INSERT INTO user_login(user_name, email, password, role) VALUES(?, ?, ?,?)",
                userName, email, userRequest.getPassword(), role
            );
        } catch(DuplicateKeyException e){

            throw new NonUniqueUserNameException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void signOut(){

        VaadinSession.getCurrent().close();
        Page.getCurrent().reload();
    }
}
