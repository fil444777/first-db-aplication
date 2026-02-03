package hibernate.validator;


import hibernate.dto.UserDto;
import hibernate.entity.Gender;
import hibernate.entity.Role;
import hibernate.util.LocalDateFormatter;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserDtoValidator implements Validator<UserDto> {
    private static final UserDtoValidator INSTANCE = new UserDtoValidator();

    @Override
    public ValidationResult isValid(UserDto userDto) {
        var validationResult = new ValidationResult();
        if (!LocalDateFormatter.isValid(userDto.getBirthday())) {
            validationResult.add(Error.of("invalid.birthday", "Birthday is invalid"));
        }
        if (Gender.find(userDto.getGender()).isEmpty()) {
            validationResult.add(Error.of("invalid.gender", "Gender is invalid"));
        }
        if (Role.find(userDto.getRole()).isEmpty()) {
            validationResult.add(Error.of("invalid.role", "Role is invalid"));
        }
        if (userDto.getName().isEmpty()) {
            validationResult.add(Error.of("invalid.name", "Name is invalid"));
        }
        if (userDto.getEmail().isEmpty()) {
            validationResult.add(Error.of("invalid.email", "Email is invalid"));
        }
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            validationResult.add(Error.of("invalid.password", "Password is invalid"));
        }
        return validationResult;
    }


    public static UserDtoValidator getInstance() {
        return INSTANCE;
    }
}
