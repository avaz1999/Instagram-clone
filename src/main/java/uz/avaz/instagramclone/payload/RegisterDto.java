package uz.avaz.instagramclone.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterDto {

    @NotBlank(message = "USERNAME MUST NOT BE BLANK")
    String username;

    @NotBlank(message = "USERNAME MUST NOT BE BLANK")
    @Size(min = 4, max = 16, message = "PASSWORD LENGTH MUST BE BETWEEN 4 AND 16")
    String password;

    String email;

    @NotBlank(message = "FULL NAME MUST NOT BE BLANK")
    String fullName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate birthDate;

    String bio;

    MultipartFile profileImg;

    boolean isPublic;
}
