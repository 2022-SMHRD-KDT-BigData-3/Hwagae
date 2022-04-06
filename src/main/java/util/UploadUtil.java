package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.Part;

public class UploadUtil {

	
	// 폴더를 만들 디렉토리 경로(Window 기반)
    private static final String BASE_PATH = "C:\\HWAGAE";
    private String storeId;
    private String itemId;
    private String savePath;
    
	public UploadUtil(String storeId, String itemId) {
		super();
		makeBaseDir(storeId, itemId);
		savePath = BASE_PATH+"\\"+storeId+"\\"+itemId;
	}
    
	private void makeBaseDir(String storeId, String itemId) {
		
		 File makeFolder = new File(BASE_PATH+"\\"+storeId+"\\"+itemId);
		 
		 if(!makeFolder.exists()) {
			 makeFolder.mkdirs();
		 }
	}
	
	public void saveFiles(Part filePart) {
		
		try {
		
			String filePath  =  savePath+"\\"+filePart.getSubmittedFileName();
			
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
	
	
	
   
}