package vesel.newsservice.mapper;

import vesel.newsservice.dto.response.EvaluationResponse;
import vesel.newsservice.entity.EvaluationArticle;
import vesel.newsservice.entity.EvaluationComment;
import vesel.newsservice.entity.Type;

import java.util.List;


public class EvaluationMapper {
    public static EvaluationResponse getEvaluationResponsebyArticle(List<EvaluationArticle> evaluationArticles) {
        long likes = evaluationArticles.stream()
                .filter(evaluation -> evaluation.getType() == Type.LIKE)
                .count();
        long dislikes = evaluationArticles.stream()
                .filter(evaluation -> evaluation.getType() == Type.DISLIKE)
                .count();
        return new EvaluationResponse(likes, dislikes);
    }

    public static EvaluationResponse getEvaluationResponseByComment(List<EvaluationComment> evaluationComments) {
        long likes = evaluationComments.stream()
                .filter(evaluation -> evaluation.getType() == Type.LIKE)
                .count();
        long dislikes = evaluationComments.stream()
                .filter(evaluation -> evaluation.getType() == Type.DISLIKE)
                .count();
        return new EvaluationResponse(likes, dislikes);
    }

}
