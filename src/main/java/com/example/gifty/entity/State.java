package com.example.gifty.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum State {
    ONGOING("Ongoing"),
    COMPLETE("Complete");

    private final String state;
}
