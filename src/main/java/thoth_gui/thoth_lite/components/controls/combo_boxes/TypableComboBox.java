package thoth_gui.thoth_lite.components.controls.combo_boxes;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import thoth_core.thoth_lite.ThothLite;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Typable;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_gui.thoth_lite.components.controls.Label;
import thoth_gui.thoth_lite.tools.TextCase;

import java.sql.SQLException;
import java.util.List;

public class TypableComboBox {

    public static controls.ComboBox<Typable> getInstance(
            AvaliableTables table
            , Typable value
    ){
        controls.ComboBox<Typable> res = ComboBox.getInstance();

        res.setChangeListener((observableValue, typables, t1) -> {
            res.setCellFactory(data -> null);
            res.setButtonCell(null);
            res.setItems(res.getDataProperty().getValue() );
            res.setCellFactory(data -> new TypableComboBoxCell());
            res.setButtonCell(new TypableComboBoxCell());
        });

//        try {
//            res.setItems(FXCollections.observableList((List<Typable>) ThothLite.getInstance().getDataFromTable(table)));
//        } catch (NotContainsException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

        if(value == null){
            if(!res.getItems().isEmpty()){
                res.setValue(res.getItems().get(0));
            }
        }else{
            res.setValue(value);
        }

        try {
            ThothLite.getInstance().subscribeOnTable(table, res);
        } catch (NotContainsException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

//        res.setCellFactory(data -> new TypableComboBoxCell());
//        res.setButtonCell(new TypableComboBoxCell());

        return res;
    }

    private static class TypableComboBoxCell
            extends ListCell<Typable> {
        @Override
        protected void updateItem(Typable typable, boolean b) {
            if (typable != null) {
                super.updateItem(typable, b);
                setPadding(new Insets(0));
                setGraphic(Label.getInstanse(typable.getValue()));
            }
        }
    }

}
