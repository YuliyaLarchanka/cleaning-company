package by.larchanka.tiptopcleaning.entity;

import java.math.BigDecimal;

public class CatalogItem {
    private long id;
    private String name;
    private BigDecimal price;
    private boolean multipleSupported;
    private String imageName;

    public CatalogItem(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isMultipleSupported() {
        return multipleSupported;
    }

    public void setMultipleSupported(boolean multipleSupported) {
        this.multipleSupported = multipleSupported;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CatalogItem that = (CatalogItem) o;

        if (id != that.id) return false;
        if (multipleSupported != that.multipleSupported) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        return imageName != null ? imageName.equals(that.imageName) : that.imageName == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (multipleSupported ? 1 : 0);
        result = 31 * result + (imageName != null ? imageName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CatalogItem{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", price=").append(price);
        sb.append(", multipleSupported=").append(multipleSupported);
        sb.append(", imageName='").append(imageName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
