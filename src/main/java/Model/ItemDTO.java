package Model;

public class ItemDTO {

	private int itemId;
	private int StoreId;
	private String itemTitle; 
	private String itemInfo;
	private String itemCategory;
	private String itemStatus;       
	private String exchangeYn;
	private int price;
	private String includeDeliveryPriceYn;
	private String relationTag;
	private String tradeArea;
	private int stock;
	private String safetyTradeYn;
	private String imgPath;
	private int numLike;
	private int numQuestion;
	private String registrationDate;
	private String registrationDate2;
	private String tradeStatus;
	private String vendorUrl;
	private String vendorUrlInfo;
	


	//바둑판 형식의 상품정보 출력에 필요한 정보
	public ItemDTO(int itemId, String imgPath, String tradeStatus, String itemTitle, int price, String safetyTradeYn, int numLike, int numQuestion, String registrationDate, String registrationDate2) {
		super();
		this.itemId = itemId;
		this.imgPath = imgPath;
		this.tradeStatus = tradeStatus;
		this.itemTitle = itemTitle;
		this.price = price;
		this.safetyTradeYn = safetyTradeYn;
		this.numLike = numLike;
		this.numQuestion = numQuestion;
		this.registrationDate  = registrationDate;
		this.registrationDate2 = registrationDate2;
	}
	
	
	/*입력에 필요한 일부 정보*/
	public ItemDTO(int storeId,String itemTitle, String itemInfo, String itemCategory, String itemStatus, String exchangeYn,
			int price, String includeDeliveryPriceYn, String relationTag, String tradeArea, int stock,
			String safetyTradeYn, String imgPath, String vendorUrl, String vendorUrlInfo) {
		super();
		this.StoreId = storeId;
		this.itemTitle = itemTitle;
		this.itemInfo = itemInfo;
		this.itemCategory = itemCategory;
		this.itemStatus = itemStatus;
		this.exchangeYn = exchangeYn;
		this.price = price;
		this.includeDeliveryPriceYn = includeDeliveryPriceYn;
		this.relationTag = relationTag;
		this.tradeArea = tradeArea;
		this.stock = stock;
		this.safetyTradeYn = safetyTradeYn;
		this.imgPath = imgPath;
		this.vendorUrl = vendorUrl;
		this.vendorUrlInfo = vendorUrlInfo;
	}
	
	/*출력에 필요한 전체 정보*/
	public ItemDTO(int itemId, int storeId, String itemTitle, String itemInfo, String itemCategory, String itemStatus,
			String exchangeYn, int price, String includeDeliveryPriceYn, String relationTag, String tradeArea,
			int stock, String safetyTradeYn, int numLike,String registrationDate, String tradeState, String vendorUrl, String vendorUrlInfo) {
		super();
		this.itemId = itemId;
		this.StoreId = storeId;
		this.itemTitle = itemTitle;
		this.itemInfo = itemInfo;
		this.itemCategory = itemCategory;
		this.itemStatus = itemStatus;
		this.exchangeYn = exchangeYn;
		this.price = price;
		this.includeDeliveryPriceYn = includeDeliveryPriceYn;
		this.relationTag = relationTag;
		this.tradeArea = tradeArea;
		this.stock = stock;
		this.safetyTradeYn = safetyTradeYn;
		this.numLike = numLike;
		this.registrationDate = registrationDate;
		this.tradeStatus = tradeState;
		this.vendorUrl = vendorUrl;
		this.vendorUrlInfo = vendorUrlInfo;
	}


	public int getItemId() {
		return itemId;
	}


	public int getStoreId() {
		return StoreId;
	}


	public String getItemTitle() {
		return itemTitle;
	}


	public String getItemInfo() {
		return itemInfo;
	}


	public String getItemCategory() {
		return itemCategory;
	}


	public String getItemStatus() {
		return itemStatus;
	}


	public String getExchangeYn() {
		return exchangeYn;
	}


	public int getPrice() {
		return price;
	}


	public String getIncludeDeliveryPriceYn() {
		return includeDeliveryPriceYn;
	}


	public String getRelationTag() {
		return relationTag;
	}


	public String getTradeArea() {
		return tradeArea;
	}


	public int getStock() {
		return stock;
	}


	public String getSafetyTradeYn() {
		return safetyTradeYn;
	}


	public String getImgPath() {
		return imgPath;
	}


	public int getNumLike() {
		return numLike;
	}
	
	public int getNumQuestion() {
		return numQuestion;
	}
	
	public String getRegistrationDate() {
		return registrationDate;
	}
	
	public String getRegistrationDate2() {
		return registrationDate2;
	}
	
	public String getTradeStatus() {
		return tradeStatus;
	}
	
	public String getVendorUrl() {
		return vendorUrl;
	}
	
	public String getVendorUrlInfo() {
		return vendorUrlInfo;
	}
}


