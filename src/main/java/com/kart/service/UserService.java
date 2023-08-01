package com.kart.service;

import com.kart.model.AuthenticationToken;
import com.kart.model.User;
import com.kart.model.dto.SignInInput;
import com.kart.model.dto.SignUpOutput;
import com.kart.repository.IAuthenticationRepo;
import com.kart.repository.IUserRepo;
import com.kart.service.utility.EmailHandler;
import com.kart.service.utility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    IUserRepo userRepo;

    @Autowired
    IAuthenticationRepo authenticationRepo;
    public SignUpOutput userRegister(User user) {
        boolean signUpStatus = true;
        String signUpStatusMessage = null;

        String newEmail = user.getEmail();

        if(newEmail == null) {
            signUpStatusMessage = "Invalid email";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        //check if this patient email already exists ??
        User existingUser = userRepo.findFirstByEmail(newEmail);

        if(existingUser != null)
        {
            signUpStatusMessage = "Email already registered!!!";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(user.getPassword());

            //saveAppointment the patient with the new encrypted password

            user.setPassword(encryptedPassword);
            userRepo.save(user);

            return new SignUpOutput(signUpStatus, "User registered successfully!!!");
        }
        catch(Exception e)
        {
            signUpStatusMessage = "Internal error occurred during sign up";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }
    }

    public String userLogin(SignInInput signInInput) {
        String signInStatusMessage = null;

        String signInEmail = signInInput.getEmail();

        if(signInEmail == null)
        {
            signInStatusMessage = "Invalid email";
            return signInStatusMessage;


        }

        //check if this user email already exists ??
        User existingUser = userRepo.findFirstByEmail(signInEmail);

        if(existingUser == null)
        {
            signInStatusMessage = "Email not registered!!!";
            return signInStatusMessage;

        }

        //match passwords :

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());
            if(existingUser.getPassword().equals(encryptedPassword))
            {
                //session should be created since password matched and user id is valid
                AuthenticationToken authToken  = new AuthenticationToken(existingUser);
                authenticationRepo.save(authToken);

                EmailHandler.sendEmail(signInEmail,"email testing",authToken.getTokenValue());
                return "Token sent to your email";
            }
            else {
                signInStatusMessage = "Invalid credentials!!!";
                return signInStatusMessage;
            }
        }
        catch(Exception e)
        {
            signInStatusMessage = "Internal error occurred during sign in";
            return signInStatusMessage;
        }

    }

    public String userSignOut(String email) {
        User user = userRepo.findFirstByEmail(email);
        authenticationRepo.delete(authenticationRepo.findFirstByUser(user));
        return "Patient Signed out successfully";
    }
}
