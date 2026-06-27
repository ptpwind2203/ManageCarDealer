package com.example.manageCar.service;

import com.example.manageCar.DTO.AccountRequest;
import com.example.manageCar.DTO.ChangePasswordRequest;
import com.example.manageCar.DTO.CreateAccountRequest;
import com.example.manageCar.DTO.accountResponse;
import com.example.manageCar.exception.BadRequestException;
import com.example.manageCar.model.Account;
import com.example.manageCar.model.Role;
import com.example.manageCar.repository.accountRepository;
import com.example.manageCar.repository.roleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class accountService {
    @Autowired
    private roleRepository roleRepository;

    @Autowired
    private accountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<accountResponse> getAllAccounts(){
        List<Account> accounts = accountRepository.findAll();
        List<accountResponse> responses = new ArrayList<>();
        for (Account account : accounts) {
            accountResponse response = new accountResponse();
            response.setUserID(account.getUserID());
            response.setAccount(account.getAccount());
            response.setFullName(account.getFullName());
            response.setBirthDate(account.getBirthDate());
            response.setNumberPhone(account.getNumberPhone());
            response.setEmail(account.getEmail());
            response.setAddress(account.getAddress());

            response.setRoleID(account.getRole().getRoleID());
            response.setRoleName(account.getRole().getRoleName());

            responses.add(response);
        }
        return responses;
    }


    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

    public accountResponse getDetailsByUserID(Integer id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));

        accountResponse response = new accountResponse();

        response.setUserID(account.getUserID());
        response.setFullName(account.getFullName());
        response.setAccount(account.getAccount());
        response.setEmail(account.getEmail());
        response.setBirthDate(account.getBirthDate());
        response.setNumberPhone(account.getNumberPhone());
        response.setAddress(account.getAddress());
        response.setRoleID(account.getRole().getRoleID());
        response.setRoleName(account.getRole().getRoleName());

        return response;
    }

    public void createAccount(CreateAccountRequest request) {

        if (request.getFullName() == null || request.getFullName().isBlank()) {
            throw new BadRequestException("Họ và tên không được để trống");
        }

        if (request.getAccount() == null || request.getAccount().isBlank()) {
            throw new BadRequestException("Tài khoản không được để trống");
        }

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new BadRequestException("Mật khẩu không được để trống");
        }

        if (request.getRoleID() == null) {
            throw new BadRequestException("Vai trò không được để trống");
        }

        if (accountRepository.existsByAccount(request.getAccount())) {
            throw new BadRequestException("Tài khoản đã tồn tại");
        }

        if (request.getEmail() != null
                && !request.getEmail().isBlank()
                && accountRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email đã tồn tại");
        }

        if (request.getNumberPhone() != null
                && !request.getNumberPhone().isBlank()
                && accountRepository.existsByNumberPhone(request.getNumberPhone())) {
            throw new BadRequestException("Số điện thoại đã tồn tại");
        }

        Role role = roleRepository.findById(request.getRoleID())
                .orElseThrow(() -> new BadRequestException("Vai trò không tồn tại"));

        Account account = new Account();

        account.setFullName(request.getFullName());
        account.setAccount(request.getAccount());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setBirthDate(request.getBirthDate());
        account.setEmail(request.getEmail());
        account.setNumberPhone(request.getNumberPhone());
        account.setAddress(request.getAddress());
        account.setRole(role);

        accountRepository.save(account);
    }


    public void updateAccount(AccountRequest request, Integer id) {

        // Kiểm tra tài khoản có tồn tại không
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));

        // ==========================
        // Kiểm tra họ tên
        // ==========================
        if (request.getFullName() == null || request.getFullName().trim().isEmpty()) {
            throw new RuntimeException("Họ và tên không được để trống");
        }

        // ==========================
        // Kiểm tra tài khoản
        // ==========================
        if (request.getAccount() == null || request.getAccount().trim().isEmpty()) {
            throw new RuntimeException("Tài khoản không được để trống");
        }

        Optional<Account> accountExist = accountRepository.findByAccount(request.getAccount().trim());

        if (accountExist.isPresent()
                && !accountExist.get().getUserID().equals(id)) {
            throw new RuntimeException("Tài khoản đã tồn tại");
        }

        // ==========================
        // Kiểm tra Email
        // ==========================
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Email không được để trống");
        }

        Optional<Account> emailExist = accountRepository.findByEmail(request.getEmail().trim());

        if (emailExist.isPresent()
                && !emailExist.get().getUserID().equals(id)) {
            throw new RuntimeException("Email đã tồn tại");
        }

        // ==========================
        // Kiểm tra số điện thoại
        // ==========================
        if (request.getNumberPhone() == null || request.getNumberPhone().trim().isEmpty()) {
            throw new RuntimeException("Số điện thoại không được để trống");
        }

        Optional<Account> phoneExist = accountRepository.findByNumberPhone(request.getNumberPhone().trim());

        if (phoneExist.isPresent()
                && !phoneExist.get().getUserID().equals(id)) {
            throw new RuntimeException("Số điện thoại đã tồn tại");
        }

        // ==========================
        // Kiểm tra Role
        // ==========================
        Role role = roleRepository.findById(request.getRoleID())
                .orElseThrow(() -> new BadRequestException("Vai trò không tồn tại"));

        // ==========================
        // Cập nhật dữ liệu
        // ==========================
        account.setFullName(request.getFullName().trim());
        account.setAccount(request.getAccount().trim());
        account.setEmail(request.getEmail().trim());
        account.setNumberPhone(request.getNumberPhone().trim());
        account.setBirthDate(request.getBirthDate());
        account.setAddress(request.getAddress() == null ? "" : request.getAddress().trim());
        account.setRole(role);

        accountRepository.save(account);
    }

    public void changePassword(ChangePasswordRequest request, Integer id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));

        if (request.getPasswordOld() == null
                || request.getPasswordOld().trim().isEmpty()) {

            throw new RuntimeException("Vui lòng nhập mật khẩu hiện tại");
        }

        if (!passwordEncoder.matches(
                request.getPasswordOld(),
                account.getPassword())) {

            throw new RuntimeException("Mật khẩu hiện tại không đúng");
        }

        if (request.getPasswordNew() == null
                || request.getPasswordNew().trim().isEmpty()) {

            throw new RuntimeException("Vui lòng nhập mật khẩu mới");
        }

        if (request.getPasswordNew().length() < 6) {
            throw new RuntimeException("Mật khẩu mới phải có ít nhất 6 ký tự");
        }

        if (passwordEncoder.matches(
                request.getPasswordNew(),
                account.getPassword())) {

            throw new RuntimeException("Mật khẩu mới không được trùng mật khẩu cũ");
        }

        if (request.getConfirmPasswordNew() == null
                || request.getConfirmPasswordNew().trim().isEmpty()) {

            throw new RuntimeException("Vui lòng xác nhận mật khẩu");
        }

        if (!request.getPasswordNew().equals(request.getConfirmPasswordNew())) {
            throw new RuntimeException("Mật khẩu xác nhận không khớp");
        }

        account.setPassword(
                passwordEncoder.encode(request.getPasswordNew())
        );

        accountRepository.save(account);
    }
}
