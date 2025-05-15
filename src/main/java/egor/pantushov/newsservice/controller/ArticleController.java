package egor.pantushov.newsservice.controller;

import egor.pantushov.newsservice.dto.request.ArticleRequest;
import egor.pantushov.newsservice.dto.request.CommentRequest;
import egor.pantushov.newsservice.dto.response.ArticleResponse;
import egor.pantushov.newsservice.entity.Category;
import egor.pantushov.newsservice.service.AnsichtenService;
import egor.pantushov.newsservice.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("news/articles")
public class ArticleController {
    private final ArticleService articleService;
    private final AnsichtenService ansichtenService;

    @PreAuthorize("hasAnyAuthority('EDITOR','ADMIN')")
    @GetMapping("create")
    public String getNewArticle(Model model, @ModelAttribute("articleRequest") ArticleRequest articleRequest) {
        model.addAttribute("articleRequest", articleRequest);
        model.addAttribute("categories", Category.values());

        // 👇 Добавь эту строку:
        model.addAttribute("actionUrl", "/news/articles/create");

        return "news/articles/new_article";
    }

    @PreAuthorize("hasAnyAuthority('EDITOR','ADMIN')")
    @PostMapping({"/create", "/edit/{articleId}"})
    public String saveOrUpdateArticle(@PathVariable(required = false) Long articleId,
                                      Model model,
                                      @Valid @ModelAttribute ArticleRequest articleRequest,
                                      BindingResult bindingResult,
                                      @RequestParam("imageFile") MultipartFile imageFile,
                                      Principal principal) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", Category.values());
            return "news/articles/new_article";
        }

        if (articleId != null) {
            articleService.updateArticle(articleId, articleRequest, imageFile);
        } else {
            articleService.createArticle(principal, articleRequest, imageFile);
        }

        return "redirect:/news/articles";
    }

    @PreAuthorize("hasAnyAuthority('EDITOR','ADMIN')")
    @GetMapping("/edit/{articleId}")
    public String editArticle(@PathVariable Long articleId, Model model) {
        ArticleResponse article = articleService.findArticle(articleId);

        ArticleRequest articleRequest = new ArticleRequest(
                article.getTitle(),
                article.getContent(),
                article.getCategory()
        );

        model.addAttribute("articleRequest", articleRequest);
        model.addAttribute("categories", Category.values());
        model.addAttribute("articleId", articleId);
        model.addAttribute("imagePath", article.getImagePath()); // 👈 добавить
        model.addAttribute("actionUrl", articleId != null
                ? "/news/articles/edit/" + articleId
                : "/news/articles/create");
        return "news/articles/new_article";
    }

    @GetMapping("/sorted/date")
    public String getArticlesSortedByDate(Model model) {
        model.addAttribute("articlesResponse", articleService.findAllSortedByDate());
        return "news/articles/list";
    }

    @GetMapping("/sorted/likes")
    public String getArticlesSortedByLikes(Model model) {
        model.addAttribute("articlesResponse", articleService.findAllSortedByLikes());
        return "news/articles/list";
    }

    @GetMapping("/sorted/comments")
    public String getArticlesSortedByComments(Model model) {
        model.addAttribute("articlesResponse", articleService.findAllSortedByComments());
        return "news/articles/list";
    }

    @GetMapping("article/{articleId:\\d+}")
    public String getArticle(@PathVariable Long articleId, Model model, Principal principal, @ModelAttribute("commentRequest") CommentRequest commentRequest) {
        model.addAttribute("articleResponse", this.articleService.findArticle(articleId));
        model.addAttribute("commentRequest", commentRequest);
        this.ansichtenService.addAnsichten(articleId, principal);
        return "news/articles/article";
    }

    @GetMapping("by/{category}")
    public String getArticlesByCategory(Model model, @PathVariable Category category) {
        model.addAttribute("articlesResponse", this.articleService.findArticlesByCategory(category));
        return "news/articles/list";
    }


    @GetMapping("")
    public String getPopularArticles(Model model) {
        model.addAttribute("articlesResponse", this.articleService.getPopularArticles());
        return "news/articles/list";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("verifications")
    public String getVerificationArticles(Model model) {
        model.addAttribute("articlesResponse", this.articleService.getVerificationsArticle());
        return "news/articles/verification_articles";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("verifications/{articleId:\\d+}")
    public String getVerificationArticle(@PathVariable Long articleId, Model model) {
        model.addAttribute("articleResponse", this.articleService.findArticle(articleId));
        return "news/articles/verification_article";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("verifications/{articleId:\\d+}")
    public String approveArticle(@PathVariable Long articleId) {
        this.articleService.approveArticle(articleId);
        return "redirect:/news/articles/verifications";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("delete/{articleId:\\d+}")
    public String deleteArticle(@PathVariable Long articleId, RedirectAttributes redirectAttributes) {
        this.articleService.deleteArticle(articleId);
        return "redirect:/news/articles";
    }

}
