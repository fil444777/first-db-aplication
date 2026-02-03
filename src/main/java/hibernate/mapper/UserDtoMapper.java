package hibernate.mapper;

import hibernate.dto.UserDto;
import hibernate.entity.*;
import hibernate.util.LocalDateFormatter;

public class UserDtoMapper implements Mapper<User, UserDto> {
    private static final UserDtoMapper INSTANCE = new UserDtoMapper();

    public static UserDtoMapper getInstance() {
        return INSTANCE;
    }

    private UserDtoMapper() {
    }

    @Override
    public User mapFrom(UserDto userDto) {
        return User.builder()
                .personalInfo(PersonalInfo.builder().name(userDto.getName())
                        .birthday(new Birthday(LocalDateFormatter.format(userDto.getBirthday()))).build())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .gender(Gender.valueOf(userDto.getGender()))
                .role(Role.valueOf(userDto.getRole()))
                .build();
    }

}
