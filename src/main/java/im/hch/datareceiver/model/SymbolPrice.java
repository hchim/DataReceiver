package im.hch.datareceiver.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.util.Date;

@Entity(Market.TABLE_NAME)
@Indexes(
        @Index(value = "name", fields = @Field("name"))
)
public class SymbolPrice {
    public static final String TABLE_NAME = "prices";

    public enum PriceType {
        ONEMIN, FIVEMIN, THIRTYMIN, HOUR, DAY, WEEK, MONTH
    };

    @Id
    private ObjectId id;
    private PriceType type;
    @Reference
    private Symbol symbol;
    private float open;
    private float close;
    private float high;
    private float low;
    private long volume;
    private Date time;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public PriceType getType() {
        return type;
    }

    public void setType(PriceType type) {
        this.type = type;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public float getOpen() {
        return open;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public float getClose() {
        return close;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public float getLow() {
        return low;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
