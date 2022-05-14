package com.perseus.task.service;

import com.perseus.task.model.User;
import com.perseus.task.payload.request.UserContactRequest;
import com.perseus.task.payload.request.UserCreationRequest;
import com.perseus.task.payload.request.UserUpdateContactRequest;
import com.perseus.task.payload.response.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    MessageResponse createUser(UserCreationRequest req);
    User getUserById(Long userId);
    Page<User> getUserByName(String name, Pageable pageable);
    MessageResponse userAddContact(UserContactRequest req);
    MessageResponse userUpdateContact(UserUpdateContactRequest req);
    MessageResponse deleteUser(Long userId);}
