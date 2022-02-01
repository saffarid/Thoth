package thoth_gui.thoth_lite.components.controls.table_view;

public class TableColumn {

    public static controls.table_view.TableColumn getInstance(){
        controls.table_view.TableColumn instance = new controls.table_view.TableColumn();
        return instance;
    }

    public static controls.table_view.TableColumn getInstance(String s){
        controls.table_view.TableColumn instance = getInstance();
        instance.setText(s);
        return instance;
    }

}
