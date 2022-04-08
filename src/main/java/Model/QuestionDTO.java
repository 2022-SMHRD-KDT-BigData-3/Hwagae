package Model;

public class QuestionDTO {

	private String storeName;
	private String question;
	private String writeDate;
	
	
	public QuestionDTO(String storeName, String question, String writeDate) {
		super();
		this.storeName = storeName;
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
	
	
	
	
	
	
	
	
}
