package by.larchanka.tiptopcleaning.entity;

public class OrderItem {
    private long id;
    private CatalogItem catalogItem;
    private AccountOrder accountOrder;
    private byte amount;

    public OrderItem(){}

    public OrderItem(long catalogItemId, byte amount){
        catalogItem = new CatalogItem();
        catalogItem.setId(catalogItemId);
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CatalogItem getCatalogItem() {
        return catalogItem;
    }

    public void setCatalogItem(CatalogItem catalogItem) {
        this.catalogItem = catalogItem;
    }

    public AccountOrder getAccountOrder() {
        return accountOrder;
    }

    public void setAccountOrder(AccountOrder accountOrder) {
        this.accountOrder = accountOrder;
    }

    public byte getAmount() {
        return amount;
    }

    public void setAmount(byte amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItem orderItem = (OrderItem) o;

        if (id != orderItem.id) return false;
        if (amount != orderItem.amount) return false;
        if (catalogItem != null ? !catalogItem.equals(orderItem.catalogItem) : orderItem.catalogItem != null)
            return false;
        return accountOrder != null ? accountOrder.equals(orderItem.accountOrder) : orderItem.accountOrder == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (catalogItem != null ? catalogItem.hashCode() : 0);
        result = 31 * result + (accountOrder != null ? accountOrder.hashCode() : 0);
        result = 31 * result + (int) amount;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderItem{");
        sb.append("id=").append(id);
        sb.append(", catalogItem=").append(catalogItem);
        sb.append(", accountOrder=").append(accountOrder);
        sb.append(", amount=").append(amount);
        sb.append('}');
        return sb.toString();
    }
}
