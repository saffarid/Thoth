package thoth_gui.thoth_lite.components.controls.combo_boxes;

import controls.ComboBox;
import javafx.collections.FXCollections;
import javafx.scene.control.ListCell;
import thoth_core.thoth_lite.ThothLite;
import thoth_core.thoth_lite.db_data.db_data_element.properties.Typable;
import thoth_core.thoth_lite.db_lite_structure.AvaliableTables;
import thoth_core.thoth_lite.exceptions.NotContainsException;

import java.sql.SQLException;
import java.util.List;

public class TypableComboBox {

    public static ComboBox<Typable> getInstance(
            AvaliableTables table
            , Typable value
    ){
        ComboBox<Typable> res = thoth_gui.thoth_lite.components.controls.combo_boxes.ComboBox.getInstance();

        try {
            res.setItems(FXCollections.observableList((List<Typable>) ThothLite.getInstance().getDataFromTable(table)));
        } catch (NotContainsException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if(value == null){
            if(!res.getItems().isEmpty()){
                res.setValue(res.getItems().get(0));
            }
        }else{
            res.setValue(value);
        }

        res.setCellFactory(data -> new TypableComboBoxCell());
        res.setButtonCell(new TypableComboBoxCell());

        return res;
    }

    private static class TypableComboBoxCell
            extends ListCell<Typable> {
        @Override
        protected void updateItem(Typable typable, boolean b) {
            if (typable != null) {
                super.updateItem(typable, b);
                setText(typable.getValue());
            }
        }
    }

}
