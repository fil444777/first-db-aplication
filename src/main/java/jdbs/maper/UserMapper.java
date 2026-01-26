package jdbs.maper;

import jdbs.dto.UserDto;
import jdbs.entity.Gender;
import jdbs.entity.Role;
import jdbs.entity.User;
import jdbs.utils.LocalDateFormatter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PRIVATE)
public class UserMapper implements Mapper<UserDto, User> {
    private static final UserMapper INSTANCE = new UserMapper();

    @Override
    public UserDto mapFrom(User user) {
        return UserDto.builder()
                .name(user.getName())
                .birthday(user.getBirthday())
                .email(user.getEmail())
                .gender(user.getGender())
                .role(user.getRole())
                .build();
    }

    public static UserMapper getINSTANCE() {
        return INSTANCE;
    }
}
