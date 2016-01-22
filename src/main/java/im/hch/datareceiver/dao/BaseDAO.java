package im.hch.datareceiver.dao;

import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;

import java.util.List;

public abstract class BaseDAO implements DAO {
    private static final Logger logger = Logger.getLogger(BaseDAO.class);
    protected MongoDBHelper dbHelper;
    protected Datastore datastore;

    public BaseDAO() {
        dbHelper = new MongoDBHelper();
        datastore = dbHelper.getDatastore();
    }

    public void insert(Object obj) {
        datastore.save(obj);
    }

    public void update(Object obj) {
        datastore.save(obj);
    }

    public void delete(Object id) {
        datastore.delete(getEntityClass(), id);
    }

    public Object get(Object id) {
        return datastore.get(getEntityClass(), id);
    }

    public List<Object> getAll() {
        Query query = datastore.createQuery(getEntityClass());
        final List<Object> objects = query.asList();

        return objects;
    }

    public long count() {
        return datastore.getCount(getEntityClass());
    }

    public void removeAll() {
        Query query = datastore.createQuery(getEntityClass());
        datastore.delete(query);
    }

    public abstract Class getEntityClass();
}
