package com.project.employeeservice.exception.enums;

public enum FriendlyMessageCodeImpl implements FriendlyMessageCode{
    OK(1000),
    ERROR(1001),
    SUCCESS(1002),
    EMPLOYEE_NOT_CREATED_EXCEPTION(1500),
    EMPLOYEE_SUCCESSFULLY_CREATED(1501),
    EMPLOYEE_NOT_FOUND(1502),
    EMPLOYEE_SUCCESSFULLY_UPDATED(1503),
    EMPLOYEE_ALREADY_DELETED(1504),
    EMPLOYEE_SUCCESSFULLY_DELETED(1505);
    private final int value;

    FriendlyMessageCodeImpl(int value){
        this.value=value;
    }
    @Override
    public int getFriendlyMessageCode() {
        return value;
    }
}
