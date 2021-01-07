package com.boots.service;

import com.boots.entity.Employee;
import com.boots.entity.Role;
import com.boots.entity.User;
import com.boots.repository.EmployeeRepository;
import com.boots.repository.RoleRepository;
import com.boots.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
/*
@Service
public class EmployeeService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) {
        /*Employee employee = employeeRepository.findByEmail(email);
        System.out.println("Load By Email");
        return employee;
        return em.createQuery("select e from Employee e where " +
                "e.email like :email", Employee.class).setParameter("email", email).
                getSingleResult();
    }

    public void save(Employee employee){
        employee.setRoles(Collections.singleton(new Role(3L, "ROLE_CLEANER")));
        employeeRepository.save(employee);
    }
}
*/