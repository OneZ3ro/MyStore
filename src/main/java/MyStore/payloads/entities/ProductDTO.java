package MyStore.payloads.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductDTO(
        @NotEmpty(message = "The name field is mandatory")
        @Size(min = 5, max = 120, message = "The name must be min 5 and max 120 length")
        String name,
        @NotEmpty(message = "The main category field is mandatory")
        String mainCategory,
        String subCategory,
        @NotEmpty(message = "The image field is mandatory")
        String image,
        double discountPrice,
        @NotNull(message = "The price field is mandatory")
        double actualPrice,
        @NotEmpty(message = "The seller field is mandatory")
        String seller
) {}
