package com.example.manageCar.service;

import com.example.manageCar.DTO.accountResponse;
import com.example.manageCar.model.Account;
import com.example.manageCar.model.Role;
import com.example.manageCar.repository.accountRepository;
import com.example.manageCar.repository.roleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class accountService {
    @Autowired
    private roleRepository roleRepository;

    @Autowired
    private accountRepository accountRepository;

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
}
