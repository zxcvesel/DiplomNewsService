package vesel.newsservice.service;

import vesel.newsservice.dto.request.UserRequest;
import vesel.newsservice.dto.response.UserResponse;
import vesel.newsservice.entity.Role;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);

    List<UserResponse> findAllUsers();

    UserResponse giveRole(Long userId, Role role);
}

