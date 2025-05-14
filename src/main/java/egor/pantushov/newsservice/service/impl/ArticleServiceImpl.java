package egor.pantushov.newsservice.service.impl;

import egor.pantushov.newsservice.dto.request.ArticleRequest;
import egor.pantushov.newsservice.dto.response.ArticleResponse;
import egor.pantushov.newsservice.entity.*;
import egor.pantushov.newsservice.exeption.ArticleNotFoundException;
import egor.pantushov.newsservice.exeption.UserNotFoundException;
import egor.pantushov.newsservice.mapper.ArticleMapper;
import egor.pantushov.newsservice.repository.ArticleRepository;
import egor.pantushov.newsservice.repository.UserRepository;
import egor.pantushov.newsservice.service.ArticleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public ArticleResponse createArticle(Principal principal, ArticleRequest articleRequest, MultipartFile imageFile) {
        Article article = ArticleMapper.getArticle(articleRequest);

        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UserNotFoundException(principal.getName()));
        article.setAuthor(user);

        if (user.getRole() == Role.ADMIN)
            article.setStatus(Status.PUBLICATION);
        else
            article.setStatus(Status.VERIFICATION);

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // Уникальное имя
                String filename = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();

                // Папка uploads рядом с проектом
                String uploadDir = System.getProperty("user.dir") + "/uploads";
                Path uploadPath = Paths.get(uploadDir);
                Files.createDirectories(uploadPath); // создаст если нет

                // Сохраняем файл
                Path filePath = uploadPath.resolve(filename);
                imageFile.transferTo(filePath.toFile());

                // Сохраняем путь для отображения в шаблоне
                article.setImagePath("/uploads/" + filename);

            } catch (IOException e) {
                throw new RuntimeException("Не удалось сохранить изображение", e);
            }
        }

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
