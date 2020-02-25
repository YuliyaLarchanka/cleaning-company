package by.larchanka.tiptopcleaning.dao.impl;

import by.larchanka.tiptopcleaning.connection.ConnectionPool;
import by.larchanka.tiptopcleaning.connection.ConnectionPoolException;
import by.larchanka.tiptopcleaning.dao.CatalogItemDao;
import by.larchanka.tiptopcleaning.dao.DaoException;
import by.larchanka.tiptopcleaning.entity.CatalogItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_CATALOG_ITEM_ID;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_CATALOG_ITEM_IMAGE_NAME;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_CATALOG_ITEM_MULTIPLE_SUPPORTED;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_CATALOG_ITEM_NAME;
import static by.larchanka.tiptopcleaning.dao.SqlParameterConstant.SQL_CATALOG_ITEM_PRICE;

public class CatalogItemDaoImpl implements CatalogItemDao {
    private static final Logger logger = LogManager.getLogger();

    private static final String GET_CATALOG_ITEM_LIST =
            "SELECT catalog_item_id, name, price, multiple_supported, image_name FROM catalog_item WHERE is_active = 1";

    private static final String GET_CATALOG_ITEM_LIST_BY_IDS =
            "SELECT catalog_item_id, name, price, multiple_supported, image_name FROM catalog_item WHERE is_active = 1 AND catalog_item_id IN (";

    private static final String DELETE_CATALOG_ITEM_BY_ID_COMMAND =
            "UPDATE catalog_item SET is_active = 0 WHERE catalog_item_id = ?";

    private static final String CREATE_CATALOG_ITEM =
            "INSERT INTO catalog_item (name, price, multiple_supported, is_active, image_name) VALUES (?, ?, ?, ?, ?)";

    private final static String UPDATE_CATALOG_ITEM_COMMAND = "UPDATE catalog_item SET name = ?," +
            " price = ?, multiple_supported = ?, image_name = ?" +
            " WHERE catalog_item_id = ?";

    @Override
    public List<CatalogItem> getCatalogItemList() throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_CATALOG_ITEM_LIST)) {

            ResultSet resultSet = statement.executeQuery();
            return parseResultSet(resultSet);
        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<CatalogItem> getCatalogItemListByIds(List<Long> ids) throws DaoException {
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }

        StringBuilder sbSql = new StringBuilder(GET_CATALOG_ITEM_LIST_BY_IDS);
        for (int i = 0; i < ids.size(); i++) {
            if (i > 0) {
                sbSql.append(",");
            }
            sbSql.append(" ?");
        }
        sbSql.append(" )");

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sbSql.toString())) {
            for (int i = 0; i < ids.size(); i++) {
                statement.setLong(i + 1, ids.get(i));
            }

            ResultSet resultSet = statement.executeQuery();

            return parseResultSet(resultSet);
        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    private List<CatalogItem> parseResultSet(ResultSet resultSet) throws SQLException {
        List<CatalogItem> catalogItemList = new ArrayList<>();
        while (resultSet.next()) {
            long id = resultSet.getLong(SQL_CATALOG_ITEM_ID);
            String name = resultSet.getString(SQL_CATALOG_ITEM_NAME);
            BigDecimal price = BigDecimal.valueOf(Double.parseDouble(resultSet.getString(SQL_CATALOG_ITEM_PRICE)));
            boolean multipleSupported = resultSet.getBoolean(SQL_CATALOG_ITEM_MULTIPLE_SUPPORTED);
            String imageName = resultSet.getString(SQL_CATALOG_ITEM_IMAGE_NAME);

            CatalogItem catalogItem = new CatalogItem();
            catalogItem.setId(id);
            catalogItem.setName(name);
            catalogItem.setPrice(price);
            catalogItem.setMultipleSupported(multipleSupported);
            catalogItem.setImageName(imageName);

            catalogItemList.add(catalogItem);
        }
        return catalogItemList;
    }

    @Override
    public boolean deleteCatalogItemById(long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_CATALOG_ITEM_BY_ID_COMMAND)) {
            statement.setLong(1, id);
            int resultInt = statement.executeUpdate();
            return resultInt > 0;
        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    public Optional<CatalogItem> createCatalogItem(CatalogItem catalogItem) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_CATALOG_ITEM)) {

            statement.setString(1, catalogItem.getName());
            statement.setBigDecimal(2, catalogItem.getPrice());
            statement.setBoolean(3, catalogItem.isMultipleSupported());
            statement.setByte(4, (byte) 1);
            statement.setString(5, catalogItem.getImageName());

            statement.executeUpdate();
            return Optional.of(catalogItem);
        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException();
        }
    }

    public Optional<CatalogItem> updateCatalogItem(CatalogItem catalogItem) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CATALOG_ITEM_COMMAND)) {

            int index = 1;
            statement.setString(index++, catalogItem.getName());
            statement.setBigDecimal(index++, catalogItem.getPrice());
            statement.setBoolean(index++, catalogItem.isMultipleSupported());
            statement.setString(index++, catalogItem.getImageName());
            statement.setLong(index, catalogItem.getId());
            statement.executeUpdate();

            return Optional.of(catalogItem);

        } catch (ConnectionPoolException | SQLException e) {
            logger.error(e);
            throw new DaoException();
        }
    }
}
