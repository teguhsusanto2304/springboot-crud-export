package com.teguh.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;

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

    @NotEmpty(message = "Nama wajib diisi")
    private String name;

    @NotEmpty(message = "Email wajib diisi")
    @Email
    private String email;

    @NotEmpty(message = "Email wajib diisi")
    private String password;

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }
    @NotNull(message = "Tgl Lahir wajib diisi")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    public String getIc_number() {
        return ic_number;
    }

    public void setIc_number(String ic_number) {
        this.ic_number = ic_number;
    }

    @NotEmpty(message = "Nomor KTP wajib diisi")
    private String ic_number;

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    @NotEmpty(message = "Handphone wajib diisi")
    private String phone_number;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @NotEmpty(message = "Gender wajib diisi")
    private String gender;


}
