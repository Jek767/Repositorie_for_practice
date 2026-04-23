package currency.converter.project_interface;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

public class CppBridge {
    private static String getExecutablePath() throws IOException {
        String platformFolder = PlatformDetector.getPlatformFolder();
        String executableName = PlatformDetector.getExecutableName();

        // 1. Пытаемся найти бинарник в папке natives рядом с JAR
        Path externalPath = Path.of("natives", platformFolder, executableName);
        if (Files.exists(externalPath)) {
            return externalPath.toAbsolutePath().toString();
        }

        // 2. Ищем в ресурсах внутри JAR и извлекаем во временную папку
        String resourcePath = "/natives/" + platformFolder + "/" + executableName;
        try (InputStream in = CppBridge.class.getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new FileNotFoundException("Native executable not found: " + resourcePath);
            }

            // Создаём временный файл с правами на выполнение
            Path tempFile = Files.createTempFile("converter_", executableName);
            Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);

            // Устанавливаем права на выполнение (для Linux/mac)
            File file = tempFile.toFile();
            file.setExecutable(true);
            file.deleteOnExit(); // удалится при завершении JVM

            return file.getAbsolutePath();
        }
    }

    /* Вызывает внешний C++ процесс, передаёт запрос и возвращает результат.
     request - строка запроса (формат: "сумма===fromId===toId")
     Возвращает строку ответа от C++ программы (число или сообщение об ошибке) */
    public static String sendRequest(String request, ResourceBundle bundle) {
        try {
            String executablePath = getExecutablePath();
            ProcessBuilder pb = new ProcessBuilder(executablePath);
            Process process = pb.start();
            // Передаём запрос в stdin процесса
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream(), StandardCharsets.UTF_8))) {
                writer.write(request);
                writer.newLine();
                writer.flush();
            }
            // Читаем ответ из stdout
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            String response = reader.readLine();
            // Ждём завершения процесса
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                // Если процесс завершился с ошибкой, читаем stderr
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8));
                StringBuilder errorMsg = new StringBuilder();
                String line;
                while ((line = errorReader.readLine()) != null) {
                    errorMsg.append(line).append("\n");
                }
                return bundle.getString("error.cpp.execution") + " " + errorMsg;
            }
            return response != null ? response.trim() : bundle.getString("error.cpp.empty");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return bundle.getString("error.cpp.io") + " " + e.getMessage();
        }
    }
}
