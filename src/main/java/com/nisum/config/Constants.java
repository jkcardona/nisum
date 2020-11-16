package com.nisum.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";
    
    /**La clave debe seguir una expresión regular para validar que formato sea el correcto. (Una Mayuscula, letras minúsculas, y dos numeros)**/
    public static final String PASSWORD_REGEX = "^(?=(?:.*\\d){2})(?=(?:.*[A-Z]){1})(?=(?:.*[a-z]))\\S{8,16}$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String DEFAULT_LANGUAGE = "es";
    public static final String ANONYMOUS_USER = "anonymoususer";

    private Constants() {
    }
}
