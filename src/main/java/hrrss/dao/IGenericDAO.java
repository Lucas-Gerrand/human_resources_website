package hrrss.dao;

public interface IGenericDAO<T> {

    T create(T t);

    void delete(Object id);

    T find(Object id);

    T update(T t);   
}
