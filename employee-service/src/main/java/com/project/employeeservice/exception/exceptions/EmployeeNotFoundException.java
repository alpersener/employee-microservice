package com.project.employeeservice.exception.exceptions;

import com.project.employeeservice.enums.Language;
import com.project.employeeservice.exception.enums.FriendlyMessageCode;
import com.project.employeeservice.exception.utils.FriendlyMessageUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class EmployeeNotFoundException extends RuntimeException{
    private final Language language;
    private final FriendlyMessageCode friendlyMessageCode;

    public EmployeeNotFoundException(Language language,FriendlyMessageCode friendlyMessageCode,String message) {
        super(FriendlyMessageUtils.getFriendlyMessage(language,friendlyMessageCode));
        this.language = language;
        this.friendlyMessageCode = friendlyMessageCode;
        log.error("[EmployeeNotFoundException]->message:{} developer message : {},"
                ,FriendlyMessageUtils.getFriendlyMessage(language,friendlyMessageCode)
                ,message);

    }
}
