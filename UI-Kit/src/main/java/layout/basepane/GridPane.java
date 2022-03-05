package layout.basepane;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

public class GridPane
        extends javafx.scene.layout.GridPane {

    public GridPane() {
        super();
    }

    /**
     * Функция добавляет колонку с заданным параметром расширения колонки.
     *
     * @param priority параметр расширения колонки.
     * @return объект текущей панели компоновки
     */
    public GridPane addColumn(Priority priority) {
        ColumnConstraints row = new ColumnConstraints();
        row.setHgrow(priority);
        getColumnConstraints().add(row);
        return this;
    }

    /**
     * Функция добавляет колонку с заданным параметром расширения колонки.
     *
     * @param priority параметр расширения колонки.
     * @param hPos     выравнивание по горизонтали
     * @return объект текущей панели компоновки
     */
    public GridPane addColumn(Priority priority, HPos hPos) {
        ColumnConstraints row = new ColumnConstraints();
        row.setHgrow(priority);
        row.setHalignment(hPos);
        getColumnConstraints().add(row);
        return this;
    }

    /**
     * Функция добавляет колонку с заданной шириной колонки.
     *
     * @param width параметр расширения колонки.
     * @param hPos     выравнивание по горизонтали
     * @return объект текущей панели компоновки
     */
    public GridPane addColumn(Double width, HPos hPos) {
        ColumnConstraints row = new ColumnConstraints(width);
        row.setHalignment(hPos);
        getColumnConstraints().add(row);
        return this;
    }

    /**
     * Функция добавляет колонку с заданными параметрами.
     *
     * @param minWidth  минимальная ширина колонки
     * @param prefWidth предпочтительная ширина колонки
     * @param maxWidth  максимальная ширина колонки
     * @param priority  параметр расширения колонки
     * @param hPos      выравнивание по горизонтали
     * @param fillWidth растяжение элемента по всей ширине колонки
     */
    public GridPane addColumn(double minWidth, double prefWidth, double maxWidth, Priority priority, HPos hPos, boolean fillWidth) {
        getColumnConstraints().add(
                new ColumnConstraints(minWidth, prefWidth, maxWidth, priority, hPos, fillWidth)
        );
        return this;
    }

    /**
     * Функция добавляет строку с заданным параметром расширения строки.
     *
     * @param priority параметр расширения строки.
     * @return объект текущей панели компоновки
     */
    public GridPane addRow(Priority priority) {
        RowConstraints row = new RowConstraints();
        row.setVgrow(priority);
        row.setFillHeight(true);
        getRowConstraints().add(row);
        return this;
    }

    /**
     * Функция добавляет строку с заданным параметром расширения строки.
     *
     * @param priority параметр расширения строки.
     * @param vPos     выравнивание по вертикали
     * @return объект текущей панели компоновки
     */
    public GridPane addRow(Priority priority, VPos vPos) {
        RowConstraints row = new RowConstraints();
        row.setVgrow(priority);
        row.setValignment(vPos);
        getRowConstraints().add(row);
        return this;
    }

    /**
     * Функция добавляет строку с заданной высотой строки.
     *
     * @param height параметр расширения строки.
     * @param vPos     выравнивание по вертикали
     * @return объект текущей панели компоновки
     */
    public GridPane addRow(Double height, VPos vPos) {
        RowConstraints row = new RowConstraints(height);
        row.setValignment(vPos);
        getRowConstraints().add(row);
        return this;
    }

    /**
     * Функция добавляет строку с заданными параметрами.
     *
     * @param minHeight  минимальная высота строки
     * @param prefHeight предпочтительная высота строки
     * @param maxHeight  максимальная высота строки
     * @param priority   параметр расширения строки
     * @param vPos       выравнивание по вертикали
     * @param fillHeight растяжение элемента по всей высоте строки
     */
    public GridPane addRow(double minHeight, double prefHeight, double maxHeight, Priority priority, VPos vPos, boolean fillHeight) {
        getRowConstraints().add(
                new RowConstraints(minHeight, prefHeight, maxHeight, priority, vPos, fillHeight)
        );
        return this;
    }

    public GridPane addOnlyRow() {

        RowConstraints only = new RowConstraints();

        only.setVgrow(Priority.ALWAYS);
        only.setValignment(VPos.CENTER);
        only.setFillHeight(true);

        getRowConstraints().add(only);

        return this;
    }


}
