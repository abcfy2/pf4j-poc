package pf4j.poc;

import org.pf4j.DefaultPluginManager;
import org.pf4j.PluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger LOG = LoggerFactory.getLogger(App.class);
    private static PluginManager pluginManager;

    public static void main(String[] args) {
        pluginManager = new DefaultPluginManager();
        pluginManager.loadPlugins();
        pluginManager.startPlugins();

        // Add shutdown hook.
        Runtime.getRuntime().addShutdownHook(new Thread(App::stop));

        while (true) {
            try {
                LOG.info("App is running.");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void stop() {
        if (pluginManager != null) {
            pluginManager.stopPlugins();
        }
        LOG.info("App stopped");
    }

}
