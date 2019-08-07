package somnium.sarafan.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import somnium.sarafan.domain.User;
import somnium.sarafan.enums.ServerStatus;
import somnium.sarafan.service.UserService;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Api(value = "Auth", description = "REST API for Auth", tags = {"Auth"})

public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping("/auth")
    public ResponseEntity login(@RequestBody User user){
        Map<String,Object> responseBody = new HashMap<>();
        if (user.getPassword() == null || user.getUsername() == null){
            responseBody.put("status", ServerStatus.ERROR);
            responseBody.put("message", "Bad request");
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
        User loggedUser = userService.getByUserName(user.getUsername());
        if(loggedUser == null){
            responseBody.put("status", ServerStatus.ERROR);
            responseBody.put("message", "No such user");
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        }

        if(!loggedUser.getPassword().equals(user.getPassword())){
            responseBody.put("status", ServerStatus.ERROR);
            responseBody.put("message", "Wrong password");
            return new ResponseEntity<>(responseBody, HttpStatus.FORBIDDEN);
        }

        String token  = userService.authenticate(loggedUser);
        responseBody.put("status",ServerStatus.SUCCESS);
        responseBody.put("message","Login successful");
        Map<String, Object> data = new HashMap<>();
        data.put("id", loggedUser.getId());
        data.put("fullname", loggedUser.getUsername());
        data.put("email", loggedUser.getEmail());
        data.put("activation_code", loggedUser.getActivationCode());
        data.put("is_admin", loggedUser.getAdmin());
        data.put("auth_token", token);
        responseBody.put("data", data);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
