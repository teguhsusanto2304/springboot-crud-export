package com.teguh.member.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 150)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return Objects.requireNonNullElse(gender, "N/A");
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDob() {
        if (dob == null) {
            return new Date(); // Replace with appropriate default value or handling logic
        } else {
            return dob;
        }
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getIc_number() {
        return Objects.requireNonNullElse(ic_number,"N/A");
    }

    public void setIc_number(String ic_number) {
        this.ic_number = ic_number;
    }

    public String getPhone_number() {
        return Objects.requireNonNullElse(phone_number, "N/A");
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Column(nullable = false, unique = true,length = 200)
    private String email;
    @Column(nullable = false,length = 100)
    private String password;
    @Column(nullable = false,length = 10)
    private String gender;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;
    @Column(nullable = true,length = 20)
    private String ic_number;
    @Column(nullable = false,length = 15)
    private String phone_number;
    @Column(nullable = true,length = 200)
    private String avatar;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private List<Role> roles = new ArrayList<>();

    public User(
            String name,
            String email,
            String password,
            String gender,
            Date dob,
            String ic_number,
            String phone_number,
            List<Role> roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.dob = dob;
        this.gender = gender;
        this.ic_number = ic_number;
        this.phone_number = phone_number;
        this.roles = roles;
    }
}
