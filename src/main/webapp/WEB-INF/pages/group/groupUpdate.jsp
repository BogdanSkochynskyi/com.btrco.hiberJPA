<%@include file="/WEB-INF/pages/include.jsp"%>
<html>
<head>
    <title>Update group</title>
</head>
<body>
<h1>Update group</h1>

<form method="post" action="update">
    <ul>
        <li>
            Set new group name:
            <input name="newGroupName" type="text">
            <input type="hidden" name="id" value="${requestScope.id}">
        </li>
        <li>
            Submit:
            <input name="submit" type="submit">
        </li>
    </ul>
</form>
</body>
</html>
