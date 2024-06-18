package hr.javafx.sports.domain;

import java.math.BigDecimal;

public class SkatingRing extends Facility {

    private BigDecimal temperature;

    private SkatingRing(Long id, String name, BigDecimal temperature, String date, String reserved) {
        super(id, name, date, reserved);
        this.temperature = temperature;
    }

    public static class Builder {

        private Long id;
        private String name;
        private BigDecimal temperature;

        private String date;

        private String reserved;

        public Builder(Long id) {
            this.id = id;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder temperature(BigDecimal temperature) {
            this.temperature = temperature;
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

        public SkatingRing build() {
            return new SkatingRing(id, name, temperature, date, reserved);
        }


    }

    public BigDecimal getTemperature() {
        return temperature;
    }

}