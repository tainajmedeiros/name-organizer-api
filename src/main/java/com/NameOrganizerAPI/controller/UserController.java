package com.NameOrganizerAPI.controller;

import com.NameOrganizerAPI.model.User;
import com.NameOrganizerAPI.payload.Response;
import com.NameOrganizerAPI.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * This class represent the endpoint for this API.
 * You can add, list or find a user.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * This endpoint is for adding a new user.
     * Don’t allow duplicated names.
     * @param user
     * @return If the user don't exist in database the user is created and this method return 201. If the user exist in database the return is 400.
     */
    @ApiOperation(value = "Add a new User")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Returns the registered user", response = Response.class),
            @ApiResponse(code = 400, message = "An exception was thrown", response = Response.class) })
    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try {
            if (userService.add(user)) {
                return new ResponseEntity(
                        new Response(true, "Successfully registered user"),
                        HttpStatus.CREATED
                );
            }
            return new ResponseEntity(
                    new Response(true, "There is already a registered user with that name"),
                    HttpStatus.BAD_REQUEST
            );

        } catch (Exception e) {
            return new ResponseEntity(
                    new Response(false, "Bad request"),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    /**
     * This endpoint is for listing all the users.
     * @return a list of names.
     */
    @ApiOperation(value = "Finds a list users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns the list users", response = Response.class),
            @ApiResponse(code = 404, message = "User not found", response = Response.class),
            @ApiResponse(code = 400, message = "An exception was thrown", response = Response.class),
    })
    @GetMapping
    public ResponseEntity<?> listUsers() {
        try {
            List<User> users = userService.list();
            if(users == null){
                return new ResponseEntity<>(
                        new Response(false, "Not found users"),
                        HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new Response(false, "Bad request"),
                    HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This endpoint is for finding a specific user.
     * @param name
     * @return If it finds a user it returns true, if not it returns false
     */
    @ApiOperation(value = "Find user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns the information that the user exists or not", response = Response.class),
            @ApiResponse(code = 400, message = "An exception was thrown", response = Response.class),
    })
    @GetMapping("/{name}")
    public ResponseEntity<?> findUser(@RequestParam String name) {
        try {
            Optional<User> findUser = userService.findByName(name);
            if(findUser.isPresent()){
                return new ResponseEntity(
                        new Response(true, "User exist in database"),
                        HttpStatus.OK
                );
            }
            return new ResponseEntity(
                    new Response(true, "User don't exist in database"),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new Response(false, "Bad request"),
                    HttpStatus.BAD_REQUEST);
        }
    }

}
