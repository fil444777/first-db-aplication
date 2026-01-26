package jdbs.maper;

import jdbs.dto.CreateUserDto;
import jdbs.dto.UserDto;
import jdbs.entity.Gender;
import jdbs.entity.Role;
import jdbs.entity.User;
import jdbs.utils.LocalDateFormatter;

import java.time.LocalDate;

public class CreateUserMapper implements Mapper<User, CreateUserDto>{
    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    private CreateUserMapper() {
    }

    @Override
    public User mapFrom(CreateUserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .birthday(LocalDateFormatter.format(userDto.getBirthday()))
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .gender(Gender.valueOf(userDto.getGender()))
                .role(Role.valueOf(userDto.getRole()))
                .build();
    }

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }
}
