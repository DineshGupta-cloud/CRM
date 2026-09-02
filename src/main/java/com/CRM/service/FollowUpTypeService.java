package com.CRM.service;

import com.CRM.dto.request.FollowUpTypeRequest;
import com.CRM.dto.response.FollowUpTypeResponse;

import java.util.List;

public interface FollowUpTypeService {

    FollowUpTypeResponse save(FollowUpTypeRequest request);

    FollowUpTypeResponse update(Long id,
                                FollowUpTypeRequest request);

    FollowUpTypeResponse findById(Long id);

    List<FollowUpTypeResponse> findAll();

    void delete(Long id);

    String getNextTypeCode();

}