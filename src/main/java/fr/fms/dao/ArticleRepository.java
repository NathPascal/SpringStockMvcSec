package fr.fms.dao;

import fr.fms.entities.Article;
import fr.fms.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
   public List<Article>findByDescriptionContains(String description);
   public List<Article>findByCategory(Category category);
   Page<Article> findByDescriptionContains(String description, Pageable pageable);
   Page<Article> findByCategory(Category category, Pageable pageable);
   Page<Article> findByCategoryAndDescriptionContains(Category category, String description, Pageable pageable);

   @Query("SELECT a.category, COUNT(a) FROM Article a JOIN a.category c GROUP BY c")
    List<Object[]> countArticlesByCategory();

}
