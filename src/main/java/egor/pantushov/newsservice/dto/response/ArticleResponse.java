package egor.pantushov.newsservice.dto.response;

import lombok.Value;

import java.util.List;

@Value
public class ArticleResponse {
    Long articleId;
    String title;
    String content;
    String dateOfCreate;
    String imagePath;
    UserResponse userResponse;
    List<CommentResponse> commentResponses;
    EvaluationResponse evaluationResponse;
    AnsichtenResponse ansichtenResponse;

}
