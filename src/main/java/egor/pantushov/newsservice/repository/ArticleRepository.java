package vesel.newsservice.repository;

import vesel.newsservice.entity.Article;
import vesel.newsservice.entity.Category;
import vesel.newsservice.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByCategoryAndStatus(Category category, Status status);

    List<Article> findAllByStatus(Status status);
}
