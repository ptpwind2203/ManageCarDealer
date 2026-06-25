package com.example.manageCar.controller;

import com.example.manageCar.DTO.accountResponse;
import com.example.manageCar.model.Account;
import com.example.manageCar.model.Role;
import com.example.manageCar.model.cars;
import com.example.manageCar.repository.carDetailRepository;
import com.example.manageCar.service.accountService;
import org.springframework.beans.factory.annotation.Autowired;
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

}
