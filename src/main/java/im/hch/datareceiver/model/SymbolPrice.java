package im.hch.datareceiver.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;
import org.mongodb.morphia.utils.IndexType;

import java.util.Date;

@Entity(value = SymbolPrice.TABLE_NAME, noClassnameStored = true)
@Indexes({
    @Index(fields = {@Field("symbol"), @Field("type"), @Field(value = "time", type = IndexType.DESC)})
})
public class SymbolPrice {
    public static final String TABLE_NAME = "prices";

    public static enum PriceType {
        ONEMIN, FIVEMIN, THIRTYMIN, HOUR, DAY, WEEK, MONTH
    };

    public static enum Columns {
        ID("_id"),
        PRICETYPE("type"),
        SYMBOL("symbol"),
        OPEN("open"),
        CLOSE("close"),
        HIGH("high"),
        LOW("low"),
        VOLUME("volume"),
        TIME("time");

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
    private PriceType type;
    @Reference
    private Symbol symbol;
    private float open;
    private float close;
    private float high;
    private float low;
    private long volume;
    private Date time;

    public SymbolPrice() {}

    public SymbolPrice(PriceType type, Symbol symbol, float open,
                       float high, float low, float close, long volume, Date time) {
        this.type = type;
        this.symbol = symbol;
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
        this.volume = volume;
        this.time = time;
    }

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
