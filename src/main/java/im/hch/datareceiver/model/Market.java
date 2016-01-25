package im.hch.datareceiver.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.util.TimeZone;

@Entity(value = Market.TABLE_NAME, noClassnameStored = true)
@Indexes(
    @Index(fields = @Field("name"))
)
public class Market {
    public static final String TABLE_NAME = "markets";
    public static enum Columns {
        ID("_id"),
        NAME("name"),
        OPENTIME("open_time"),
        CLOSETIME("close_time"),
        TIMEZONE("timezone");

        private final String name;

        Columns(String name) {
            this.name = name;
        }

        public String val() {
            return name;
        }
    };

    @Id
    private ObjectId id;
    private String name;
    @Property("open_time")
    private int openTime;
    @Property("close_time")
    private int closeTime;
    private String timezone;

    public Market() {}

    public Market(String name, int openTime, int closeTime, String timeZone) {
        this.name = name;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.timezone = timeZone;
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

    public String getTimezone() {
        return timezone;
    }

    public TimeZone getTimeZoneObj() {
        if (timezone == null) {
            return null;
        }

        return TimeZone.getTimeZone(timezone);
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}
