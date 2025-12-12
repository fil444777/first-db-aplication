<%--
  Created by IntelliJ IDEA.
  User: artem
  Date: 11.12.2025
  Time: 19:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <span>CONTENT РУССКИЙ</span>
    <p>Size: ${requestScope.flights.size()}</p>
    <p>description: ${requestScope.flights.get(0).description()}</p>
    <p>id: ${requestScope.flights[1].id()}</p>
    <p>JSESSIONID: ${cookie.get("JSESSIONID")}</p>
    <p>PARAM id: ${param.id}</p>
    <p>HEADER id: ${header["cookie"]}</p>
    <p>NOT EMPTY: ${not empty flights}</p>
</div>
</body>
</html>
