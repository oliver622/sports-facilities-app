package hr.javafx.sports.domain;

import java.math.BigDecimal;

public abstract class Facility {

    private Long id;
    private String name;

    private String date;

    private String reserved;

    public Facility(Long id, String name, String date, String reserved) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.reserved = reserved;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReserved() {
        return reserved;
    }
    public void setReserved(String reserved) {
        this.reserved = reserved;
    }
}
