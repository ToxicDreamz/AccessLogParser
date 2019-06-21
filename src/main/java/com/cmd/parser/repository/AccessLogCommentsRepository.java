package com.cmd.parser.repository;

import com.cmd.parser.models.AccessLogComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessLogCommentsRepository extends JpaRepository<AccessLogComments, Long> {}
