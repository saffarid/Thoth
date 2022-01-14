package tools;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Класс обёртка для упрощённого создания границы графических узлов.
 * @version 1.0
 * @author PavlovNO
 * */
public class BorderWrapper {

    private Color colorDefault = Color.valueOf("#707070");
    private final double borderWidthDefault = 0;

    private Border border;

    private Paint color = colorDefault;
    private BorderStrokeStyle style = BorderStrokeStyle.SOLID;
    private CornerRadii radii = CornerRadii.EMPTY;
    private double topWidth = borderWidthDefault;
    private double rightWidth = borderWidthDefault;
    private double bottomWidth = borderWidthDefault;
    private double leftWidth = borderWidthDefault;

    public BorderWrapper() {
        border = new Border(
                new BorderStroke(
                        colorDefault, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(borderWidthDefault)
                )
        );
    }

    /**
     * Функция добавляет границу
     * */
    public BorderWrapper addBorder(double width){
        topWidth = width;
        rightWidth = width;
        bottomWidth = width;
        leftWidth = width;
        return this;
    }

    /**
     * Функция добавляет верхнюю границу
     * */
    public BorderWrapper addTopBorder(double width){
        topWidth = width;
        return this;
    }

    /**
     * Функция добавляет правую границу
     * */
    public BorderWrapper addRightBorder(double width){
        rightWidth = width;
        return this;
    }

    /**
     * Функция добавляет нижнюю границу
     * */
    public BorderWrapper addBottomBorder(double width){
        bottomWidth = width;
        return this;
    }

    /**
     * Функция добавляет левую границу
     * */
    public BorderWrapper addLeftBorder(double width){
        leftWidth = width;
        return this;
    }

    /**
     * Функция возвращает объект границы с внесёнными изменениями
     * */
    public Border commit(){
        border = new Border(
                new BorderStroke(
                        color, style, radii, new BorderWidths(topWidth, rightWidth, bottomWidth, leftWidth)
                )
        );
        return border;
    }

    /**
     * Функция удаляет верхнюю границу
     * */
    public BorderWrapper removeTopBorder(){
        topWidth = 0;
        return this;
    }

    /**
     * Функция удаляет правую границу
     * */
    public BorderWrapper removeRightBorder(){
        rightWidth = 0;
        return this;
    }

    /**
     * Функция удаляет нижнюю границу
     * */
    public BorderWrapper removeBottomBorder(){
        bottomWidth = 0;
        return this;
    }

    /**
     * Функция удаляет левую границу
     * */
    public BorderWrapper removeLeftBorder(){
        leftWidth = 0;
        return this;
    }

    /**
     * Функция устанавливает цвет границы
     * */
    public BorderWrapper setColor(Paint color) {
        this.color = color;
        return this;
    }

    /**
     * Функция устанавливает радиус границы
     * */
    public BorderWrapper setRadius(CornerRadii radii){
        this.radii = radii;
        return this;
    }

    /**
     * Функция устанавливает стиль границы
     * */
    public BorderWrapper setStyle(BorderStrokeStyle style){
        this.style = style;
        return this;
    }

}
