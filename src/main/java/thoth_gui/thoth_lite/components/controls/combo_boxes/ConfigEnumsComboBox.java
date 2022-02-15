package thoth_gui.thoth_lite.components.controls.combo_boxes;

import controls.ComboBox;
import controls.ListCell;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;

import thoth_core.thoth_lite.config.ConfigEnums;

import java.util.concurrent.CompletableFuture;

public class ConfigEnumsComboBox {

    public static ComboBox<ConfigEnums> getInstance(ConfigEnums[] list, Object value, ChangeListener<ConfigEnums> valueChanger){
        ComboBox<ConfigEnums> res = thoth_gui.thoth_lite.components.controls.combo_boxes.ComboBox.getInstance();

        CompletableFuture.runAsync(() -> {
            for(ConfigEnums enums : list){
                res.getItems().add( enums );
                if(enums.getName().equals(value)){
                    Platform.runLater(() -> res.setValue(enums));
                }
            }
        });

        res.valueProperty().addListener( valueChanger );

        res.setButtonCell(new ConfigEnumsListCell());
        res.setCellFactory(cell -> new ConfigEnumsListCell());

        return res;
    }

    private static class ConfigEnumsListCell
        extends ListCell<ConfigEnums>{

        @Override
        protected void updateItem(ConfigEnums configEnums, boolean b) {
            if(configEnums != null) {
                super.updateItem(configEnums, b);
                setText(configEnums.getName());
            }
        }
    }


}
