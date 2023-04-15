package uz.avaz.instagramclone.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.avaz.instagramclone.entity.Attachment;
import uz.avaz.instagramclone.entity.User;
import uz.avaz.instagramclone.payload.ApiResponse;
import uz.avaz.instagramclone.payload.JwtResponse;
import uz.avaz.instagramclone.payload.LoginDto;
import uz.avaz.instagramclone.payload.RegisterDto;
import uz.avaz.instagramclone.repository.RoleRepository;
import uz.avaz.instagramclone.repository.UserRepository;
import uz.avaz.instagramclone.entity.Role;
import uz.avaz.instagramclone.security.JwtAuthenticationUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtAuthenticationUtil jwtTokenUtil;
    private final EmailValidator emailValidator;

    public ApiResponse<String> registerUser(RegisterDto registrationDto) {
        if (userRepository.existsByUsername(registrationDto.getUsername())) {
            return new ApiResponse<>(false, "Username already taken!", null);
        }
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            return new ApiResponse<>(false, "Email already taken!", null);
        }
        if (!isValidEmail(registrationDto.getEmail())) {
            return new ApiResponse<>(false, "Invalid email or phone number!", null);
        }
        User user = userRepository.save(prepareUser(registrationDto, "ROLE_USER"));
        String code = UUID.randomUUID().toString();
        user.setEmailCode(code);
        String link = "<a href=\"http://localhost:8080/api/auth/verifyAccount?email=" + user.getEmail() + "&code=" + code + "\">Verify your account!</a>";
        mailService.sendSimpleMailMessage(user.getEmail(), "Email verification!", link);
        return new ApiResponse<>(true, "Successfully registered!", null);
    }

    private User prepareUser(RegisterDto registerDto, String... roles) {
        User user = modelMapper.map(registerDto, User.class);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRoles(Arrays.stream(roles).map(this::createRoleIfNotFound).collect(Collectors.toSet()));

        try {
            user.setAttachment(Attachment.prepareAttachment(registerDto.getProfileImg()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    private Role createRoleIfNotFound(String role) {
        return roleRepository.existsByName(role) ?
                roleRepository.getByName(role)
                : roleRepository.save(new Role(role));
    }

    private boolean isValidEmail(String email) {
        return emailValidator.isValid(email);
    }

    public ApiResponse<String> verifyEmail(String email, String code) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (!user.isEmailVerified() && user.getEmailCode().equals(code)) {
                user.setEmailVerified(true);
                user.setEmailCode(null);
                return new ApiResponse<>(true, "Email verified");
            }
        }
        return new ApiResponse<>(false, "Email not present or already verified!");
    }

    public ApiResponse<JwtResponse> login(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            User user = (User) authentication.getPrincipal();
            List<String> roles = user.getRoles().stream().map(Role::getName).toList();
            JwtResponse jwtResponse = new JwtResponse(
                    jwtTokenUtil.generateToken(user.getUsername()),
                    "Bearer",
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    roles
            );
            return new ApiResponse<>(true, "Logged in!", jwtResponse);
        } catch (BadCredentialsException e) {
            return new ApiResponse<>(false, "Invalid username or password!", null);
        }
    }
}
