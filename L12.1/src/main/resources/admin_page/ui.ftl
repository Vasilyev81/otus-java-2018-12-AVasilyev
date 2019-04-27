<#macro table id rows>
    <table border="0" cellspacing="2" cellpadding="2">
        <tr><td>ID</td><td>Name</td></tr>
        <#list users as user>
            <tr><td>${user.id}<td>${user.name}
        </#list>
    </table>
</#macro>