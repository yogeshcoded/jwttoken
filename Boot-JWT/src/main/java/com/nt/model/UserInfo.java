package com.nt.model;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import lombok.Data;

@Data
@Entity
public class UserInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer uid;
private String  username;
private String password;

@ElementCollection(fetch = FetchType.EAGER)
@CollectionTable(name = "USER_ROLE",joinColumns = @JoinColumn(name="USER_ID",referencedColumnName = "uid"    ))
private Set<String> role;


}
