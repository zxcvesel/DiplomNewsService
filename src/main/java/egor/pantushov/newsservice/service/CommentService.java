package vesel.newsservice.service;

import vesel.newsservice.dto.request.CommentRequest;
import vesel.newsservice.dto.response.CommentResponse;

import java.security.Principal;

public interface CommentService {
    CommentResponse createComment(Principal principal, CommentRequest commentRequest, Long id);

    CommentResponse findComment(Long id);

    CommentResponse update(CommentRequest commentRequest, Long commentId);

    CommentResponse getNewCommentForUpdate(Long commentId, Principal principal);

    CommentResponse deleteComment(Principal principal, Long commentId);
}
