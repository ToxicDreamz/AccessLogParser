package com.cmd.parser.models;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "log")
@Table(name = "logs")
public class AccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private String ip;
    private String method;
    private String response;
    private String userAgent;

    public AccessLog() {
    }

    public AccessLog(Date date, String ip, String method, String response, String userAgent) {
        this.date = date;
        this.ip = ip;
        this.method = method;
        this.response = response;
        this.userAgent = userAgent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
