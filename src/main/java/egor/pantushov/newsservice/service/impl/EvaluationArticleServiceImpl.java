package vesel.newsservice.service.impl;

import vesel.newsservice.dto.response.ArticleResponse;
import egor.pantushov.newsservice.entity.*;
import vesel.newsservice.entity.Article;
import vesel.newsservice.entity.EvaluationArticle;
import vesel.newsservice.entity.Type;
import vesel.newsservice.entity.User;
import vesel.newsservice.exeption.ArticleNotFoundException;

import vesel.newsservice.exeption.EvaluationArticleNotFoundException;
import vesel.newsservice.exeption.UserNotFoundException;
import vesel.newsservice.mapper.ArticleMapper;
import vesel.newsservice.repository.ArticleRepository;
import vesel.newsservice.repository.EvaluationArticleRepository;
import vesel.newsservice.repository.UserRepository;
import vesel.newsservice.service.EvaluationArticleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

import static java.lang.Boolean.TRUE;

@Service
@RequiredArgsConstructor
public class EvaluationArticleServiceImpl implements EvaluationArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final EvaluationArticleRepository evaluationArticleRepository;

    @Transactional
    @Override
    public ArticleResponse addEvaluationArticleLike(Long articleId, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UserNotFoundException(principal.getName()));
        System.out.println(evaluationArticleRepository.existsByArticleIdAndUserId(user.getUserId(), articleId));
        if (evaluationArticleRepository.existsByArticleIdAndUserId(articleId, user.getUserId()) == TRUE) {
            EvaluationArticle evaluationArticle = evaluationArticleRepository.findEvaluationArticleByUserIdArticleId(articleId, user.getUserId())
                    .orElseThrow(() -> new EvaluationArticleNotFoundException(articleId));

            if (evaluationArticle.getType() == Type.LIKE) {
                evaluationArticleRepository.delete(evaluationArticle);
                return null;
            }

            evaluationArticleRepository.delete(evaluationArticle);
        }

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        EvaluationArticle evaluation = new EvaluationArticle();
        evaluation.setArticle(article);
        evaluation.setType(Type.LIKE);
        evaluation.setUser(user);

        evaluationArticleRepository.save(evaluation);
        article.addEvaluationsArticles(evaluation);
        user.addEvaluationsArticles(evaluation);

        return ArticleMapper.getArticleResponse(article);

    }

    @Transactional
    @Override
    public ArticleResponse addEvaluationArticleDislike(Long articleId, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UserNotFoundException(principal.getName()));
        if (evaluationArticleRepository.existsByArticleIdAndUserId(articleId, user.getUserId())) {
            EvaluationArticle evaluationArticle = evaluationArticleRepository.findEvaluationArticleByUserIdArticleId(articleId, user.getUserId())
                    .orElseThrow(() -> new EvaluationArticleNotFoundException(articleId));

            if (evaluationArticle.getType() == Type.DISLIKE) {
                evaluationArticleRepository.delete(evaluationArticle);
                return null;
            }

            evaluationArticleRepository.delete(evaluationArticle);
        }

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        EvaluationArticle evaluation = new EvaluationArticle();
        evaluation.setArticle(article);
        evaluation.setType(Type.DISLIKE);
        evaluation.setUser(user);

        evaluationArticleRepository.save(evaluation);
        article.addEvaluationsArticles(evaluation);
        user.addEvaluationsArticles(evaluation);

        return ArticleMapper.getArticleResponse(article);
    }


}
