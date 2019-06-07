<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Founded User</title>
</head>
<body>
<h2>Founded User</h2>
<table border="0" cellspacing="2" cellpadding="1">
    <tr>
        <td>User name:</td>
        <td>${user.name}</td>
    </tr>
    <tr>
        <td>Phone:</td>
        <#list user.phones as phone>
        <tr>
        <td>${phone.number}
            </#list>
    </tr>
    <tr>
        <td>Address:</td>
        <#list user.addresses as address>
        <tr>
        <td>${address.street}
            </#list>
    </tr>
    <tr>
        <td>Company:</td>
        <td>${user.company.name}</td>
    </tr>
</table>
<h4><a href="/admin_page">Admin page</a></h4>
<h4><a href="index.html">Main Page</a></h4>
</body>
</html>