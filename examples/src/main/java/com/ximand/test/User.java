package com.ximand.test;

import com.ximand.annotation.Email;
import com.ximand.annotation.Password;
import com.ximand.annotation.UUID;
import com.ximand.annotation.Validatable;
import com.ximand.annotation.Validate;

@Validatable
public class User {

    @UUID(version = 4)
    @Validate
    private String id;

    @Email
    @Validate
    private String email;

    @Password
    @Validate
    private String password;

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
