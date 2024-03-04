package com.example.gifty.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Event {
    BIRTHDAY("Birthday"),
    MARRIAGE("Marriage"),
    FIRST_BIRTHDAY_PARTY("FirstBirthdayParty"),
    GRADUATION("Graduation");

    private String event;
}
