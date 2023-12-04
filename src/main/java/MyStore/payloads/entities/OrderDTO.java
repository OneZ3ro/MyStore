package MyStore.payloads.entities;

import MyStore.entities.Product;

import java.time.LocalDate;
import java.util.List;

public record OrderDTO(
        List<Product> products,
        double total
) {}
