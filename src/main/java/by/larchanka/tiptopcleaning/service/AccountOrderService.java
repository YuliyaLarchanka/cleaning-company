package by.larchanka.tiptopcleaning.service;

import by.larchanka.tiptopcleaning.entity.AccountOrder;
import by.larchanka.tiptopcleaning.entity.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface AccountOrderService {
    /**
     *Validates account order and calls dao method
     *
     * @param order an {@code AccountOrder} object to validate
     * @return {@code Optional<AccountOrder>} object containing account order
     */
    Optional<AccountOrder> createAccountOrder(AccountOrder order) throws ServiceException;

    /**
     *Calls dao method
     *
     * @param userId a {@code long} value to pass into dao method
     * @return {@code List<AccountOrder>} object containing account orders
     */
    List<AccountOrder> findAccountOrdersByUserId(long userId) throws ServiceException;

    /**
     *Calls dao method
     *
     * @return {@code List<AccountOrder>} object containing account orders
     */
    List<AccountOrder> findAllAccountOrders() throws ServiceException;

    /**
     *Calls dao method
     *
     * @param id a {@code long} value to pass into dao method
     * @param status a {@code OrderStatus} object to pass into dao method
     * @return {@code boolean} value. True if changing was successful and false otherwise.
     */
    boolean changeAccountOrderStatus(long id, OrderStatus status) throws ServiceException;
}
