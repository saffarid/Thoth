package ThothGUI.Guardkeeper;

import java.io.File;

public class ListCell extends controls.ListCell<File> {

    private File path;

    public ListCell() {
    }

    public String getPath(){
        return path.getParent();
    }

    public String getName(){
        return path.getName();
    }

    @Override
    protected void updateItem(File file, boolean b) {
        super.updateItem(file, b);
        if(file != null){
            path = file;
            setText(getName());
        }
    }
}
