package window;

import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import styleconstants.Stylesheets;
import styleconstants.Styleclasses;

public class StageResizer {

    private Stage stage;
    private int mouseOn = 0;

    private final int MOUSE_ON_TOP = 1;
    private final int MOUSE_ON_SECOND_DIAG_TOP = 2;
    private final int MOUSE_ON_RIGHT = 3;
    private final int MOUSE_ON_PRIMARY_DIAG_BOTTOM = 4;
    private final int MOUSE_ON_BOTTOM = 5;
    private final int MOUSE_ON_SECOND_DIAG_BOTTOM = 6;
    private final int MOUSE_ON_LEFT = 7;
    private final int MOUSE_ON_PRIMARY_DIAG_TOP = 8;

    private final String CLASS_H_RESIZE = Styleclasses.H_RESIZER;
    private final String CLASS_V_RESIZE = Styleclasses.V_RESIZER;
    private final String CLASS_MAIN_DIAG_RESIZE = Styleclasses.MAIN_DIAG_RESIZER;
    private final String CLASS_SECOND_DIAG_RESIZE = Styleclasses.SECOND_DIAG_RESIZER;

    private final int BORDER_PX_RESIZE = 3;
    private final int BORDER_PX_DIAG_RESIZE = 10;


    public StageResizer() {
    }

    public StageResizer(Stage stage) {
        setStage(stage);
    }

    /**
     * Функция изменяет размер в верхнем положении
     * */
    private void changeSizeOnTop(MouseEvent mouseEvent){
        if((stage.getHeight() - (mouseEvent.getScreenY() - stage.getY())) >= stage.getMinHeight()) {
            stage.setHeight(stage.getHeight() - (mouseEvent.getScreenY() - stage.getY()));
            stage.setY(mouseEvent.getScreenY());
        }
    }

    /**
     * Функция изменяет размер в правом положении
     * */
    private void changeSizeOnRight(MouseEvent mouseEvent){
        if((stage.getWidth() - (stage.getWidth() - mouseEvent.getSceneX())) >= stage.getMinWidth()) {
            stage.setWidth(stage.getWidth() - (stage.getWidth() - mouseEvent.getSceneX()));
        }
    }

    /**
     * Функция изменяет размер в нижнем положении
     * */
    private void changeSizeOnBottom(MouseEvent mouseEvent){
        if((stage.getHeight() - (stage.getHeight() - mouseEvent.getSceneY())) >= stage.getMinHeight()) {
            stage.setHeight(stage.getHeight() - (stage.getHeight() - mouseEvent.getSceneY()));
        }
    }

    /**
     * Функция изменяет размер в левом положении
     * */
    private void changeSizeOnLeft(MouseEvent mouseEvent){
        if((stage.getWidth() - (mouseEvent.getScreenX() - stage.getX())) >= stage.getMinWidth()) {
            stage.setWidth(stage.getWidth() - (mouseEvent.getScreenX() - stage.getX()));
            stage.setX(mouseEvent.getScreenX());
        }
    }

    /**
     * Функция проверяет положение курсора на верхней границе
     */
    private boolean checkMouseOnTop(MouseEvent mouseEvent) {
        return (mouseEvent.getSceneY() < BORDER_PX_RESIZE);
    }

    /**
     * Функция проверяет положение курсора на нижней границе
     */
    private boolean checkMouseOnBottom(MouseEvent mouseEvent) {
        return (this.stage.getScene().getHeight() - BORDER_PX_RESIZE) < mouseEvent.getSceneY() && mouseEvent.getSceneY() < this.stage.getScene().getHeight();
    }

    /**
     * Функция проверяет положение курсора на левой стороне
     */
    private boolean checkMouseOnLeft(MouseEvent mouseEvent) {
        return (mouseEvent.getSceneX() < BORDER_PX_RESIZE);
    }

    /**
     * Функция проверяет положение курсора на правой стороне
     */
    private boolean checkMouseOnRight(MouseEvent mouseEvent) {
        return (this.stage.getScene().getWidth() - BORDER_PX_RESIZE) < mouseEvent.getSceneX() && mouseEvent.getSceneX() < this.stage.getScene().getWidth();
    }

    /**
     * Функция проверяет положение курсора на главной диагонали сверху
     */
    private boolean checkMainDiagTop(MouseEvent mouseEvent) {
        return ((mouseEvent.getSceneX() < BORDER_PX_DIAG_RESIZE) && (mouseEvent.getSceneY() < BORDER_PX_DIAG_RESIZE));
    }

    /**
     * Функция проверяет положение курсора на главной диагонали снизу
     */
    private boolean checkMainDiagBottom(MouseEvent mouseEvent) {
        return ((this.stage.getScene().getWidth() - BORDER_PX_DIAG_RESIZE) < mouseEvent.getSceneX()) && ((this.stage.getScene().getHeight() - BORDER_PX_DIAG_RESIZE) < mouseEvent.getSceneY());
    }

    /**
     * Функция проверяет положение курсора на главной диагонали
     */
    private boolean checkMainDiag(MouseEvent mouseEvent) {
        return (checkMainDiagTop(mouseEvent) || checkMainDiagBottom(mouseEvent));
    }

    /**
     * Функция проверяет положение курсора на второстепенной диагонали сверху
     */
    private boolean checkSecondDiagTop(MouseEvent mouseEvent) {
        return ((this.stage.getScene().getWidth() - BORDER_PX_DIAG_RESIZE < mouseEvent.getSceneX()) && (mouseEvent.getSceneY() < BORDER_PX_DIAG_RESIZE));
    }

    /**
     * Функция проверяет положение курсора на второстепенной диагонали снизу
     */
    private boolean checkSecondDiagBottom(MouseEvent mouseEvent) {
        return ((mouseEvent.getSceneX() < BORDER_PX_DIAG_RESIZE) && (this.stage.getScene().getHeight() - BORDER_PX_DIAG_RESIZE < mouseEvent.getSceneY()));
    }

    /**
     * Функция проверяет положение курсора на второстепенной диагонали
     */
    private boolean checkSecondDiag(MouseEvent mouseEvent) {
        return (checkSecondDiagTop(mouseEvent) || checkSecondDiagBottom(mouseEvent));
    }

    private void clearCursor(Parent root) {
        if (root.getStyleClass().contains(CLASS_H_RESIZE)) {
            root.getStyleClass().remove(CLASS_H_RESIZE);
        }
        if (root.getStyleClass().contains(CLASS_V_RESIZE)) {
            root.getStyleClass().remove(CLASS_V_RESIZE);
        }
        if (root.getStyleClass().contains(CLASS_MAIN_DIAG_RESIZE)) {
            root.getStyleClass().remove(CLASS_MAIN_DIAG_RESIZE);
        }
        if (root.getStyleClass().contains(CLASS_SECOND_DIAG_RESIZE)) {
            root.getStyleClass().remove(CLASS_SECOND_DIAG_RESIZE);
        }
        mouseOn = 0;
    }

    public void setStage(Stage stage) {
        this.stage = stage;

        Parent root = this.stage.getScene().getRoot();
        root.getStylesheets().add(getClass().getResource(Stylesheets.STAGE_RESIZER).toExternalForm());

        root.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, mouseEvent -> {
            if (!root.equals(mouseEvent.getTarget())) {
                clearCursor(root);
            }
        });

        root.setOnMouseMoved(mouseEvent -> {
            clearCursor(root);
            //Сначала проверяем нахождение курсора на диагоналях
            if (checkMainDiag(mouseEvent) || checkSecondDiag(mouseEvent)) {
//                Проверка положения курсора на главной диагонали
                if (checkMainDiag(mouseEvent)) {
                    if (!root.getStyleClass().contains(CLASS_MAIN_DIAG_RESIZE)) {
                        root.getStyleClass().add(CLASS_MAIN_DIAG_RESIZE);
                    }
                }
//                ПРоверка положения курсора на второстепенной диагонали
                if (checkSecondDiag(mouseEvent)) {
                    if (!root.getStyleClass().contains(CLASS_SECOND_DIAG_RESIZE)) {
                        root.getStyleClass().add(CLASS_SECOND_DIAG_RESIZE);
                    }
                }
            } else {
//                Проверка положения курсора на горизонтали
                if (checkMouseOnLeft(mouseEvent) || checkMouseOnRight(mouseEvent)) {
                    if (!root.getStyleClass().contains(CLASS_H_RESIZE)) {
                        root.getStyleClass().add(CLASS_H_RESIZE);
                    }
                }
//                Проверка положения курсора на вертикали
                if (checkMouseOnTop(mouseEvent) || checkMouseOnBottom(mouseEvent)) {
                    if (!root.getStyleClass().contains(CLASS_V_RESIZE)) {
                        root.getStyleClass().add(CLASS_V_RESIZE);
                    }
                }
            }
        });

        root.setOnMousePressed(mouseEvent -> {
                if (checkMainDiag(mouseEvent) || checkSecondDiag(mouseEvent)) {
//                Проверка положения курсора на главной диагонали
                    if (checkMainDiag(mouseEvent)) {
                        mouseOn = (checkMainDiagTop(mouseEvent)) ? (MOUSE_ON_PRIMARY_DIAG_TOP) : (MOUSE_ON_PRIMARY_DIAG_BOTTOM);
                    }
//                ПРоверка положения курсора на второстепенной диагонали
                    if (checkSecondDiag(mouseEvent)) {
                        mouseOn = (checkSecondDiagTop(mouseEvent)) ? (MOUSE_ON_SECOND_DIAG_TOP) : (MOUSE_ON_SECOND_DIAG_BOTTOM);
                    }
                } else {
//                Проверка положения курсора на горизонтали
                    if (checkMouseOnLeft(mouseEvent) || checkMouseOnRight(mouseEvent)) {
                        mouseOn = (checkMouseOnLeft(mouseEvent)) ? (MOUSE_ON_LEFT) : (MOUSE_ON_RIGHT);
                    }
//                Проверка положения курсора на вертикали
                    if (checkMouseOnTop(mouseEvent) || checkMouseOnBottom(mouseEvent)) {
                        mouseOn = (checkMouseOnTop(mouseEvent)) ? (MOUSE_ON_TOP) : (MOUSE_ON_BOTTOM);
                    }
                }
        });

        root.setOnMouseDragged(mouseEvent -> {
            switch (mouseOn){
                case MOUSE_ON_TOP:{
                    changeSizeOnTop(mouseEvent);
                    break;
                }
                case MOUSE_ON_SECOND_DIAG_TOP:{
                    changeSizeOnTop(mouseEvent);
                    changeSizeOnRight(mouseEvent);
                    break;
                }
                case MOUSE_ON_RIGHT:{
                    changeSizeOnRight(mouseEvent);
                    break;
                }
                case MOUSE_ON_PRIMARY_DIAG_BOTTOM:{
                    changeSizeOnBottom(mouseEvent);
                    changeSizeOnRight(mouseEvent);
                    break;
                }
                case MOUSE_ON_BOTTOM:{
                    changeSizeOnBottom(mouseEvent);
                    break;
                }
                case MOUSE_ON_SECOND_DIAG_BOTTOM:{
                    changeSizeOnBottom(mouseEvent);
                    changeSizeOnLeft(mouseEvent);
                    break;
                }
                case MOUSE_ON_LEFT:{
                    changeSizeOnLeft(mouseEvent);
                    break;
                }
                case MOUSE_ON_PRIMARY_DIAG_TOP:{
                    changeSizeOnTop(mouseEvent);
                    changeSizeOnLeft(mouseEvent);
                    break;
                }
            }

        });
    }
}
