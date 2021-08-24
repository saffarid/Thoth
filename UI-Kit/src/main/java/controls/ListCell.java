package controls;

import javafx.scene.control.TreeCell;

public class ListCell<T> extends javafx.scene.control.ListCell<T> {

    private String getLinearGradient(String color, int midGrad, int gradRange){
        if(midGrad <= gradRange){
            return "linear-gradient(to right, " +
                    color + " 0%,  " +
                    color + " " + midGrad + "%,  " +
                    color + " " + (midGrad + gradRange) + "%, " +
                    "transparent)";
        }
        if((midGrad + gradRange) >= 100){
            return "linear-gradient(to right, " +
                    "transparent,  " +
                    color + " " + (midGrad - gradRange) + "%, " +
                    color + " " + midGrad + "%,  " +
                    color + ")";
        }
        return "linear-gradient(to right, " +
                "transparent,  " +
                color + " " + (midGrad - gradRange) + "%, " +
                color + " " + midGrad + "%,  " +
                color + " " + (midGrad + gradRange) + "%, " +
                "transparent)";
    }

    @Override
    protected void updateItem(T t, boolean b) {
        super.updateItem(t, b);
        if(t != null){
            setOnMouseMoved(mouseEvent -> {
                ListCell source = (ListCell) mouseEvent.getSource();
                int perX = (int) ((mouseEvent.getSceneX() / getWidth()) * 100);
                StringBuilder style = new StringBuilder("");

                style.append("-fx-background-color: " + getLinearGradient("#6C757D", perX, 25) + ";");
                style.append("-fx-border-color: " + getLinearGradient("grey", perX, 15) + ";");

                source.setStyle(style.toString());
            });
            setOnMouseExited(mouseEvent -> {
                ((ListCell) mouseEvent.getSource()).setStyle("");
            });
        }
    }
}
