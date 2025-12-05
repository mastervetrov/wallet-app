package company.wallet.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Metadata {

    private final Instant timestamp;

    public Metadata(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public static Metadata create() {
        return new Metadata(Instant.now());
    }
}
