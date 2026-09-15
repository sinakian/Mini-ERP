package dev.ordy.erp.user_management.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String roles;

    Users(){}
    public Users(String username,String password,String roles){
        this.username=username;
        this.password=password;
        this.roles=roles;
    }


    public String getUsername() {
        return username;
    }

    @JsonIgnore
    public String getPassword() {
        return  password;
    }

    public String getRoles() {
        return  roles;
    }
}
