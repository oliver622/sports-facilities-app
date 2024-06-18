package hr.javafx.sports.domain;

import java.math.BigDecimal;

public class SkiResort extends Facility {

    private BigDecimal humidity;

    private SkiResort(Long id, String name, BigDecimal humidity, String date, String reserved) {
        super(id, name, date, reserved);
        this.humidity = humidity;
    }

    public static class Builder {

        private Long id;
        private String name;
        private BigDecimal humidity;

        private String date;

        private String reserved;

        public Builder(Long id) {
            this.id = id;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder humidity(BigDecimal humidity) {
            this.humidity = humidity;
            return this;
        }

        public Builder date(String date) {
            this.date = date;
            return this;
        }

        public Builder reserved(String reserved) {
            this.reserved = reserved;
            return this;
        }

        public SkiResort build() {
            return new SkiResort(id, name, humidity, date, reserved);
        }

    }

    public BigDecimal getHumidity() {
        return humidity;
    }


}