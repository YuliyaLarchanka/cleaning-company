package by.larchanka.tiptopcleaning.service;

import by.larchanka.tiptopcleaning.entity.AccountOrder;
import by.larchanka.tiptopcleaning.entity.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface AccountOrderService {
    Optional<AccountOrder> createAccountOrder(AccountOrder order) throws ServiceException;

    List<AccountOrder> findAccountOrdersByUserId(long userId) throws ServiceException;

    List<AccountOrder> findAllAccountOrders() throws ServiceException;

    boolean changeAccountOrderStatus(long id, OrderStatus status) throws ServiceException;
}
