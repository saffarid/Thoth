module thoth {
    exports Database;
    exports ThothCore.ThothLite;
    exports ThothCore.ThothLite.DBData;
    exports ThothCore.ThothLite.DBData.Tables;
    exports ThothCore.ThothLite.DBData.DBDataElement;
    exports ThothCore.ThothLite.DBData.DBDataElement.Properties;
    exports ThothCore.ThothLite.DBData.DBDataElement.Implements;

    requires java.sql;

}