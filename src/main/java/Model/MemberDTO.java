package Model;

public class MemberDTO {
	
	private String store_id;
	private String snsid;
	private String store_name;
	private String store_info;
	private String mobile;
	private String area;
	private String zip_code;
	private String basic_address;
	private String dtail_address;
	
	public MemberDTO(String store_id, String snsid) {
		this.setStore_id(store_id);
		this.snsid = snsid;
	}
	
	public MemberDTO(String snsid, String store_name, String store_info, String mobile, String area, String zip_code,
			String basic_address, String dtail_address) {
		this.snsid = snsid;
		this.store_name = store_name;
		this.store_info = store_info;
		this.mobile = mobile;
		this.area = area;
		this.zip_code = zip_code;
		this.basic_address = basic_address;
		this.dtail_address = dtail_address;
	}
	
	public String getSnsid() {
		return snsid;
	}

	public void setSnsid(String snsid) {
		this.snsid = snsid;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getStore_info() {
		return store_info;
	}

	public void setStore_info(String store_info) {
		this.store_info = store_info;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getZip_code() {
		return zip_code;
	}

	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}

	public String getBasic_address() {
		return basic_address;
	}

	public void setBasic_address(String basic_address) {
		this.basic_address = basic_address;
	}

	public String getDtail_address() {
		return dtail_address;
	}

	public void setDtail_address(String dtail_address) {
		this.dtail_address = dtail_address;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}


	
}
