package fr.fms;

import fr.fms.dao.ArticleRepository;
import fr.fms.dao.CategoryRepository;
import fr.fms.entities.Article;
import fr.fms.entities.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class SpringStockJPATests {
    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void test_add_article(){
        Category anonymous = categoryRepository.save(new Category(null, "anonymous", null));
        articleRepository.save(new Article(null,"incognito 007", 375, anonymous));

        Article article = articleRepository.findByDescriptionContains("incognito 007").get(0);

        assertEquals("incognito 007", article.getDescription());
    }

    @Test
    public void should_find_one_article(){
        Category anonymous = categoryRepository.save(new Category(null, "smart007", null));
        articleRepository.save(new Article(null,"smarto 007bis", 200, anonymous));

        Iterable<Article> articles = articleRepository.findAll();
        assertThat(articles).isNotEmpty();
    }
}
