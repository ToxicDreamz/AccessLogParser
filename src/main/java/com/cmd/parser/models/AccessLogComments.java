package com.cmd.parser.models;

import javax.persistence.*;


@Entity
@Table(name = "comment_log")
public class AccessLogComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "ip_address")
    private String ip;
    private String comment;

    public AccessLogComments(String ip, String comment) {
        this.ip = ip;
        this.comment = comment;
    }

    public AccessLogComments() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
