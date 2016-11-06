<%@ include file="../include.jsp" %>
<html>
<head>
    <title>List of groups</title>
</head>
<body>
    <h1>List of groups</h1>
    <p>
        <c:forEach items="${groupsList}" var="group">
            <a href="info?id=${group.id}">${group.name}</a><br>
        </c:forEach>
    </p>
    <p>
        <c:if test="${currentPage != 1}">
            <a href="list?page=${currentPage-1}">PREV</a>
        </c:if>
        <a href="list?page=${currentPage+1}">NEXT</a>
    </p>
</body>
</html>