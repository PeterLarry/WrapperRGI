package it.cg.main.dto.inbound;

public class InboundAddressDTO {
	
	private String provinceDesc;
	private String province;
	private String provinceCode;
	private String city;
	private String cap;
	private Boolean incity;
	
	
	public String getProvinceDesc() {
		return provinceDesc;
	}
	public void setProvinceDesc(String provinceDesc) {
		this.provinceDesc = provinceDesc;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public Boolean getIncity() {
		return incity;
	}
	public void setIncity(Boolean incity) {
		this.incity = incity;
	}
	
	

}
