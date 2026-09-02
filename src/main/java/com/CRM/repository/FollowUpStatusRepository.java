package com.CRM.repository;


import com.CRM.entity.FollowUpStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowUpStatusRepository extends JpaRepository<FollowUpStatus,Long> {

    Optional<FollowUpStatus> findByStatusCode(String statusCode);

    Optional<FollowUpStatus> findByStatusNameIgnoreCase(String statusName);

}
