package news.controllers;

import news.models.News;
import news.models.Role;
import news.models.Tags;
import news.models.User;
import news.repository.NewsRepository;
import news.repository.UserRepository;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@PreAuthorize("hasAuthority('Admin')")
public class AdminController {

    @Autowired
    NewsRepository newsRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/adminpanel")
    public String adminPanel() {
        return "adminpanel";
    }

    @GetMapping("/useradminpanel")
    public String listOfUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "usermanagment";
    }

    @GetMapping("/newsadminpanel")
    public String newsForAdminPanel(Model model) {
        List<News> allnews = newsRepository.findAll();
        model.addAttribute("allnews", allnews);
        return "newsmanagment";
    }

    @GetMapping("/delete/newsdelete/{id}")
    public String deleteNews(@PathVariable(name = "id") long id) {
        newsRepository.deleteById(id);
        return "redirect:/newsadminpanel";
    }

    @GetMapping("/delete/userdelete/{id}")
    public String deleteFromDatabase(@PathVariable(name = "id") long id) {
        List<News> newsListForDelete = newsRepository.findNewsByUser(userRepository.findById(id).get());
        newsRepository.deleteAll(newsListForDelete);
        userRepository.deleteById(id);
        return "redirect:/useradminpanel";
    }


    @GetMapping("/edit/editnews/{id}")
    public String editNews(@PathVariable(name = "id") long id, Model model) {
        List<News> news = new ArrayList<>();
        news.add(newsRepository.findById(id).get());
        model.addAttribute("editnews", news);
        return "editnews";
    }


    @PostMapping("/editnews")
    public String saveEditing(@RequestParam(name = "title") String title, @RequestParam(name = "content") String content,
                              @RequestParam(name = "newstag") String tag, @RequestParam(name = "id") String id, @RequestParam(name = "user") String user) throws Exception {
        List<Tags> tagList = new ArrayList<>();
        tagList.add(Tags.valueOf(tag));
        News news = newsRepository.findById(Long.parseLong(id)).get();
        news.setTitle(title);
        news.setContent(content);
        news.setTag(tagList);
        newsRepository.save(news);
        return "redirect:/newsadminpane";
    }

    @GetMapping("/ban/userban/{id}")
    public String userBan(@PathVariable(name = "id") long id) {
        User userId = userRepository.findById(id).get();
        if (userId.isActive()) {
            userId.setActive(false);
        } else {
            userId.setActive(true);
        }
        userRepository.save(userId);
        return "redirect:/useradminpanel";
    }

    @GetMapping("/edit/edituser/{id}")
    public String editUser(@PathVariable(name = "id") long id, Model model) {
        List<User> user = new ArrayList<>();
        user.add(userRepository.findById(id).get());
        model.addAttribute("edituser", user);
        return "edituser";
    }


    @PostMapping("/edituser")
    public String saveEditing(@RequestParam(name = "name") String userName, @RequestParam(name = "password") String userPassword,
                              @RequestParam(name = "userrole") String role, @RequestParam(name = "id") String id) throws Exception {
        List<Role> roleList = new ArrayList<>();
        roleList.add(Role.valueOf(role));
        User user = userRepository.findById(Long.parseLong(id)).get();
        user.setUsername(userName);
        user.setPassword(userPassword);
        user.setRole(roleList);
        userRepository.save(user);
        return "redirect:/useradminpanel";
    }
}
