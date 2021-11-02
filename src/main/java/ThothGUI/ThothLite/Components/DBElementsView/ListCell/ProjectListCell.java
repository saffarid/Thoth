package ThothGUI.ThothLite.Components.DBElementsView.ListCell;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Projectable;
import thoth_styleconstants.Image;

public class ProjectListCell
        extends IdentifiableListCell {

    protected ProjectListCell(Projectable project) {
        super(Image.PROJECT,
                project.getName(),
                project.getName(),  //Дата создания
                project.getName()   //Тип проекта
        );
    }

}
