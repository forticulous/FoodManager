package net.foodmanager.modules;

import com.google.common.io.Resources;
import com.google.inject.AbstractModule;
import com.google.inject.ProvisionException;
import com.google.inject.name.Names;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * @author fort
 */
public class SqlModule extends AbstractModule {

    private static final String SQL_PROPERTIES_FILE = "sql-source.xml";
    public static final String INSERT_FOOD_DAY = "insertFoodDay";
    public static final String INSERT_FOOD_DAY_ITEM = "insertFoodDayItem";
    public static final String GET_FOOD_DAY_BY_LOCAL_DATE = "getFoodDayByLocalDate";
    public static final String FIND_ALL_FOOD_DAYS = "findAllFoodDays";

    @Override
    protected void configure() {
        Properties sqlProps = getProperties();

        bindConstant().annotatedWith(Names.named(GET_FOOD_DAY_BY_LOCAL_DATE)).to(sqlProps.getProperty(GET_FOOD_DAY_BY_LOCAL_DATE));
        bindConstant().annotatedWith(Names.named(INSERT_FOOD_DAY)).to(sqlProps.getProperty(INSERT_FOOD_DAY));
        bindConstant().annotatedWith(Names.named(INSERT_FOOD_DAY_ITEM)).to(sqlProps.getProperty(INSERT_FOOD_DAY_ITEM));
        bindConstant().annotatedWith(Names.named(FIND_ALL_FOOD_DAYS)).to(sqlProps.getProperty(FIND_ALL_FOOD_DAYS));
    }

    private Properties getProperties() {
        URL resource = Resources.getResource(SQL_PROPERTIES_FILE);
        Properties props = new Properties();

        try {
            InputStream inputStream = resource.openStream();
            props.loadFromXML(inputStream);
        } catch (IOException ex) {
            throw new ProvisionException("Failed to load properties", ex);
        }
        return props;
    }

}
