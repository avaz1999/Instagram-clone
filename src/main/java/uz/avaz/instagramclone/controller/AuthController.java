package uz.avaz.instagramclone.controller;

import uz.avaz.instagramclone.payload.ApiResponse;
import uz.avaz.instagramclone.payload.JwtResponse;
import uz.avaz.instagramclone.payload.LoginDto;
import uz.avaz.instagramclone.payload.RegisterDto;
import uz.avaz.instagramclone.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {
        ApiResponse<String> response = authService.registerUser(registerDto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponse>> login(@RequestBody LoginDto loginDto) {
        ApiResponse<JwtResponse> response = authService.login(loginDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/verifyAccount")
    public ResponseEntity<?> verifyAccount(@RequestParam(name = "email") String email,
                                           @RequestParam(name = "code") String code) {
        ApiResponse<String> response = authService.verifyEmail(email, code);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
}
