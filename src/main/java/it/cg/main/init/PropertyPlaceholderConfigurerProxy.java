package it.cg.main.init;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.AccessControlException;
import java.util.Arrays;
import java.util.Properties;

public class PropertyPlaceholderConfigurerProxy extends PropertyPlaceholderConfigurer {

    public PropertyPlaceholderConfigurerProxy() {
        super();
    }

    @Override
    protected String resolvePlaceholder(String placeholder, Properties props) {
        return super.resolvePlaceholder(placeholder, props);
    }

    @Override
    protected void loadProperties(Properties props) throws IOException {
        super.loadProperties(props);
    }

    @Override
    public void setLocation(Resource location) {
        super.setLocation(location);
    }

    @Override
    public void setLocations(Resource... locations) {
        UrlResource location;

        String configurationFilepath;
        try {
            configurationFilepath = SystemEnvironmentBean.getFullPathNameConfigFile();

        } catch (Exception e) {
            throw new RuntimeException(
                    String.format(
                            "Unable to read environment variable '%s'.",
                            SystemEnvironmentBean.CNST_SYSTEM_ENV_PROPERTIES_FILE),
                    e);
        }

        if (configurationFilepath == null || configurationFilepath.isEmpty()) {
            throw new RuntimeException(
                    String.format(
                            "Variable '%s' is not defined in the environment or has no value.",
                            SystemEnvironmentBean.CNST_SYSTEM_ENV_PROPERTIES_FILE));
        }

        final File configurationFile = new File(configurationFilepath);
        if (!configurationFile.exists()) {
            throw new RuntimeException(
                    String.format(
                            "Configuration file '%s' does not exist.",
                            configurationFilepath));
        }


        final String configurationFileResource = "file:".concat(configurationFilepath);
        try {
            location = new UrlResource(configurationFileResource);

        } catch (MalformedURLException e) {
            throw new RuntimeException(
                    String.format(
                            "Unable to create a UrlResource from path '%s', for configuration file '%s'.",
                            configurationFileResource,
                            configurationFilepath),
                    e);
        }

        final Resource[] newLocations = Arrays.copyOf(locations, locations.length + 1);
        newLocations[newLocations.length - 1] = location;
        super.setLocations(newLocations);
    }
}
