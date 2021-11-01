package ThothCore.ThothLite.DBData.DBDataElement.Implements;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;

public class ListElement
        implements Listed {

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
    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
    @Override
    public void setValue(String value) {
        this.value = value;
    }
}
