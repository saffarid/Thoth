package ThothCore.ThothLite.DBData.DBDataElement;

import ThothCore.ThothLite.DBData.Identifiable;

public class ListElement
        implements Identifiable {

    private String id;
    private String value;

    public ListElement(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public boolean equals(ListElement obj) {
        return (this.id.equals(obj.getId()) &&
                this.value.equals(obj.getValue()));
    }

    @Override
    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
