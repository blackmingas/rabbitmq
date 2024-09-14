package routing;

public enum LogLevelEnum {
    INFO( "info", 1 ),
    WARNING( "warning", 2 ),
    ERROR( "error", 3 );
    private String name;
    private int code;

    private LogLevelEnum(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }
    public int getCode() {
        return code;
    }
}
