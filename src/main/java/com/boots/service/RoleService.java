package com.boots.service;

import com.boots.entity.Hotel;
import com.boots.entity.Role;
import com.boots.repository.HotelRepository;
import com.boots.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles(){
        return new ArrayList<>(roleRepository.findAll());
    }
}
