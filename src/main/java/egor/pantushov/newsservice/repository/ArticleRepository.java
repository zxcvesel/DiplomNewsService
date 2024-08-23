package egor.pantushov.newsservice.repository;

import egor.pantushov.newsservice.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article,Long> {

}
