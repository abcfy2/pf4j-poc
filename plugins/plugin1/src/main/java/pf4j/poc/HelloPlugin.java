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
        System.out.println(descriptor.toString());
        System.out.printf("%s %s started%n", descriptor.getPluginId(), descriptor.getVersion());
    }

    @Override
    public void stop() {
        System.out.printf("%s %s stopped%n", descriptor.getPluginId(), descriptor.getVersion());
    }

    @Override
    public void delete() {
        System.out.printf("%s %s deleted", descriptor.getPluginId(), descriptor.getVersion());
    }

}
