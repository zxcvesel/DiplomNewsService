package vesel.newsservice.service;

import vesel.newsservice.dto.response.ArticleResponse;

import java.security.Principal;

public interface EvaluationArticleService {
    ArticleResponse addEvaluationArticleLike(Long articleId, Principal principal);

    ArticleResponse addEvaluationArticleDislike(Long ArticleId, Principal principal);

}
