package ivanovvasil.u5d5w3Project.payloadsDTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EventDTO(@NotEmpty(message = "The title is a required field")
                       @Size(min = 4, max = 30, message = "The title must be between 3 and 30 characters")
                       String title,
                       @NotEmpty(message = "The description is a required field")
                       String description,
                       @NotEmpty(message = "The date is a required field")
                       String date,
                       @NotEmpty(message = "The location is a required field")
                       String location,
                       @NotNull(message = "The available places is a required field")
                       @Min(value = 5, message = "Value available places must be greater than or equal to 5")
                       int availablePlaces,
                       String picture,
                       @Column(name = "user_id")
                       @NotNull(message = "The available places is a required field")
                       Long user_id

) {
}