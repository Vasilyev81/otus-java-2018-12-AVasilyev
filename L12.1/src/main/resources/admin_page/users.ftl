<#import "ui.ftl" as ui/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Users</title>
    <link href="style/my.css" rel="stylesheet">
</head>
<body>
<@ui.table id="table1" rows=persons![]/>
<h4><a href="/admin_page">Admin page</a></h4>
</body>
</html>