package ivanovvasil.u5d5w3Project.payloadsDTO;

import ivanovvasil.u5d5w3Project.entities.User;

public record PrenotationResponseDTO(User user, EventResponseDTO event) {
}
