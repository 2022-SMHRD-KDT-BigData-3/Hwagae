package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

import Command.AjaxCommand;
import Model.ItemDAO;
import util.BusinessException;

public class CrawlingItemInfoServiceCon implements AjaxCommand{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// TODO Auto-generated method stub
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
		
			String url = request.getParameter("url").toString();
			//쿠팡테스트
			//String url = "https://www.coupang.com/vp/products/330410866?itemId=1056033808&vendorItemId=5528601567&sourceType=srp_product_ads&clickEventId=f8eb2f9d-df48-4b22-95b8-2cfef1635fd8&korePlacement=15&koreSubPlacement=5&q=%EB%AA%A8%EB%8B%88%ED%84%B0&itemsCount=36&searchId=970ec6bd1d534b189e3426c436b1e82c&rank=4&isAddedCart=";
			
			//11번가테스트
			//String url = "https://www.11st.co.kr/products/2968823506?trTypeCd=PW24&trCtgrNo=585021";
			
			//g마켓테스트
			//String url = "http://item.gmarket.co.kr/Item?goodscode=1267464808&ver=637853533511276836";
			
			//위메프 테스트
			//String url ="https://front.wemakeprice.com/deal/627372472";
			
			System.out.println("url : "+ url);
			String shopName = getShopName(url);
			
			System.out.println("shopName : "+ shopName);
			
			if(shopName == null) {
				throw new BusinessException("해당 URL는 상품정보를 지원하지 않는 페이지입니다.");
			}
				
			Document doc = Jsoup.connect(url).get();
			String elemTitle = selectItemTitle(doc, shopName);
			String elemPrice = selectItemPrice(doc, shopName);
		
			System.out.println("elemTitle : "+ elemTitle);
			System.out.println("elemPrice : "+ elemPrice);
			
			if(elemTitle.length() == 0) {
				throw new BusinessException("해당 URL는 상품정보를 지원하지 않는 페이지입니다.");
			}
			
			String NaverShopUrl = "https://search.shopping.naver.com/search/all?query="+elemTitle.replace(" ", "+");
			
			System.out.println("NaverShopUrl : "+ NaverShopUrl);
			
			doc = Jsoup.connect(NaverShopUrl).get();			
						
			LinkedHashMap<String,String> itemInfoMap = new LinkedHashMap<String, String>();
			itemInfoMap.put("상품명", elemTitle);
			itemInfoMap.put("상품가격", elemPrice);
			
			HashMap<String,String> tempMap = selectItemInfo(doc);			
			
			itemInfoMap.putAll(tempMap);
			
			if(itemInfoMap.size() == 2) {
				throw new BusinessException("해당 상품에 대한 상세정보를 찾을 수 없습니다.");
			}
	
			resultMap.put("rsltCd", 0);
			resultMap.put("itemInfo", itemInfoMap);
					
		}catch(BusinessException err) {
			err.printStackTrace();
			resultMap.put("rsltCd", -1);
			resultMap.put("errMsg", err.getMessage());
		}catch(Exception err) {
			err.printStackTrace();
			resultMap.put("rsltCd", -1);
			resultMap.put("errMsg", "상품구매 중 알 수 없는 오류가 발생하였습니다.");
		}
		
		out.print(gson.toJson(resultMap));
		
		
	}
	
	private HashMap<String, String> selectItemInfo(Document doc) {
		
		HashMap<String,String> itemInfoMap = new HashMap<String, String>();
		Elements elems  = doc.select(".basicList_detail_box__3ta3h");
		String elemText = ""; 
		
		int max = 0;
		
		for(Element elem : elems) {
			if(elem.text().length() > max) {
				elemText = elem.text();  //가장 많은 정보를 가진 행의 정보로 제공
			}	
		}
		System.out.println("elemText : " + elemText);
		String[] infoList = elemText.split(Pattern.quote("|"));
		String[] temp;
		
		for(String info : infoList) {
			if(info.contains(":")) {
				temp = info.split(":");
				itemInfoMap.put(temp[0].trim(), temp[1].trim());
			}
		}
		
		return itemInfoMap;
	}

	private String selectItemPrice(Document doc, String shopName) throws Exception {
		
		Elements elem;
		String price = "";
		if(shopName.equals("coupang")) {
			elem  = doc.select(".prod-sale-price > .total-price > strong");
		}else if(shopName.equals("11st")) {
			elem = doc.select(".price_wrap strong > .value");
		}else if(shopName.equals("gmarket")) {
			elem = doc.select("strong.price_real");
		}else {
			elem = doc.select(".normal > s.num");
		}
		
		
		price = elem.text().replace("원", "");
		price += "원";
		return price;
	}

	private String selectItemTitle(Document doc, String shopName) throws Exception {
		// TODO Auto-generated method stub
		
		Elements elem;
		String title = "";
		
		if(shopName.equals("coupang")) {
			elem  = doc.select("h2.prod-buy-header__title");
			
		}else if(shopName.equals("11st")) {
			elem = doc.select("h1.title");
		}else if(shopName.equals("gmarket")) {
			elem = doc.select("h1.itemtit");
		}else{
			elem = doc.select("h3.deal_tit");
		}
		
		title = elem.text();
		return title;
	}
	

	private String getShopName(String url) {
		
		if(url.indexOf("coupang") > 0) {
			return "coupang";
		}else if(url.indexOf("11st") > 0) {
			return "11st";
		}else if(url.indexOf("gmarket") > 0) {
			return "gmarket";
		}else if(url.indexOf("wemakeprice")> 0) {
			return "wemakeprice";
		}
		
		return null;
	}
	
	
	
	

}
