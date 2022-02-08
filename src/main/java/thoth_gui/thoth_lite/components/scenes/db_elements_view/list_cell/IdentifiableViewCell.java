package thoth_gui.thoth_lite.components.scenes.db_elements_view.list_cell;

import controls.Label;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import layout.basepane.BorderPane;
import layout.basepane.GridPane;
import tools.BackgroundWrapper;
import tools.BorderWrapper;
import thoth_core.thoth_lite.db_data.db_data_element.properties.*;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import controls.TextField;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import thoth_gui.thoth_styleconstants.svg.Images;
import tools.SvgWrapper;

public abstract class IdentifiableViewCell
        extends BorderPane {

    protected Node icon;
    protected Label title;
    protected Label subtitle;
    protected Label property;
    protected Node edit;

    protected AvaliableTables table;

    public IdentifiableViewCell() {
        super();
        init();
    }

    protected IdentifiableViewCell(
            Node node,
            String title,
            String subtitle,
            String property) {
        super();
        init();

        this.icon = node;
        this.title = thoth_gui.thoth_lite.components.controls.Label.getInstanse();
        this.subtitle = thoth_gui.thoth_lite.components.controls.Label.getInstanse();
        this.property = thoth_gui.thoth_lite.components.controls.Label.getInstanse();
        this.edit = SvgWrapper.getInstance(Images.ARROW_RIGHT());

        setTextTitle(title);
        setTextSubtitle(subtitle);
        setTextProperty(property);

        setLeft(this.icon);
        setCenter(getFillCenter());
        setRight(this.edit);

        BorderPane.setAlignment(this, Pos.CENTER);

    }

    /**
     * Общая инициализация для всех исполнений
     */
    private void init() {
        initStyle();
    }

    /**
     * Инициализация стиля
     */
    private void initStyle() {
        setMargin(this, new Insets(0));
        setBackground(
                new BackgroundWrapper()
                        .setColor(Color.TRANSPARENT)
                        .commit()
        );
        setBorder(
                new BorderWrapper()
                        .setColor(Color.GREY)
                        .addBorder(1)
                        .commit()
        );
        hoverProperty().addListener((observableValue, aBoolean, t1) -> {
            if (t1) {
                setBackground(
                        new BackgroundWrapper()
                                .setColor(Color.GREY)
                                .commit()
                );
            } else {
                setBackground(
                        new BackgroundWrapper()
                                .setColor(Color.TRANSPARENT)
                                .commit()
                );
            }
        });
    }

    public void setTable(AvaliableTables table) {
        this.table = table;
    }

    static IdentifiableViewCell getInstance(Identifiable identifiable) {
        if (identifiable instanceof Orderable) {
            return new OrderableViewCell((Orderable) identifiable);
        } else if (identifiable instanceof Storagable) {
            return new StoragableViewCell((Storagable) identifiable);
        } else if (identifiable instanceof Projectable) {
            return new ProjectableViewCell((Projectable) identifiable);
        } else if (identifiable instanceof Purchasable) {
            return new PurchasableViewCell((Purchasable) identifiable);
        } else if (identifiable instanceof Storing) {
            return new StoringViewCell((Storing) identifiable);
        } else if (identifiable instanceof Typable) {
            return new ListedViewCell((Typable) identifiable);
        } else if (identifiable instanceof Finance) {
            return new FinanceViewCell((Finance) identifiable);
        }
        return null;
    }

    private GridPane getFillCenter() {
        GridPane res = new GridPane();
        res.setGridLinesVisible(true);
        res
                .addColumn(Priority.ALWAYS, HPos.LEFT)
                .addColumn(Priority.ALWAYS, HPos.RIGHT)
                .addRow(Priority.NEVER)
                .addRow(Priority.NEVER);

        res.add(this.title, 0, 0);
        res.add(this.subtitle, 0, 1);
        res.add(this.property, 1, 1);

        BorderPane.setAlignment(res, Pos.CENTER);

        return res;
    }

    protected TextField getTextField(String text) {
        TextField node = thoth_gui.thoth_lite.components.controls.TextField.getInstance(text);
        return node;
    }

    public void setTextTitle(String text) {
        this.title.setText(text);
    }

    public void setTextSubtitle(String text) {
        this.subtitle.setText(text);
    }

    public void setTextProperty(String text) {
        this.property.setText(text);
    }


}
