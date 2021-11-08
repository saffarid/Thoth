package ThothCore.ThothLite;

import ThothCore.ThothLite.DBLiteStructure.StructureDescription;

public enum ListedTables{
    COUNT_TYPES(StructureDescription.CountTypes.TABLE_NAME),
    CURRENCIES(StructureDescription.Currency.TABLE_NAME),
    INCOMES_TYPES(StructureDescription.IncomeTypes.TABLE_NAME),
    ORDER_STATUS(StructureDescription.OrderStatus.TABLE_NAME),
    PARTNERS(StructureDescription.Partners.TABLE_NAME),
    PRODUCT_TYPES(StructureDescription.ProductTypes.TABLE_NAME);

    private String table;

    ListedTables(String table) {
        this.table = table;
    }

    public String getTableName() {
        return table;
    }
}
