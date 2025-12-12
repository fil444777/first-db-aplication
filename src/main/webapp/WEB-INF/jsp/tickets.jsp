<%@ page import="jdbs.service.TicketService" %>
<%@ page import="jdbs.dto.TicketDto" %><%--
  Created by IntelliJ IDEA.
  User: artem
  Date: 11.12.2025
  Time: 19:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Купленные билеты:</h1>
<ul>
    <c:if test="${not empty requestScope.tickets}">
        <c:forEach var="ticket" items="${requestScope.tickets}">
            <li>${fn:toLowerCase(ticket.seatNo())}</li>
        </c:forEach>
    </c:if>
</ul>
</body>
</html>
