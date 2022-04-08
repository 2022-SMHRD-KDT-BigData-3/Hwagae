package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

public class UploadUtil {

	// 폴더를 만들 디렉토리 경로(Window 기반)
    private String serverPath;
    private String storeId;
    private String itemId;
    
	public UploadUtil(HttpServletRequest request) {
		super();
		this.serverPath =request.getServletContext().getRealPath("/");
	}
	
	private void makeBaseDir(String storeId, String itemId) {
		
		 File makeFolder = new File(String.format("%s\\uplodedFiles\\%s\\%s", serverPath,storeId,itemId));
		 
		 if(!makeFolder.exists()) {
			 makeFolder.mkdirs();
		 }
		 
	}
	
	public void saveFile(Part filePart, String storeId, String itemId){
		
		try {
			makeBaseDir(storeId, itemId);
			String filePath  = String.format("%s\\uplodedFiles\\%s\\%s\\%s", serverPath,storeId,itemId, filePart.getSubmittedFileName());
			
			InputStream fis = filePart.getInputStream();
			OutputStream fos = new FileOutputStream(filePath);
			
			byte[] buf = new byte[1024];
			int len = 0;
			
			while((len = fis.read(buf, 0, 1024)) != -1)
				fos.write(buf, 0, len);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void saveFile(Part filePart, int storeId, int itemId) {
		saveFile(filePart, String.valueOf(storeId), String.valueOf(itemId));
	}
	
	public List<String> getImgFiles(String storeId, String itemId){

		List<String> resultList = new ArrayList<String>();
		
		File dir = new File(String.format("%s\\uplodedFiles\\%s\\%s", serverPath,storeId,itemId));
		System.out.println("abs : "+dir.getAbsolutePath());
		String[] filenames = dir.list();
		
		for (String filename : filenames) {
			resultList.add(String.format("\\Hwagae\\uplodedFiles\\%s\\%s\\%s", storeId, itemId, filename)); 
		}

		return resultList;
	}
	
	
	public List<String> getImgFiles(int storeId, int itemId){

		return getImgFiles(String.valueOf(storeId), String.valueOf(itemId));
	}
	
	public String getImgFile(String filename,String storeId, String itemId){
		
		File dir = new File(String.format("%s\\uplodedFiles\\%s\\%s", serverPath,storeId,itemId));
		
		if(!dir.exists()) {  //프로필 사진을 등록하지 않은 경우
			 return "\\Hwagae\\Hwagae\\images\\user.png";
		}
		
		return String.format("\\Hwagae\\uplodedFiles\\%s\\%s\\%s", storeId, itemId, filename);
	}
	
	public String getImgFile(String filename,int storeId, int itemId){
		
		return getImgFile(filename, String.valueOf(storeId), String.valueOf(itemId));
	}

	public void saveProfile(Part profile, String storeId, String itemId) {
		
		makeBaseDir(storeId,itemId);
		
		File dir = new File(String.format("%s\\uplodedFiles\\%s\\%s", serverPath,storeId,itemId));
		System.out.println("abs : "+dir.getAbsolutePath());
		String[] filenames = dir.list();
		
		for (String filename : filenames) {
			dir = new File(String.format("%s\\uplodedFiles\\%s\\%s\\%s", serverPath,storeId,itemId, filename)); 
			dir.delete();
		}
		
		saveFile(profile, storeId, itemId);
	
	}
	
	

   
}