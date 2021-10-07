package ThothGUI.Guardkeeper;

import Main.ChangeScreen;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import ThothCore.Thoth.Guardkeeper.DataBaseException.DatabaseExistsException;
import controls.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import layout.custompane.ListPane;
import layout.basepane.VBox;
import window.SecondaryWindow;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import static Main.Main.LOG;

public class Guardkeeper extends SecondaryWindow {

    /**
     * Окно, в котором отображается представление
     * */
    private Stage stage;

    /**
     * Навигационное меню. В него записываются пользовательские БД
     */
    private ListPane<File> navigationMenu;

    /**
     * Объект выбора взаимодействия с Thoth
     * */
    private ThothCore.Thoth.Guardkeeper.Guardkeeper guardkeeper;

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

    /**
     * Список пользовательских таблиц
     * */
    private SimpleListProperty<File> databases;

    private ChangeScreen changeScreen;

    public Guardkeeper(Stage stage, ChangeScreen changeScreen) throws SQLException, ClassNotFoundException {
        super(stage, "Thoth");
        guardkeeper = new ThothCore.Thoth.Guardkeeper.Guardkeeper();
        this.stage = stage;
        this.changeScreen = changeScreen;
        databases = new SimpleListProperty<File>(FXCollections.observableList(guardkeeper.getDatabases()));

        name = new SimpleStringProperty("");
        path = new SimpleStringProperty("");
        template = new SimpleStringProperty("");

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
        vBox.setPadding(new Insets(52, 0, 0, 5));

        Button sandbox = getButton("SANDBOX", null, this::openSandbox);
        Button viewer = getButton("VIEWER", thoth_styleconstants.Image.EYE, this::openViewer);
        Button edit = getButton("РЕДАКТИРОВАТЬ", thoth_styleconstants.Image.EDIT, this::editUserDb);
        Button create = getButton("СОЗДАТЬ", null, this::createUserDb);
        Button remove = getButton("УДАЛИТЬ", null, this::removeUserDb);

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
        if(!path.get().equals("") && !name.get().equals("")) {
            try {
                guardkeeper.createNewDatabase(new File(path.get(), name.get()), new File(template.get()));
                refresh();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (DatabaseExistsException e) {
                System.out.println("БД уже существует");
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
            refresh();
        } catch (IOException e) {
            LOG.log(Level.INFO,"Не найден файл " + e.getMessage());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Функция общей инициализации компонента Button
     * */
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

    /**
     * Функция общей инициализации компонента PathFile
     * */
    private PathFile getPathFile(EventHandler<ActionEvent> event){
        PathFile pathFile = new PathFile();
        pathFile.setOnAction(event);
        return pathFile;
    }

    /**
     * Настройка навигационного меню
     * */
    private void navigationMenuConfig(){
        navigationMenu = new ListPane("Доступные БД", databases);
        ListView<File> list = navigationMenu.getList();
        list.itemsProperty().bind(databases);
        refresh();
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
        File selectedItem = navigationMenu.getList().getSelectionModel().getSelectedItem();
        LOG.log(Level.INFO, "Запуск Viewer для пользовательской БД " + selectedItem);
        try {
            guardkeeper.openViewer(selectedItem);
            changeScreen.changeScreen(selectedItem);
        } catch (NoSuchFileException e) {
            LOG.log(Level.INFO, "Не найден файл " + e.getMessage());
        }
    }

    private void openSandbox(ActionEvent event) {
        File selectedItem = navigationMenu.getList().getSelectionModel().getSelectedItem();
        LOG.log(Level.INFO, "Запуск SandBox для пользовательской БД " + selectedItem);
        try {
            guardkeeper.openSandBox(selectedItem);
        } catch (NoSuchFileException e) {
            LOG.log(Level.INFO, "Не найден файл " + e.getMessage());
        }
    }

    private void setStyle(){
        getStylesheets().add(getClass().getResource("/style/list.css").toExternalForm());
    }

    /**
     * Функция обновляет список пользовательских БД
     * */
    private void refresh() {
        List<File> databases = guardkeeper.getDatabases();
        Collections.sort(databases);

        navigationMenu.getList().setCellFactory(null);
        this.databases.setValue(FXCollections.observableList(databases));
        navigationMenu.getList().setCellFactory(fileListView -> new ListCell());
    }

    /**
     * Функция удаляет пользовательскую БД
     * */
    private void removeUserDb(ActionEvent event) {
        try {
            guardkeeper.removeDatabase(navigationMenu.getList().getSelectionModel().getSelectedItem());
            refresh();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            LOG.log(Level.INFO,"Не найден файл " + e.getMessage());
        }
    }
}
