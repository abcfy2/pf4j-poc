package pf4j.poc;

import org.pf4j.DefaultPluginManager;
import org.pf4j.PluginManager;
import org.pf4j.update.PluginInfo;
import org.pf4j.update.UpdateManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);
    private static PluginManager pluginManager;

    public static void main(String[] args) {
        pluginManager = new DefaultPluginManager();
        pluginManager.loadPlugins();

        // create update manager
        UpdateManager updateManager = new UpdateManager(pluginManager);

        // >> keep system up-to-date <<
        boolean systemUpToDate = true;

        // check for updates
        if (updateManager.hasUpdates()) {
            List<PluginInfo> updates = updateManager.getUpdates();
            log.debug("Found {} updates", updates.size());
            for (PluginInfo plugin : updates) {
                log.debug("Found update for plugin '{}'", plugin.id);
                PluginInfo.PluginRelease lastRelease = updateManager.getLastPluginRelease(plugin.id);
                String lastVersion = lastRelease.version;
                String installedVersion = pluginManager.getPlugin(plugin.id).getDescriptor().getVersion();
                log.debug("Update plugin '{}' from version {} to version {}", plugin.id, installedVersion, lastVersion);
                boolean updated = updateManager.updatePlugin(plugin.id, lastVersion);
                if (updated) {
                    log.debug("Updated plugin '{}'", plugin.id);
                } else {
                    log.error("Cannot update plugin '{}'", plugin.id);
                    systemUpToDate = false;
                }
            }
        } else {
            log.debug("No updates found");
        }

        // check for available (new) plugins
        if (updateManager.hasAvailablePlugins()) {
            List<PluginInfo> availablePlugins = updateManager.getAvailablePlugins();
            log.debug("Found {} available plugins", availablePlugins.size());
            for (PluginInfo plugin : availablePlugins) {
                log.debug("Found available plugin '{}'", plugin.id);
                PluginInfo.PluginRelease lastRelease = updateManager.getLastPluginRelease(plugin.id);
                String lastVersion = lastRelease.version;
                log.debug("Install plugin '{}' with version {}", plugin.id, lastVersion);
                boolean installed = updateManager.installPlugin(plugin.id, lastVersion);
                if (installed) {
                    log.debug("Installed plugin '{}'", plugin.id);
                } else {
                    log.error("Cannot install plugin '{}'", plugin.id);
                    systemUpToDate = false;
                }
            }
        } else {
            log.debug("No available plugins found");
        }

        pluginManager.startPlugins();

        if (systemUpToDate) {
            log.debug("System up-to-date");
        }

        // Add shutdown hook.
        Runtime.getRuntime().addShutdownHook(new Thread(App::stop));

        try {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                log.info("App is running.");
                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            stop();
            Thread.currentThread().interrupt();
        }

    }

    public static void stop() {
        if (pluginManager != null) {
            pluginManager.stopPlugins();
        }
        log.info("App stopped");
    }

}
