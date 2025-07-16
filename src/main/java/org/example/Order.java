package org.example;

import lombok.With;
import java.time.Instant;
import java.util.List;

public record Order(
        String id,
        List<Product> products,
        @With OrderStatus status,
        @With Instant orderDate
) {
}
