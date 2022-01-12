package thoth_gui.thoth_styleconstants.svg;

import javafx.scene.Group;

public enum Images {
    ARROW_RIGHT(ArrowRight.getInstance()),
    CHECKMARK(Checkmark.getInstance()),
    EDIT(Edit.getInstance()),
    HOME(Home.getInstance()),
    LIST(List.getInstance()),
    PLUS(Plus.getInstance()),
    POINT(Point.getInstance()),
    PRODUCT(Product.getInstance()),
    PURCHASE(Purchase.getInstance()),
    REFRESH(Refresh.getInstance()),
    TRADINGDOWN(TradingDown.getInstance()),
    TRADINGUP(TradingUp.getInstance()),
    TRASH(Trash.getInstance())
    ;

    private Group svg;
    Images(Group svg) {
        this.svg = svg;
    }
    public Group getSvg() {
        return svg;
    }
}
