package hr.javafx.sports.domain;

import java.math.BigDecimal;

public class Stadium extends Facility {

    private BigDecimal audienceCapacity;

    private Stadium(Long id, String name, BigDecimal audienceCapacity, String date, String reserved) {
        super(id, name, date, reserved);
        this.audienceCapacity = audienceCapacity;
    }

    public static class Builder {

        private Long id;
        private String name;
        private BigDecimal audienceCapacity;
        private String date;

        private String reserved;

        public Builder(Long id) {
            this.id = id;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder audienceCapacity(BigDecimal audienceCapacity) {
            this.audienceCapacity = audienceCapacity;
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

        public Stadium build() {
            return new Stadium(id, name, audienceCapacity, date, reserved);
        }

    }

    public BigDecimal getAudienceCapacity() {
        return audienceCapacity;
    }

}