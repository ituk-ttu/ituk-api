package ee.ituk.api.repository;

import ee.ituk.tables.records.UserRecord;
import org.jetbrains.annotations.NotNull;
import org.jooq.Field;
import org.jooq.Record;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JooqHelper {

    @NotNull
    static List<Field<?>> getNotNullFields(Record record) {
        return Arrays.stream(record.fields())
                .filter(field -> field.get(record) != null)
                .collect(Collectors.toList());
    }
}
