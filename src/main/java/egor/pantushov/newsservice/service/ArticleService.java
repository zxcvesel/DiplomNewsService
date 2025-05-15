package egor.pantushov.newsservice.service;

import egor.pantushov.newsservice.dto.request.ArticleRequest;
import egor.pantushov.newsservice.dto.response.ArticleResponse;
import egor.pantushov.newsservice.entity.Category;

import java.security.Principal;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ArticleService {
    ArticleResponse createArticle(Principal principal, ArticleRequest request, MultipartFile imageFile);

    ArticleResponse findArticle(Long id);

    List<ArticleResponse> findArticlesByCategory(Category category);


    List<ArticleResponse> getPopularArticles();

    List<ArticleResponse> getVerificationsArticle();

    List<ArticleResponse> findAllSortedByDate();

    List<ArticleResponse> findAllSortedByLikes();

    List<ArticleResponse> findAllSortedByComments();

    ArticleResponse updateArticle(Long articleId, ArticleRequest request, MultipartFile imageFile);

    ArticleResponse approveArticle(Long articleId);

    void deleteArticle(Long articleId);

    void deleteAllDeletedArticles();

}
