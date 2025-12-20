package com.practice.thoughtstream.utility;

/**
 * Central place for all user-facing messages
 * used in BlogPost application.
 */
public final class MessageConstants {

    public static final String USER_CREATED = "user created successfully. ";
    public static final String TOKEN_EXPIRE = "Token has expire, pls login again";
    public static final String COMMENT_UPDATED = "Comment has been updated.. ";

    // Private constructor to prevent instantiation
    private MessageConstants() {}

    /* ==========================
       COMMON MESSAGES
       ========================== */
    public static final String SUCCESS = "Operation completed successfully.";
    public static final String FAILURE = "Operation failed. Please try again.";
    public static final String INVALID_REQUEST = "Invalid request data.";
    public static final String RESOURCE_NOT_FOUND = "Requested resource not found.";

    /* ==========================
       BLOG POST MESSAGES
       ========================== */
    public static final String BLOG_CREATED = "Blog post created successfully.";
    public static final String BLOG_UPDATED = "Blog post updated successfully.";
    public static final String BLOG_DELETED = "Blog post deleted successfully.";
    public static final String BLOG_FETCHED = "Blog post fetched successfully.";
    public static final String BLOG_LIST_FETCHED = "Blog posts fetched successfully.";

    public static final String BLOG_NOT_FOUND = "Blog post not found.";
    public static final String BLOG_TITLE_REQUIRED = "Blog title is required.";
    public static final String BLOG_CONTENT_REQUIRED = "Blog content is required.";
    public static final String BLOG_AUTHOR_REQUIRED = "Blog author is required.";

    /* ==========================
       COMMENT MESSAGES
       ========================== */
    public static final String COMMENT_ADDED = "Comment added successfully.";
    public static final String COMMENT_DELETED = "Comment deleted successfully.";
    public static final String COMMENT_NOT_FOUND = "Comment not found.";
    public static final String COMMENT_CONTENT_REQUIRED = "Comment content is required.";

    /* ==========================
       AUTH / USER MESSAGES
       ========================== */
    public static final String USER_NOT_AUTHORIZED = "You are not authorized to perform this action.";
    public static final String USER_NOT_FOUND = "User not found.";
    public static final String LOGIN_SUCCESS = "Login successful.";
    public static final String LOGIN_FAILED = "Invalid username or password.";

    /* ==========================
       VALIDATION MESSAGES
       ========================== */
    public static final String VALIDATION_FAILED = "Validation failed.";
    public static final String ID_REQUIRED = "Id must not be null.";

    /* ==========================
       SYSTEM / ERROR MESSAGES
       ========================== */
    public static final String INTERNAL_SERVER_ERROR = "Internal server error.";
    public static final String DATABASE_ERROR = "Database operation failed.";
}
