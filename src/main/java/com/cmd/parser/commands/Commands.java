package com.cmd.parser.commands;

import com.cmd.parser.models.AccessLog;
import com.cmd.parser.models.AccessLogComments;
import com.cmd.parser.config.AsciiTableConfig;
import com.cmd.parser.models.Duration;
import com.cmd.parser.models.command.ParseParameters;
import com.cmd.parser.service.AccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@ShellComponent
public class Commands {
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Autowired
    private AccessLogService accessLogService;

    @Autowired
    private AsciiTableConfig asciiTableConfig;


    @ShellMethod(value = "Parses the input access.log file", key = {"-load", "-l"})
    public void configLoadData(
            @ShellOption(value = "--file") String accessLogFilePath) {

        ParseParameters parseParameters = new ParseParameters();

        parseParameters.setAccessLog(accessLogFilePath);

        try {
            loadLog(parseParameters);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error occurred while parsing data!");
            logger.log(Level.SEVERE, "ERROR MESSAGE: " + e.getMessage());
        }
    }

    private void loadLog(ParseParameters parseParameters) throws IOException {
        accessLogService.saveAccessLogs(accessLogService.retrieveAccessLogFromFile(parseParameters.getAccessLog()));
        logger.log(Level.INFO, "Saving log to database successful. You may now continue...");
    }

    @ShellMethod(value = "Finds blocked IP Addresses, and displays the reason for it being blocked.", key = {"-findblocked", "-fb"})
    public void findAccessLogComments(@ShellOption(value = {"--start-date", "--sd"}) String startDate,
                                      @ShellOption(value = {"--duration", "-d"}) String duration,
                                      @ShellOption(value = {"--threshold", "--t"}) Long threshold) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss");

        ParseParameters parseParameters = new ParseParameters();

        try {
            parseParameters.setStartDate(simpleDateFormat.parse(startDate));
        } catch (ParseException e) {
            logger.log(Level.SEVERE, "Error while trying to parse date: " + e.getMessage());
        }

        parseParameters.setDuration(duration);
        if (duration.equalsIgnoreCase(Duration.HOURLY.toString())) {
            parseParameters.setEndDate(new Date(parseParameters.getStartDate().getTime() + TimeUnit.HOURS.toMillis(1)));
        } else if (duration.equalsIgnoreCase(Duration.DAILY.toString())) {
            parseParameters.setEndDate(new Date(parseParameters.getStartDate().getTime() + TimeUnit.DAYS.toMillis(1)));
        }

        parseParameters.setThreshold(threshold);

        displayAccessLogComments(parseParameters);
    }


    @ShellMethod(value = "Loads log into database and retrieves blocked IP Addresses based on specified parameters.", key = {"-load-and-retrieve", "-lr"})
    public void loadDataAndRetrieveComments(@ShellOption(value = "--file") String accessLogFilePath,
                                            @ShellOption(value = {"--start-date", "--sd"}) String startDate,
                                            @ShellOption(value = {"--duration", "-d"}) String duration,
                                            @ShellOption(value = {"--threshold", "--t"}) Long threshold) {

        ParseParameters parseParameters = new ParseParameters();

        parseParameters.setAccessLog(accessLogFilePath);

        configLoadData(accessLogFilePath);
        try {
            loadLog(parseParameters);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to retrieve access log from specified path.");
        }

        findAccessLogComments(startDate, duration, threshold);
    }

    private void displayAccessLogComments(ParseParameters parseParameters) {

        List<AccessLog> accessLogs = accessLogService.findAccessLogs(parseParameters);
        List<AccessLogComments> accessLogComments = accessLogService.saveComments(parseParameters, accessLogs);

        asciiTableConfig.configureTable();

        asciiTableConfig.displayAccessLogComments(accessLogComments, parseParameters);
    }
}
