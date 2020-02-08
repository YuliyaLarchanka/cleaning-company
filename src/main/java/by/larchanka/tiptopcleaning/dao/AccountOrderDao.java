package by.larchanka.tiptopcleaning.dao;

import by.larchanka.tiptopcleaning.entity.AccountOrder;
import by.larchanka.tiptopcleaning.entity.OrderItem;
import by.larchanka.tiptopcleaning.entity.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface AccountOrderDao {
    Optional<AccountOrder> createAccountOrder(AccountOrder accountOrder, List<OrderItem> orderItemList) throws DaoException;

    List<AccountOrder> findAccountOrdersByUserId(long userId) throws DaoException;

    List<AccountOrder> findAllAccountOrders() throws DaoException;

    boolean changeAccountOrderStatus(long id, OrderStatus status) throws DaoException;
}
