package com.web.service;


import com.web.dto.CustomUserDetails;
import com.web.dto.TokenDto;
import com.web.dto.UserDto;
import com.web.entity.Authority;
import com.web.entity.User;
import com.web.exception.MessageException;
import com.web.jwt.JwtTokenProvider;
import com.web.repository.AuthorityRepository;
import com.web.repository.UserRepository;
import com.web.utils.Contains;
import com.web.utils.MailService;
import com.web.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserUtils userUtils;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public TokenDto login(String username, String password) throws Exception {
        Optional<User> users = userRepository.findByUsername(username);
        // check infor user
        checkUser(users);
        if(passwordEncoder.matches(password, users.get().getPassword())){
            CustomUserDetails customUserDetails = new CustomUserDetails(users.get());
            String token = jwtTokenProvider.generateToken(customUserDetails);
            TokenDto tokenDto = new TokenDto();
            tokenDto.setToken(token);
            tokenDto.setUser(users.get());
            return tokenDto;
        }
        else{
            throw new MessageException("Mật khẩu không chính xác", 400);
        }
    }


    public User regisUser(User user) {
        userRepository.findByUsername(user.getUsername())
                .ifPresent(exist->{
                    if(exist.getActivation_key() != null){
                        throw new MessageException("Tài khoản chưa được kích hoạt", 330);
                    }
                    throw new MessageException("Tên đăng nhập đã tồn tại", 400);
                });
        userRepository.findByEmail(user.getEmail())
                .ifPresent(exist->{
                    if(exist.getActivation_key() != null){
                        throw new MessageException("Tài khoản chưa được kích hoạt", 330);
                    }
                    throw new MessageException("Email đã được sử dụng", 400);
                });
        user.setCreatedDate(new Date(System.currentTimeMillis()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActived(false);
        user.setPoint(0);
        user.setType("REGULAR");
        user.setActivation_key(userUtils.randomKey());
        Authority authority = authorityRepository.findById(Contains.ROLE_USER).get();
        user.setAuthorities(authority);
        User result = userRepository.save(user);
        mailService.sendEmail(user.getEmail(), "Xác nhận tài khoản của bạn","Cảm ơn bạn đã tin tưởng và xử dụng dịch vụ của chúng tôi:<br>" +
                "Để kích hoạt tài khoản của bạn, hãy nhập mã xác nhận bên dưới để xác thực tài khoản của bạn<br><br>" +
                "<a style=\"background-color: #2f5fad; padding: 10px; color: #fff; font-size: 18px; font-weight: bold;\">"+user.getActivation_key()+"</a>",false, true);
        return result;
    }

    // kich hoat tai khoan
    public void activeAccount(String activationKey, String email) {
        Optional<User> user = userRepository.getUserByActivationKeyAndEmail(activationKey, email);
        user.ifPresent(exist->{
            exist.setActivation_key(null);
            exist.setActived(true);
            userRepository.save(exist);
            return;
        });
        if(user.isEmpty()){
            throw new MessageException("email hoặc mã xác nhận không chính xác", 404);
        }
    }

    public Boolean checkUser(Optional<User> users){
        if(users.isPresent() == false){
            throw new MessageException("Không tìm thấy tài khoản", 404);
        }
        else if(users.get().getActivation_key() != null && users.get().getActived() == false){
            throw new MessageException("Tài khoản chưa được kích hoạt", 300);
        }
        else if(users.get().getActived() == false && users.get().getActivation_key() == null){
            throw new MessageException("Tài khoản đã bị khóa", 500);
        }
        return true;
    }

    public void changePass(String oldPass, String newPass) {
        User user = userUtils.getUserWithAuthority();
        if(passwordEncoder.matches(oldPass, user.getPassword())){
            user.setPassword(passwordEncoder.encode(newPass));
            userRepository.save(user);
        }
        else{
            throw new MessageException("Invalid password", 500);
        }
    }

    public void forgotPassword(String email) {
        Optional<User> users = userRepository.findByEmail(email);
        // check infor user
        checkUser(users);
        String randomPass = userUtils.randomPass();
        users.get().setPassword(passwordEncoder.encode(randomPass));
        userRepository.save(users.get());
        mailService.sendEmail(email, "Quên mật khẩu","Cảm ơn bạn đã tin tưởng và xử dụng dịch vụ của chúng tôi:<br>" +
                "Chúng tôi đã tạo một mật khẩu mới từ yêu cầu của bạn<br>" +
                "Tuyệt đối không được chia sẻ mật khẩu này với bất kỳ ai. Bạn hãy thay đổi mật khẩu ngay sau khi đăng nhập<br><br>" +
                "<a style=\"background-color: #2f5fad; padding: 10px; color: #fff; font-size: 18px; font-weight: bold;\">"+randomPass+"</a>",false, true);

    }

    @Transactional
    public void updateUserType() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getPoint() >= 10000) {
                user.setType("DIAMOND");
            } else if (user.getPoint() >= 5000) {
                user.setType("GOLD");
            } else if (user.getPoint() >= 1000) {
                user.setType("SILVER");
            } else {
                user.setType("REGULAR");
            }
        }
        userRepository.saveAll(users);
    }
}
