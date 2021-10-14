package ThothCore.ThothLite;

import ThothCore.ThothLite.DBData.Identifiable;
import ThothCore.ThothLite.DBData.Nameable;
import ThothCore.ThothLite.DBData.Pricing;
import ThothCore.ThothLite.DBData.Typable;

/**
 * Объект, который возможно поместить вхранимую ячейку
 * */
public interface Storagable
        extends Identifiable, Nameable, Typable, Pricing {
}
