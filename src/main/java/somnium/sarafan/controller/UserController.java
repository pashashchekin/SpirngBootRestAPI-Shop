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
import java.util.List;
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
    public ResponseEntity findAllUsers(){
        Map<String,Object> responseBody = new HashMap<>();
        List<User> data = userService.getAllUsers();
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
        if (user.getUsername() == null || user.getPassword() == null || user.getEmail() == null){
            responseBody.put("status", "ERROR");
            responseBody.put("message", "Bad request");
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
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

    @PostMapping("/forgetPassword")
    public ResponseEntity forgetPassword(String email, String name){
        HashMap<String, Object> responseBody = new HashMap<>();
        User user = userService.getByUserName(name);

        if(!user.getUsername().equals(name)){
            responseBody.put("status", "ERROR");
            responseBody.put("message", "No such user");
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        }
        if(!user.getEmail().equals(email)){
            responseBody.put("status", "ERROR");
            responseBody.put("message", "Wrong email");
            return new ResponseEntity<>(responseBody, HttpStatus.FORBIDDEN);
        }

        User data = userService.forgetPassword(user);
        responseBody.put("status","SUCCESS");
        responseBody.put("message","Password reset");
        responseBody.put("data", data);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/changePassword")
    public ResponseEntity changePassword(String name, String oldPassword, String newPassword, String confirmPassword){
        User user = userService.getByUserName(name);
        HashMap<String, Object> responseBody = new HashMap<>();
        if(!user.getPassword().equals(oldPassword)){
            responseBody.put("status", "ERROR");
            responseBody.put("message", "Wrong password");
            return new ResponseEntity<>(responseBody, HttpStatus.FORBIDDEN);
        }
        if(!newPassword.equals(confirmPassword)){
            responseBody.put("status", "ERROR");
            responseBody.put("message","Passwords do not match");
            return new ResponseEntity<>(responseBody, HttpStatus.FORBIDDEN);
        }
        if (!user.getResetPassword()){
            responseBody.put("status", "ERROR");
            responseBody.put("message", "you cannot change your password");
            return new ResponseEntity<>(responseBody, HttpStatus.FORBIDDEN);
        }

        User data =  userService.changePassword(name,oldPassword,newPassword,confirmPassword);
        responseBody.put("status", "SUCCESS");
        responseBody.put("message", "password has been changed");
        responseBody.put("data", data);

        return new ResponseEntity<>(responseBody,HttpStatus.OK);
    }
}
