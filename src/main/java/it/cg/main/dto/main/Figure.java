package it.cg.main.dto.main;

import java.util.Date;

public class Figure {
	
	private Date birthDate;
	private Integer age;
	private Address residenceAddress;
	private String maritalStatus;
	private String occupation;
	private Date licenseIssueDate;
	private Integer yearsWithLicence;
	private String role;
	private String personType;
	private Boolean highRiskDriver;
	
	
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Address getResidenceAddress() {
		return residenceAddress;
	}
	public void setResidenceAddress(Address residenceAddress) {
		this.residenceAddress = residenceAddress;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public Date getLicenseIssueDate() {
		return licenseIssueDate;
	}
	public void setLicenseIssueDate(Date licenseIssueDate) {
		this.licenseIssueDate = licenseIssueDate;
	}
	public Integer getYearsWithLicence() {
		return yearsWithLicence;
	}
	public void setYearsWithLicence(Integer yearsWithLicence) {
		this.yearsWithLicence = yearsWithLicence;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getPersonType() {
		return personType;
	}
	public void setPersonType(String personType) {
		this.personType = personType;
	}
	public Boolean getHighRiskDriver() {
		return highRiskDriver;
	}
	public void setHighRiskDriver(Boolean highRiskDriver) {
		this.highRiskDriver = highRiskDriver;
	}
	
	

}
