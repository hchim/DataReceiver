package im.hch.datareceiver.dao;

import com.mongodb.MongoClient;
import im.hch.datareceiver.Config;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class MongoDBHelper {
    private static Datastore datastore;

    static  {
        final Morphia morphia = new Morphia();
        morphia.mapPackage("im.hch.datareceiver.model");

        Config.DatabaseConfig dbConfg = Config.getConfig().getDatabaseConfig();
        datastore = morphia.createDatastore(new MongoClient(dbConfg.host, dbConfg.port), dbConfg.dbName);
    }

    public Datastore getDatastore() {
        return datastore;
    }
}
