package com.boots.service;

import com.boots.entity.Employee;
import com.boots.entity.Role;
import com.boots.entity.User;
import com.boots.repository.EmployeeRepository;
import com.boots.repository.RoleRepository;
import com.boots.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
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

@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws StringIndexOutOfBoundsException{
        System.out.println(username);
        UserDetails user = userRepository.findByUsername(username);
        System.out.println("Load By Username");
        System.out.println("username: " + username);
        if (user == null) {
            user = employeeRepository.findByEmail(username);
            if(user == null){
                throw new UsernameNotFoundException("User not found XDDDDDDDDDDD");
            }
        }
        System.out.println("AAAAAAAAAAAA: " + user.getUsername());
        System.out.println("AAAAAAAAAAAA: " + user.getPassword());
        return user;
    }

    public List<Employee> getHotelEmployees(int hotelId){
        return em.createQuery("select e from Employee e where" +
                " e.hotel.hotelId = :hotelId", Employee.class).
                setParameter("hotelId", hotelId).getResultList();
    }


    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public List<User> userList(Long idMin) {
        return em.createQuery("SELECT u FROM User u WHERE u.id = :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }
    public void updateUser(User u){
        userRepository.save(u);
    }
}
