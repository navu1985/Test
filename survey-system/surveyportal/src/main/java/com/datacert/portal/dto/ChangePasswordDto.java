package com.datacert.portal.dto;

import org.hibernate.validator.constraints.NotBlank;

public class ChangePasswordDto {
	
	@NotBlank
	private String oldpassword;
	
	@NotBlank
	private String password;
	
	@NotBlank
	private String confirmpassword;

	public String getOldpassword() {
		return oldpassword;
	}

	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

}
