package somnium.sarafan.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import somnium.sarafan.domain.User;
import somnium.sarafan.service.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@Api(value = "Users", description = "REST API for Users", tags = {"Users"})
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation(value =  "Get all users", response = Iterable.class)
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping()
    private ResponseEntity findAllUsers(){
        Map<String,Object> responseBody = new HashMap<>();
        Collection<User> data = userService.getAllUsers();
        responseBody.put("status", "SUCCESS");
        responseBody.put("message", "list of users");
        responseBody.put("data", data);
        return new  ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @ApiOperation(value =  "Create a new user", response = Iterable.class)
    @PostMapping("/registration")
    public ResponseEntity addUser(@Valid @RequestBody User user){
        Map<String,Object> responseBody = new HashMap<>();
        User newUser = userService.addUser(user);
        responseBody.put("status", "SUCCESS");
        responseBody.put("message", "user added");
        responseBody.put("data", newUser);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @ApiOperation(value = "Activate user account", response = Iterable.class)
    @GetMapping("/activate/{code}")
    public ResponseEntity activate( @PathVariable String code) {
        Map<String,Object> responseBody = new HashMap<>();
        User activatedUser = userService.activateUser(code);
        responseBody.put("status", "SUCCESS");
        responseBody.put("message", "user activated");
        responseBody.put("data", activatedUser);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
