package vesel.newsservice.service;

import vesel.newsservice.dto.request.ArticleRequest;
import vesel.newsservice.dto.response.ArticleResponse;
import vesel.newsservice.entity.Category;

import java.security.Principal;
import java.util.List;

public interface ArticleService {
    ArticleResponse createArticle(Principal principal, ArticleRequest articleRequest);

    ArticleResponse findArticle(Long id);

    List<ArticleResponse> findArticlesByCategory(Category category);


    List<ArticleResponse> getPopularArticles();

    List<ArticleResponse> getVerificationsArticle();


    ArticleResponse approveArticle(Long articleId);

    ArticleResponse deleteArticle(Long articleId);
}
