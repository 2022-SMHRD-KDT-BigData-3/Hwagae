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
	private String registrationDate;
	private String tradeStatus;
	
	//바둑판 형식의 상품정보 출력에 필요한 정보
	public ItemDTO(int itemId, String itemTitle, int price, String registrationDate, String imgPath, String tradeStatus) {
		super();
		this.itemId = itemId;
		this.itemTitle = itemTitle;
		this.price = price;
		this.registrationDate = registrationDate;
		this.imgPath = imgPath;
		this.tradeStatus = tradeStatus;
	}
	
	
	/*입력에 필요한 일부 정보*/
	public ItemDTO(int storeId,String itemTitle, String itemInfo, String itemCategory, String itemStatus, String exchangeYn,
			int price, String includeDeliveryPriceYn, String relationTag, String tradeArea, int stock,
			String safetyTradeYn, String imgPath) {
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
	}
	
	/*출력에 필요한 전체 정보*/
	public ItemDTO(int itemId, int storeId, String itemTitle, String itemInfo, String itemCategory, String itemStatus,
			String exchangeYn, int price, String includeDeliveryPriceYn, String relationTag, String tradeArea,
			int stock, String safetyTradeYn, int numLike,String registrationDate, String tradeState) {
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
	
	public String getRegistrationDate() {
		return registrationDate;
	}
	
	public String getTradeStatus() {
		return tradeStatus;
	}
	
}


