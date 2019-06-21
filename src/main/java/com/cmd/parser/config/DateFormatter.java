package com.cmd.parser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Component
public final class DateFormatter {

    private DateFormat dateFormat;

    public DateFormat DateFormatter() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return dateFormat;
    }
}
