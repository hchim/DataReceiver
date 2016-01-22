package im.hch.datareceiver.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

@Entity(Market.TABLE_NAME)
@Indexes(
    @Index(value = "name", fields = @Field("name"))
)
public class Market {
    public static final String TABLE_NAME = "markets";

    @Id
    private ObjectId id;
    private String name;
    // open time and close time are minutes of the day
    @Property("open_time")
    private int openTime;
    @Property("close_time")
    private int closeTime;

    public Market() {}

    public Market(String name, int openTime, int closeTime) {
        this.name = name;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOpenTime() {
        return openTime;
    }

    public void setOpenTime(int openTime) {
        this.openTime = openTime;
    }

    public int getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(int closeTime) {
        this.closeTime = closeTime;
    }
}
