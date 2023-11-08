package com.aodfeedback.demo.h2database;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.RequiredArgsConstructor;

import java.util.Objects;


@RequiredArgsConstructor
public class TelegramDataBaseUsers {

    @JsonProperty("id")
    private final int id;
    @JsonProperty("userName")
    private final String userName;
    @JsonProperty("userId")
    private final int userId;

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, userId);
    }
}
