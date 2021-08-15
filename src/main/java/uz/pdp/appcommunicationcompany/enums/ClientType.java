package uz.pdp.appcommunicationcompany.enums;

public enum ClientType {
    PHYSICAL_PERSON("JISMONIY_SHAXS"),
    LEGAL_PERSON("YURIDIK_SHAXS");

    private final String code;

    ClientType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
