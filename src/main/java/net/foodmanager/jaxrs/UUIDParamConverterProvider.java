package net.foodmanager.jaxrs;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.UUID;

/**
 * @author fort
 */
@Provider
public class UUIDParamConverterProvider implements ParamConverterProvider {

    private final UUIDParamConverter uuidConverter = new UUIDParamConverter();

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (UUID.class.equals(rawType)) {
            @SuppressWarnings("unchecked")
            ParamConverter<T> converter = (ParamConverter<T>) uuidConverter;
            return converter;
        }
        return null;
    }

}
