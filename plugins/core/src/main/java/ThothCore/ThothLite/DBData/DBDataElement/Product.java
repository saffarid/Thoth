package ThothCore.ThothLite.DBData.DBDataElement;

public class Product {

    private String article;
    private String name;
    private String type;
    private Double price;
    private String currency;
    private String note;

    public Product(String article, String name, String type, Double price, String currency) {
        this.article = article;
        this.name = name;
        this.type = type;
        this.price = price;
        this.currency = currency;
    }

    public Product(String article, String name, String type, Double price, String currency, String note) {
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

    public String getCurrency() {
        return currency;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setType(String type) {
        this.type = type;
    }
}
