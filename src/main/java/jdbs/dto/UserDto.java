package jdbs.dto;

import jdbs.entity.Gender;
import jdbs.entity.Role;
import lombok.*;

import java.time.LocalDate;

@Value
@Builder
public class UserDto {
     Long id;
     String name;
     LocalDate birthday;
     String email;
     Role role;
     Gender gender;
}
