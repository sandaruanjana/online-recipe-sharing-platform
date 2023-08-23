package ac.uk.bolton.onlinerecipesharingplatform.util;

import org.springframework.http.HttpStatus;

/**
 * @author Sandaru Anjana <sandaruanjana@outlook.com>
 */

public class AjaxResponse<T> {
    private int status;
    private String message;
    private T data;

    private AjaxResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public static <T> AjaxResponse<T> success() {
        return new AjaxResponse<>(HttpStatus.OK.value(), "Success", null);
    }

    public static <T> AjaxResponse<T> success(T data) {
        return new AjaxResponse<>(HttpStatus.OK.value(), "Success", data);
    }

    public static <T> AjaxResponse<T> success(String message, T data) {
        return new AjaxResponse<>(HttpStatus.OK.value(), message, data);
    }

    public static <T> AjaxResponse<T> error() {
        return new AjaxResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error", null);
    }

    public static <T> AjaxResponse<T> error(String message) {
        return new AjaxResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null);
    }

    public static <T> AjaxResponse<T> error(HttpStatus status, String message) {
        return new AjaxResponse<>(status.value(), message, null);
    }

    public static <T> AjaxResponse<T> error(HttpStatus status) {
        return new AjaxResponse<>(status.value(), "Error", null);
    }
}
