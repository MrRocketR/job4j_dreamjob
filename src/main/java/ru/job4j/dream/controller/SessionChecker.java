package ru.job4j.dream.controller;

import ru.job4j.dream.model.User;

import javax.servlet.http.HttpSession;

public class SessionChecker {

    public static User getUserToModel(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
       return user;
    }
}
