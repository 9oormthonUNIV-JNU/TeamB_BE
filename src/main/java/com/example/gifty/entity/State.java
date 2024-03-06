package com.example.gifty.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum State {
    Ongoing("Ongoing"),
    Finish("Finish");

    private final String state;
}
