package com.datacert.surveysystem.db.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.datacert.surveysystem.dto.User;

@NamedQueries({
		@NamedQuery(name = "getUserByID", query = "from UserDescriptor where userId= :userId", cacheable = true),
		@NamedQuery(name = "getUserByUserName", query = "from UserDescriptor where username= :username", cacheable = true),
		@NamedQuery(name = "getUserByContactId", query = "from UserDescriptor where userPassportId= :userPassportId"),
		@NamedQuery(name = "getUserWithRole", query = "from UserDescriptor as user join fetch user.authority where user.username=:username", cacheable = true) })
@Entity
@Table(name = "ps_user")
public class UserDescriptor implements UserDetails, User {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private long userId;

	@Column(name = "contact_id", nullable = true)
	private long userPassportId = -1;

	@Column(name = "user_name", length = 500)
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "enable")
	private int enable;

	@Column(name = "locked")
	private int locked;

	@Column(name = "is_registered")
	private int registered;

	@Column(name = "is_first_login")
	private Boolean firstLogin;
	
	@Column(name = "uuId")
	private String urlIdentifier;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_profile_id")
	private UserProfileInfoDescriptor userProfileInfoDescriptor;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<UserSecurityAnswer> userSecurityAnswer = new HashSet<UserSecurityAnswer>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.user")
	private Set<UserSurvey> userSurveys;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.user")
	private Set<UserPolicy> userpolicies;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ps_user_role", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id", insertable = false, updatable = false, nullable = false) })
	private Set<Role> authority = new HashSet<Role>();

	private transient Collection<GrantedAuthority> authorities;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public UserDescriptor() {
	}

	public UserDescriptor(String username, String password) {
		this.username = username;
		this.password = password;
		this.enable = 0;
		this.registered = 0;
		this.firstLogin=Boolean.TRUE;
	}

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	public int getLocked() {
		return locked;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public Set<UserSecurityAnswer> getUserSecurityAnswer() {
		return userSecurityAnswer;
	}

	public void setUserSecurityAnswer(Set<UserSecurityAnswer> userSecurityAnswer) {
		this.userSecurityAnswer = userSecurityAnswer;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getAuthority() {
		return authority;
	}

	public void setAuthority(Set<Role> authorities) {
		this.authority = authorities;
	}

	public Collection<GrantedAuthority> getAuthorities() {
		if (!authority.isEmpty() && authorities == null) {
			List<GrantedAuthority> listOfAuthorities = new ArrayList<GrantedAuthority>();
			for (Role role : authority) {
				listOfAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
			}
			this.authorities = listOfAuthorities;
		}
		return authorities;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return getLocked() == 0;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return getEnable() == 1;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public long getUserPassportId() {
		return userPassportId;
	}

	public void setUserPassportId(long userPassportId) {
		this.userPassportId = userPassportId;
	}

	public Set<UserSurvey> getUserSurveys() {
		return userSurveys;
	}

	public void setUserSurveys(Set<UserSurvey> userSurveys) {
		this.userSurveys = userSurveys;
	}

	@Override
	public long getContactId() {
		return userPassportId;
	}

	public int getRegistered() {
		return registered;
	}

	public void setRegistered(int registered) {
		this.registered = registered;
	}

	public String getUrlIdentifier() {
		return urlIdentifier;
	}

	public void setUrlIdentifier(String urlIdentifier) {
		this.urlIdentifier = urlIdentifier;
	}

	public String getSurveyResponseId() {
		return null;
	}

	public Set<UserPolicy> getUserpolicies() {
		return userpolicies;
	}

	public void setUserpolicies(Set<UserPolicy> userpolicies) {
		this.userpolicies = userpolicies;
	}

	public UserProfileInfoDescriptor getUserProfileInfoDescriptor() {
		return userProfileInfoDescriptor;
	}

	public void setUserProfileInfoDescriptor(
			UserProfileInfoDescriptor userProfileInfoDescriptor) {
		this.userProfileInfoDescriptor = userProfileInfoDescriptor;
	}

	@Override
	public String getFirstName() {
		return userProfileInfoDescriptor.getFirstName();
	}

	@Override
	public String getLastName() {
		return userProfileInfoDescriptor.getLastName();
	}

	public Boolean getFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(Boolean firstLogin) {
		this.firstLogin = firstLogin;
	}
}
