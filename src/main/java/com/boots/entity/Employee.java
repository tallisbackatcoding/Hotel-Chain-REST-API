package com.boots.entity;

import com.boots.idclasses.EmployeeIds;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.Persistable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@IdClass(EmployeeIds.class)
@Table(name = "t_employee")
public class Employee implements UserDetails, Persistable<EmployeeIds> {
    @Id
    @GeneratedValue
    private Integer id;

    @Transient
    private boolean isNew = true;

    /*
    private String lastName;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }*/

    private String email;

    private Long phoneNumber;
    private String password;

    private int payload;
    private int salary;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Id
    @ManyToOne
    @JoinColumn(name="hotel_id")
    private Hotel hotel;

    public Employee() {
    }

    public int getPayload() {
        return payload;
    }

    public void setPayload(int payload) {
        this.payload = payload;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getUserId(){
        return id;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }

    @JsonIgnore
    @Override
    public EmployeeIds getId() {
        return new EmployeeIds(id, hotel);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public int getHotel() {
        return hotel.getHotelId();
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
