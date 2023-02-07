package com.example.blooddonation;

public class Constants {
    public static final String USERS_COLLECTION = "Users";
    public static final String USER_EXTRA = "user";
    public static final String APPLICATION_EXTRA = "application";
    public static final String CLIENT_ROLE = "CLIENT";
    public static final String APPLICATION_ACCEPTED_STATUS = "ACCEPTED";
    public static final String APPLICATION_CLOSED_STATUS = "CLOSED";
    public static final String APPLICATIONS_COLLECTION = "Applications";
    public static final String USER_ID_FIELD = "userId";
    public static final String QR_CODE_API_QUERY_TEMPLATE
            = "https://api.qrserver.com/v1/create-qr-code/?size=150x150&bgcolor=255-0-0&color=255-255-255&data=%s";
}
