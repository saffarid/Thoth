package ThothGUI.Guardkeeper;

import thoth_styleconstants.Styleclasses;
import ThothCore.Guardkeeper.DataBaseException.DatabaseExistsException;
import controls.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import layout.custompane.ListPane;
import layout.basepane.VBox;
import layout.title.TitleWithoutMenu;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Guardkeeper extends BorderPane {

    private final String IMG_EYE = "/image/icon/eye.png";

    /**
     * Окно, в котором отображается представление
     * */
    private Stage stage;

    /**
     * Заголовок окна
     */
    private TitleWithoutMenu title;

    /**
     * Навигационное меню. В него записываются пользовательские БД
     */
    private ListPane<File> navigationMenu;

    /**
     * Объект выбора взаимодействия с Thoth
     * */
    private ThothCore.Guardkeeper.Guardkeeper guardkeeper;

    /**
     * Наименование пользовательской БД
     * */
    private SimpleStringProperty name;

    /**
     * Расположение пользовательской БД
     * */
    private SimpleStringProperty path;

    /**
     * Расположение шаблона пользовательской БД
     * */
    private SimpleStringProperty template;


    public Guardkeeper(Stage stage) throws SQLException, ClassNotFoundException {
        guardkeeper = new ThothCore.Guardkeeper.Guardkeeper();
        this.stage = stage;

        name = new SimpleStringProperty("");
        path = new SimpleStringProperty("");
        template = new SimpleStringProperty("");

        titleConfig();
        navigationMenuConfig();
        contentConfig();

        setStyle();
    }

    /**
     * Функция выбора расположения создаваемой пользовательской БД
     * */
    private void choisePath(ActionEvent event) {
        DirectoryChooser chooser = new DirectoryChooser();
        String s = path.get();
        if(s != null && !s.equals("")) chooser.setInitialDirectory(new File(s));
        File file = chooser.showDialog(stage);
        if(file != null){
            path.set(file.getAbsolutePath());
        }
    }

    /**
     * Функция выбора шаблона пользовательской БД
     * */
    private void choiseTemplate(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(stage);
        if(file != null){
            template.set(file.getAbsolutePath());
        }
    }

    private void contentConfig(){
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(50, 0, 0, 5));

        Button sandbox = getButton("SANDBOX", null, this::openSandbox);
        Button viewer = getButton("VIEWER", IMG_EYE, this::openViewer);
        Button edit = getButton("РЕДАКТИРОВАТЬ", null, this::editUserDb);
        Button create = getButton("СОЗДАТЬ", null, this::createUserDb);
        Button remove = getButton("УДАЛИТЬ", null, this::createUserDb);

        sandbox.disableProperty().bind(navigationMenu.getList().getSelectionModel().selectedItemProperty().isNull());
        viewer.disableProperty().bind(navigationMenu.getList().getSelectionModel().selectedItemProperty().isNull());
        edit.disableProperty().bind(navigationMenu.getList().getSelectionModel().selectedItemProperty().isNull());
        create.disableProperty().bind(navigationMenu.getList().getSelectionModel().selectedItemProperty().isNotNull());
        remove.disableProperty().bind(navigationMenu.getList().getSelectionModel().selectedItemProperty().isNull());

        TextField name = new TextField();
        PathFile path = getPathFile(this::choisePath);
        PathFile template = getPathFile(this::choiseTemplate);

        this.name.bindBidirectional(name.textProperty());
        this.path.bindBidirectional(path.textProperty());
        template.textProperty().bind(this.template);

        vBox.getChildren().addAll(
                new Twin(sandbox, viewer),
                new Twin(new Label("Наименование"), name),
                new Twin(new Label("Путь"), path),
                new Twin(new Label("Шаблон"), template),
                new Twin(edit, create),
                new Twin(new Label(), remove)
        );
        setCenter(vBox);
    }

    /**
     * Функция создает пользовательскую БД
     * */
    private void createUserDb(ActionEvent event) {
        if(!path.get().equals("") || !name.get().equals("")) {
            try {
                guardkeeper.createNewDatabase(new File(path.get(), name.get()), new File(template.get()));
                navigationMenu.getList().getItems().setAll(guardkeeper.getDatabases());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (DatabaseExistsException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Не введено имя или не выбрано расположение БД");
        }
    }

    /**
     * Функция редактирует расположение пользовательской БД
     * */
    private void editUserDb(ActionEvent event) {
        try {
            guardkeeper.renameDatabase(navigationMenu.getList().getSelectionModel().getSelectedItem(), new File(path.getValue(), name.getValue()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Button getButton(String text, String img, EventHandler<ActionEvent> event) {
        Button btn = new Button(text);
        if (img != null) {
            btn.setGraphic(
                    new ImageView(
                            new Image(getClass().getResourceAsStream(img), 30, 30, true, true)
                    )
            );
        }
        btn.setOnAction(event);
        return btn;
    }

    private PathFile getPathFile(EventHandler<ActionEvent> event){
        PathFile pathFile = new PathFile();
        pathFile.setOnAction(event);
        return pathFile;
    }

    /**
     * Настройка навигационного меню
     * */
    private void navigationMenuConfig(){
        navigationMenu = new ListPane("Доступные БД", guardkeeper.getDatabases());
        ListView<File> list = navigationMenu.getList();
        list.setCellFactory(fileListView -> new ListCell());
        list.getSelectionModel().clearSelection();

        list.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            if(t1 != null){
                this.name.set(t1.getName());
                this.path.set(t1.getParent());
            }
        });
        list.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()){
                case ESCAPE:{
                    list.getSelectionModel().clearSelection();
                    this.name.set("");
                    this.path.set("");
                }
            }
        });

        setLeft(navigationMenu);
    }

    /**
     *
     * */
    private void openViewer(ActionEvent event) {

    }

    private void openSandbox(ActionEvent event) {

    }

    private void setStyle(){
        getStyleClass().add(Styleclasses.WINDOW);

        getStylesheets().add(getClass().getResource("/style/list.css").toExternalForm());
        getStylesheets().add(getClass().getResource("/style/window.css").toExternalForm());
    }

    /**
     * Настройка заголовка окна
     * */
    private void titleConfig(){
        title = new TitleWithoutMenu(stage, "Thoth");

        setTop(title);
    }
}
