package pf4j.poc;

import org.pf4j.Plugin;
import org.pf4j.PluginDescriptor;
import org.pf4j.PluginWrapper;

public class HelloPlugin extends Plugin {

    private final PluginDescriptor descriptor;

    public HelloPlugin(PluginWrapper wrapper) {
        super(wrapper);
        descriptor = wrapper.getDescriptor();
    }

    @Override
    public void start() {
        log.info("{} ({}) is running, plugin info: {}", descriptor.getPluginId(), descriptor.getVersion(), descriptor);
    }

    @Override
    public void stop() {
        log.info("{} ({}) stopped.", descriptor.getPluginId(), descriptor.getVersion());
    }

    @Override
    public void delete() {
        log.info("{} ({}) deleted.", descriptor.getPluginId(), descriptor.getVersion());
    }

}
