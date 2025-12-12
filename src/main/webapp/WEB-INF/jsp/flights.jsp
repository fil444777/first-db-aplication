<%--
  Created by IntelliJ IDEA.
  User: artem
  Date: 11.12.2025
  Time: 20:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Перелеты</title>
</head>
<body>
<h1> Список перелетов: </h1>
<c:if test="${not empty requestScope.flights}">
    <c:forEach var="flight" items="${requestScope.flights}">
        <li><a href="${pageContext.request.contextPath}/tickets?flightId=${flight.id()}">${flight.description()}</a></li>
    </c:forEach>
</c:if>
</body>
</html>
