package com.perseus.task.service;

import com.perseus.task.model.User;
import com.perseus.task.payload.request.UserCreationRequest;
import com.perseus.task.payload.response.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    MessageResponse createUser(UserCreationRequest req);
    User getUserById(Long userId);
    Page<User> getUserByName(String name, Pageable pageable);
}
