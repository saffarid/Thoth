package ThothCore.ThothLite;

import ThothCore.ThothLite.DBLiteStructure.StructureDescription;

public enum DataTables{
    LISTED(""),
    ORDERABLE(StructureDescription.Orders.TABLE_NAME),
    PROJECTABLE(StructureDescription.ProjectsList.TABLE_NAME),
    PURCHASABLE(StructureDescription.Purchases.TABLE_NAME),
    STORAGABLE(StructureDescription.Products.TABLE_NAME),
    STORING(StructureDescription.Storage.TABLE_NAME);

    private String tableName;

    DataTables(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

}
