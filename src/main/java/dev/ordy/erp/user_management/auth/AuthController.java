package dev.ordy.erp.user_management.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils){
        this.authenticationManager=authenticationManager;
        this.jwtUtils = jwtUtils;
    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginRequest.getUsername(), loginRequest.getPassword()
//                )
//        );
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String jwt = jwtUtils.generateJwtToken(authentication);
//        return ResponseEntity.ok(new JwtResponse(jwt));
//    }


    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login") //todo this part of code is for developing logs and response have to be change in production
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        logger.info("Login request received for username: {}", loginRequest.getUsername());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(), loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtils.generateJwtToken(authentication);
            logger.info("Login successful for username: {}", loginRequest.getUsername());

            return ResponseEntity.ok(new JwtResponse(jwt));

        } catch (Exception e) {
            logger.error("Login failed for username: {}", loginRequest.getUsername(), e);

            // Return a detailed error response
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Error: " + e.getMessage());
        }
    }


}
