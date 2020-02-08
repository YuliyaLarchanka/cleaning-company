package by.larchanka.tiptopcleaning.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class AccountOrder {
    private long id;
    private User user;
    private PromoCode promoCode;
    private BigDecimal totalCost;
    private PaymentMethod paymentMethod;
    private OrderStatus orderStatus;
    private Timestamp dateTime;
    private List<OrderItem> orderItemList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PromoCode getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(PromoCode promoCode) {
        this.promoCode = promoCode;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountOrder that = (AccountOrder) o;

        if (id != that.id) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (promoCode != null ? !promoCode.equals(that.promoCode) : that.promoCode != null) return false;
        if (totalCost != null ? !totalCost.equals(that.totalCost) : that.totalCost != null) return false;
        if (paymentMethod != that.paymentMethod) return false;
        if (orderStatus != that.orderStatus) return false;
        if (dateTime != null ? !dateTime.equals(that.dateTime) : that.dateTime != null) return false;
        return orderItemList != null ? orderItemList.equals(that.orderItemList) : that.orderItemList == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (promoCode != null ? promoCode.hashCode() : 0);
        result = 31 * result + (totalCost != null ? totalCost.hashCode() : 0);
        result = 31 * result + (paymentMethod != null ? paymentMethod.hashCode() : 0);
        result = 31 * result + (orderStatus != null ? orderStatus.hashCode() : 0);
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        result = 31 * result + (orderItemList != null ? orderItemList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AccountOrder{");
        sb.append("id=").append(id);
        sb.append(", user=").append(user);
        sb.append(", promoCode=").append(promoCode);
        sb.append(", totalCost=").append(totalCost);
        sb.append(", paymentMethod=").append(paymentMethod);
        sb.append(", orderStatus=").append(orderStatus);
        sb.append(", dateTime=").append(dateTime);
        sb.append(", orderItemList=").append(orderItemList);
        sb.append('}');
        return sb.toString();
    }
}
