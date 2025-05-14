package vesel.newsservice.service.impl;

import vesel.newsservice.dto.request.ArticleRequest;
import vesel.newsservice.dto.response.ArticleResponse;
import egor.pantushov.newsservice.entity.*;
import vesel.newsservice.entity.*;
import vesel.newsservice.exeption.ArticleNotFoundException;
import vesel.newsservice.exeption.UserNotFoundException;
import vesel.newsservice.mapper.ArticleMapper;
import vesel.newsservice.repository.ArticleRepository;
import vesel.newsservice.repository.UserRepository;
import vesel.newsservice.service.ArticleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public ArticleResponse createArticle(Principal principal, ArticleRequest articleRequest) {
        Article article = ArticleMapper.getArticle(articleRequest);
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UserNotFoundException(principal.getName()));
        article.setAuthor(user);
        if (user.getRole() == Role.ADMIN)
            article.setStatus(Status.PUBLICATION);
        else
            article.setStatus(Status.VERIFICATION);

        user.addArticle(article);
        articleRepository.save(article);
        return ArticleMapper.getArticleResponse(article);
    }

    @Override
    public ArticleResponse findArticle(Long id) {
        return articleRepository.findById(id).map(ArticleMapper::getArticleResponse)
                .orElseThrow(() -> new ArticleNotFoundException(id));
    }

    @Override
    public List<ArticleResponse> findArticlesByCategory(Category category) {
        return articleRepository.findAllByCategoryAndStatus(category, Status.PUBLICATION)
                .stream().map(ArticleMapper::getArticleResponse)
                .collect(Collectors.toList());

    }


    @Override
    public List<ArticleResponse> getPopularArticles() {
        return articleRepository.findAllByStatus(Status.PUBLICATION)
                .stream().map(ArticleMapper::getArticleResponse)
                .sorted((a1, a2) -> Long.compare(a2.getAnsichtenResponse().getAnsichtens(), a1.getAnsichtenResponse().getAnsichtens()))
                .collect(Collectors.toList());

    }

    @Override
    public List<ArticleResponse> getVerificationsArticle() {
        return articleRepository.findAllByStatus(Status.VERIFICATION)
                .stream().map(ArticleMapper::getArticleResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ArticleResponse approveArticle(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new ArticleNotFoundException(articleId));
        article.setStatus(Status.PUBLICATION);
        articleRepository.save(article);
        return ArticleMapper.getArticleResponse(article);
    }

    @Override
    public ArticleResponse deleteArticle(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new ArticleNotFoundException(articleId));
        article.setStatus(Status.DELETED);
        articleRepository.save(article);
        return ArticleMapper.getArticleResponse(article);
    }


}
