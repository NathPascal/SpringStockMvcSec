package fr.fms;

import fr.fms.dao.ArticleRepository;
import fr.fms.dao.CategoryRepository;
import fr.fms.entities.Article;
import fr.fms.entities.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SpringStockTotalArticlesByCatTests {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void testCountArticlesByCategory(){
        Category smartphones = categoryRepository.save(new Category(null, "Smartphone", null));
        Category ordinateurs = categoryRepository.save(new Category(null, "Ordinateur", null));

        articleRepository.save(new Article(null, "Samsung Galaxy S8", 250, smartphones));
        articleRepository.save(new Article(null, "Samsung Galaxy S9", 300, smartphones));
        articleRepository.save(new Article(null, "HP pavilion", 1200, ordinateurs));

        List<Object[]> totalArticles = articleRepository.countArticlesByCategory();
        Map<String, Long> totalArticlesByCategory = new HashMap<>();
        for (Object[] articleData : totalArticles) {
            Category category = (Category) articleData[0];
            Long count = (Long) articleData[1];
            totalArticlesByCategory.put(category.getName(), count);
        }
        assertEquals(2, totalArticlesByCategory.get("Smartphone"));
        assertEquals(1, totalArticlesByCategory.get("Ordinateur"));

    }

}
