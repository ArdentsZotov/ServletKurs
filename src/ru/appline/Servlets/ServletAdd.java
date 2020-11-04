package ru.appline.Servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ru.appline.logic.Model;
import ru.appline.logic.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet(urlPatterns = "/add")
public class ServletAdd extends HttpServlet {

    private static AtomicInteger counter = new AtomicInteger(4);

    Model model = Model.getInstance();

    private Gson gson = new GsonBuilder().create();

//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setContentType("text/html;charset=utf-8");
//        request.setCharacterEncoding("utf-8");
//        PrintWriter pw = response.getWriter();
//
//        String name = request.getParameter("name");
//        String vorname = request.getParameter("vorname");
//        double salary = Double.parseDouble(request.getParameter("salary"));
//
//        User user = new User(name, vorname, salary);
//        model.add(counter.getAndIncrement(),user);
//
//        pw.printf("<html>"+
//                "<h1>Пользователь "+vorname+" "+name+" с зарплатой="+salary+" добавлен</h1>"+
//                "<a href=\"addUser.html\">Создать нового пользователя</a><br/>"+
//                "<a href=\"index.jsp\">Домой</a>"+
//                "</html>");
//
//    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuffer sb = new StringBuffer();
        String line;

        BufferedReader reader = request.getReader();
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonObject jo = gson.fromJson(String.valueOf(sb), JsonObject.class);
        request.setCharacterEncoding("utf-8");

        String name = jo.get("name").getAsString();
        String vorname = jo.get("vorname").getAsString();
        double salary = jo.get("salary").getAsDouble();

        User user = new User(name, vorname, salary);

        model.add(counter.getAndIncrement(),user);

        response.setContentType("application/json;charset=utf-8");

        PrintWriter pw = response.getWriter();
        pw.print(gson.toJson(model.getFromList()));
    }

    protected static int getNowCounter() {
        return counter.get();
    }
}
