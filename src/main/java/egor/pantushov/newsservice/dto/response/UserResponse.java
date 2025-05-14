package vesel.newsservice.dto.response;

import vesel.newsservice.entity.Role;
import lombok.Value;


@Value
public class UserResponse {
    String firstname;
    String lastname;
    Role role;
    Long userId;
}
