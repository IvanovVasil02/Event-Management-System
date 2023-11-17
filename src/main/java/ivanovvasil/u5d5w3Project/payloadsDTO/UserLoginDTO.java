package ivanovvasil.u5d5w3Project.payloadsDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record UserLoginDTO(@Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Invalid email")
                           String email,
                           @NotEmpty(message = "The password field cannot be empty")
                           String password) {
}
