package com.smart_home.s_home.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String username;
    @Column(name = "email_address")
    private String emailAddress;
    @Column(nullable = false)
    @JsonIgnore
    private String password;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private int age;
    @Column(name = "created_by", nullable = false)
    @CreatedBy
    private String createdBy;
    @Column(name = "created_date", nullable = false)
    @CreatedDate
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    @Column(name = "modify_by")
    @LastModifiedBy
    private String modifyBy;
    @Column(name = "modify_date")
    @LastModifiedDate
    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    private Date modifyDate;
    @Column
    private String address;
    @Column
    private int phone;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	@PrePersist
	protected void prePersist() {
		if (this.createdBy == null ) createdBy = new User().firstName;
		if (this.createdDate == null) createdDate = new Date();
		if (this.modifyBy == null ) modifyBy = new User().firstName;
        if (this.modifyDate == null) modifyDate = new Date();
	}

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
	public String getCreatedBy() {
		return createdBy;
	}

    public void setCreatedDate( Date createdDate) {
        this.createdDate = createdDate;
    }
	public Date getCreatedDate() {
		return createdDate;
	}

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }
	public String getModifyBy() {
		return modifyBy;
	}

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
	public Date getModifyDate() {
		return modifyDate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getPhone() {
		return phone;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}
}