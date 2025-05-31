<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
<head>
	<title>我的订单</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link type="text/css" rel="stylesheet" href="css/bootstrap.css">
	<link type="text/css" rel="stylesheet" href="css/style.css">
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="layer/layer.js"></script>
	<script type="text/javascript" src="js/cart.js"></script>
</head>
<body>
	
<jsp:include page="/header.jsp">
<jsp:param value="5" name="flag"/>
</jsp:include>

	<div class="cart-items">
		<div class="container">
			<h2>我的订单</h2>
			<table class="table table-bordered table-hover">
				<tr>
					<th>ID</th>
					<th>总价</th>
					<th>商品详情</th>
					<th>收货信息</th>
					<th>订单状态</th>
					<th>支付方式</th>
					<th>下单时间</th>
					<th>操作</th>
				</tr>
				
				<%--@elvariable id="orderList" type="java.util.List"--%>
				<c:forEach items="${orderList }" var="order">
>
					<tr>
						<td><p>${order.id }</p></td>
						<td><p>${order.total }</p></td>
						<td>
							<c:forEach items="${order.itemList }" var="item">
								<p>${item.goodsName }(${item.price }) x ${item.amount }</p>
							</c:forEach>
						</td>
						<td>
							<p>${order.name }</p>
							<p>${order.phone }</p>
							<p>${order.address }</p>
						</td>
						<td>
							<p>
								<c:if test="${order.status==2 }"><span style="color:red;">已付款</span></c:if>
								<c:if test="${order.status==3 }"><span style="color:green;">已发货</span></c:if>
								<c:if test="${order.status==4 }"><span style="color:black;">已完成</span></c:if>
							</p>
						</td>
						<td>
							<p>
								<c:if test="${order.paytype==1 }">微信</c:if>
								<c:if test="${order.paytype==2 }">支付宝</c:if>
								<c:if test="${order.paytype==3 }">货到付款</c:if>
							</p>
						</td>
						<td><p>${order.datetime }</p></td>
					</tr>
				</c:forEach>
			</table>
			<div class="text-center">
				<ul class="pagination">
					<c:if test="${p.pageNo > 1}">
						<li><a href="order_list?pageNo=${p.pageNo - 1}">上一页</a></li>
					</c:if>
					<li class="active"><span>第${p.pageNo}页/共${p.totalPage}页</span></li>
					<c:if test="${p.pageNo < p.totalPage}">
						<li><a href="order_list?pageNo=${p.pageNo + 1}">下一页</a></li>
					</c:if>
				</ul>
			</div>
		</div>
	</div>
	
<jsp:include page="/footer.jsp"></jsp:include>

</body>
</html>