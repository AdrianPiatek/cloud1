package com.example.back.controller;

import com.example.back.dto.ErrorDTO;
import com.example.back.dto.UserCredentialsDTO;
import com.example.back.dto.UserInfoDTO;
import com.example.back.service.ResultService;
import com.example.back.service.UserService;
import com.example.back.utility.BidingResultError;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private ResultService resultService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserCredentialsDTO userCredentials, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(BidingResultError.getListErrorDTO(bindingResult));
        if (userService.existsByUsername(userCredentials.getUsername())) {
            return ResponseEntity.badRequest().body(new ErrorDTO("User with this username already exists"));
        }
        return ResponseEntity.ok(userService.register(userCredentials));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserCredentialsDTO userCredentials, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(BidingResultError.getListErrorDTO(bindingResult));
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<?> info(@RequestParam String username) {
        var user = userService.getByUsername(username);
        if (user.isEmpty())
            return ResponseEntity.badRequest().body(new ErrorDTO("User with this username dose not exists"));
        return ResponseEntity.ok(new UserInfoDTO(username, resultService.getUserResultsCount(user.get())));
    }
}
