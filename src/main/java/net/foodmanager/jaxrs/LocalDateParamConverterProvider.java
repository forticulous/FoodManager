package net.foodmanager.jaxrs;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;

/**
 * @author fort
 */
@Provider
public class LocalDateParamConverterProvider implements ParamConverterProvider {

    private final LocalDateParamConverter localDateConverter = new LocalDateParamConverter();

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        // This interface is not so great.  Best I can do is check the types here
        if (LocalDate.class.equals(rawType)) {
            @SuppressWarnings("unchecked")
            ParamConverter<T> converter = (ParamConverter<T>) localDateConverter;
            return converter;
        }
        return null;
    }

}
