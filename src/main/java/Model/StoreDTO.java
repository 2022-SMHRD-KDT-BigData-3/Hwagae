package Model;

public class StoreDTO {
	
	private int storeId;
	private String storeName;
	private String storeInfo;
	private String snsId;
	private String snsType;
	private String mobile;
	private String mobileOpenYn;
	private String contactStartTime;
	private String contactEndTime;
	private String authenticationMobile;
	private String authenticationYn;
	private String area;
	private String zipCode;
	private String basicAddress;
	private String dtailAddress;
	private int numVisitors;
	private int numSales;
	private int numDelivery;
	private int averagePoint;
	private String lastLoginTime;
	private String accoutCreationDate;
	private String leaveDate;
	private String profileImg;
	
	public StoreDTO(int storeId, String storeName, String storeInfo, int numVisitors, int numSales, int numDelivery, String accoutCreationDate,String profileImg) {
		super();
		this.storeId = storeId;
		this.storeName = storeName;
		this.storeInfo = storeInfo;
		this.numVisitors = numVisitors;
		this.numSales = numSales;
		this.numDelivery = numDelivery;
		this.accoutCreationDate = accoutCreationDate;
		this.profileImg = profileImg;
	}

	public int getStoreId() {
		return storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public String getStoreInfo() {
		return storeInfo;
	}

	public String getSnsId() {
		return snsId;
	}

	public String getSnsType() {
		return snsType;
	}

	public String getMobile() {
		return mobile;
	}

	public String getMobileOpenYn() {
		return mobileOpenYn;
	}

	public String getContactStartTime() {
		return contactStartTime;
	}

	public String getContactEndTime() {
		return contactEndTime;
	}

	public String getAuthenticationMobile() {
		return authenticationMobile;
	}

	public String getAuthenticationYn() {
		return authenticationYn;
	}

	public String getArea() {
		return area;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getBasicAddress() {
		return basicAddress;
	}

	public String getDtailAddress() {
		return dtailAddress;
	}

	public int getNumVisitors() {
		return numVisitors;
	}

	public int getNumSales() {
		return numSales;
	}

	public int getNumDelivery() {
		return numDelivery;
	}

	public int getAveragePoint() {
		return averagePoint;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public String getAccoutCreationDate() {
		return accoutCreationDate;
	}

	public String getLeaveDate() {
		return leaveDate;
	}

	public String getProfileImg() {
		return profileImg;
	}
	
	
	
	
	
}
