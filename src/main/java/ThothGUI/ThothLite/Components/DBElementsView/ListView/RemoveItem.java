package ThothGUI.ThothLite.Components.DBElementsView.ListView;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Identifiable;

@FunctionalInterface
public interface RemoveItem {

    void removeItem(Identifiable identifiable);

}
