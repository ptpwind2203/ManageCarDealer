package com.example.manageCar.service;

import com.example.manageCar.DTO.LoginRequest;
import com.example.manageCar.DTO.LoginResponse;
import com.example.manageCar.model.Account;
import com.example.manageCar.repository.accountRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final accountRepository accountRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {

        Account account = accountRepo.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Email không tồn tại"));

        if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            throw new RuntimeException("Sai mật khẩu");
        }

        String token = jwtService.generateToken(account.getEmail());

        return new LoginResponse(
                account.getUserID(),
                account.getFullName(),
                account.getAccount(),
                account.getRole().getRoleName(),
                token,
                account.getEmail(),
                account.getNumberPhone()
        );
    }
}
