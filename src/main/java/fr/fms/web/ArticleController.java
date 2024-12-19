package fr.fms.web;

import fr.fms.dao.ArticleRepository;
import fr.fms.entities.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;


@Controller
public class ArticleController {
    @Autowired
    ArticleRepository articleRepository;

    @GetMapping("/index")
    public String index(Model model, @RequestParam(name="page", defaultValue = "0") int page,
                                     @RequestParam(name = "keyword", defaultValue = "") String kw) {
        //List<Article> articles = articleRepository.findAll();
        Page<Article> articles = articleRepository.findByDescriptionContains(kw, PageRequest.of(page, 5));
        model.addAttribute("listArticle",articles.getContent());
        model.addAttribute("pages", new int[articles.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",kw);
        return "articles";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id, @RequestParam int page,@RequestParam String keyword){
        articleRepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/article")
    public String article(Model model) {
        model.addAttribute("article", new Article());
        return "article";
    }

    @PostMapping("/save")
    public String save(@Valid Article article, BindingResult bindingResult){
        if(bindingResult.hasErrors())  return "article";
        articleRepository.save(article);
        return "redirect:/index";

    }
}
