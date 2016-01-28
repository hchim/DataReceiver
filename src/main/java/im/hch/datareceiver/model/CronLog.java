package im.hch.datareceiver.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;
import org.mongodb.morphia.utils.IndexType;

import java.util.Date;

@Entity(value = CronLog.TABLE_NAME, noClassnameStored = true)
@Indexes({
    @Index(fields = {@Field("jobid"), @Field("state"), @Field(value = "time", type = IndexType.DESC)})
})
public class CronLog {
    public static final String TABLE_NAME = "cronlogs";

    public static enum State {
        UNKNOWN, SUCCESS, FAIL
    }

    public static enum Columns {
        ID("_id"),
        JOBID("jobid"),
        STATE("state"),
        LOG("log"),
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
    private ObjectId jobid;
    private State state;
    private String log;
    private Date time;

    public CronLog() {
        time = new Date();
    }

    public CronLog(ObjectId jobid, State state, String log) {
        this.jobid = jobid;
        this.state = state;
        this.log = log;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getJobid() {
        return jobid;
    }

    public void setJobid(ObjectId jobid) {
        this.jobid = jobid;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
