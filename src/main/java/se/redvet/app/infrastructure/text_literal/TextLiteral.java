package se.redvet.app.infrastructure.text_literal;

public class TextLiteral {
    private TextLiteral() {throw new IllegalStateException("TextLiteral class");}
    public static final String USER_NOT_FOUND = "User not found";
    public static final String USER_WAS_REMOVED = "User was removed.";
    public static final String USER_NOT_UPDATED = "User not updated.";
    public static final String USER_NOT_CREATED = "User not created";
    public static final String TOKEN_NOT_CREATED = "Token not created";
    public static final String TOKEN_NOT_FOUND = "Token not found";
    public static final String EMAIL_TEXT = """
            Hello,
            You have successfully created your profile. Please verify your email by clicking the link below to activate your profile:
            
            %s%s
            
            Best regards
            RedVet Marcin Topolski
            """;
}
