package jdbs.validator;


import jdbs.dto.CreateUserDto;
import jdbs.entity.Gender;
import jdbs.entity.Role;
import jdbs.utils.LocalDateFormatter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PRIVATE)
public class CreateUserValidator implements Validator<CreateUserDto> {
    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    @Override
    public ValidationResult isValid(CreateUserDto userDto) {
        var validationResult = new ValidationResult();
        if (!LocalDateFormatter.isValid(userDto.getBirthday())) {
            validationResult.add(Error.of("invalid.birthday", "Birthday is invalid"));
        }
        if (Gender.find(userDto.getGender()).isEmpty()){
            validationResult.add(Error.of("invalid.gender", "Gender is invalid"));
        }
        if (Role.find(userDto.getRole()).isEmpty()){
            validationResult.add(Error.of("invalid.role", "Role is invalid"));
        }
        if (userDto.getName().isEmpty()){
            validationResult.add(Error.of("invalid.name", "Name is invalid"));
        }
        if (userDto.getEmail().isEmpty()){
            validationResult.add(Error.of("invalid.email", "Email is invalid"));
        }
        if (userDto.getPassword().isEmpty()){
            validationResult.add(Error.of("invalid.password", "Password is invalid"));
        }
        return validationResult;
    }


    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }
}
