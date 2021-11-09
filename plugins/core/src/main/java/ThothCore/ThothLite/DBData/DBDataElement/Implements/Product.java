package ThothCore.ThothLite.DBData.DBDataElement.Implements;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storagable;

public class Product
        implements Storagable {

    private String article;
    private String name;
    private Listed type;
    private Double price;
    private Currency currency;
    private String note;

    public Product() {
        this.article = "";
        this.name = "";
        this.type = null;
        this.price = 0.0;
        this.currency = null;
        this.note = "";
    }

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
    public Currency getCurrency() {
        return currency;
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
    public Listed getType() {
        return type;
    }

    @Override
    public void setCurrency(Currency currency) {

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
    public void setType(Listed type) {

    }

    @Override
    public String toString() {
        return "Product{" +
                "article='" + article + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", price=" + price +
                ", currency=" + currency +
                ", note='" + note + '\'' +
                '}';
    }
}
