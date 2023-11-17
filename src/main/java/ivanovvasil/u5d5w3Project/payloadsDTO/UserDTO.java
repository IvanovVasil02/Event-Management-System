package ivanovvasil.u5d5w3Project.payloadsDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserDTO(@NotEmpty(message = "The name is a required field")
                      @Size(min = 4, max = 30, message = "The name must be between 3 and 30 characters")
                      String name,
                      @NotEmpty(message = "The surname is a required field")
                      @Size(min = 4, max = 30, message = "The surname must be between 3 and 30 characters")
                      String surname,
                      @NotEmpty(message = "The email is a required field")
                      @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "The email entered is invalid")
                      String email,
                      @NotEmpty(message = "The password is a required field")
                      @Size(min = 6, max = 16, message = "The password must be between 6 and 16 characters")
                      String password
) {
}
