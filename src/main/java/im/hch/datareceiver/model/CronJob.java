package im.hch.datareceiver.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;
import org.mongodb.morphia.utils.IndexType;

import java.util.Date;

@Entity(value = CronJob.TABLE_NAME, noClassnameStored = true)
@Indexes({
        @Index(fields = @Field("name")),
        @Index(fields = @Field("class_name")),
        @Index(fields = @Field(value = "create_time", type = IndexType.DESC))
})
public class CronJob {
    public static final String TABLE_NAME = "cronjobs";

    public static enum Columns {
        ID("_id"),
        NAME("name"),
        CRON_EXPR("cron_expr"),
        CLASSNAME("class_name"),
        ARGS("args"),
        TIMEZONE("timezone"),
        STATUS("status"),
        CREATETIME("create_time");

        private final String name;

        Columns(String name) {
            this.name = name;
        }

        public String val() {
            return name;
        }
    };

    public CronJob() {
        status = false;
        createTime = new Date();
    }

    public CronJob(String name, String cronExpr, String className, String[] args, String timezone, boolean status) {
        this.name = name;
        this.cronExpr = cronExpr;
        this.className = className;
        this.args = args;
        this.timezone = timezone;
        this.status = status;
        this.createTime = new Date();
    }

    @Id
    private ObjectId id;
    private String name;
    @Property("cron_expr")
    private String cronExpr;
    @Property("class_name")
    private String className;
    private String[] args;
    private String timezone;
    private boolean status;
    @Property("create_time")
    private Date createTime;

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

    public String getCronExpr() {
        return cronExpr;
    }

    public void setCronExpr(String cronExpr) {
        this.cronExpr = cronExpr;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
