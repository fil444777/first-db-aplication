package hibernate.service;



import hibernate.dao.UserDao;
import hibernate.dto.UserDto;
import hibernate.entity.User;
import hibernate.exception.ValidationException;
import hibernate.mapper.UserDtoMapper;
import hibernate.validator.UserDtoValidator;
import lombok.NoArgsConstructor;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserService {
    private static final UserService INSTANCE = new UserService();
    private static final UserDao userDao = UserDao.getInstance();
    private static final UserDtoMapper userDtoMapper = UserDtoMapper.getInstance();
    private static final UserDtoValidator userDtoValidator = UserDtoValidator.getInstance();


    public Optional<User> login(String email, String password) {
        return userDao.findByEmailAndPassword(email, password);
    }


    public Integer create(UserDto userDto) {
        var validationResult = userDtoValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }

        var user = userDtoMapper.mapFrom(userDto);

        userDao.save(user);
        return user.getId();
    }

    public static UserService getInstance() {
        return INSTANCE;
    }


}
