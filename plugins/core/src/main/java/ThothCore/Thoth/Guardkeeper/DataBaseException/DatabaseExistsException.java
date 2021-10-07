package ThothCore.Thoth.Guardkeeper.DataBaseException;

import java.io.File;

public class DatabaseExistsException extends Exception{

    private static final String MESSAGE_TEMPLATE = "Database %1s already exists.";

    public DatabaseExistsException(File db) {
        super(String.format(MESSAGE_TEMPLATE, db.getAbsolutePath()));
    }
}
