package uz.avaz.instagramclone.payload;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    boolean success;
    String message;
    T body;

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
