package hr.javafx.sports.domain;

import java.math.BigDecimal;

public final class Pool extends Facility {

    private BigDecimal depth;
    private Pool(Long id, String name, BigDecimal depth, String date, String reserved) {
        super(id, name, date, reserved);
        this.depth = depth;
    }
    public static class Builder {

        private Long id;
        private String name;
        private BigDecimal depth;

        private String date;

        private String reserved;

        public Builder(Long id) {
            this.id = id;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder depth(BigDecimal depth) {
            this.depth = depth;
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

        public Pool build() {
            return new Pool(id, name, depth, date, reserved);
        }
    }

    public BigDecimal getDepth() {
        return depth;
    }


}