<%@include file="/WEB-INF/pages/include.jsp"%>
<html>
<head>
    <title>Add group</title>
</head>
<body>
    <h1>Add new group</h1>
    <form method="post" action="add">
        <ul>
            <li>
                Input name:
                <input name="groupName" type="text">
            </li>
            <li>
                Submit:
                <input name="submit" type="submit">
            </li>
        </ul>
    </form>
</body>
</html>
