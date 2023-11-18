package ivanovvasil.u5d5w3Project.payloadsDTO;

import ivanovvasil.u5d5w3Project.entities.Event;
import ivanovvasil.u5d5w3Project.entities.Prenotation;
import ivanovvasil.u5d5w3Project.entities.User;

import java.util.List;

public record AdminProfileResponseDTO(User user, List<Prenotation> prenotation_list,
                                      List<Event> eventList) implements ProfileResponseDTO {
}
