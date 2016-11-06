<%@ include file="../include.jsp"%>
<html>
<head>
    <title>Group ${group.name} info</title>
</head>
<body>
    <h1>${group.name}</h1>
    <h2><a href="update?id=${group.id}">Update group info</a></h2>
    <%--<c:if test="${group.students} != null"--%>
    <%--<p><c:forEach items="${group.students}" var="student">${student}</c:forEach></p>--%>
</body>
</html>
