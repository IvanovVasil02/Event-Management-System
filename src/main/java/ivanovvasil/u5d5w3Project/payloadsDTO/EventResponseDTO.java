package ivanovvasil.u5d5w3Project.payloadsDTO;

public record EventResponseDTO(
        Long event_id,
        String title,
        String description,
        String date,
        String location,
        int availablePlaces,
        String picture,
        String manager

) {
}