package com.example.gifty.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum State {
    Ongoing("Ongoing"),
    Ccmplete("Complete");

    private final String state;
}
