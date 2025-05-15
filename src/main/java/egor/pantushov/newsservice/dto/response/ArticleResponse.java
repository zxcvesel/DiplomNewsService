package egor.pantushov.newsservice.dto.response;

import egor.pantushov.newsservice.entity.Category;
import lombok.Value;

import java.util.List;

@Value
public class ArticleResponse {
    Long articleId;
    String title;
    String content;
    String dateOfCreate;
    Category category;
    String imagePath;
    UserResponse userResponse;
    List<CommentResponse> commentResponses;
    EvaluationResponse evaluationResponse;
    AnsichtenResponse ansichtenResponse;

}
