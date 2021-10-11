package ThothCore.ThothLite.DBData.DBDataElement;

import ThothCore.ThothLite.DBData.Identifiable;

public class Product
        implements Identifiable {

    private String id;
    private String article;
    private String name;
    private ListElement type;
    private Double price;
    private Currency currency;
    private String note;

    public Product(String id, String article, String name, ListElement type, Double price, Currency currency) {
        this.id = id;
        this.article = article;
        this.name = name;
        this.type = type;
        this.price = price;
        this.currency = currency;
    }

    public Product(String id, String article, String name, ListElement type, Double price, Currency currency, String note) {
        this.id = id;
        this.article = article;
        this.name = name;
        this.type = type;
        this.price = price;
        this.currency = currency;
        this.note = note;
    }

    public boolean equals(Product obj) {
        if( (this.article != null && !this.article.equals(""))
                && (obj.getArticle() != null && !obj.getArticle().equals("")) ) {
            return this.article.equals(obj.getArticle());
        }else{
            return this.name.equals(obj.getName());
        }
    }

    public String getArticle() {
        return article;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public ListElement getType() {
        return type;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setType(ListElement type) {
        this.type = type;
    }
}
