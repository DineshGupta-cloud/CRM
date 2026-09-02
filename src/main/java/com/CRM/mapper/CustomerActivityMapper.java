package com.CRM.mapper;

import com.CRM.dto.request.CustomerActivityRequest;
import com.CRM.dto.response.CustomerActivityResponse;
import com.CRM.entity.Customer;
import com.CRM.entity.CustomerActivity;
import com.CRM.entity.CustomerActivityType;
import com.CRM.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class CustomerActivityMapper {

    public CustomerActivity toEntity(CustomerActivityRequest request,
                                     Customer customer,
                                     CustomerActivityType activityType,
                                     Employee employee) {

        return CustomerActivity.builder()
                .customer(customer)
                .activityType(activityType)
                .performedBy(employee)
                .title(request.getTitle())
                .description(request.getDescription())
                .oldValue(request.getOldValue())
                .newValue(request.getNewValue())
                .entityType(request.getEntityType())
                .entityId(request.getEntityId())
                .visible(request.getVisible() == null ? true : request.getVisible())
                .build();
    }

    public CustomerActivityResponse toResponse(CustomerActivity activity) {

        return CustomerActivityResponse.builder()
                .id(activity.getId())
                .customerCode(activity.getCustomer().getCustomerCode())
                .customerName(activity.getCustomer().getCustomerName())
                .activityCode(activity.getActivityType().getActivityCode())
                .activityName(activity.getActivityType().getActivityName())
                .performedBy(activity.getPerformedBy() != null
                        ? activity.getPerformedBy().getFirstName() + " "
                        + activity.getPerformedBy().getLastName()
                        : null)
                .title(activity.getTitle())
                .description(activity.getDescription())
                .oldValue(activity.getOldValue())
                .newValue(activity.getNewValue())
                .entityType(activity.getEntityType())
                .entityId(activity.getEntityId())
                .visible(activity.getVisible())
                .activityDate(activity.getActivityDate())
                .build();
    }
}