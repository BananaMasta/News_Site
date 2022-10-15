package news.controllers;

import news.models.News;
import news.models.Role;
import news.models.Tags;
import news.models.User;
import news.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/registration")
    public String userRegForm() {
        return "registration";
    }

    @GetMapping("/login")
    public String loginUser() {
        return "login";
    }

    @PostMapping("/registration")
    public String newUserRegistration(@RequestParam(name = "login") String login, @RequestParam(name = "psw") String password,
                                      @RequestParam(name = "psw-repeat") String rePassword, Model model) {
        List<Role> roleList = new ArrayList<>();
        roleList.add(Role.User);
        User user = new User(login, password, roleList, true);
        if (password.equals(rePassword)) {
            userRepository.save(user);
        } else {
            model.addAttribute("errorreg", true);
            return "redirect:/registration";
        }
        return "registration";
    }
}
