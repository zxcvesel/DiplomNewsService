package egor.pantushov.newsservice.rest;

import egor.pantushov.newsservice.dto.request.ArticleRequest;
import egor.pantushov.newsservice.dto.response.ArticleResponse;
import egor.pantushov.newsservice.entity.Category;
import egor.pantushov.newsservice.exeption.ArticleNotFoundException;
import egor.pantushov.newsservice.service.AnsichtenService;
import egor.pantushov.newsservice.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.http.MediaType;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ArticleRestController {
    private final ArticleService articleService;
    private final AnsichtenService ansichtenService;


    @PreAuthorize("hasAnyAuthority('EDITOR','ADMIN')")
    @PostMapping("/new_article")
    @ResponseStatus(HttpStatus.CREATED)
    public ArticleResponse createArticle(@RequestPart("articleRequest") @Valid ArticleRequest articleRequest,
                                         @RequestPart("imageFile") MultipartFile imageFile,
                                         Principal principal) {
        return this.articleService.createArticle(principal, articleRequest, imageFile);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{articleId}")
    public ArticleResponse getArticle(@PathVariable("articleId") Long articleId, Principal principal) {
        if (articleId <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Неправильно введён id");
        }
        try {
            this.ansichtenService.addAnsichten(articleId, principal);
            return this.articleService.findArticle(articleId);
        } catch (ArticleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/category/{category}")
    public List<ArticleResponse> getArticlesByCategory(@PathVariable("category") Category category) {
        return this.articleService.findArticlesByCategory(category);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    public List<ArticleResponse> getPopularArticles() {
        return this.articleService.getPopularArticles();

    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/verifications")
    public List<ArticleResponse> getVerificationArticles() {
        return this.articleService.getVerificationsArticle();

    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/verifications/{articleId:\\d+}")
    public ArticleResponse getVerificationArticle(@PathVariable("articleId") Long articleId) {
        if (articleId <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Неправильно введён id");
        }
        try {
            return this.articleService.findArticle(articleId);
        } catch (ArticleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/verifications/{articleId:\\d+}")
    public ArticleResponse approveArticle(@PathVariable("articleId") Long articleId) {
        if (articleId <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Неправильно введён id");
        }
        try {
            return this.articleService.approveArticle(articleId);
        } catch (ArticleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }


    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/{articleId:\\d+}")
    public String deleteArticle(@PathVariable("articleId") Long articleId) {
        if (articleId <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Неправильно введён id");
        }
        try {
            this.articleService.deleteArticle(articleId);
            return String.format("Статья с id: %d удалена", articleId);
        } catch (ArticleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

}