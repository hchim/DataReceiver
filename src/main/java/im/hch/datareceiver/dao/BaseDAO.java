package im.hch.datareceiver.dao;

import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
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
        if (obj == null) {
            return;
        }

        datastore.save(obj);
    }

    public void insert(List objs) {
        if (objs == null || objs.isEmpty()) {
            return;
        }

        for (Object obj : objs) {
            datastore.save(obj);
        }
    }

    public void update(Object obj) {
        if (obj == null) {
            return;
        }

        datastore.save(obj);
    }

    public void delete(Object id) {
        if (id == null) {
            return;
        }

        datastore.delete(getEntityClass(), id);
    }

    public Object get(Object id) {
        if (id == null) {
            return null;
        }

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

    protected Object findOne(Query query) {
        if (query == null) {
            return null;
        }

        List results = query.asList();

        if (results.size() > 0) {
            return results.get(0);
        }

        return null;
    }

    public abstract Class getEntityClass();

    public void creatDefaultIndex() {
        datastore.ensureIndexes();
    }
}
