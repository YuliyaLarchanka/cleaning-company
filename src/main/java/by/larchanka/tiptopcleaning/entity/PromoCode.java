package by.larchanka.tiptopcleaning.entity;

public class PromoCode {
    private long id;
    private String value;
    private byte discountPercentage;

    public PromoCode(){}

    public PromoCode(String value) {
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public byte getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(byte discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PromoCode promoCode = (PromoCode) o;

        if (id != promoCode.id) return false;
        if (discountPercentage != promoCode.discountPercentage) return false;
        return value != null ? value.equals(promoCode.value) : promoCode.value == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (int) discountPercentage;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PromoCode{");
        sb.append("id=").append(id);
        sb.append(", value='").append(value).append('\'');
        sb.append(", discountPercentage=").append(discountPercentage);
        sb.append('}');
        return sb.toString();
    }
}
