package controls;

public class TextArea extends javafx.scene.control.TextArea {

    public TextArea() {
        super();
        init();
    }

    public TextArea(String s) {
        super(s);
        init();
    }

    private void init(){
        getStylesheets().add(getClass().getResource("/style/controls/text_field.css").toExternalForm());
    }
}
