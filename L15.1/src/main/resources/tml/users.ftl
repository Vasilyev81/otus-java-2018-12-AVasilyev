<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Users</title>
</head>
<body>
<h2>Users in database</h2>
<table border="0" cellspacing="2" cellpadding="2">
    <tr>
        <td>ID</td>
        <td>Name</td>
    </tr>
    <#list users as user>
    <tr>
        <td>${user.id}
        <td>${user.name}
            </#list>
</table>
<h4><a href="/admin_page">Admin page</a></h4>
</body>
</html>