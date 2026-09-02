package com.CRM.service;

import com.CRM.dto.response.PipelineStageSummaryResponse;

import java.util.List;

public interface PipelineDashboardService {

    List<PipelineStageSummaryResponse> getPipelineSummary();
}