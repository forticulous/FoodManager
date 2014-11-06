package net.foodmanager.jaxrs;

import javax.ws.rs.ext.ParamConverter;
import java.util.UUID;

/**
 * @author fort
 */
public class UUIDParamConverter implements ParamConverter<UUID> {

    @Override
    public UUID fromString(String value) {
        return UUID.fromString(value);
    }

    @Override
    public String toString(UUID value) {
        return value.toString();
    }

}
