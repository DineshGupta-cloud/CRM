package com.CRM.repository;

import com.CRM.entity.CustomerNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerNoteRepository extends JpaRepository<CustomerNote, Long> {

    Optional<CustomerNote> findTopByOrderByIdDesc();

    List<CustomerNote> findByCustomerIdAndDeletedFalseOrderByCreatedDateDesc(Long customerId);

}