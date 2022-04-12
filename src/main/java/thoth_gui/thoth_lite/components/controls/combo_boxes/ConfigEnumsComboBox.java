package thoth_gui.thoth_lite.components.controls.combo_boxes;

import controls.ListCell;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;

import thoth_core.thoth_lite.config.ConfigEnums;
import thoth_gui.thoth_lite.components.controls.Label;
import thoth_gui.thoth_lite.tools.TextCase;

import java.util.concurrent.CompletableFuture;

public class ConfigEnumsComboBox {

    public static controls.ComboBox<ConfigEnums> getInstance(
            ConfigEnums[] list,
            Object value,
            ChangeListener<ConfigEnums> valueChanger){
        controls.ComboBox<ConfigEnums> res = ComboBox.getInstance();

        CompletableFuture.runAsync(() -> {
            for(ConfigEnums enums : list){
                res.getItems().add( enums );
                String name = enums.getName();
                if(name.equals(value)){
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

                if(configEnums.getValue() instanceof Integer || configEnums.getValue() instanceof Double) {

                    if (configEnums.getName().equalsIgnoreCase("never")) {
                        setGraphic(
                                Label.getInstanse(configEnums.getName(), TextCase.NORMAL)
                        );
                    } else {
                        setGraphic(
                                Label.getInstanse(String.valueOf(configEnums.getValue()))
                        );
                    }

                } else {

                    setGraphic(
                            Label.getInstanse(configEnums.getName(), TextCase.NORMAL)
                    );

                }
            }
        }
    }

}
