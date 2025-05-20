package io.servertap.api.platform.plugin;

import java.util.List;

/**
 * Class representing information about a plugin
 */
public class PluginInfo {
    private final String name;
    private final String version;
    private final String description;
    private final List<String> authors;
    private final String website;
    private final boolean enabled;

    /**
     * Create a new PluginInfo instance
     * @param name The name of the plugin
     * @param version The version of the plugin
     * @param description The description of the plugin
     * @param authors The authors of the plugin
     * @param website The website of the plugin
     * @param enabled Whether the plugin is enabled
     */
    public PluginInfo(String name, String version, String description, List<String> authors, String website, boolean enabled) {
        this.name = name;
        this.version = version;
        this.description = description;
        this.authors = authors;
        this.website = website;
        this.enabled = enabled;
    }

    /**
     * Get the name of the plugin
     * @return The plugin name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the version of the plugin
     * @return The plugin version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Get the description of the plugin
     * @return The plugin description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the authors of the plugin
     * @return The plugin authors
     */
    public List<String> getAuthors() {
        return authors;
    }

    /**
     * Get the website of the plugin
     * @return The plugin website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * Check if the plugin is enabled
     * @return True if the plugin is enabled, false otherwise
     */
    public boolean isEnabled() {
        return enabled;
    }
}
