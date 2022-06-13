package infrastructure.entities;

import java.util.HashMap;
import java.util.Map;

public class InterfaceImplementations {
    private final static Map<String, String> implementationMap = new HashMap<>();

    public static void addImplementations(String implementedType, String implementationAbsolutePath) {
        implementationMap.put(implementedType, implementationAbsolutePath);
    }

    public static String getImplementedTypesByInterface(String implementedType) {
        return implementationMap.get(implementedType);
    }
}
