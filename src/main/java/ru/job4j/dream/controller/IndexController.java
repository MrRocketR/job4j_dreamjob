package ru.job4j.dream.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.dream.model.User;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        SessionChecker sessionChecker = SessionChecker.getInstance();
        sessionChecker.checkSession(model, session);
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }
}
