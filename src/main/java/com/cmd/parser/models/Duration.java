package com.cmd.parser.models;

public enum Duration {
    HOURLY("HOURLY"),
    DAILY("DAILY");


    private final String duration;

    Duration(String duration) {
        this.duration = duration;
    }
}
