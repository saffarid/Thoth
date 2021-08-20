package controls;

public class TextField extends javafx.scene.control.TextField {

    public TextField() {
        super();
        init();
    }

    public TextField(String s) {
        super(s);
        init();
    }

    private void init(){
        getStylesheets().add(getClass().getResource("/style/controls/text_field.css").toExternalForm());
    }

}
