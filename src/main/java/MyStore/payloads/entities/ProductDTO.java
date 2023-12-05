package MyStore.payloads.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ProductDTO(
        @NotEmpty(message = "The name field is mandatory")
        @Size(min = 5, max = 120, message = "The name must be min 5 and max 120 length")
        String name,
        @NotEmpty(message = "The image field is mandatory")
        String imgUrl,
        double price,
        @NotNull(message = "The price field is mandatory")
        double listPrice,
        @NotEmpty(message = "The category field is mandatory")
        String subCategory,
        @NotEmpty(message = "The seller field is mandatory")
        String seller,
        UUID userSeller
) {}
