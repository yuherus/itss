package controller;

import java.sql.SQLException;
import java.util.List;

public interface CRUDController<T> {
    List<T> getAll() throws SQLException;
    T getById(int id) throws SQLException;
    void add(T t) throws SQLException;
    void update(T t) throws SQLException;
    void delete(int id) throws SQLException;
}
