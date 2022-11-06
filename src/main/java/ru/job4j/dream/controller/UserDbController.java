package ru.job4j.dream.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.dream.model.User;
import ru.job4j.dream.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class UserDbController {

   private final UserService userService;

    public UserDbController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/regUser")
    public String regUser(Model model, @RequestParam(name = "fail", required = false) Boolean fail,
                          HttpSession session) {
        model.addAttribute("fail", fail != null);
        SessionChecker sessionChecker = SessionChecker.getInstance();
        User user = sessionChecker.getUserToModel(session);
        model.addAttribute("user", user);
        return "regUser";
    }

    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute User user) {
        Optional<User> regUser = userService.add(user);
        if (regUser.isEmpty()) {
            return "redirect:/regUser?fail=true";
        }
        return "redirect:/regUser";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = userService.findUserByEmailAndPassword(
                user.getEmail(), user.getPassword()
        );
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/index";
    }


}
