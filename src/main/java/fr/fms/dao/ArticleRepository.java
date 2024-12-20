package fr.fms.dao;

import fr.fms.entities.Article;
import fr.fms.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
   Page<Article> findByDescriptionContains(String description, Pageable pageable);
   Page<Article> findByCategory(Category category, Pageable pageable);
   Page<Article> findByCategoryAndDescriptionContains(Category category, String description, Pageable pageable);
}
