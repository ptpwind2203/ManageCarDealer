package com.example.manageCar.controller;

import com.example.manageCar.DTO.accountRequest;
import com.example.manageCar.DTO.accountResponse;
import com.example.manageCar.DTO.changePasswordRequest;
import com.example.manageCar.DTO.createAccountRequest;
import com.example.manageCar.model.Account;
import com.example.manageCar.model.Role;
import com.example.manageCar.model.cars;
import com.example.manageCar.repository.carDetailRepository;
import com.example.manageCar.service.accountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "http://localhost:8081")
public class accountController {

    @Autowired
    private accountService accountService;

    @GetMapping("/roles")
    public List<Role> getAllRoles(){
        return accountService.getAllRoles();
    }

    @GetMapping
    public List<accountResponse> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    public accountResponse getAllAccounts(@PathVariable Integer id){
        return accountService.getDetailsByUserID(id);
    }



    @PostMapping
    public ResponseEntity<String> createAccount(
            @RequestBody createAccountRequest request) {

        accountService.createAccount(request);

        return ResponseEntity.ok("Thêm tài khoản thành công");

    }



    @PutMapping("/{id}")
    public ResponseEntity<String> updateAccount(
            @RequestBody accountRequest request,
            @PathVariable Integer id) {

        try {
            accountService.updateAccount(request, id);
            return ResponseEntity.ok("Cập nhật tài khoản thành công");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/change-password")
    public ResponseEntity<String> changePassword(
            @PathVariable Integer id,
            @RequestBody changePasswordRequest request) {

        try {

            accountService.changePassword(request, id);

            return ResponseEntity.ok("Đổi mật khẩu thành công");

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }
}
