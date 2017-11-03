package models;

import java.io.FileReader;
import java.util.Properties;

/**
 *
 * @author Mahendra Tennakoon
 */
public class Configuration {

    // read the configuration file passed as a parameter
    private Properties readConfig(String file) {
        Properties prop = new Properties();

        try {
            FileReader reader = new FileReader(file);
            prop.load(reader);

            return prop;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prop;
    }

    // read database configurations. keys:(user_name, password).
    public Properties readDbConfig() {
        Properties db_properties = readConfig("C:\\Users\\VTENNM1\\IdeaProjects\\WaterfordMobile\\src\\main\\resources\\config\\db_config.properties");
        return db_properties;
    }

    /**
     * read configurations for team email account.
     * @return
     */
    public Properties readEmailConfig() {
        Properties email_properties = readConfig("C:\\Users\\VTENNM1\\IdeaProjects\\WaterfordMobile\\src\\main\\resources\\config\\email_config.properties");
        return email_properties;
    }

    /**
     * read configurations project assets (images etc).
     * @return
     */
    public Properties readAssetsConfig() {
        Properties assets_properties = readConfig("./src/config/assets.properties");
        return assets_properties;
    }
}
