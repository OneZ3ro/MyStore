package MyStore.payloads.entities;

public record ProductDatasetDTO(
        String name,
        String imgUrl,
        double starts,
        long reviews,
        double price,
        double listPrice,
        String subCategory,
        boolean bestSeller,
        long boughtInLastMonth,
        String seller
) {}