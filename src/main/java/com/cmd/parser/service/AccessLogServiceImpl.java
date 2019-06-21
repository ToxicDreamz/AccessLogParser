package com.cmd.parser.service;

import com.cmd.parser.config.DateFormatter;
import com.cmd.parser.models.AccessLog;
import com.cmd.parser.models.AccessLogComments;
import com.cmd.parser.models.command.ParseParameters;
import com.cmd.parser.repository.AccessLogCommentsRepository;
import com.cmd.parser.repository.AccessLogRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.*;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Transactional
public class AccessLogServiceImpl implements AccessLogService {

    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Autowired
    private AccessLogRepository accessLogRepository;

    @Autowired
    private AccessLogCommentsRepository accessLogCommentsRepository;

    @Autowired
    DateFormatter dateFormatter;

    @Autowired
    private EntityManager entityManager;

    /**
     * Saves the access logs to the database.
     *
     * @param accessLogs - Represents the array containing the Access Logs.
     */

    public void saveAccessLogs(List<AccessLog> accessLogs) {
        accessLogs.forEach(accessLog -> accessLogRepository.save(accessLog));
    }

    /**
     * {@link #retrieveAccessLogFromFile(String filePath)}
     *
     * <p>
     * Retrieve access logs from {@param filePath}, then
     * parses the whole file with the given delimiter {@code withDelimiter('|')},
     * and creates a {@link AccessLog} Object which is then stored inside a
     * {@link List<AccessLog>}
     * </p>
     *
     * @param filePath - Represents path to the log file.
     * @return {@link List<AccessLog>} - Returns the list containing all the Access Logs.
     * @throws IOException
     */

    ///Users/ToxicDreamz/Documents/IntelliJ-IDEA Projects/Access Log Parser/src/main/resources/access.log

    public List<AccessLog> retrieveAccessLogFromFile(String filePath) throws IOException {
        List<AccessLog> accessLogItemsList = new ArrayList<>();

        File accessLogFile = new File(filePath);

        if (!fileExists(filePath)) {
            logger.log(Level.SEVERE, "Error while trying to load the file! Please make sure you have specified the correct path.");
        }

        Iterable<CSVRecord> accessLogItems = CSVFormat.RFC4180
                .withDelimiter('|')
                .parse(new BufferedReader(new FileReader(accessLogFile)));

        logger.log(Level.INFO, "Reading Access Log, please wait.");

        for (CSVRecord accessLogItem : accessLogItems) {
            AccessLog accessLog = new AccessLog();
            String accessLogItemDate = accessLogItem.get(0);

            Date date = new Date();
            try {
                date = dateFormatter.DateFormatter().parse(accessLogItemDate);
            } catch (ParseException e) {
                logger.log(Level.SEVERE, "Error while trying to parse date: " + e.getMessage());
            }

            accessLog.setDate(date);
            accessLog.setIp(accessLogItem.get(1));
            accessLog.setMethod(accessLogItem.get(2));
            accessLog.setResponse(accessLogItem.get(3));
            accessLog.setUserAgent(accessLogItem.get(4));
            accessLogItemsList.add(accessLog);
        }

        logger.log(Level.INFO, "Log file read successfully, saving log file into database.");

        return accessLogItemsList;
    }

    public List<AccessLog> findAccessLogs(ParseParameters parseParameters) {
        return accessLogRepository.findAccessLogs(parseParameters.getStartDate(), parseParameters.getEndDate(), parseParameters.getThreshold());
    }

    public List<AccessLogComments> saveComments(ParseParameters parseParameters, List<AccessLog> logs) {
        List<AccessLogComments> accessLogCommentsList = new ArrayList<>();
        for (AccessLog log : logs) {
            AccessLogComments accessLogComments = new AccessLogComments();
            accessLogComments.setIp(log.getIp());
            accessLogComments.setComment("Blocked due to requests surpassing " + parseParameters.getThreshold()
                    + " requests between specified Date Interval.");
            accessLogCommentsList.add(accessLogComments);
        }

        accessLogCommentsList.forEach(accessLogComments -> accessLogCommentsRepository.save(accessLogComments));

        return accessLogCommentsList;
    }

    private boolean fileExists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }


}
