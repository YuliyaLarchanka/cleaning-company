package by.larchanka.tiptopcleaning.command;

public class CommandResponse {
    private boolean errorStatus;
    private String message;
    private String targetURL;

    public CommandResponse() {
    }

    public CommandResponse(boolean errorStatus, String message, String targetURL) {
        this.errorStatus = errorStatus;
        this.message = message;
        this.targetURL = targetURL;
    }

    public boolean isErrorStatus() {
        return errorStatus;
    }

    public void setErrorStatus(boolean errorStatus) {
        this.errorStatus = errorStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTargetURL() {
        return targetURL;
    }

    public void setTargetURL(String targetURL) {
        this.targetURL = targetURL;
    }
}
