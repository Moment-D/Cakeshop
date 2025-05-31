<%@ page language="java" contentType="text/html; charset=utf-8"
		 pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>订单列表</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
	<style>
		.order-status {
			padding: 4px 8px;
			border-radius: 4px;
			font-size: 0.85em;
		}
		.status-unpaid { background-color: #ffeeba; color: #856404; }
		.status-paid { background-color: #c3e6cb; color: #155724; }
		.status-shipping { background-color: #b8daff; color: #004085; }
		.status-completed { background-color: #d6d8db; color: #383d41; }
		.product-list {
			list-style: none;
			padding-left: 0;
			margin-bottom: 0;
		}
		.product-list li {
			margin-bottom: 5px;
			padding-bottom: 5px;
			border-bottom: 1px dashed #eee;
		}
		.product-list li:last-child {
			border-bottom: none;
			margin-bottom: 0;
		}
		.table-responsive {
			overflow-x: auto;
		}
		.action-buttons .btn {
			margin-bottom: 5px;
		}
	</style>
</head>
<body>
<div class="container-fluid">
	<jsp:include page="/admin/header.jsp"></jsp:include>

	<!-- 调试信息 -->
	<div class="alert alert-info mt-3">
		<p>分页对象: ${p != null ? '存在' : '不存在'}</p>
		<p>订单列表: ${p.list != null ? p.list.size() : 0} 条记录</p>
		<p>当前状态: ${status}</p>
	</div>

	<div class="row mt-3">
		<div class="col-md-12">
			<ul class="nav nav-tabs">
				<li class="nav-item">
					<a class="nav-link ${status == 0 ? 'active' : ''}"
					   href="${pageContext.request.contextPath}/admin/order_list">全部订单</a>
				</li>
				<li class="nav-item">
					<a class="nav-link ${status == 1 ? 'active' : ''}"
					   href="${pageContext.request.contextPath}/admin/order_list?status=1">未付款</a>
				</li>
				<li class="nav-item">
					<a class="nav-link ${status == 2 ? 'active' : ''}"
					   href="${pageContext.request.contextPath}/admin/order_list?status=2">已付款</a>
				</li>
				<li class="nav-item">
					<a class="nav-link ${status == 3 ? 'active' : ''}"
					   href="${pageContext.request.contextPath}/admin/order_list?status=3">配送中</a>
				</li>
				<li class="nav-item">
					<a class="nav-link ${status == 4 ? 'active' : ''}"
					   href="${pageContext.request.contextPath}/admin/order_list?status=4">已完成</a>
				</li>
			</ul>

			<div class="card mt-3">
				<div class="card-body">
					<c:if test="${empty p.list}">
						<div class="alert alert-info text-center">
							暂无订单数据
						</div>
					</c:if>

					<c:if test="${not empty p.list}">
						<div class="table-responsive">
							<table class="table table-hover">
								<thead class="thead-light">
								<tr>
									<th>ID</th>
									<th>总价</th>
									<th>商品详情</th>
									<th>收货信息</th>
									<th>订单状态</th>
									<th>支付方式</th>
									<th>下单用户</th>
									<th>下单时间</th>
									<th>操作</th>
								</tr>
								</thead>
								<tbody>
								<c:forEach items="${p.list}" var="order">
									<tr>
										<td>${order.id}</td>
										<td><fmt:formatNumber value="${order.total}" type="currency" currencySymbol="¥"/></td>
										<td>
											<ul class="product-list">
												<c:forEach items="${order.itemList}" var="item">
													<li>
															${item.goodsName}
														(<fmt:formatNumber value="${item.price}" type="currency" currencySymbol="¥"/>)
														× ${item.amount}
													</li>
												</c:forEach>
											</ul>
										</td>
										<td>
											<div><strong>${order.name}</strong></div>
											<div>${order.phone}</div>
											<div class="text-muted small">${order.address}</div>
										</td>
										<td>
											<c:choose>
												<c:when test="${order.status == 1}">
													<span class="order-status status-unpaid">未付款</span>
												</c:when>
												<c:when test="${order.status == 2}">
													<span class="order-status status-paid">已付款</span>
												</c:when>
												<c:when test="${order.status == 3}">
													<span class="order-status status-shipping">配送中</span>
												</c:when>
												<c:when test="${order.status == 4}">
													<span class="order-status status-completed">已完成</span>
												</c:when>
											</c:choose>
										</td>
										<td>
											<c:choose>
												<c:when test="${order.paytype == 1}">微信</c:when>
												<c:when test="${order.paytype == 2}">支付宝</c:when>
												<c:when test="${order.paytype == 3}">货到付款</c:when>
											</c:choose>
										</td>
										<td>${order.user.username}</td>
										<td><fmt:formatDate value="${order.datetime}" pattern="yyyy-MM-dd HH:mm"/></td>
										<td class="action-buttons">
											<c:if test="${order.status == 2}">
												<a class="btn btn-sm btn-success"
												   href="${pageContext.request.contextPath}/admin/order_status?id=${order.id}&status=3">
													发货
												</a>
											</c:if>
											<c:if test="${order.status == 3}">
												<a class="btn btn-sm btn-warning"
												   href="${pageContext.request.contextPath}/admin/order_status?id=${order.id}&status=4">
													完成
												</a>
											</c:if>
											<a class="btn btn-sm btn-danger"
											   onclick="return confirm('确定要删除此订单吗？')"
											   href="${pageContext.request.contextPath}/admin/order_delete?id=${order.id}&pageNo=${p.pageNo}&status=${status}">
												删除
											</a>
										</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
						</div>
					</c:if>

					<c:if test="${not empty p.list}">
						<div class="d-flex justify-content-center mt-4">
							<jsp:include page="/page.jsp">
								<jsp:param value="/admin/order_list" name="url"/>
								<jsp:param value="&status=${status}" name="param"/>
							</jsp:include>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
	// 删除订单确认
	function confirmDelete() {
		return confirm('确定要删除此订单吗？');
	}
</script>
</body>
</html>