package ThothGUI.ThothLite.Components.DBElementsView.ListCell;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Finance;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import ThothCore.ThothLite.DBLiteStructure.AvaliableTables;
import ThothGUI.OpenSubwindow;
import ThothGUI.ThothLite.Subwindows.IdentifiableCardWindow;
import ThothGUI.ThothLite.ThothLiteWindow;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

public class IdentifiableListCell<T extends Identifiable>
        extends ListCell<T> {

    private final static String STYLE_CLASS_IDENTIFIABLE_CELL = "identifiable-cell";

    private Identifiable identifiable;
    private AvaliableTables table;

    private IdentifiableViewCell instance;

    public IdentifiableListCell(AvaliableTables table) {
        super();
        getStyleClass().add(STYLE_CLASS_IDENTIFIABLE_CELL);
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
