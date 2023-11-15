package com.project.employeeservice.exception.utils;

import com.project.employeeservice.enums.Language;
import com.project.employeeservice.exception.enums.FriendlyMessageCode;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Slf4j
@UtilityClass
public class FriendlyMessageUtils {
    private static final String RESOURCE_BUNDLE_NAME="FriendlyMessage";
    private static final String SPECIAL_CHARACTER="__";

    public static String getFriendlyMessage(Language language, FriendlyMessageCode messageCode){
        String messageKey=null;
        try {
            Locale locale=new Locale(language.name());
            ResourceBundle resourceBundle=ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME,locale);
            messageKey=messageCode.getClass().getSimpleName()+SPECIAL_CHARACTER+messageCode;
            return resourceBundle.getString(messageKey);
        }catch (MissingResourceException exception){
            log.error("Friendly Message not found for key:{}",messageKey);
            return null;
        }

    }
}
