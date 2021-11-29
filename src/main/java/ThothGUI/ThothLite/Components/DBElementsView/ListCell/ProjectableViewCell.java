package ThothGUI.ThothLite.Components.DBElementsView.ListCell;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Projectable;
import ThothGUI.ThothLite.Components.DBElementsView.ListCell.IdentifiableViewCell;
import ThothGUI.thoth_styleconstants.Image;

public class ProjectableViewCell
        extends IdentifiableViewCell {

    protected ProjectableViewCell(Projectable project) {
        super(Image.PROJECT,
                project.getName(),
                project.getName(),  //Дата создания
                project.getName()   //Тип проекта
        );
    }

}
