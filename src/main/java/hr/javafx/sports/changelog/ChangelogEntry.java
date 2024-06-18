package hr.javafx.sports.changelog;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ChangelogEntry(LocalDateTime dateTime, String username, String content) implements Serializable {
    private static final long serialVersionUID = 1L;
}