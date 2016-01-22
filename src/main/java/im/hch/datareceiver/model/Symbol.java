package im.hch.datareceiver.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.util.Date;

@Entity(Market.TABLE_NAME)
@Indexes({
    @Index(fields = @Field("symbol")),
    @Index(fields = @Field("name"))
})
public class Symbol {
    public static final String TABLE_NAME = "symbols";

    @Id
    private ObjectId id;
    private String symbol;
    private String name;
    private String industry;
    @Reference
    private Market market;
    @Property("update_time")
    private Date lastUpdateTime;

    public Symbol() {}

    public Symbol(String symbol, String name, Market market, String industry) {
        this.symbol = symbol;
        this.name = name;
        this.market = market;
        this.industry = industry;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
