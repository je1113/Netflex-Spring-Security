package fast.campus.netflix.audit;

import org.springframework.data.auditing.DateTimeProvider;

import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

public class RequestedAtAuditorAware implements DateTimeProvider {
    @Override
    public Optional<TemporalAccessor> getNow() {
        return Optional.of(ZonedDateTime.now());
    }
}
