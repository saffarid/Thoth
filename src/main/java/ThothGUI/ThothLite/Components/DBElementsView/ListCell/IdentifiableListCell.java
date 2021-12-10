package ThothGUI.ThothLite.Components.DBElementsView.ListCell;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Finance;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;
import ThothCore.ThothLite.Exceptions.NotContainsException;
import ThothCore.ThothLite.ThothLite;
import ThothGUI.OpenSubwindow;
import ThothGUI.ThothLite.DialogWindows.DialogWindow;
import ThothGUI.ThothLite.DialogWindows.DialogWindowType;
import ThothGUI.ThothLite.Subwindows.IdentifiableCardWindow;
import ThothGUI.ThothLite.ThothLiteWindow;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class IdentifiableListCell<T extends Identifiable>
        extends ListCell<T> {

    private final static String STYLE_CLASS_IDENTIFIABLE_CELL = "identifiable-cell";

    private T identifiable;
    private AvaliableTables table;

    private IdentifiableViewCell instance;

    public IdentifiableListCell(AvaliableTables table) {
        super();
        getStyleClass().addAll(
                STYLE_CLASS_IDENTIFIABLE_CELL
        );
        setPadding(new Insets(1, 0, 1, 0));
        this.table = table;
        setOnMouseClicked(this::cellClick);

    }

    private void cellClick(MouseEvent mouseEvent) {

        switch (mouseEvent.getButton()) {
            case PRIMARY: {
                if (!(identifiable instanceof Listed) &&
                        !(identifiable instanceof Finance) &&
                        (identifiable != null)) {
                    open();
                }
                break;
            }
            case SECONDARY:{
                if (!(identifiable instanceof Listed) &&
                        !(identifiable instanceof Finance) &&
                        (identifiable != null)) {
                    setContextMenu(createContextMenu());
                }
                break;
            }
        }
    }

    private ContextMenu createContextMenu(){
        ContextMenu contextMenu = new ContextMenu();

        MenuItem open = new MenuItem("open");
        open.setOnAction(event -> open());

        MenuItem remove = new MenuItem("remove");
        remove.setOnAction(event -> removeIdentifiable());

        contextMenu.getStyleClass().add("context-menu");

        contextMenu.getItems().add(open);
        contextMenu.getItems().add(remove);

        return contextMenu;
    }

    public Node getView() {
        return instance;
    }

    private void open(){
        ((OpenSubwindow) ThothLiteWindow.getInstance()).openSubwindow(new IdentifiableCardWindow("Карточка", table, identifiable));
    }

    private void removeIdentifiable(){

        DialogWindow<ButtonType> instance = DialogWindow.getInstance(DialogWindowType.CONFIRM, "Вы подтверждаете удаление записи из БД?");
        Optional<ButtonType> optional = instance.showAndWait();
        if (optional.isPresent()){
            if(optional.get() == ButtonType.YES){
                List<T> removeList = new LinkedList<>();
                removeList.add(this.identifiable);
                try {
                    ThothLite.getInstance().removeFromTable(table, removeList);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (NotContainsException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    protected void updateItem(T identifiable, boolean b) {
        if (identifiable != null) {
            super.updateItem(identifiable, b);
            this.identifiable = identifiable;
            instance = IdentifiableViewCell.getInstance(identifiable);
            setGraphic(instance);
            instance.setTable(table);
        }
    }

}
