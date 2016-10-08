<%@page import="java.util.List"%>
<%@page import="app.SenderServlet"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head><title>First JSP</title></head>
<body>
    <h1>Send to WildFly</h1>
    <div>
        <%
        List<String> messages = (List<String>)request.getAttribute(SenderServlet.SENDER_MESSAGES_KEY);
        for (String message : messages) {
        %>
            <p>This is message (<%=message%>)</p>
        <%
        }
        %>
    </div>
</body>
</html>