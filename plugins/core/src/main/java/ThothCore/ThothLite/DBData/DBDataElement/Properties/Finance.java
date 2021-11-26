package ThothCore.ThothLite.DBData.DBDataElement.Properties;

public interface Finance 
    extends Identifiable
{
    String getCurrency();
    void setCurrency(String currency);
    Double getCourse();
    void setCourse(Double course);
}
