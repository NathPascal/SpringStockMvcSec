package fr.fms;

import fr.fms.dao.ArticleRepository;
import fr.fms.dao.CategoryRepository;
import fr.fms.entities.Article;
import fr.fms.entities.Category;
import fr.fms.service.Cart;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SpringStockServiceTests {

    public static Article article;
    public static Category category;

    @Mock
    ArticleRepository articleRepository;

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    Cart cart;

    @BeforeAll
    static void initialzeData(){
        category = new Category(1L, "Smartphone", null );
        article = new Article(1L, "Samsung Galaxy S8", 250, category);
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_find_by_category(){
        List<Article> items = new ArrayList<>();
        items.add(article);
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        when(articleRepository.findByCategory(category)).thenReturn(items);

        final List<Article> expectedResults = cart.findByCategory(category);

        verify(articleRepository).findByCategory(category);
        assertNotEquals(0, expectedResults.size());
    }
}
