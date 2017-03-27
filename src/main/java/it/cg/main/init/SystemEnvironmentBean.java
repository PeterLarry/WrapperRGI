package it.cg.main.init;

/**
 * Created by RiccardoEstia on 24/03/2017.
 */
public class SystemEnvironmentBean {

    public final static String CNST_SYSTEM_ENV_PROPERTIES_FILE = "WRAPPER_CONFIG";

    public final static String getFullPathNameConfigFile() {

        // May throw AccessControlException
        return System.getenv(CNST_SYSTEM_ENV_PROPERTIES_FILE);
    }
}
