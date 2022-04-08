<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.net.MalformedURLException" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
    <title>네이버로그인</title>
  </head>
  <body>
  <%!
  private static String get(String apiUrl, Map<String, String> requestHeaders){
      HttpURLConnection con = connect(apiUrl);
      try {
          con.setRequestMethod("GET");
          for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
              con.setRequestProperty(header.getKey(), header.getValue());
          }


          int responseCode = con.getResponseCode();
          if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
              return readBody(con.getInputStream());
          } else { // 에러 발생
              return readBody(con.getErrorStream());
          }
      } catch (IOException e) {
          throw new RuntimeException("API 요청과 응답 실패", e);
      } finally {
          con.disconnect();
      }
  }


  private static HttpURLConnection connect(String apiUrl){
      try {
          URL url = new URL(apiUrl);
          return (HttpURLConnection)url.openConnection();
      } catch (MalformedURLException e) {
          throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
      } catch (IOException e) {
          throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
      }
  }


  private static String readBody(InputStream body){
      InputStreamReader streamReader = new InputStreamReader(body);


      try (BufferedReader lineReader = new BufferedReader(streamReader)) {
          StringBuilder responseBody = new StringBuilder();


          String line;
          while ((line = lineReader.readLine()) != null) {
              responseBody.append(line);
          }


          return responseBody.toString();
      } catch (IOException e) {
          throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
      }
  }
  %>
  <%
    String clientId = "iYurGV0OE6snPVlinTga";//애플리케이션 클라이언트 아이디값";
    String clientSecret = "Vnt0JOeCYA";//애플리케이션 클라이언트 시크릿값";
    String code = request.getParameter("code");
    String state = request.getParameter("state");
    String redirectURI = URLEncoder.encode("https://openapi.naver.com/v1/nid/me", "UTF-8");
    String apiURL;
    apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
    apiURL += "client_id=" + clientId;
    apiURL += "&client_secret=" + clientSecret;
    apiURL += "&redirect_uri=" + redirectURI;
    apiURL += "&code=" + code;
    apiURL += "&state=" + state;
    String access_token = "";
    String refresh_token = "";
    System.out.println("apiURL="+apiURL);
    try {
    	System.out.println("test1");
      URL url = new URL(apiURL);
      HttpURLConnection con = (HttpURLConnection)url.openConnection();
      con.setRequestMethod("GET");
      int responseCode = con.getResponseCode();
      BufferedReader br;
      System.out.println("test2");
      System.out.print("responseCode="+responseCode);
      if(responseCode==200) { // 정상 호출
        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
      } else {  // 에러 발생
        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
      }
      String inputLine;
      StringBuffer res = new StringBuffer();
      System.out.println("test3");
      while ((inputLine = br.readLine()) != null) {
        res.append(inputLine);
      }
      br.close();
      System.out.println("test4");
      if(responseCode==200) {
    	System.out.println("test5");
    	System.out.println(res.toString());
        out.println(res.toString());
        String[] temp_tokens = res.toString().split(","); 
        access_token = temp_tokens[0].split(":")[1];
        refresh_token = temp_tokens[1].split(":")[1];
        System.out.println(access_token + ":" + refresh_token);
        String header = "Bearer " + access_token; // Bearer 다음에 공백 추가
        apiURL = "https://openapi.naver.com/v1/nid/me";
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization", header);
        String responseBody = get(apiURL,requestHeaders);
        System.out.println(responseBody);
        int idxEmail = responseBody.indexOf("email");
        int idxMobile = responseBody.indexOf("mobile");
        int idxNextMobile = responseBody.indexOf("mobile_");
        System.out.println("Get Email : -> "+responseBody.substring(idxEmail + 8, idxMobile-3));
        String snsid = responseBody.substring(idxEmail + 8, idxMobile-3);
        System.out.println("Get Mobile : -> "+responseBody.substring(idxMobile + 9, idxNextMobile-3));
        out.print("<br>"+responseBody.substring(idxEmail + 8, idxMobile-3)+"<br>");
        out.print(responseBody.substring(idxMobile + 9, idxNextMobile-3));
        response.sendRedirect("LoginServiceCon.do?snsid="+snsid);
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  %>
  
  </body>
</html>
