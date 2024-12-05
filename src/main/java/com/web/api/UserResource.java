package com.web.api;

import com.web.dto.*;
import com.web.entity.Authority;
import com.web.entity.User;
import com.web.exception.MessageException;
import com.web.jwt.JwtTokenProvider;
import com.web.repository.AuthorityRepository;
import com.web.repository.UserRepository;
import com.web.service.UserService;
import com.web.utils.Contains;
import com.web.utils.MailService;
import com.web.utils.UserUtils;
import org.apache.tomcat.util.http.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserResource {

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserUtils userUtils;

    private final MailService mailService;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResource(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, UserUtils userUtils, MailService mailService) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userUtils = userUtils;
        this.mailService = mailService;
    }

    @PostMapping("/login")
    public TokenDto authenticate(@RequestBody LoginDto loginDto) throws Exception {
        TokenDto tokenDto = userService.login(loginDto.getUsername(), loginDto.getPassword());
        return tokenDto;
    }

    @PostMapping("/regis")
    public ResponseEntity<?> regisUser(@RequestBody User user) throws URISyntaxException {
        User result= userService.regisUser(user);
        return ResponseEntity
                .created(new URI("/api/register-user/" + user.getUsername()))
                .body(result);
    }

    @PostMapping("/active-account")
    public ResponseEntity<?> activeAccount(@RequestParam String email, @RequestParam String key) throws URISyntaxException {
        userService.activeAccount(key, email);
        return new ResponseEntity<>("kích hoạt thành công", HttpStatus.OK);
    }

    @GetMapping("/admin/check-role-admin")
    public void checkRoleAdmin(){
        System.out.println("admin");
    }

    @GetMapping("/user/check-role-user")
    public void checkRoleUser(){
        System.out.println("user");
    }


    @GetMapping("/admin/get-user-by-role")
    public ResponseEntity<?> getUserByRole(@RequestParam(value = "role", required = false) String role){
        List<User> list = new ArrayList<>();
        if(role != null){
            list = userRepository.getUserByRole(role);
        }
        else{
            list = userRepository.findAll();
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/admin/lockOrUnlockUser")
    public void activeOrUnactiveUser(@RequestParam("id") Long id){
        User user = userRepository.findById(id).get();
        if(user.getActived() == true){
            user.setActived(false);
            userRepository.save(user);
            return;
        }
        else{
            user.setActived(true);
            userRepository.save(user);
        }
    }

    @PostMapping("/admin/addaccount")
    public ResponseEntity<?> addaccount(@RequestBody User user) {
        userRepository.findByEmail(user.getEmail())
                .ifPresent(exist->{
                    throw new MessageException("Email đã được sử dụng", 400);
                });
        user.setCreatedDate(new Date(System.currentTimeMillis()));
        user.setUsername(user.getEmail());
        user.setPoint(0);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActived(true);
        Authority authority = authorityRepository.findById(Contains.ROLE_ADMIN).get();
        user.setAuthorities(authority);
        User result = userRepository.save(user);
        return new ResponseEntity<>(result,HttpStatus.CREATED);

    }


    @PostMapping("/user/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordDto passwordDto){
        userService.changePass(passwordDto.getOldPass(), passwordDto.getNewPass());
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PostMapping("/all/change-password")
    public ResponseEntity<?> allChangePassword(@RequestBody PasswordDto passwordDto){
        userService.changePass(passwordDto.getOldPass(), passwordDto.getNewPass());
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> changePassword(@RequestParam String email){
        userService.forgotPassword(email);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PostMapping("/user/user-logged")
    public ResponseEntity<?> userLogged(){
        return new ResponseEntity<>(userUtils.getUserWithAuthority(), HttpStatus.OK);
    }
}
