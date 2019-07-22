package somnium.sarafan.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import somnium.sarafan.domain.Product;
import somnium.sarafan.domain.User;
import somnium.sarafan.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("registration")
@Api(value="registration", description="Operations pertaining to users")
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation(value =  "Get all users", response = Iterable.class)
    @GetMapping
    private ResponseEntity<List<User>> findAllUsers(){
        return  ResponseEntity.ok(userService.getAllUsers());
    }

    @ApiOperation(value =  "Create a new user", response = Iterable.class)
    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody User user){
        return ResponseEntity.ok(userService.addUser(user));
    }

}
