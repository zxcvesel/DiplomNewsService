package vesel.newsservice.service;

import vesel.newsservice.dto.response.ArticleResponse;

import java.security.Principal;

public interface AnsichtenService {
    ArticleResponse addAnsichten(Long articleId, Principal principal);
}
