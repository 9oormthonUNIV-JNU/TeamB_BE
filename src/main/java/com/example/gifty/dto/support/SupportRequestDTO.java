package com.example.gifty.dto.support;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

public class SupportRequestDTO {
    @Getter
    @Setter
    public static class SupportDTO {
        @NotEmpty
        private int fundingId;

        @NotEmpty
        private long amount;

        @NotEmpty
        private String message;
    }
}
