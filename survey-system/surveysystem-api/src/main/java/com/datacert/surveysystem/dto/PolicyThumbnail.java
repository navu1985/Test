package com.datacert.surveysystem.dto;

public class PolicyThumbnail {

	private Long policyId;

	private byte[] policyThumbnail;

	public Long getPolicyId() {
		return policyId;
	}

	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}

	public byte[] getPolicyThumbnail() {
		return policyThumbnail;
	}

	public void setPolicyThumbnail(byte[] policyThumbnail) {
		this.policyThumbnail = policyThumbnail;
	}

}
