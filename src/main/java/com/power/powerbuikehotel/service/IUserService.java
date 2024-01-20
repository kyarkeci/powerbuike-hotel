package com.power.powerbuikehotel.service;

import com.power.powerbuikehotel.data.model.User;

import java.util.List;

public interface IUserService {
    User registerUser(User user);
    List<User> getUsers();
    void deleteUser(String email);
    User getUserByEmail(String email);
    public User registerAdminUser(User user);

    boolean existsByEmail(String email);
}
