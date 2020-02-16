package Exception;

public class DoesNotExistException extends BlackListException {
    public DoesNotExistException(){
        super("Entry does not exist");
    }
}
