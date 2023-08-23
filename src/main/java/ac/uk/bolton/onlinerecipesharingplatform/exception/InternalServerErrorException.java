package ac.uk.bolton.onlinerecipesharingplatform.exception;

/**
 * @author Sandaru Anjana <sandaruanjana@outlook.com>
 */
public class InternalServerErrorException extends RuntimeException{
    public InternalServerErrorException(String message) {
        super(message);
    }
}
