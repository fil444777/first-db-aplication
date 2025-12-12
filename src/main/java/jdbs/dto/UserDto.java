package jdbs.dto;

import lombok.*;

@Data
@Builder
public class UserDto {
    private Long id;
    private String email;
}
