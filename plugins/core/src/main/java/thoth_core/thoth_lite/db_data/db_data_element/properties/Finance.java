package thoth_core.thoth_lite.db_data.db_data_element.properties;

public interface Finance 
    extends Identifiable
{
    String getCurrency();
    void setCurrency(String currency);
    Double getCourse();
    void setCourse(Double course);
}
