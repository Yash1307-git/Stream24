package com.freelancers.Stream24.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freelancers.Stream24.Entity.LoginRequest;
import com.freelancers.Stream24.Entity.OtpRequest;
import com.freelancers.Stream24.Entity.User;
import com.freelancers.Stream24.Service.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class userController {

	 @Autowired
	    private UserService userService;

	    @PostMapping("/register")
	    public String registerUser(@RequestBody User user) {
	        userService.registerUser(user);
	        userService.generateAndSendOtp(user);
	        return "Registration successful, OTP sent to your email.";
	    }

	    @PostMapping("/login")
	    public String loginUser(@RequestBody LoginRequest loginRequest) {
	        User user = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
	        if (user != null && user.isOtpVerified()) {
	            return "Login successful";
	        } else if (user != null) {
	            return "OTP not verified. Please verify your OTP.";
	        } else {
	            return "Invalid credentials";
	        }
	    }
	    
	    

	    @PostMapping("/verify-otp")
	    public String verifyOtp(@RequestBody OtpRequest otpRequest) {
	        if (userService.verifyOtp(otpRequest.getEmail(), otpRequest.getOtp())) {
	            return "OTP verified successfully";
	        } else {
	            return "Invalid OTP or OTP expired";
	        }
	    }
	    


	    
	    @PutMapping("/update")
	    public String updateUser(@RequestBody User user) {
	        boolean isUpdated = userService.updateUserDetails(user);
	        if (isUpdated) {
	            return "User details updated successfully.";
	        } else {
	            return "Failed to update user details.";
	        }
	    }
}
