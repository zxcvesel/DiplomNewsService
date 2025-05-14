package vesel.newsservice.service.impl;

import vesel.newsservice.dto.response.ArticleResponse;
import vesel.newsservice.entity.*;
import vesel.newsservice.entity.Ansichten;
import vesel.newsservice.entity.Article;
import vesel.newsservice.entity.User;
import vesel.newsservice.exeption.ArticleNotFoundException;
import vesel.newsservice.exeption.UserNotFoundException;
import vesel.newsservice.mapper.ArticleMapper;
import vesel.newsservice.repository.AnsichtenRepository;
import vesel.newsservice.repository.ArticleRepository;
import vesel.newsservice.repository.UserRepository;
import vesel.newsservice.service.AnsichtenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnsichtenServiceImpl implements AnsichtenService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final AnsichtenRepository ansichtenRepository;

    @Transactional
    @Override
    public ArticleResponse addAnsichten(Long articleId, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UserNotFoundException(principal.getName()));
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));
        Optional<Ansichten> optionalAnsichten = ansichtenRepository.findAnsichtenByUserIdArticleId(articleId, user.getUserId());
        if (optionalAnsichten.isPresent()) {
            return null;
        }
        Ansichten ansichten = new Ansichten();
        ansichten.setArticle(article);
        ansichten.setUser(user);
        ansichtenRepository.save(ansichten);
        article.addAnsichtens(ansichten);
        user.addAnsichtens(ansichten);
        return ArticleMapper.getArticleResponse(article);
    }
}
