package vesel.newsservice.service.impl;


import vesel.newsservice.dto.response.CommentResponse;
import egor.pantushov.newsservice.entity.*;
import vesel.newsservice.entity.Comment;
import vesel.newsservice.entity.EvaluationComment;
import vesel.newsservice.entity.Type;
import vesel.newsservice.entity.User;
import vesel.newsservice.exeption.ArticleNotFoundException;


import vesel.newsservice.exeption.CommentNotFoundException;
import vesel.newsservice.exeption.EvaluationCommentNotFoundException;
import vesel.newsservice.exeption.UserNotFoundException;
import vesel.newsservice.mapper.CommentMapper;
import egor.pantushov.newsservice.repository.*;
import vesel.newsservice.repository.CommentRepository;
import vesel.newsservice.repository.EvaluationCommentRepository;
import vesel.newsservice.repository.UserRepository;
import vesel.newsservice.service.EvaluationCommentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class EvaluationCommentServiceImpl implements EvaluationCommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EvaluationCommentRepository evaluationCommentRepository;

    @Transactional
    @Override
    public CommentResponse addEvaluationCommentLike(Long commentId, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UserNotFoundException(principal.getName()));
        if (evaluationCommentRepository.existsByUserIdAndCommentId(user.getUserId(), commentId)) {
            EvaluationComment evaluationComment = evaluationCommentRepository.findEvaluationCommentByUserIdCommentId(commentId, user.getUserId())
                    .orElseThrow(() -> new EvaluationCommentNotFoundException(commentId));

            if (evaluationComment.getType() == Type.LIKE) {
                evaluationCommentRepository.delete(evaluationComment);
                return null;
            }

            evaluationCommentRepository.delete(evaluationComment);
        }

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));

        EvaluationComment evaluation = new EvaluationComment();
        evaluation.setComment(comment);
        evaluation.setType(Type.LIKE);
        evaluation.setUser(user);

        evaluationCommentRepository.save(evaluation);
        comment.addEvaluationsComments(evaluation);
        user.addEvaluationsComments(evaluation);

        return CommentMapper.getCommentResponse(comment);

    }

    @Transactional
    @Override
    public CommentResponse addEvaluationCommentDislike(Long commentId, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UserNotFoundException(principal.getName()));
        if (evaluationCommentRepository.existsByUserIdAndCommentId(user.getUserId(), commentId)) {
            EvaluationComment evaluationComment = evaluationCommentRepository.findEvaluationCommentByUserIdCommentId(commentId, user.getUserId())
                    .orElseThrow(() -> new EvaluationCommentNotFoundException(commentId));

            if (evaluationComment.getType() == Type.DISLIKE) {
                evaluationCommentRepository.delete(evaluationComment);
                return null;
            }

            evaluationCommentRepository.delete(evaluationComment);
        }

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ArticleNotFoundException(commentId));

        EvaluationComment evaluation = new EvaluationComment();
        evaluation.setComment(comment);
        evaluation.setType(Type.DISLIKE);
        evaluation.setUser(user);

        evaluationCommentRepository.save(evaluation);
        comment.addEvaluationsComments(evaluation);
        user.addEvaluationsComments(evaluation);

        return CommentMapper.getCommentResponse(comment);

    }


}
