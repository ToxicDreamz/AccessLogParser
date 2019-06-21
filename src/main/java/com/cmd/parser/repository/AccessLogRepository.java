package com.cmd.parser.repository;

import com.cmd.parser.models.AccessLog;
import com.cmd.parser.models.command.ParseParameters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {
//    WHERE date BETWEEN (:startDate) AND (:endDate) HAVING COUNT(ip) >= (:threshold)

    @Query(value = "SELECT accessLog FROM log accessLog " +
            "WHERE accessLog.date BETWEEN :startDate AND :endDate " +
            "GROUP BY accessLog.ip HAVING COUNT(accessLog.ip) >= :threshold")
    List<AccessLog> findAccessLogs(@Param("startDate") Date startDate,
                                   @Param("endDate") Date endDate,
                                   @Param("threshold") Long threshold);

}
