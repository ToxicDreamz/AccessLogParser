package com.cmd.parser.service;

import com.cmd.parser.models.AccessLog;
import com.cmd.parser.models.AccessLogComments;
import com.cmd.parser.models.command.ParseParameters;
import com.cmd.parser.models.dto.AccessLogDto;

import java.io.IOException;
import java.util.List;

public interface AccessLogService {

    void saveAccessLogs(List<AccessLog> accessLog);

    List<AccessLog> retrieveAccessLogFromFile(String filePath) throws IOException;

    List findAccessLogs(ParseParameters parseParameters);

    List<AccessLogComments> saveComments(ParseParameters parseParameters, List<AccessLog> logs);
}
