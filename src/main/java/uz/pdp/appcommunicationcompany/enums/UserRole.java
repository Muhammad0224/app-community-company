package uz.pdp.appcommunicationcompany.enums;

public enum UserRole {
    DIRECTOR("ROLE_DIRECTOR"),
    HR_MANAGER("ROLE_MANAGER"),
    BRANCH_DIRECTOR("ROLE_BRANCH_DIRECTOR"),
    BRANCH_MANAGER("ROLE_BRANCH_MANAGER"),
    NUMBER_MANAGER("ROLE_NUMBER_MANAGER"),
    WORKER("ROLE_WORKER"),
    USER("ROLE_USER");

    private final String code;

    UserRole(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
