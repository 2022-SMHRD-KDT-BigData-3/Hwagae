package Model;

public class QuestionDTO {

	private String storeName;
	private int storeId;
	private String profileImg;
	private String question;
	private String writeDate;
	private String itemTitle;
	private int itemId;
	
	public QuestionDTO(int itemId,String profileImg, String question, String writeDate, String itemTitle) {
		super();
		this.itemId = itemId;
		this.profileImg = profileImg;
		this.question = question;
		this.writeDate = writeDate;
		this.itemTitle = itemTitle;
	}


	public QuestionDTO(String storeName, int storeId,String profileImg,String question, String writeDate) {
		super();
		this.storeName = storeName;
		this.storeId = storeId;
		this.profileImg = profileImg;
		this.question = question;
		this.writeDate = writeDate;
	}


	public String getStoreName() {
		return storeName;
	}


	public String getQuestion() {
		return question;
	}


	public String getWriteDate() {
		return writeDate;
	}


	public int getStoreId() {
		return storeId;
	}

	
	public String getProfileImg() {
		return profileImg;
	}

	
	public String getItemTitle() {
		return itemTitle;
	}


	public int getItemId() {
		return itemId;
	}

	

	
	
	
	
	
	
	
}
