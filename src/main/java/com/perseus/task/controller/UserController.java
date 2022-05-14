package com.perseus.task.controller;

import com.perseus.task.model.User;
import com.perseus.task.payload.request.UserContactRequest;
import com.perseus.task.payload.request.UserCreationRequest;
import com.perseus.task.payload.response.MessageResponse;
import com.perseus.task.service.UserService;
import com.perseus.task.validator.annotation.ValidContact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<MessageResponse> createUser(@Valid @RequestBody UserCreationRequest req){
        return ResponseEntity.ok().body(userService.createUser(req));
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId){
        return ResponseEntity.ok().body(userService.getUserById(userId));
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Page<User>> getUserByName(@RequestParam("name") String name,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "8") int size){
        Pageable paging = PageRequest.of(page, size);
        return ResponseEntity.ok().body(userService.getUserByName(name, paging));
    }

    @PostMapping(value = "/contact/add")
    public ResponseEntity<MessageResponse> userAddNewContact(@Valid @RequestBody UserContactRequest req){
        return ResponseEntity.ok().body(userService.userAddContact(req));
    }

    @PutMapping(value = "/contact/update")
    public ResponseEntity<MessageResponse> userUpdateExistingContact(@Valid @RequestBody UserContactRequest req){
        return ResponseEntity.ok().body(userService.userAddContact(req));
    }

    @DeleteMapping(value = "/delete/{userId}")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable Long userId){
        return ResponseEntity.ok().body(userService.deleteUser(userId));
    }
}
