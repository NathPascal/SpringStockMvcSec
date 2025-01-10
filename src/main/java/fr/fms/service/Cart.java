package fr.fms.service;

import fr.fms.dao.ArticleRepository;
import fr.fms.dao.CategoryRepository;
import fr.fms.entities.Article;
import fr.fms.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Cart implements Serializable {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private static final long serialVersionUID = 1L;
    private List<Article> items = new ArrayList<>();

    public void addItem(Article article){
        items.add(article);
    }

    public List<Article> getItems(){
        return items;
    }

    public double getTotalPrice(){
        return items.stream().mapToDouble(Article::getPrice).sum();
    }

    public String great(){
        return "Hello World";
    }

    public List<Article> findByCategory(Category category) {
        if (categoryRepository.findById(category.getId()).isPresent()) {
            return articleRepository.findByCategory(category);
        } else {
            return new ArrayList<>();
        }
    }

}
