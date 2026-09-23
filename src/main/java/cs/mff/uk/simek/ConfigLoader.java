package cs.mff.uk.simek;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class ConfigLoader {

    public static final String GENERATOR_SECTION = "generator";
    public static final String IMPORTER_SECTION = "importer";
    public static final String MONGO_IMPORTER_SECTION = "mongo";

    /**
     * Load whole app config.
     * @return Map of key-value pairs of whole app config.
     */
    public static Map<String, Object> getConfig() throws IOException {
        String configFile = System.getenv().getOrDefault(
                "CONFIG_FILE",
                "oxm_generator.yaml"
        );

        Yaml yaml = new Yaml();

        try (InputStream input = Files.newInputStream(Path.of(configFile))) {
            return yaml.load(input);
        }
    }

    /**
     * Loads nested config based on given structured path.
     * @param path List of in order nested subsections of config yaml.
     * @return Map of key-value pairs from requested config subsection.
     */
    public static Map<String, Object> getSection(String... path) throws IOException {
        Map<String, Object> current = getConfig();

        for (String key : path) {
            Object value = current.get(key);

            if (value == null) {
                throw new IllegalArgumentException(
                        "Missing configuration section: " + String.join(".", path)
                );
            }

            if (!(value instanceof Map<?, ?>)) {
                throw new IllegalArgumentException(
                        "Configuration section '" + key + "' must be a map"
                );
            }

            current = (Map<String, Object>) value;
        }

        return current;
    }
}
