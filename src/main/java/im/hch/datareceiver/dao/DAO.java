package im.hch.datareceiver.dao;

import java.util.List;

public interface DAO {

    public void insert(Object obj);

    public void insert(List objs);

    public void update(Object obj);

    public void delete(Object id);

    public Object get(Object id);

    public long count();

    public List<Object> getAll();

    public void removeAll();
}
