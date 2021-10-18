package ThothCore.ThothLite.DBData.DBDataElement.Implements;

import ThothCore.ThothLite.DBData.DBDataElement.Listed;
import ThothCore.ThothLite.DBData.DBDataElement.Storagable;

public class Product
        implements Storagable {

    private String article;
    private String name;
    private Listed type;
    private Double price;
    private Currency currency;
    private String note;

    public Product(String article, String name, Listed type, Double price, Currency currency) {
        this.article = article;
        this.name = name;
        this.type = type;
        this.price = price;
        this.currency = currency;
    }

    public Product(String article, String name, Listed type, Double price, Currency currency, String note) {
        this.article = article;
        this.name = name;
        this.type = type;
        this.price = price;
        this.currency = currency;
        this.note = note;
    }

    public boolean equals(Product obj) {
        if( (this.article != null && !this.article.equals(""))
                && (obj.getId() != null && !obj.getId().equals("")) ) {
            return this.article.equals(obj.getId());
        }else{
            return this.name.equals(obj.getName());
        }
    }

    @Override
    public String getCurrency() {
        return currency.getCurrency();
    }

    @Override
    public String getId() {
        return article;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Double getPrice() {
        return price;
    }

    @Override
    public String getType() {
        return type.getValue();
    }

    @Override
    public void setCurrency(String currency) {

    }

    @Override
    public void setId(String id) {
        this.article = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public void setType(String type) {

    }
}
