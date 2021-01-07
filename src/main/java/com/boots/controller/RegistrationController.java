package com.boots.controller;

import com.boots.entity.*;
import com.boots.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.*;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private RoomService roomService;
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    private ReservationService reservationService;

    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static String hashPassword(String password_plaintext) {
        String salt = BCrypt.gensalt(10);
        return(BCrypt.hashpw(password_plaintext, salt));
    }

    public static boolean checkPassword(String password_plaintext, String stored_hash) {
        boolean password_verified = false;

        if(null == stored_hash || !stored_hash.startsWith("$2a$"))
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

        password_verified = BCrypt.checkpw(password_plaintext, stored_hash);

        return(password_verified);
    }

    //@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping(value="/signin")
    public Map<String, Object> login(@RequestBody Map<String, String> user, HttpServletRequest request, Authentication authentication) {
        int status = 0;
        String login;
        if (user.containsKey("email")) {
            login = user.get("email");
        }else{
            login = user.get("username");
        }
        Map<String, Object> response = new HashMap<>();
        System.out.println("(Login) Session id is" + RequestContextHolder.currentRequestAttributes().getSessionId());
        try{
            String passwordToCheckForUser = userService.loadUserByUsername(login).getPassword();
            if(!checkPassword(user.get("password"), passwordToCheckForUser)){
                status = 1;
            }
        }catch (StringIndexOutOfBoundsException e){
            status = 1;
        }
        if(status == 0){
            UsernamePasswordAuthenticationToken authReq
                    = new UsernamePasswordAuthenticationToken(login, user.get("password"));
            Authentication auth = authManager.authenticate(authReq);
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
            HttpSession session = request.getSession(true);
            session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);
            UserDetails u = userService.loadUserByUsername(login);
            response.put("user", u);
        }
        response.put("status", status);
        return response;
    }

    @GetMapping("success")
    public String successLogin(){
        return "Bazara net";
    }

    //@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/registration")
    public Map<String, Object> addUser(@RequestBody User userForm) {
        Map<String, Object> responseJson = new HashMap<>();
        System.out.println(userForm.getUsername());
        System.out.println(userForm.getPassword());
        if (!userService.saveUser(userForm)){
            responseJson.put("status", "1");
        }else{
            responseJson.put("status", "0");
        }
        responseJson.put("user", userForm);
        return responseJson;
    }
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/api/v1/currentuser")
    public Map<String, Object> currentUser(Principal principal) {
        User u = (User) userService.loadUserByUsername(principal.getName());
        System.out.println("Session id is" + RequestContextHolder.currentRequestAttributes().getSessionId());
        u.setPassword(null);
        Map<String, Object> responseJson = new HashMap<>();
        responseJson.put("status", 0);
        responseJson.put("user", u);
        return responseJson;
    }

    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @GetMapping("/api/v1/currentreservations")
    public Map<String, Object> userReservations(Principal principal) {
        User u = (User) userService.loadUserByUsername(principal.getName());

        List<Reservation> allReservations = u.getReservations();
        List<Reservation> currentReservations = new ArrayList<>();
        List<Reservation> pastReservations = new ArrayList<>();

        for(Reservation r: allReservations){
            Date currentDate = new Date();
            if(r.getOutDate().before(currentDate)){
                pastReservations.add(r);
            }else{
                currentReservations.add(r);
            }
        }
        Map<String, Object> responseJson = new HashMap<>();
        responseJson.put("status", 0);
        responseJson.put("currentReservations", currentReservations);
        responseJson.put("pastReservations", pastReservations);
        return responseJson;
    }

    @GetMapping("/signout")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public String customLogout(HttpServletRequest request, HttpServletResponse response) {
        // Get the Spring Authentication object of the current request.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // In case you are not filtering the users of this request url.
        if (authentication != null){
            new SecurityContextLogoutHandler().logout(request, response, authentication); // <= This is the call you are looking for.
        }
        return "redirect:/login-page";
    }

    @GetMapping("/getallrooms")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public List<Room> getAllRooms(){
        return roomService.getAllRooms();
    }

    @GetMapping("/getallhotels")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public List<Hotel> getAllHotels(@RequestBody(required = false) Map<String, String> requestJson){
        if(requestJson == null){
            return hotelService.getAllHotels();
        }else{
            String city = requestJson.get("city");
            return hotelService.getHotelsByCity(city);
        }
    }


}