package pt.isel.ls.model.exceptions;

/**
 * Created by Leonardo Freire on 29/04/2017.
 */
public class CommandNotFoundException extends RuntimeException {

    public CommandNotFoundException(String message) {
        super(message);
    }
}
