package com.boots.config;

import com.boots.entity.Hotel;
import com.boots.service.HotelService;
import com.boots.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import com.boots.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

@Configuration
@EnableWebMvc
@EnableScheduling
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;
    @Autowired
    HotelService hotelService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity

                .authorizeRequests()
                    //Доступ только для не зарегистрированных пользователей
                    .antMatchers("/registration").not().fullyAuthenticated()
                    .antMatchers("/signin").permitAll()
                    //Доступ только для пользователей с ролью Администратор
                    .antMatchers("/admin/**").hasAnyRole("ADMIN", "MANAGER")
                    .antMatchers("/hotel/**").hasAnyRole("ADMIN")
                    .antMatchers("/room/**").hasAnyRole("ADMIN", "MANAGER")
                    .antMatchers("/roomType/**").hasAnyRole("ADMIN", "MANAGER")
                    .antMatchers("/season/**").hasAnyRole("ADMIN", "MANAGER")
                    .antMatchers("/clean/**").hasAnyRole("ADMIN", "CLEANER", "MANAGER")
                    .antMatchers("/reserve").hasAnyRole("USER", "ADMIN")
                    //Доступ разрешен всем пользователей
                //Все остальные страницы требуют аутентификации
                .anyRequest().authenticated()
                .and()
                .cors().and()
                .csrf().disable();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Scheduled(fixedDelay = 1800000)
    public void scheduleFixedDelayTask() {

        if(new Date().getHours() == 9){
            for(Hotel h: hotelService.getAllHotels()){
                hotelService.unCleanAllRoomsForHotel(h.getHotelId());
            }
        }
    }

}
