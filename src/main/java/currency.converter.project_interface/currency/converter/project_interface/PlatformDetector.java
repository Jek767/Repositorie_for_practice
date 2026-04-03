package currency.converter.project_interface;

public class PlatformDetector {
    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
    private static final String OS_ARCH = System.getProperty("os.arch").toLowerCase();

    public static boolean isWindows() { return OS_NAME.contains("win"); }
    public static boolean isMac() { return OS_NAME.contains("mac"); }
    public static boolean isLinux() { return OS_NAME.contains("nix") || OS_NAME.contains("nux"); }

    public static String getArch() {
        if (OS_ARCH.contains("64")) return "x64";
        if (OS_ARCH.contains("86")) return "x86";
        if (OS_ARCH.contains("arm")) return "arm";
        return OS_ARCH; // fallback
    }

    public static String getPlatformFolder() {
        if (isWindows()) return "win/" + getArch();
        if (isMac()) return "mac/" + getArch();
        if (isLinux()) return "linux/" + getArch();
        throw new UnsupportedOperationException("Unsupported platform: " + OS_NAME);
    }

    public static String getExecutableName() {
        if (isWindows()) return "converter.exe";
        return "converter"; // для Linux/mac
    }
}
