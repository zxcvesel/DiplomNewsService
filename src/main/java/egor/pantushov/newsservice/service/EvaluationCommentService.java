package vesel.newsservice.service;

import vesel.newsservice.dto.response.CommentResponse;

import java.security.Principal;

public interface EvaluationCommentService {
    CommentResponse addEvaluationCommentLike(Long commentId, Principal principal);

    CommentResponse addEvaluationCommentDislike(Long commentId, Principal principal);

}
