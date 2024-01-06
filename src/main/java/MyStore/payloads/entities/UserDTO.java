package MyStore.payloads.entities;

import java.time.LocalDate;

public record UserDTO(
        String name,
        String surname,
        LocalDate born,
        String region,
        String province,
        String sigla,
        String cap,
        String municipality,
        long municipalityId,
        String address,
        long number,
        String username,
        String email)
{ }
