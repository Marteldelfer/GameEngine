package engine.helper;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class ClasspathScanner {

    private static final Set<Class<?>> allClasses = scanClasses();

    public static Set<Class<?>> getAllClasses() {
        return allClasses;
    }

    public static Set<Class<?>> getAnnotatedClass(Class<? extends Annotation> annotation) {
        return allClasses.stream().filter(c -> c.isAnnotationPresent(annotation))
                .collect(Collectors.toSet());
    }

    private static Set<Class<?>> scanClasses() {
        Set<Class<?>> classes = new HashSet<>();
        String classpath = System.getProperty("java.class.path");
        String[] entries = classpath.split(File.pathSeparator);

        for (String entry : entries) {
            File file = new File(entry);
            if (file.isDirectory()) scanDir(file, classpath, classes);
            else readJar(file, classpath, classes);
        }
        return classes;
    }

    private static void scanDir(File dir, String classpath, Set<Class<?>> classes) {
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory()) scanDir(file, classpath, classes);
            else readJar(file, classpath, classes);
        }
    }

    private static void readJar(File file, String classpath, Set<Class<?>> classes) {
        if (file.getAbsolutePath().endsWith(".class")) {
            try {
                String classname = file.getAbsolutePath()
                        .replace(classpath + File.separator, "")
                        .replace(File.separator, ".")
                        .replace(".class", "");
                Class<?> cls = Class.forName(classname);
                classes.add(cls);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
