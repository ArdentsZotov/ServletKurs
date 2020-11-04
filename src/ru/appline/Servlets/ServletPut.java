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

@WebServlet(urlPatterns = "/put")
public class ServletPut extends HttpServlet {

    private Model model = Model.getInstance();

    private Gson gson = new GsonBuilder().create();

    /**
     * Примем, что если поля пользователя не указаны, то они не изменяются
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        JsonObject jo = gson.fromJson(String.valueOf(sb), JsonObject.class);

        // обязательное значение
        int id = jo.get("id").getAsInt();

        //значения, которые могут и не быть
        String newName = null;
        String newVorname = null;
        double newSalary = 0;

        try {
            newName = jo.get("name").getAsString();
        } catch (NullPointerException e) {}

        try {
            newVorname = jo.get("vorname").getAsString();
        } catch (NullPointerException e) {}

        try {
            newSalary = jo.get("salary").getAsDouble();
        } catch (NullPointerException e) {}

        PrintWriter pw = response.getWriter();
        if (model.getFromList().containsKey(id)) {
            User user = model.getFromList().get(id);

            user.setName(newName == null ? user.getName() : newName);
            user.setVorname(newVorname == null ? user.getVorname() : newVorname);
            user.setSalary(newSalary == 0 ? user.getSalary() : newSalary);

            model.getFromList().put(id, user);

            pw.print(gson.toJson(model.getFromList().get(id)));

        } else pw.print(gson.toJson("Некорректное запрос пользователя."));
    }
}
