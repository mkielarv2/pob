import java.util.Date;

public class Entry {
    private String name;
    private Date date;
    private Boolean type;
    private String path;

    public Entry() {
    }

    public Entry(String name, Date date, Boolean type, String path) {
        this.name = name;
        this.date = date;
        this.type = type;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "name='" + name + '\'' +
                ", date=" + date +
                ", type='" + type + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
