package learn.carbackend.controllers;

import java.util.HashSet;
//import java.util.List;
import java.util.Optional;
import java.util.Set;
//import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import learn.carbackend.persistence.RoleEntity;
import learn.carbackend.persistence.RoleRepository;
import learn.carbackend.persistence.UserEntity;
import learn.carbackend.persistence.UserRepository;
import learn.carbackend.security.jwt.JwtUtils;
import learn.carbackend.security.request.LoginRequest;
import learn.carbackend.security.request.SignupRequest;
//import learn.carbackend.security.response.JwtResponse;
import learn.carbackend.security.response.MessageResponse;
//import learn.carbackend.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder encoder;

    @PostMapping(value = "/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Generate JWT token
        String jwt = jwtUtils.generateJwtToken(authentication);

        // UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // List<String> roles = userDetails.getAuthorities().stream()
        //                         .map(item -> item.getAuthority())
        //                         .collect(Collectors.toList());

        // return ResponseEntity.ok(new JwtResponse(jwt, 
        //                         userDetails.getId(),
        //                         userDetails.getUsername(), 
        //                         userDetails.getEmail(),
        //                         roles));
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
                                .build();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {

        Optional<UserEntity> user = userRepository.findByUsername(signupRequest.getUsername());
        if(user.isPresent()) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Username is already registered!"));
        }

        Set<String> strRoles = signupRequest.getRoles();
        Set<RoleEntity> roles = new HashSet<>();

        if(strRoles == null) {
            RoleEntity userRole = new RoleEntity();
            userRole = roleRepository.findByName("ROLE_USER");
            roles.add(userRole);

        } else {
            strRoles.forEach(strRole -> {
                switch (strRole) {
                case "admin":
                    RoleEntity adminRole = new RoleEntity();
                    adminRole = roleRepository.findByName("ROLE_ADMIN");
                    roles.add(adminRole);
                    break;

                case "mod":
                    RoleEntity modRole = new RoleEntity();
                    modRole = roleRepository.findByName("ROLE_MODERATOR");
                    roles.add(modRole);
                    break;

                case "user":
                    RoleEntity userRole = new RoleEntity();
                    userRole = roleRepository.findByName("ROLE_USER");
                    roles.add(userRole);
                    break;
                    
                default:
                    break;
                }
            });
        }

        //Create new user's account
        UserEntity userEntity = new UserEntity(signupRequest.getUsername(),
        signupRequest.getEmail(), encoder.encode(signupRequest.getPassword()), roles, signupRequest.getFirstName(), signupRequest.getLastName());
        
        userRepository.save(userEntity);

        return ResponseEntity.ok(new MessageResponse("User is successfully registered!"));         
    }
}
