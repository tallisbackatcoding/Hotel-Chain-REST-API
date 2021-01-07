package com.boots.controller;

import com.boots.entity.Employee;
import com.boots.entity.Hotel;
import com.boots.entity.Role;
import com.boots.entity.User;
import com.boots.repository.EmployeeRepository;
import com.boots.service.HotelService;
import com.boots.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
public class AdminController {
    @Autowired
    private UserService userService;

    private HotelService hotelService;

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/admin/getusers")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public List<User> userList() {
        //u.setPassword(null);
        return new ArrayList<User>(userService.allUsers());
    }

    @PostMapping("/admin")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public Map<String, Object> deleteUser(@RequestParam(defaultValue = "" ) Long userId,
                              @RequestParam(defaultValue = "" ) String action) {
        Map<String, Object> responseJson = new HashMap<>();
        if (action.equals("delete")){
            User user = userService.findUserById(userId);
            user.setEnabled(false);
            userService.updateUser(user);
            /*
            if(userService.deleteUser(userId)){
                responseJson.put("status", 0);
            }else{
                responseJson.put("status", 1);
                responseJson.put("message", "Username with id: " + userId + "doesn't exist");
            }*/
        }else{
            responseJson.put("message", "Invalid action: " + action);
            responseJson.put("status", 1);
        }
        return responseJson;
    }

    @GetMapping("/admin/getuser/{userId}")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public List<User> getUser(@PathVariable("userId") Long userId) {
        return userService.userList(userId);
    }

    @GetMapping("/admin/getallemployees")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public List<Employee> getAllEmployees(Principal principal){
        Employee employee = (Employee) userService.loadUserByUsername(principal.getName());
        return userService.getHotelEmployees(employee.getHotel());
    }

    /*
    @GetMapping("/admin/addemployee")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public List<Employee> addEmployee(@RequestBody Map<String, Object> requestJson,  Principal principal){
        Employee employee = (Employee) userService.loadUserByUsername(principal.getName());

        return userService.getHotelEmployees(employee.getHotel());
    }*/

    @PostMapping("/admin/addemployee")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public Map<String, Object> addEmployee(@RequestBody Map<String, String> requestJson, Principal principal){
        Employee employee;
        try{
            employee = (Employee)userService.loadUserByUsername(requestJson.get("email"));
        }catch (Exception e){
            employee = new Employee();
            employee.setEmail(requestJson.get("email"));
            employee.setPassword(bCryptPasswordEncoder.encode(requestJson.get("password")));
        }
        employee.setRoles(Collections.singleton(new Role(3L, "ROLE_CLEANER")));
        employee.setPayload(Integer.parseInt(requestJson.get("payload")));
        employee.setSalary(Integer.parseInt(requestJson.get("salary")));
        employeeRepository.save(employee);
        Map<String, Object> response = new HashMap<>();
        response.put("status", 0);
        response.put("employee", employee);
        return response;
    }
}
