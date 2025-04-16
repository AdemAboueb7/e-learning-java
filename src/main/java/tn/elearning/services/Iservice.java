package services;
import java.sql.SQLException;
import java.util.List;

public interface Iservice<T> {

    void create(T t)throws SQLException;

    Boolean update(T t)throws SQLException;

    void delete(int id)throws SQLException;

    List<T> getAll()throws SQLException;

}
