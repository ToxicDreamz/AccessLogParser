package com.cmd.parser.models.dto;

public class AccessLogDto {
    private String ip;

    public AccessLogDto(String ip) {
        this.ip = ip;
    }

    public AccessLogDto() {
    }

    public String getIp() {
        return ip;
    }
}
