package ru.appline.logic;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Model implements Serializable {

    private static final Model instance = new Model();

    private final Map<Integer,User> model;

    private Model() {
        model = new HashMap<>();
        model.put(1,new User("Anton", "Dydkin", 100000));
        model.put(1,new User("Nikolay", "Petrov", 80000));
        model.put(1,new User("Alexey", "Daranov", 70000));
    }

    public static Model getInstance() {
        return instance;
    }

    public void add(int id, User user) {
        model.put(id, user);
    }

    public Map<Integer,User> getFromList() {
        return model;
    }
}
