package Model;

public class WS_TalkDTO {
	private int talk_Seq;
	private int item_ID;
	private int sender_store_ID;
	private int receiver_store_ID;
	private String talk_Info;
	private String confirm_YN;
	private String talk_Date;
	
	public WS_TalkDTO() {
	      
	   }
	public WS_TalkDTO(int talk_Seq, int item_ID, int sender_store_ID, int receiver_store_ID, String talk_Info,
			String confirm_YN, String talk_Date) {
		super();
		this.talk_Seq = talk_Seq;
		this.item_ID = item_ID;
		this.sender_store_ID = sender_store_ID;
		this.receiver_store_ID = receiver_store_ID;
		this.talk_Info = talk_Info;
		this.confirm_YN = confirm_YN;
		this.talk_Date = talk_Date;
	}

	public int getTalk_Seq() {
		return talk_Seq;
	}

	public void setTalk_Seq(int talk_Seq) {
		this.talk_Seq = talk_Seq;
	}

	public int getItem_ID() {
		return item_ID;
	}

	public void setItem_ID(int item_ID) {
		this.item_ID = item_ID;
	}

	public int getSender_store_ID() {
		return sender_store_ID;
	}

	public void setSender_store_ID(int sender_store_ID) {
		this.sender_store_ID = sender_store_ID;
	}

	public int getReceiver_store_ID() {
		return receiver_store_ID;
	}

	public void setReceiver_store_ID(int receiver_store_ID) {
		this.receiver_store_ID = receiver_store_ID;
	}

	public String getTalk_Info() {
		return talk_Info;
	}

	public void setTalk_Info(String talk_Info) {
		this.talk_Info = talk_Info;
	}

	public String getConfirm_YN() {
		return confirm_YN;
	}

	public void setConfirm_YN(String confirm_YN) {
		this.confirm_YN = confirm_YN;
	}

	public String getTalk_Date() {
		return talk_Date;
	}

	public void setTalk_Date(String talk_Date) {
		this.talk_Date = talk_Date;
	}
	
	
	
}
