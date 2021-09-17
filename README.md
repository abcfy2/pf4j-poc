# pf4j-poc

This is only a poc project for [pf4j](https://pf4j.org/).

## Requirements

- Java 8 or above (LTS version is recommended, e.g: 8, 11, 17)

## How to run

```sh
./gradlew shadowJar
mkdir -p build/plugins
cp -v app/build/libs/app-1.0.0-all.jar build/
cp -v plugins/plugin1/build/libs/hello-plugin-0.0.1.jar build/plugins/
cd build
java -jar app-1.0.0-all.jar
```

Example output:

```txt
23:45:35.885 [main] INFO org.pf4j.DefaultPluginStatusProvider - Enabled plugins: []
23:45:35.886 [main] INFO org.pf4j.DefaultPluginStatusProvider - Disabled plugins: []
23:45:35.889 [main] INFO org.pf4j.DefaultPluginManager - PF4J version 1.0.0 in 'deployment' mode
23:45:35.889 [main] DEBUG org.pf4j.AbstractPluginManager - Lookup plugins in '[plugins]'
23:45:35.894 [main] DEBUG org.pf4j.AbstractPluginManager - Found 1 possible plugins: [plugins\hello-plugin-0.0.1.jar]
23:45:35.894 [main] DEBUG org.pf4j.AbstractPluginManager - Use 'org.pf4j.CompoundPluginDescriptorFinder@842006db' to find plugins descriptors
23:45:35.894 [main] DEBUG org.pf4j.AbstractPluginManager - Finding plugin descriptor for plugin 'plugins\hello-plugin-0.0.1.jar'
23:45:35.894 [main] DEBUG org.pf4j.CompoundPluginDescriptorFinder - 'org.pf4j.PropertiesPluginDescriptorFinder@d0749e96' is applicable for plugin 'plugins\hello-plugin-0.0.1.jar'
23:45:35.904 [main] DEBUG org.pf4j.PropertiesPluginDescriptorFinder - Lookup plugin descriptor in 'plugin.properties'
23:45:35.907 [main] DEBUG org.pf4j.CompoundPluginDescriptorFinder - Cannot find 'plugin.properties' path
23:45:35.907 [main] DEBUG org.pf4j.CompoundPluginDescriptorFinder - Try to continue with the next finder
23:45:35.907 [main] DEBUG org.pf4j.CompoundPluginDescriptorFinder - 'org.pf4j.ManifestPluginDescriptorFinder@728f83aa' is applicable for plugin 'plugins\hello-plugin-0.0.1.jar'
23:45:35.908 [main] DEBUG org.pf4j.AbstractPluginManager - Found descriptor PluginDescriptor [pluginId=HelloPlugin, pluginClass=pf4j.poc.HelloPlugin, version=0.0.1, provider=null, dependencies=[], description=This is my first plugin named HelloPlugin., requires=*, license=null]
23:45:35.908 [main] DEBUG org.pf4j.AbstractPluginManager - Class 'pf4j.poc.HelloPlugin' for plugin 'plugins\hello-plugin-0.0.1.jar'
23:45:35.908 [main] DEBUG org.pf4j.AbstractPluginManager - Loading plugin 'plugins\hello-plugin-0.0.1.jar'
23:45:35.908 [main] DEBUG org.pf4j.CompoundPluginLoader - 'org.pf4j.JarPluginLoader@634885c1' is applicable for plugin 'plugins\hello-plugin-0.0.1.jar'
23:45:35.910 [main] DEBUG org.pf4j.PluginClassLoader - Add 'file:/C:/Users/abcfy/projects/pf4j-poc/build/plugins/hello-plugin-0.0.1.jar'
23:45:35.910 [main] DEBUG org.pf4j.AbstractPluginManager - Loaded plugin 'plugins\hello-plugin-0.0.1.jar' with class loader 'org.pf4j.PluginClassLoader@374ff83d'
23:45:35.910 [main] DEBUG org.pf4j.AbstractPluginManager - Creating wrapper for plugin 'plugins\hello-plugin-0.0.1.jar'
23:45:35.910 [main] DEBUG org.pf4j.AbstractPluginManager - Created wrapper 'PluginWrapper [descriptor=PluginDescriptor [pluginId=HelloPlugin, pluginClass=pf4j.poc.HelloPlugin, version=0.0.1, provider=null, dependencies=[], description=This is my first plugin named HelloPlugin., requires=*, license=null], pluginPath=plugins\hello-plugin-0.0.1.jar]' for plugin 'plugins\hello-plugin-0.0.1.jar'
23:45:35.910 [main] DEBUG org.pf4j.DependencyResolver - Graph:
   HelloPlugin -> []
23:45:35.911 [main] DEBUG org.pf4j.DependencyResolver - Plugins order: [HelloPlugin]
23:45:35.911 [main] INFO org.pf4j.AbstractPluginManager - Plugin 'HelloPlugin@0.0.1' resolved
23:45:35.911 [main] INFO org.pf4j.AbstractPluginManager - Start plugin 'HelloPlugin@0.0.1'
23:45:35.911 [main] DEBUG org.pf4j.DefaultPluginFactory - Create instance for plugin 'pf4j.poc.HelloPlugin'
23:45:35.912 [main] INFO pf4j.poc.HelloPlugin - HelloPlugin (0.0.1) is running, plugin info: PluginDescriptor [pluginId=HelloPlugin, pluginClass=pf4j.poc.HelloPlugin, version=0.0.1, provider=null, dependencies=[], description=This is my first plugin named HelloPlugin., requires=*, license=null]
23:45:35.912 [main] INFO pf4j.poc.App - App is running...
23:45:35.912 [main] DEBUG pf4j.poc.App - No updates found
23:45:35.912 [main] DEBUG pf4j.poc.App - No available plugins found
23:45:35.912 [main] DEBUG pf4j.poc.App - System up-to-date
```

## Online Update

Create update plugins directory `build/updates/`:

```sh
mkdir -p build/updates
```

> repositories.json and plugins.json format can be found in pf4j-update document: https://github.com/pf4j/pf4j-update#repository-structure

Create `repositories.json` in `build/updates`:

```sh
cat > build/repositories.json <<EOF
[
  {
    "id": "folder",
    "url": "file://$(realpath build/updates)/"
  }
]
EOF
```

Put plugins to be updated in `build/updates`:

```sh
cp -v plugins/plugin1/build/libs/hello-plugin-0.0.1.jar build/updates/
```

Create `plugins.json` in `build/updates`:

```sh
cat > build/updates/plugins.json <<EOF
[
  {
    "id": "HelloPlugin",
    "description": "Hello plugin",
    "releases": [
      {
        "version": "0.0.1",
        "date": "2021-09-17T20:22:01+08:00",
        "url": "hello-plugin-0.0.1.jar"
      }
    ]
  }
]
EOF
```

Current time the directory under `build` directory is like:

```txt
.
├── app-1.0.0-all.jar
├── plugins
│   └── hello-plugin-0.0.1.jar
├── repositories.json
└── updates
    ├── hello-plugin-0.0.1.jar
    └── plugins.json
```

First run under `build` directory:

```sh
cd build
java -jar app-1.0.0-all.jar
```

Output sample:

```txt
# ...
00:06:24.524 [main] INFO pf4j.poc.App - App is running...
00:06:24.525 [main] DEBUG org.pf4j.update.UpdateManager - Read repositories from 'repositories.json'
00:06:24.554 [main] DEBUG org.pf4j.update.DefaultUpdateRepository - Read plugins of 'folder' repository from 'file:/C:/Users/abcfy/projects/pf4j-poc/build/updates/plugins.json'
00:06:24.559 [main] DEBUG org.pf4j.update.DefaultUpdateRepository - Found 1 plugins in repository 'folder'
00:06:24.562 [main] DEBUG pf4j.poc.App - No updates found
00:06:24.562 [main] DEBUG pf4j.poc.App - No available plugins found
00:06:24.562 [main] DEBUG pf4j.poc.App - System up-to-date
# ...
```

Now open another terminal and do some upgrade for `build/updates/`:

1. create a new version for `hello-plugin`

    ```sh
    cp -v build/updates/hello-plugin-0.0.1.jar build/updates/hello-plugin-0.0.2.jar
    # modify META-INF/MANIFEST.MF in hello-plugin-0.0.2.jar
    # set:
    # Plugin-Version: 0.0.2
    vim build/updates/hello-plugin-0.0.2.jar
    ```

1. modify `plugins.json`:

    ```sh
    cat > build/updates/plugins.json <<EOF
    [
      {
        "id": "HelloPlugin",
        "description": "Hello plugin",
        "releases": [
          {
            "version": "0.0.2",
            "date": "2021-09-17T20:22:01+08:00",
            "url": "hello-plugin-0.0.2.jar"
          }
        ]
      }
    ]
    EOF
    ```

Then you should see the first terminal output like:

```txt
00:12:55.191 [main] DEBUG org.pf4j.update.UpdateManager - Read repositories from 'repositories.json'
00:12:55.191 [main] DEBUG org.pf4j.update.DefaultUpdateRepository - Read plugins of 'folder' repository from 'file:/C:/Users/abcfy/projects/pf4j-poc/build/updates/plugins.json'
00:12:55.192 [main] DEBUG org.pf4j.update.DefaultUpdateRepository - Found 1 plugins in repository 'folder'
00:12:55.192 [main] DEBUG pf4j.poc.App - Found 1 updates
00:12:55.192 [main] DEBUG pf4j.poc.App - Found update for plugin 'HelloPlugin'
00:12:55.192 [main] DEBUG pf4j.poc.App - Update plugin 'HelloPlugin' from version 0.0.1 to version 0.0.2
00:12:55.251 [main] DEBUG org.pf4j.update.verifier.Sha512SumVerifier - No sha512 checksum specified, skipping verification
00:12:55.260 [main] INFO org.pf4j.AbstractPluginManager - Stop plugin 'HelloPlugin@0.0.1'
00:12:55.260 [main] INFO pf4j.poc.HelloPlugin - HelloPlugin (0.0.1) stopped.
00:12:55.260 [main] DEBUG org.pf4j.AbstractPluginManager - Already stopped plugin 'HelloPlugin@0.0.1'
00:12:55.260 [main] INFO org.pf4j.AbstractPluginManager - Unload plugin 'HelloPlugin@0.0.1'
00:12:55.261 [main] INFO pf4j.poc.HelloPlugin - HelloPlugin (0.0.1) deleted.
00:12:55.263 [main] DEBUG org.pf4j.AbstractPluginManager - Loading plugin from 'plugins\hello-plugin-0.0.2.jar'
00:12:55.263 [main] DEBUG org.pf4j.AbstractPluginManager - Use 'org.pf4j.CompoundPluginDescriptorFinder@eb0c57f4' to find plugins descriptors
00:12:55.263 [main] DEBUG org.pf4j.AbstractPluginManager - Finding plugin descriptor for plugin 'plugins\hello-plugin-0.0.2.jar'
00:12:55.263 [main] DEBUG org.pf4j.CompoundPluginDescriptorFinder - 'org.pf4j.PropertiesPluginDescriptorFinder@dcf8ff06' is applicable for plugin 'plugins\hello-plugin-0.0.2.jar'
00:12:55.265 [main] DEBUG org.pf4j.PropertiesPluginDescriptorFinder - Lookup plugin descriptor in 'plugin.properties'
00:12:55.266 [main] DEBUG org.pf4j.CompoundPluginDescriptorFinder - Cannot find 'plugin.properties' path
00:12:55.266 [main] DEBUG org.pf4j.CompoundPluginDescriptorFinder - Try to continue with the next finder
00:12:55.266 [main] DEBUG org.pf4j.CompoundPluginDescriptorFinder - 'org.pf4j.ManifestPluginDescriptorFinder@2c12e0d5' is applicable for plugin 'plugins\hello-plugin-0.0.2.jar'
00:12:55.266 [main] DEBUG org.pf4j.AbstractPluginManager - Found descriptor PluginDescriptor [pluginId=HelloPlugin, pluginClass=pf4j.poc.HelloPlugin, version=0.0.2, provider=null, dependencies=[], description=This is my first plugin named HelloPlugin., requires=*, license=null]
00:12:55.266 [main] DEBUG org.pf4j.AbstractPluginManager - Class 'pf4j.poc.HelloPlugin' for plugin 'plugins\hello-plugin-0.0.2.jar'
00:12:55.266 [main] DEBUG org.pf4j.AbstractPluginManager - Loading plugin 'plugins\hello-plugin-0.0.2.jar'
00:12:55.266 [main] DEBUG org.pf4j.CompoundPluginLoader - 'org.pf4j.JarPluginLoader@f3bd7f7f' is applicable for plugin 'plugins\hello-plugin-0.0.2.jar'
00:12:55.267 [main] DEBUG org.pf4j.PluginClassLoader - Add 'file:/C:/Users/abcfy/projects/pf4j-poc/build/plugins/hello-plugin-0.0.2.jar'
00:12:55.267 [main] DEBUG org.pf4j.AbstractPluginManager - Loaded plugin 'plugins\hello-plugin-0.0.2.jar' with class loader 'org.pf4j.PluginClassLoader@604fda28'
00:12:55.267 [main] DEBUG org.pf4j.AbstractPluginManager - Creating wrapper for plugin 'plugins\hello-plugin-0.0.2.jar'
00:12:55.267 [main] DEBUG org.pf4j.AbstractPluginManager - Created wrapper 'PluginWrapper [descriptor=PluginDescriptor [pluginId=HelloPlugin, pluginClass=pf4j.poc.HelloPlugin, version=0.0.2, provider=null, dependencies=[], description=This is my first plugin named HelloPlugin., requires=*, license=null], pluginPath=plugins\hello-plugin-0.0.2.jar]' for plugin 'plugins\hello-plugin-0.0.2.jar'
00:12:55.267 [main] DEBUG org.pf4j.DependencyResolver - Graph:
   HelloPlugin -> []
00:12:55.267 [main] DEBUG org.pf4j.DependencyResolver - Plugins order: [HelloPlugin]
00:12:55.267 [main] INFO org.pf4j.AbstractPluginManager - Plugin 'HelloPlugin@0.0.2' resolved
00:12:55.267 [main] INFO org.pf4j.AbstractPluginManager - Start plugin 'HelloPlugin@0.0.2'
00:12:55.267 [main] DEBUG org.pf4j.DefaultPluginFactory - Create instance for plugin 'pf4j.poc.HelloPlugin'
00:12:55.268 [main] INFO pf4j.poc.HelloPlugin - HelloPlugin (0.0.2) is running, plugin info: PluginDescriptor [pluginId=HelloPlugin, pluginClass=pf4j.poc.HelloPlugin, version=0.0.2, provider=null, dependencies=[], description=This is my first plugin named HelloPlugin., requires=*, license=null]
00:12:55.268 [main] DEBUG pf4j.poc.App - Updated plugin 'HelloPlugin'
00:12:55.268 [main] DEBUG pf4j.poc.App - No available plugins found
00:12:55.268 [main] DEBUG pf4j.poc.App - System up-to-date
```

Now the `build` directory structure like:

```txt
.
├── app-1.0.0-all.jar
├── plugins
│   └── hello-plugin-0.0.2.jar
├── repositories.json
└── updates
    ├── hello-plugin-0.0.1.jar
    ├── hello-plugin-0.0.2.jar
    └── plugins.json
```

That's it!
