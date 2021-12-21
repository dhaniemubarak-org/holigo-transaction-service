package id.holigo.services.holigotransactionservice.web.mappers;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class DateMapper {
    public OffsetDateTime asOffsetDateTime(Timestamp timestamp) {
        if (timestamp != null) {
            return OffsetDateTime.of(timestamp.toLocalDateTime().getYear(), timestamp.toLocalDateTime().getMonthValue(),
                    timestamp.toLocalDateTime().getDayOfMonth(), timestamp.toLocalDateTime().getHour(),
                    timestamp.toLocalDateTime().getMinute(), timestamp.toLocalDateTime().getSecond(),
                    timestamp.toLocalDateTime().getNano(), ZoneOffset.UTC);
        }
        return null;
    }

    public Timestamp asTimestamp(OffsetDateTime offsetDateTime) {
        if (offsetDateTime != null) {
            final String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSxx";
            DateTimeFormatter dtfB = DateTimeFormatter.ofPattern(pattern);
            OffsetDateTime value = OffsetDateTime.parse(offsetDateTime.toString(), dtfB);
            return Timestamp.valueOf(value.toString());
        }
        return null;
    }
}
