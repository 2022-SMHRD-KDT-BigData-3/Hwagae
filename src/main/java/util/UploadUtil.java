package util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

public class UploadUtil {

	// 폴더를 만들 디렉토리 경로(Window 기반)
    private String serverPath;
    private String storeId;
    private String itemId;
    
    int newWidth = 600;                                  // 변경 할 넓이
    int newHeight = 600;
    
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
			
			//리사이즈 하기
			Image image = ImageIO.read(new File(filePath));
			
			// 원본 이미지 사이즈 가져오기
            int imageWidth = image.getWidth(null);
            int imageHeight = image.getHeight(null);
            double ratio =0;
            int w = 0;
            int h = 0;
 
            ratio = (double)newWidth/(double)imageWidth;
            w = (int)(imageWidth * ratio);
            h = w;
 
            Image resizeImage = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
            
            // 새 이미지  저장하기
            BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics g = newImage.getGraphics();
            g.drawImage(resizeImage, 0, 0, null);
            g.dispose();
            ImageIO.write(newImage, "jpg", new File(filePath));
			
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
		
		//다른 팀원이 등록한 상품이라서 이미지폴더가 없는 경우
		if(filenames == null) {
			resultList.add("\\Hwagae\\Hwagae\\images\\imgNotFound.png");
			return resultList;
		}
		
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
			if(itemId.equals("profile")) {
				return "\\Hwagae\\Hwagae\\images\\user.png";
			}else {
				return "\\Hwagae\\Hwagae\\images\\imgNotFound.png";
			}
			 
		}
		
		return String.format("\\Hwagae\\uplodedFiles\\%s\\%s\\%s", storeId, itemId, filename);
	}
	
	public String getImgFile(String filename,int storeId, int itemId){
		
		return getImgFile(filename, String.valueOf(storeId), String.valueOf(itemId));
	}

	public void saveProfile(Part profile, String storeId, String itemId) {
		
		makeBaseDir(storeId,itemId);
		
		File dir = new File(String.format("%s\\uplodedFiles\\%s\\%s", serverPath,storeId,itemId));
		
		String[] filenames = dir.list();
		
		for (String filename : filenames) {
			dir = new File(String.format("%s\\uplodedFiles\\%s\\%s\\%s", serverPath,storeId,itemId, filename)); 
			dir.delete();
		}
		
		saveFile(profile, storeId, itemId);
	
	}

	public String getMainImgName(Integer itemId) {
		String result = null;
		File dir = new File(String.format("%s\\uplodedFiles\\%s\\%s", serverPath,"10000000",itemId));
		
		if(!dir.exists()) {
			 return null;
		 }
		
		String[] filenames = dir.list();
		
		for (String filename : filenames) {
			result = filename;
			break;
		}
		
		return result;
	}
	
	

   
}