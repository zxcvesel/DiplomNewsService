package vesel.newsservice.mapper;

import vesel.newsservice.dto.request.ArticleRequest;
import egor.pantushov.newsservice.dto.response.*;
import vesel.newsservice.dto.response.AnsichtenResponse;
import vesel.newsservice.dto.response.ArticleResponse;
import vesel.newsservice.dto.response.CommentResponse;
import vesel.newsservice.dto.response.EvaluationResponse;
import vesel.newsservice.entity.Article;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class ArticleMapper {
    public static ArticleResponse getArticleResponse(Article article) {
        List<CommentResponse> commentResponses = Optional.ofNullable(article.getComments())
                .map(comments -> comments.stream()
                        .filter(comment -> Boolean.FALSE.equals(comment.getIsDeleted()))
                        .map(CommentMapper::getCommentResponse)
                        .collect(Collectors.toList()))
                .orElse(null);
        String dateOfCreate = article.getDateOfCreate().toString();
        AnsichtenResponse ansichtenResponse = Optional.ofNullable(article.getAnsichtens())
                .map(AnsichtenMapper::getAnsichtenResponseByArticle).orElse(null);
        EvaluationResponse evaluationArticleResponse = Optional.ofNullable(article.getEvaluationArticles())
                .map(EvaluationMapper::getEvaluationResponsebyArticle).orElse(null);
        return new ArticleResponse(
                article.getArticleId(),
                article.getTitle(),
                article.getContent(),
                dateOfCreate.substring(0, dateOfCreate.length() - 5),
                UserMapper.getUserResponse(article.getAuthor()),
                commentResponses,
                evaluationArticleResponse,
                ansichtenResponse
        );
    }


    public static Article getArticle(ArticleRequest articleRequest) {
        Article article = new Article();
        article.setTitle(articleRequest.getTitle());
        article.setContent(articleRequest.getContent());
        article.setCategory(articleRequest.getCategory());
        return article;
    }


}
