<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 01.11.2020
  Time: 16:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="ru.appline.logic.Model"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>JavaServletKurs</title>
  </head>
  <body>
  <h1>Домашняя страница по работе с пользователем</h1>
  <h1>Введите ID пользователя (0 - для вывода всего списка пользователя)</h1>
  <br/>

  Доступно пользователей:
    <%
      Model model = Model.getInstance();
      out.print(model.getFromList().size());
    %>
  <form method="get" action="get">
    <label>ID:
      <input type="text" name="ID">
    </label>
    <button type="submit">Поиск</button>
  </form>
  <a href="addUser.html">Создать нового пользователя</a>
  </body>
</html>
