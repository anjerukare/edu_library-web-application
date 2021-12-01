package edu.mtp.Library.util;

public class Response {

    private final Result result;
    private final String message;

    public Response(Result result, String message) {
        this.result = result;
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }
}
