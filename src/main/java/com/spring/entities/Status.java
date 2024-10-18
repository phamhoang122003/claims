package com.spring.entities;

import lombok.Getter;


@Getter
public enum Status {
    Draft,
    Pending_Approval,
    Cancelled,
    Rejected,
    Approved,
    Paid

}
