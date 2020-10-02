package com.dimagesharevn.app.constants;

public class APIMessage {
    //Common
    public static final String PASSWORD_NOT_MATCH = "Password and confirm password not match";
    public static final String PASSWORD_NOT_BLANK = "Password must not blank";
    public static final String EMAIL_NOT_BLANK = "Email must not blank";
    public static final String NAME_NOT_BLANK = "Name must not blank";
    public static final String LAST_NAME_NOT_BLANK = "Last name must not blank";
    public static final String REPEAT_PASSWORD_NOT_BLANK = "Repeat password must not blank";
    public static final String USER_TYPE_NOT_EMPTY = "User type must not empty";
    public static final String USER_NAME_NOT_BLANK = "User name must not blank";
    public static final String CREATE_FILEDIR_ERROR = "Could not create the directory where the uploaded files will be stored.";
    public static final String FILE_INVALID_PATH_SEQUENCE = "Sorry! Filename contains invalid path sequence ";
    public static final String FILE_NOT_STORE = "Could not store file %s . Please try again!";
    public static final String FILE_NOT_FOUND = "File not found %s";
    public static final String STATUS_TYPE_INVALID = "Status type invalid";
    public static final String NOT_DETERMINE_FILE_TYPE = "Could not determine file type";
    public static final String FILE_TYPE_INVALID = "File type invalid";
    public static final String PARAMS_INVALID = "Params invalid";


    //Authenticate
    public static String RESOURCE_NOT_FOUND = "%s not found with %s : '%s'";
    public static final String LOGIN_SUCCESSFUL = "Login successful";
    public static final String LOGIN_FAILURE = "Login failure";
    public static String ENDTRY_POINT_UNAUTHORIZED = "Responding with unauthorized error";
    public static final String ACCOUNT_INVALID = "Account invalid, please try again !";
    public static String OAUTH2_UNAUTHORIZED = "Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication";
    public static final String LOGOUT_SUCCESSFULL = "Logout successful";
    public static final String LOGOUT_FAILURE = "Logout failure";

    //User
    public static final String REGIST_USER_SUCCESSFUL = "Regist user successful";
    public static final String REGIST_USER_FAIL = "Regist user fail";
    public static final String REGIST_USER_FAIL_EMAIL = "This email is already taken";
    public static final String REGIST_USER_CONFLICT = "This username is already taken";
    public static final String APPROVE_TYPE_INVALID = "Approve type invalidonflict";
    public static final String GENDER_TYPE_INVALID = "Gender type invalid";
    public static final String USER_TYPE_INVALID = "User type invalid";
    public static final String SEARCH_TYPE_INVALID = "Search type invalid";
    public static final String JID_TYPE_INVALID = "JId type invalid";

    //Group
    public static final String CREATE_GROUP_SUCCESSFUL = "Create group successful";
    public static final String CREATE_GROUP_FAILURE = "Could not create a group";
    public static final String UPDATE_GROUP_SUCCESSFUL = "Update group successful";
    public static final String UPDATE_GROUP_FAILURE = "Could not update a group";

    //Chat room
    public static final String CREATE_CHAT_ROOM_SUCCESSFUL = "Create chat room successful";
    public static final String CREATE_CHAT_ROOM_FAILURE = "Create chat room failure";

    //profile
    public static final String UPDATE_PROFILE_SUCCESSFUL = "Update profile successful";
    public static final String UPDATE_PROFILE_FAILURE = "Update profile failure";
}
