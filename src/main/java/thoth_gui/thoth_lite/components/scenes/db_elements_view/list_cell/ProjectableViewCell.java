package thoth_gui.thoth_lite.components.scenes.db_elements_view.list_cell;

import thoth_core.thoth_lite.db_data.db_data_element.properties.Projectable;
import thoth_gui.thoth_styleconstants.Image;

public class ProjectableViewCell
        extends IdentifiableViewCell {

    protected ProjectableViewCell(Projectable project) {
        super(
                null,
                project.getName(),
                project.getName(),  //Дата создания
                project.getName()   //Тип проекта
        );
    }

}
