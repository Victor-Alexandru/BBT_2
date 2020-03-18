package BBT_InClass.validator;

public class ValidationException extends RuntimeException {
    public ValidationException(){}
    public ValidationException(String msg){
        super(msg);
    }
}
