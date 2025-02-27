public class InvalidLoginException extends Exception{
    public InvalidLoginException(String errorMessage){
        super(errorMessage);
    }
}
