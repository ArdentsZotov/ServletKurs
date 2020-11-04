package ru.appline.Servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/math")
public class ServletMath extends HttpServlet {

    private Gson gson = new GsonBuilder().create();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        PrintWriter pw = response.getWriter();

        double a = 0;
        double b = 0;
        char operat = '+';
        try {
            a = jo.get("a").getAsDouble();
        } catch (NullPointerException e) {}

        try {
            b = jo.get("b").getAsDouble();
        } catch (NullPointerException e) {}

        try {
            //применяю, но осуждаю
            operat = jo.get("math").getAsCharacter();
        } catch (NullPointerException e) {}

        double result = 0;
        switch (operat) {
            case ('+'):
                result = a + b;
                break;
            case ('-'):
                result = a - b;
                break;
            case ('*'):
                result = a * b;
                break;
            case ('/'):
                try {
                    if (b == 0) throw new ArithmeticException();
                    result = a / b;
                } catch (ArithmeticException exp) {
                    pw.print(gson.toJson("Деление на нуль."));
                    return;
                }
                break;
            default:
                pw.print(gson.toJson("Некорректный символ математической операции."));
                return;
        }

        JsonObject joResult = new JsonObject();
        joResult.addProperty("result", result);

        pw.print(gson.toJson(joResult,JsonObject.class));
    }
}
