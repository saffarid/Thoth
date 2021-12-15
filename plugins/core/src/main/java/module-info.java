module thoth {
    exports Database;
    exports ThothCore.ThothLite;
    exports ThothCore.ThothLite.DBLiteStructure;
    exports ThothCore.ThothLite.DBData.Tables;
    exports ThothCore.ThothLite.DBData.DBDataElement.Properties;
    exports ThothCore.ThothLite.DBData.DBDataElement.Implements;
    exports ThothCore.ThothLite.Exceptions;
    exports ThothCore.ThothLite.Config;

    requires java.sql;
    requires json.simple;

}