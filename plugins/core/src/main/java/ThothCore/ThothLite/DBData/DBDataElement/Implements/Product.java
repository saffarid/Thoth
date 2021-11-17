package ThothCore.ThothLite.DBData.DBDataElement.Implements;

import ThothCore.ThothLite.DBData.DBDataElement.Properties.Listed;
import ThothCore.ThothLite.DBData.DBDataElement.Properties.Storagable;

public class Product
        implements Storagable {

    private String article;
    private String name;
    private Listed type;
    private Double count;
    private Listed countType;
    private Listed adress;
    private String note;

    public Product() {
        this.article = "";
        this.name = "";
        this.type = null;
        this.note = "";
    }

    public Product(String article, String name, Listed type, Double count, Listed countType, Listed adress) {
        this.article = article;
        this.name = name;
        this.type = type;
        this.count = count;
        this.countType = countType;
        this.adress = adress;
    }

    public Product(String article, String name, Listed type, Double count, Listed countType, Listed adress , String note) {
        this.article = article;
        this.name = name;
        this.type = type;
        this.count = count;
        this.countType = countType;
        this.adress = adress;
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
    public String getId() {
        return article;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Listed getType() {
        return type;
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
    public void setType(Listed type) {

    }

    @Override
    public String toString() {
        return "Product{" +
                "article='" + article + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", note='" + note + '\'' +
                '}';
    }

    @Override
    public Double getCount() {
        return count;
    }

    @Override
    public void setCount(Double count) {
        this.count = count;
    }

    @Override
    public Listed getCountType() {
        return countType;
    }

    @Override
    public void setCountType(Listed countType) {
        this.countType = countType;
    }

    @Override
    public void setAdress(Listed adress) {
        this.adress = adress;
    }

    @Override
    public Listed getAdress() {
        return adress;
    }

}
