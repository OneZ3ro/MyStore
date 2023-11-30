package MyStore.payloads.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ProductDatasetDTO(
        String name,
        String mainCategory,
        String subCategory,
        String image,
        double rating,
        long numbOfRating,
        double discountPrice,
        double actualPrice,
        String seller
) {}