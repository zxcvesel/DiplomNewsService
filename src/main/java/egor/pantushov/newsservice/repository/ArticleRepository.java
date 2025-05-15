package egor.pantushov.newsservice.repository;

import egor.pantushov.newsservice.entity.Article;
import egor.pantushov.newsservice.entity.Category;
import egor.pantushov.newsservice.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByCategoryAndStatus(Category category, Status status);

    List<Article> findAllByStatus(Status status);
    List<Article> findAllByStatusOrderByDateOfCreateDesc(Status status);

    @Query("""
    SELECT a
    FROM Article a
    LEFT JOIN a.evaluationArticles e ON e.type = egor.pantushov.newsservice.entity.Type.LIKE
    WHERE a.status = :status
    GROUP BY a
    ORDER BY COUNT(e) DESC
""")
    List<Article> findAllByStatusOrderByLikesDesc(@Param("status") Status status);

    @Query("""
    SELECT a
    FROM Article a
    LEFT JOIN a.comments c
    WHERE a.status = :status
    GROUP BY a
    ORDER BY COUNT(c) DESC
""")
    List<Article> findAllByStatusOrderByCommentsDesc(@Param("status") Status status);
    void deleteByStatus(Status status);

}

