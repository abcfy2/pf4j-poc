package pf4j.poc;

import org.pf4j.DefaultPluginManager;
import org.pf4j.PluginManager;

public class App {

    public static void main(String[] args) {
        PluginManager pluginManager = new DefaultPluginManager();
        pluginManager.loadPlugins();
        pluginManager.startPlugins();
    }

}
