package thoth_gui.thoth_lite.components.controls;

import controls.Button;
import controls.Label;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import layout.basepane.GridPane;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortBy;
import thoth_gui.thoth_lite.components.controls.sort_pane.SortPane;
import thoth_gui.thoth_lite.tools.TextCase;

import java.util.Locale;

public class ToolsPane
        extends GridPane {

    /**
     * Заголовок
     * */
    private Label title;

    /**
     * Панель сортировки
     */
    private SortPane sortPane;

    /**
     * Кнопка добавки нового элемента
     * */
    private Button addNew;

    public ToolsPane(String titleText) {
        super();

        setGridLinesVisible(true);
        addRow(Priority.ALWAYS)
                .addColumn(0, 150., 150., Priority.ALWAYS, HPos.LEFT, true)
                .addColumn(Priority.NEVER)
                .addColumn(Priority.ALWAYS)
                .addColumn(Priority.NEVER);

        setHgap(5);

        title = thoth_gui.thoth_lite.components.controls.Label.getInstanse(titleText, TextCase.UPPER);
        add(title, 0, 0);
    }

    public ToolsPane addSortPane(
            SortBy[] items,
            SortBy value,
            ChangeListener<SortBy> sortMethod
    ){
        sortPane = SortPane.getInstance()
                .setSortItems(items)
                .setCell()
                .setValue(value)
                .setSortMethod(sortMethod)
        ;
        add(sortPane, 1, 0);
        return this;
    }

    public ToolsPane addSortPane(SortPane sortPane){
        this.sortPane = sortPane;
        add(sortPane, 1, 0);
        return this;
    }

    public ToolsPane addNewButton(
            Node graphic,
            EventHandler<ActionEvent> event
    ){
        addNew = thoth_gui.thoth_lite.components.controls.Button.getInstance(graphic, event);
        add(addNew, 3, 0);
        return this;
    }

    public ToolsPane addAdditional(Node additional){
        add(additional, 2, 0);
        return this;
    }
}
