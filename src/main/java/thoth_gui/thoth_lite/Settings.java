package thoth_gui.thoth_lite;

import controls.Twin;
import javafx.beans.property.SimpleObjectProperty;
import layout.basepane.BorderPane;
import layout.basepane.VBox;
import org.json.simple.parser.ParseException;
import thoth_core.thoth_lite.config.Configuration;
import thoth_gui.config.Config;
import thoth_gui.thoth_lite.components.controls.Button;
import thoth_gui.thoth_lite.components.controls.Label;
import thoth_gui.thoth_lite.components.controls.ToolsPane;
import thoth_gui.thoth_lite.components.scenes.ThothSceneImpl;
import thoth_gui.thoth_styleconstants.svg.Images;
import org.json.simple.JSONObject;
import thoth_core.thoth_lite.exceptions.NotContainsException;
import thoth_core.thoth_lite.ThothLite;
import javafx.geometry.Insets;
import javafx.scene.Node;
import layout.basepane.HBox;
import thoth_gui.Apply;
import thoth_gui.Cancel;
import tools.SvgWrapper;
import window.Closeable;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Settings
        extends ThothSceneImpl
        implements Apply
        , Cancel {

    private final String TITLE_TEXT = "settings";

    private static Settings instance;

    /**
     * Конфигурация ядра
     * */
    private Configuration configThothCore;

    /**
     * Конфигурация графической части
     * */
    private Configuration configGui;

    private List<JSONObject> configJSONs;

    public Settings() {
        super();

        //Запрос конфигураций
        try {
            configThothCore = ThothLite.getInstance().getConfig();
            configGui = Config.getInstance();

            configJSONs = new LinkedList<>();
            configJSONs.add( configGui.getConfig());
            configJSONs.add( configThothCore.getConfig());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (NotContainsException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        content = new SimpleObjectProperty<>( createContentNode() );
        tools   = new SimpleObjectProperty<>( createToolsNode()   );

    }

    @Override
    public void apply() {

    }

    @Override
    public void cancel() {

    }

    @Override
    public void close() {

    }

    @Override
    protected Node createToolsNode() {

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(0, 0, 0, 5));
        hBox.getChildren().addAll(
                Button.getInstance(
                        SvgWrapper.getInstance(Images.CHECKMARK(), svgWidthTool, svgHeightTool, svgViewBoxWidthTool, svgViewBoxHeightTool),
                        event -> apply()
                ),
                Button.getInstance(
                        SvgWrapper.getInstance(Images.CLOSE(), svgWidthTool, svgHeightTool, svgViewBoxWidthTool, svgViewBoxHeightTool),
                        event -> cancel()
                )
        );

        toolsNode =
                new ToolsPane(TITLE_TEXT)
                        .addAdditional(hBox)
        ;


        return toolsNode;
    }

    @Override
    protected Node createContentNode() {
        contentNode = new BorderPane();
        VBox vBox = new VBox();
        //Проходим по все jsonам и строим на основе их контент
        for(HashMap<String, Object> json : configJSONs){
            parseJson(json, vBox);
        }
        contentNode.setCenter( vBox );
        return contentNode;
    }

    private void parseJson(
            HashMap<String, Object> json,
            VBox vBox
    ){
        for(Object k : json.keySet()){
            String key = String.valueOf(k);
            //Игнорируем конфигурацию окна
            if(key.equals(Config.KEYS.WINDOW.getKey())) continue;
            Object value = json.get(k);
            if(value instanceof JSONObject){
                //Если value = json-объект, то добавляем компонент headline и парсим json дальше на предмет выявления компонентов
                vBox.getChildren().add(Label.getInstanse(key));
                parseJson((HashMap<String, Object>) value, vBox);
            } else {
                //Создаем компоненты
                Twin twin = new Twin();
                twin.setFirstNode(Label.getInstanse(key));

                if(value instanceof String){
                    twin.setSecondNode(Label.getInstanse("String"));
                } else if(value instanceof Boolean){
                    twin.setSecondNode(Label.getInstanse("Boolean"));
                } else{
                    twin.setSecondNode(Label.getInstanse("Number"));
                }

                vBox.getChildren().add(twin);
            }
        }
    }

    public static Settings getInstance(){
        if(instance == null){
            instance = new Settings();
        }
        return instance;
    }

    @Override
    public void setCloseable(Closeable closeable) {

    }

}
