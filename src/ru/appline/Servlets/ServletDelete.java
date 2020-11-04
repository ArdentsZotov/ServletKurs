package ru.appline.Servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ru.appline.logic.Model;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/delete")
public class ServletDelete extends HttpServlet {

    private Model model = Model.getInstance();

    private Gson gson = new GsonBuilder().create();

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");

        StringBuffer sb = new StringBuffer();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //создать json - объект
        JsonObject jo = gson.fromJson(String.valueOf(sb), JsonObject.class);

        //распарсили json
        int id = jo.get("id").getAsInt();

        //парсим id и формируем ответ
        PrintWriter pw = response.getWriter();
        if (id < 0) pw.print(gson.toJson("Некорректное значение."));

        if (id == 0) {
            model.getFromList().clear();
            pw.print("Все пользователи удалены.");
        }

        if (id > 0 & id <= ServletAdd.getNowCounter()) {
            if (model.getFromList().containsKey(id)) {
                model.getFromList().remove(id);
                pw.print("Пользователь с ID=" + id + " удален из списка.");
            } else {
                pw.print(gson.toJson("Пользователь с ID=" + id + " был удален ранее."));
            }
        }

        if (id > ServletAdd.getNowCounter()) pw.print(gson.toJson("Пользователь с ID=" + id + " не найден."));
    }
}
