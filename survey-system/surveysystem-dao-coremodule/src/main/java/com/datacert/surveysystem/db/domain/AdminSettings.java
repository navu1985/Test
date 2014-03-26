package com.datacert.surveysystem.db.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import com.datacert.surveysystem.dto.Admin;

@Entity
@Table(name = "ps_admin")
public class AdminSettings implements Admin, Serializable {

	private static final long serialVersionUID = 895321048240082267L;

	@Id
	@Column(name = "id")
	private long id = 0;

	@Column(name = "connect_pp")
	private boolean connectionFlag;

	@Column(name = "admin_page_name")
	private String pageName = "";

	@Column(name = "admin_email")
	private String adminEmail;
	
	@Column(name = "nodata_message")
	private String noDataMessage;
	
	@Column(name = "show_submit_an_inquiry")
	private Boolean showSubmitAnInquiry = Boolean.FALSE;
	
	@Column(name = "show_report_an_issue")
	private Boolean showReportAnIssue =Boolean.FALSE;
	

	public boolean isConnectionFlag() {
		return connectionFlag;
	}

	public void setConnectionFlag(boolean connectionFlag) {
		this.connectionFlag = connectionFlag;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getNoDataMessage() {
		return noDataMessage;
	}

	public void setNoDataMessage(String noDataMessage) {
		this.noDataMessage = noDataMessage;
	}

	public Boolean getShowSubmitAnInquiry() {
		return showSubmitAnInquiry;
	}

	public void setShowSubmitAnInquiry(Boolean showSubmitAnInquiry) {
		this.showSubmitAnInquiry = showSubmitAnInquiry;
	}

	public Boolean getShowReportAnIssue() {
		return showReportAnIssue;
	}

	public void setShowReportAnIssue(Boolean showReportAnIssue) {
		this.showReportAnIssue = showReportAnIssue;
	}
}
