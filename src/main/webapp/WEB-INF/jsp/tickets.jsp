<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="hibernate.service.TicketService" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Билеты</title>
</head>
<body>
<%@include file="header.jsp"%>
<h1>Купленные билеты:</h1>
<ul>
    <c:if test="${not empty requestScope.tickets}">
        <c:forEach var="ticket" items="${requestScope.tickets}">
            <li>
                Пассажир: ${ticket.passengerName},
                Место: ${ticket.seatNo},
                Стоимость: ${ticket.cost}
            </li>
        </c:forEach>
    </c:if>
</ul>
</body>
</html>
