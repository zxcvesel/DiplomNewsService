package vesel.newsservice.service.impl;

import vesel.newsservice.dto.request.CommentRequest;
import vesel.newsservice.dto.response.CommentResponse;
import vesel.newsservice.entity.Article;
import vesel.newsservice.entity.Comment;
import vesel.newsservice.entity.Role;
import vesel.newsservice.entity.User;
import vesel.newsservice.exeption.ArticleNotFoundException;
import vesel.newsservice.exeption.CommentNotFoundException;
import vesel.newsservice.exeption.CommentSubmittedException;
import vesel.newsservice.exeption.UserNotFoundException;
import vesel.newsservice.mapper.CommentMapper;
import vesel.newsservice.repository.ArticleRepository;
import vesel.newsservice.repository.CommentRepository;
import vesel.newsservice.repository.UserRepository;
import vesel.newsservice.service.CommentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    @Transactional
    @Override
    public CommentResponse createComment(Principal principal, CommentRequest commentRequest, Long id) {
        Comment comment = CommentMapper.getComment(commentRequest);
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UserNotFoundException(principal.getName()));
        comment.setUser(user);
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));
        comment.setArticle(article);
        user.addComment(comment);
        article.addComment(comment);
        this.commentRepository.save(comment);
        return CommentMapper.getCommentResponse(comment);
    }

    @Override
    public CommentResponse findComment(Long commentId) {
        return this.commentRepository.findById(commentId).map(CommentMapper::getCommentResponse)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
    }

    @Override
    public CommentResponse update(CommentRequest commentRequest, Long commentId) {
        Comment comment = this.commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
        comment.setText(commentRequest.getText());
        return CommentMapper.getCommentResponse(this.commentRepository.save(comment));
    }

    @Transactional
    @Override
    public CommentResponse getNewCommentForUpdate(Long commentId, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UserNotFoundException(principal.getName()));
        Comment comment = this.commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
        if ((!Objects.equals(comment.getUser().getUserId(), user.getUserId())) && (user.getRole() != Role.ADMIN))
            throw new CommentSubmittedException(commentId, user.getUsername());
        return CommentMapper.getCommentResponse(comment);
    }

    @Transactional
    @Override
    public CommentResponse deleteComment(Principal principal, Long commentId) {
        Comment comment = this.commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UserNotFoundException(principal.getName()));

        if ((!Objects.equals(comment.getUser().getUserId(), user.getUserId())) && (user.getRole() != Role.ADMIN))
            throw new CommentSubmittedException(commentId, user.getUsername());


        comment.setIsDeleted(Boolean.TRUE);
        commentRepository.save(comment);

        return CommentMapper.getCommentResponse(comment);
    }
}
