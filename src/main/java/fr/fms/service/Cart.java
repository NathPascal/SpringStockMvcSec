package fr.fms.service;

import fr.fms.entities.Article;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Article> items = new ArrayList<>();

    public void addItem(Article article){
        items.add(article);
    }

    public List<Article> getItems(){
        return items;
    }
}
