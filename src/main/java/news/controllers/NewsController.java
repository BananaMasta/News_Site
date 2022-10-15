package news.controllers;

import news.models.News;
import news.models.Tags;
import news.models.User;
import news.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class NewsController {
    @Autowired
    NewsRepository newsRepository;

    @GetMapping("/news")
    public String newsPage(Model model) {
        List<News> allnews = newsRepository.findAll();
        model.addAttribute("allnews", allnews);
        return "newspagetab";
    }

    @PostMapping("/news")
    public String newsPageSite(@RequestParam(name = "title") String newsTitle, @RequestParam(name = "content") String newsContent,
                               @RequestParam(name = "economic", required = false) String economicTag, @RequestParam(name = "politic", required = false) String politicTag,
                               @RequestParam(name = "auto", required = false) String autoTag, @RequestParam(name = "it", required = false) String itTag,
                               @AuthenticationPrincipal User user, Model model) {
        List<Tags> newsTags = new ArrayList<>();
        if (economicTag != null) {
            newsTags.add(Tags.ECONOMIC);
        }
        if (politicTag != null) {
            newsTags.add(Tags.POLITICAL);
        }
        if (autoTag != null) {
            newsTags.add(Tags.AUTO);
        }
        if (economicTag != null) {
            newsTags.add(Tags.IT);
        }
        if (newsTitle.equals("") || newsContent.equals("")) {
            model.addAttribute("error", true);
            return "redirect:/news";
        }
        News news = new News(newsTitle, newsContent, newsTags, new Date(), user);
        newsRepository.save(news);
        return "redirect:/news";
    }

    @PostMapping("/newsTag")
    public String searchingByTag(@RequestParam(name = "tagsearch") String search, Model model) {
        model.addAttribute("allnews", newsRepository.findByTag(Tags.valueOf(search)));
        return "newspagetab";
    }

    @PostMapping("/searchDate")
    public String seachingByDate(@RequestParam(name = "dateBefore") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateBefore,
                                 @RequestParam(name = "dateAfter") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateAfter, Model model) {
        model.addAttribute("allnews", newsRepository.findByDateBetween(dateBefore, dateAfter));
        return "newspagetab";
    }
}