package ru.appline.Servlets;

import ru.appline.logic.Model;
import ru.appline.logic.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(urlPatterns = "/get")
public class ServletList extends HttpServlet {

    private Model model = Model.getInstance();

    private final String home = "<a href=\"index.jsp\">Домой</a>";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter pw = response.getWriter();

        int position = Integer.parseInt(request.getParameter("ID"));

        if (position > model.getFromList().size() || position < 0) {
            String message = position < 0 ? "Некорректный ввод" : "Пользователь c ID=" + position + " не найден";
            pw.print("<html>" +
                    message + "</br>"+
                    home +
                    "</html>");
            return;
        }

        if (position == 0) {
            pw.print("<html>" +
                    "<h3>Доступно пользователей</h3>" +
                    "ID пользователя " +
                    "<ul>");

            for (Map.Entry<Integer, User> a : model.getFromList().entrySet()) {
                pw.print("<li>" + a.getKey() + "</li>" +
                        "<ul>" +
                        "<li>Name: " + a.getValue().getName() + "</li>" +
                        "<li>Vorname: " + a.getValue().getVorname() + "</li>" +
                        "<li>Salary: " + a.getValue().getSalary() + "</li>" +
                        "</ul>"
                );
            }
            pw.print("</ul>" +
                    home +
                    "</html>");
        } else {
            pw.print("<html>" +
                    "Пользователь, ID=" + position + "</br>" +
                    model.getFromList().get(position).getVorname() + " " +
                    model.getFromList().get(position).getName() + ", " +
                    "зарплата=" + model.getFromList().get(position).getSalary() + "</br>" +
                    home +
                    "</html>");
        }
    }
}
