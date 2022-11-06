package ru.job4j.dreamjob.controller;

import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.User;

import javax.servlet.http.HttpSession;

public class SessionChecker {

    private static SessionChecker instance;

    public static SessionChecker getInstance() {
        if (instance == null) {
            instance = new SessionChecker();
        }

        return instance;
    }
    public void checkSession(Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
    }

}
