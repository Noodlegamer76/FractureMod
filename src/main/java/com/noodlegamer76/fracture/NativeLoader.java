package com.noodlegamer76.fracture;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Locale;

public class NativeLoader {

    static {
        try {
            String os = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
            String arch = System.getProperty("os.arch").toLowerCase(Locale.ENGLISH);
            String archDir = arch.contains("64") ? "x86_64" : "x86";

            String nativeFileName;
            String nativeFolder;

            if (os.contains("win")) {
                nativeFileName = "imgui-java64.dll";
                nativeFolder = "natives/windows/" + archDir;
            } else if (os.contains("nux") || os.contains("nix") || os.contains("aix")) {
                nativeFileName = "libimgui-java64.so";
                nativeFolder = "natives/linux/" + archDir;
            } else if (os.contains("mac")) {
                nativeFileName = "libimgui-java64.dylib";
                nativeFolder = "natives/osx/" + archDir;
            } else {
                throw new UnsupportedOperationException("Noodle Engine: Unsupported OS: " + os);
            }

            String resourcePath = "/" + nativeFolder + "/" + nativeFileName;

            try (InputStream in = NativeLoader.class.getResourceAsStream(resourcePath)) {
                if (in == null) {
                    throw new RuntimeException("Noodle Engine: Native ImGui library not found in resources: " + resourcePath);
                }
                System.out.println("Loading ImGui native from resources: " + resourcePath);

                String suffix = nativeFileName.substring(nativeFileName.lastIndexOf('.'));
                Path tempFile = Files.createTempFile("imgui-native-", suffix);
                tempFile.toFile().deleteOnExit();

                Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);

                System.out.println("Loading ImGui native from temp: " + tempFile.toAbsolutePath());
                System.load(tempFile.toAbsolutePath().toString());
            }

        } catch (Throwable t) {
            throw new RuntimeException("Noodle Engine: Failed to load ImGui native library", t);
        }
    }
}