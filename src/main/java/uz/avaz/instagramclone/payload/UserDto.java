package uz.avaz.instagramclone.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    String fullName;
    String username;
    String password;
    String email;
    String bio;
    LocalDate birthDate;
    boolean isPublic;

}
