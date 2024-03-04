package com.example.gifty.dto.funding;

import com.example.gifty.entity.Event;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class FundingRequestDTO {
    @Getter
    @Setter
    public static class FundingDTO {
        @NotEmpty
        private int productId;

        @NotEmpty
        private Event event;

        @NotEmpty
        private LocalDateTime startDate;

        @NotEmpty
        private LocalDateTime endDate;

        private String message;
    }
}
