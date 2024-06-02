package sk.karab.dependencies;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import sk.karab.FPinata;
import sk.karab.util.Dependency;
import sk.karab.util.ListUtil;
import sk.karab.util.Log;

import java.util.ArrayList;

public class DependencySys {


    public static DependencySys instance;

    private ArrayList<Dependency> allDependencies;
    private ArrayList<Dependency> loadedDependencies;


    public DependencySys(Dependency ... dependencies) {

        if (instance != null) return;
        instance = this;

        this.allDependencies = ListUtil.toArrayList(dependencies);
        attemptToLoad();
    }


    private void attemptToLoad() {

        this.loadedDependencies = new ArrayList<>();
        PluginManager pluginManager = Bukkit.getPluginManager();

        allDependencies.forEach((dependency -> {
            String name = dependency.dependency();

            if (pluginManager.getPlugin(name) != null) loadedDependencies.add(dependency);
            else if (dependency.required()) {
                Log.err("The required dependency '%dependency' was not found, disabling this plugin!"
                        .replace("%dependency", name));
                pluginManager.disablePlugin(FPinata.instance);
            } else Log.warn("The optional dependency '%dependency' was not found, some parts of the plugin might need this to work."
                    .replace("%dependency", name));
        }));
    }


    private Dependency _getDependency(String dependencyName) {

        for (Dependency dependency : allDependencies)
            if (dependency.dependency().equalsIgnoreCase(dependencyName))
                return dependency;

        return null;
    }

    public Dependency getDependency(String dependencyName) {
        return instance._getDependency(dependencyName);
    }


    private boolean _isLoaded(String dependency) {
        return loadedDependencies.contains(_getDependency(dependency));
    }

    public static boolean isLoaded(String dependency) {
        return instance._isLoaded(dependency);
    }


}
