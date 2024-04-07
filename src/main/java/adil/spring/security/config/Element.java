package adil.spring.security.config;

public class Element {
    private final String value;
    private final long timestamp;

    @Override
    public String toString() {
        return "Element{" +
                "value='" + value + '\'' +
                '}';
    }

    public Element(String value) {
        this.value = value;
        this.timestamp = System.currentTimeMillis();
    }

    public String getValue() {
        return value;
    }

    public long getTimestamp() {
        return timestamp;
    }

}