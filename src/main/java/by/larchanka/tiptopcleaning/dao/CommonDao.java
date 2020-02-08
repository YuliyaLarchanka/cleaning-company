package by.larchanka.tiptopcleaning.dao;

import java.sql.Connection;
import java.sql.Statement;

public interface CommonDao {
    void close(Statement statement, Connection connection);
}
