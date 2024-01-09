package MyStore.payloads.entities;

import MyStore.entities.Resident;

import java.time.LocalDate;

public record UserDTO(
        String name,
        String surname,
        LocalDate born,
        Resident resident,
        String address,
        long number,
        String username,
        String email)
{ }
