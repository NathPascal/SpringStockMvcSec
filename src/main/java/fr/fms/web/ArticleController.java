package fr.fms.web;

import fr.fms.dao.ArticleRepository;
import fr.fms.dao.CategoryRepository;
import fr.fms.entities.Article;
import fr.fms.entities.Category;
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



@Controller
public class ArticleController {
    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/index")
    public String index(Model model, @RequestParam(name="page", defaultValue = "0") int page,
                        @RequestParam(name = "keyword", defaultValue = "") String kw,
                        @RequestParam(name = "categoryId", required = false) Long categoryId) {
        Page<Article> articles;
        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId).orElse(null);
            articles = articleRepository.findByCategoryAndDescriptionContains(category, kw, PageRequest.of(page, 5));
        } else {
            articles = articleRepository.findByDescriptionContains(kw, PageRequest.of(page, 5));
        }
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("listArticle", articles.getContent());
        model.addAttribute("pages", new int[articles.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", kw);
        model.addAttribute("selectedCategoryId", categoryId);
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
        model.addAttribute("categories", categoryRepository.findAll());
        return "article";
    }

    @PostMapping("/save")
    public String save(@Valid Article article, BindingResult bindingResult){
        if(bindingResult.hasErrors())  return "article";
        if (article.getCategory() !=null) {
            Category category = categoryRepository.findById(article.getCategory().getId()).orElse(null);
            if (category != null) {
                article.setCategory(category);
            }
        }
        System.out.println(article);
        articleRepository.save(article);
        return "redirect:/index";

    }

//    @GetMapping("/category")
//    public String category(Model model, @RequestParam Long id, @RequestParam(name="page", defaultValue="0") int page) {
//        Category category = categoryRepository.findById(id).orElse(null);
//        Page<Article> articles = articleRepository.findByCategory(category, PageRequest.of(page,5));
//        model.addAttribute("listArticle", articles.getContent());
//        model.addAttribute("pages", new int[articles.getTotalPages()]);
//        model.addAttribute("currentPage", page);
//        model.addAttribute("categories", categoryRepository.findAll());
//        model.addAttribute("selectedCategory", category);
//        return "articles";
//    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long id){
        Article article = articleRepository.findById(id).orElse(null);
        model.addAttribute("article", article);
        model.addAttribute("categories", categoryRepository.findAll());
        return "article";
    }
}
