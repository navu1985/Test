package com.datacert.surveysystem.db.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ps_role")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class Role implements Serializable {

	/**
	 * 
	 */

	private static final long serialVersionUID = 895321048240082267L;

	@Id
	@Column(name = "id")
	@GeneratedValue
	private float roleId;

	@Column(name = "role_name")
	private String roleName;
	
	@ManyToMany(fetch = FetchType.LAZY,mappedBy="authority")
	private Set<UserDescriptor> users = new HashSet<UserDescriptor>();
	
	public float getRoleId() {
		return roleId;
	}

	public void setRoleId(float roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Set<UserDescriptor> getUsers() {
	  return users;
	}

	public void setUsers(Set<UserDescriptor> users) {
	  this.users = users;
	}

}