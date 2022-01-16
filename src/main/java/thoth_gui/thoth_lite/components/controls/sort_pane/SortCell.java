package thoth_gui.thoth_lite.components.controls.sort_pane;

import controls.ListCell;
import org.json.simple.parser.ParseException;
import thoth_gui.config.Config;

import java.io.IOException;

public class SortCell
        extends ListCell<SortBy> {

    public SortCell() {
        super();
    }

    @Override
    protected void updateItem(SortBy t, boolean b) {
        if (t != null) {
            try {
                super.updateItem(t, b);
                fontProperty().bind(Config.getInstance().getFont().fontProperty());
                setText(t.getSortName());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
