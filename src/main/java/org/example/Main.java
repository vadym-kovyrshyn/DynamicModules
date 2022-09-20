package org.example;

import org.tada.price.Voiceable;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.Objects;
import java.util.ServiceLoader;

public class Main {

    public static File getThisJarFile() {
        CodeSource source = Main.class.getProtectionDomain().getCodeSource();

        File file = null;
        try {
            file = new File(source.getLocation().toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static void main(String[] args) {

        try {
            Voiceable cat = (Voiceable) Class.forName("org.tada.price.Cat").getConstructor().newInstance();
            System.out.println(cat.doVoice());
            Voiceable dog = (Voiceable) Class.forName("org.tada.price.Dog").getConstructor().newInstance();
            System.out.println(dog.doVoice());
        } catch (InstantiationException | IllegalAccessException |
                 InvocationTargetException | NoSuchMethodException |
                 ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        File thisFile = getThisJarFile();

        File folder = thisFile.getParentFile();

        assert folder != null;

        File[] jars = Objects.requireNonNull(folder.listFiles((file, s) -> s.endsWith(".jar")));

        for (final File fileEntry : jars) {
            if (fileEntry.isDirectory()
                || thisFile.getAbsolutePath().equals(fileEntry.getAbsolutePath())) {
                continue;
            }
//            URLClassLoader child = new URLClassLoader(
//                new URL[] {fileEntry.toURI().toURL()},
//                this.getClass().getClassLoader()
//            );
//            Class classToLoad = Class.forName("com.MyClass", true, child);
//            Method method = classToLoad.getDeclaredMethod("myMethod");
//            Object instance = classToLoad.newInstance();
//            Object result = method.invoke(instance);
        }

    }
}