package vesel.newsservice.service.impl;

import vesel.newsservice.dto.request.UserRequest;
import vesel.newsservice.dto.response.UserResponse;
import vesel.newsservice.entity.User;
import vesel.newsservice.entity.Role;
import vesel.newsservice.exeption.UserNotFoundException;
import vesel.newsservice.mapper.UserMapper;
import vesel.newsservice.repository.UserRepository;
import vesel.newsservice.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        return userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        "{noop}" + user.getPassword(),
                        Collections.singleton(user.getRole())
                ))
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public UserResponse createUser(UserRequest userRequest) {
        User user=UserMapper.getUser( userRequest);
        user.setRole(Role.USER);
        userRepository.save(user);
        return UserMapper.getUserResponse(user);
    }

    @Override
    public List<UserResponse> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper::getUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse giveRole(Long userId, Role role) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        user.setRole(role);
        userRepository.save(user);
        return UserMapper.getUserResponse(user);
    }

}