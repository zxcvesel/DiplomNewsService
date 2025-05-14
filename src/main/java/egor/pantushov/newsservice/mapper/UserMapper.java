package vesel.newsservice.mapper;

import vesel.newsservice.dto.request.UserRequest;
import vesel.newsservice.dto.response.UserResponse;
import vesel.newsservice.entity.User;


public class UserMapper {
    public static User getUser(UserRequest userRequest) {
        User user = new User();
        user.setFirstname(userRequest.getFirstname());
        user.setLastname(userRequest.getLastname());
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        return user;
    }


    public static UserResponse getUserResponse(User user) {

        return new UserResponse(user.getFirstname(), user.getLastname(), user.getRole(), user.getUserId());
    }


}
