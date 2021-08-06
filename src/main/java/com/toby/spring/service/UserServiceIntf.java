package com.toby.spring.service;

import com.toby.spring.domain.User;

import java.util.List;
import java.util.concurrent.Future;

public interface UserServiceIntf {
    List<User> findAllUsers();

    User findUser(String id);

    boolean removeUser(String id);

    void removeAll();

    void registerUser(User user);
}