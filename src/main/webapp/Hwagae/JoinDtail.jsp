<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <!-- Basic -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    
    <!-- Mobile Metas -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    
    <!-- Site Metas -->
    <title>Cloapedia - Stylish Magazine Blog Template</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <meta name="author" content="">
    
    <!-- Site Icons -->
    <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon" />
    <link rel="apple-touch-icon" href="images/apple-touch-icon.png">
    
    <!-- Design fonts -->
    <link href="https://fonts.googleapis.com/css?family=Ubuntu:300,400,400i,500,700" rel="stylesheet"> 
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,400i,500,700,900" rel="stylesheet"> 

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- FontAwesome Icons core CSS -->
    <link href="css/font-awesome.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="style.css" rel="stylesheet">

    <!-- Responsive styles for this template -->
    <link href="css/responsive.css" rel="stylesheet">

    <!-- Colors for this template -->
    <link href="css/colors.css" rel="stylesheet">

    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<body>
<%String snsid = request.getParameter("snsid"); %>
							<br>
                            <div class="custombox clearfix">
                                <h4 class="small-title">회원가입</h4>
                                <div class="row">
                                    <div class="col-lg-12">
                                        <form action="JoinServiceCon.do" method="post" class="form-wrapper">
                                            <input type="hidden" class="form-control" name="snsid" value="<%=snsid%>"><br>
											<input type="text" class="form-control" name="store_name" placeholder="상점이름을 입력하세요"><br>
											<textarea class="form-control" name="store_info" placeholder="상점소개글을 입력하세요"></textarea>
											<input type="text" class="form-control" name="mobile" maxlength='11' placeholder="전화번호를 입력하세요(-표 없이)"><br>
											<input type="text" class="form-control" name="area" maxlength='6' placeholder="지역을 입력하세요(도단위)"><br>
											<input type="text" class="form-control" name="zip_code" maxlength='5' placeholder="우변편호를 입력하세요(5자리)"><br>
											<input type="text" class="form-control" name="basic_address" placeholder="주소를 입력하세요"><br>
											<input type="text" class="form-control" name="dtail_address" placeholder="세부주소를 입력하세요"><br>
											<input type="submit" class="btn btn-primary" value="회원가입" class="button fit">
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div><!-- end page-wrapper -->
                    </div><!-- end col -->

    <!-- Core JavaScript
    ================================================== -->
    <script src="js/jquery.min.js"></script>
    <script src="js/tether.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/custom.js"></script>

</body>
</html>