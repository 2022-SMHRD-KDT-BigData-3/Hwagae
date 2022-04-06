package Model;

public class CategoryDTO {
	
	private int catSeq;
	private String section1;
	private String section2;
	private String section3;
	private String sectionInfo;
	
	public CategoryDTO(int catSeq, String section1, String section2, String section3, String sectionInfo) {
		super();
		this.catSeq = catSeq;
		this.section1 = section1;
		this.section2 = section2;
		this.section3 = section3;
		this.sectionInfo = sectionInfo;
	}

	public int getCatSeq() {
		return catSeq;
	}

	public String getSection1() {
		return section1;
	}

	public String getSection2() {
		return section2;
	}

	public String getSection3() {
		return section3;
	}

	public String getSectionInfo() {
		return sectionInfo;
	}
	
	
	
}
