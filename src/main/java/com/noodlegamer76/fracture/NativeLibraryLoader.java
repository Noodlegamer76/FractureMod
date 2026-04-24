package com.noodlegamer76.fracture;

import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;

public final class NativeLibraryLoader {
    private static final Logger LOGGER = FractureMod.LOGGER;
    private static final Set<String> LOADED_LIBRARIES = new HashSet<>();
    private static Path tempDir;
    private static boolean loaded = false;

    private NativeLibraryLoader() {
    }

    /**
     * Loads all required native libraries.
     * Call this during client initialization.
     */
    public static void loadNatives() {
        if (loaded) return;
        loaded = true;

        try {
            if (tempDir == null) {
                tempDir = Files.createTempDirectory("fracture_natives");
                tempDir.toFile().deleteOnExit();
            }

            String platformPath = getPlatformPath();

            String fullLibraryName = getLibraryName("imgui-java64");

            System.setProperty("imgui.library.path", tempDir.toAbsolutePath().toString());

            System.setProperty("imgui.library.name", fullLibraryName);

            loadLibrary(platformPath, fullLibraryName);

        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize native libraries", e);
        }
    }

    private static void loadLibrary(String platformPath, String libraryName) {
        if (LOADED_LIBRARIES.contains(libraryName)) {
            return;
        }

        String resourcePath = "natives/" + platformPath + "/" + libraryName;

        try (InputStream inputStream = NativeLibraryLoader.class
                .getClassLoader()
                .getResourceAsStream(resourcePath)) {

            if (inputStream == null) {
                throw new RuntimeException("Native library not found in JAR: " + resourcePath);
            }

            Path extractedPath = tempDir.resolve(libraryName);
            Files.copy(inputStream, extractedPath, StandardCopyOption.REPLACE_EXISTING);
            extractedPath.toFile().deleteOnExit();

            LOADED_LIBRARIES.add(libraryName);
            LOGGER.info("Successfully extracted and loaded native: {}", libraryName);

        } catch (UnsatisfiedLinkError e) {
            LOGGER.error("Failed to link native library: {}. Check for missing dependencies like LWJGL.", libraryName, e);
            throw e;
        } catch (IOException e) {
            throw new RuntimeException("Failed to extract native library: " + resourcePath, e);
        }
    }

    /**
     * Determines the platform-specific subdirectory.
     */
    private static String getPlatformPath() {
        String os = System.getProperty("os.name").toLowerCase();
        String arch = System.getProperty("os.arch").toLowerCase();

        boolean is64Bit = arch.contains("64") || arch.equals("amd64") || arch.equals("x86_64");

        if (os.contains("win")) {
            return is64Bit ? "windows/x86_64" : "windows/x86";
        } else if (os.contains("mac")) {
            return (arch.contains("aarch64") || arch.contains("arm"))
                    ? "osx/arm64"
                    : "osx/x86_64";
        } else if (os.contains("linux")) {
            return is64Bit ? "linux/x86_64" : "linux/x86";
        }

        throw new UnsupportedOperationException(
                "Unsupported platform: OS=" + os + ", ARCH=" + arch);
    }

    /**
     * Returns the correct native library file name for the current OS.
     */
    private static String getLibraryName(String baseName) {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            return baseName + ".dll";
        } else if (os.contains("mac")) {
            return "lib" + baseName + ".dylib";
        } else if (os.contains("linux")) {
            return "lib" + baseName + ".so";
        }

        throw new UnsupportedOperationException("Unsupported OS: " + os);
    }
}