package br.com.catalogo.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Fornece conexões JDBC configuradas em db.properties.
 */
public final class ConnectionFactory {
    private static final Properties PROPERTIES = carregarPropriedades();

    private ConnectionFactory() {
    }

    /** Abre uma conexão com o banco configurado. */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(PROPERTIES.getProperty("db.url"),
                PROPERTIES.getProperty("db.user"), PROPERTIES.getProperty("db.password"));
    }

    private static Properties carregarPropriedades() {
        Properties properties = new Properties();
        try (InputStream input = ConnectionFactory.class.getClassLoader()
                .getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new IllegalStateException("Arquivo db.properties não encontrado.");
            }
            properties.load(input);
            carregarEnvFile(properties);
            aplicarVariavelDeAmbiente(properties, "DB_URL", "db.url");
            aplicarVariavelDeAmbiente(properties, "DB_USER", "db.user");
            aplicarVariavelDeAmbiente(properties, "DB_PASSWORD", "db.password");
            return properties;
        } catch (IOException e) {
            throw new IllegalStateException("Não foi possível carregar db.properties.", e);
        }
    }

    private static void carregarEnvFile(Properties properties) throws IOException {
        Path envPath = Path.of(System.getProperty("user.dir"), ".env");
        if (!Files.exists(envPath)) {
            return;
        }

        for (String line : Files.readAllLines(envPath, StandardCharsets.UTF_8)) {
            String trimmed = line.trim();
            if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                continue;
            }
            int separator = trimmed.indexOf('=');
            if (separator <= 0) {
                continue;
            }
            String key = trimmed.substring(0, separator).trim();
            String value = removerAspas(trimmed.substring(separator + 1).trim());
            if ("DB_URL".equals(key)) {
                properties.setProperty("db.url", value);
            } else if ("DB_USER".equals(key)) {
                properties.setProperty("db.user", value);
            } else if ("DB_PASSWORD".equals(key)) {
                properties.setProperty("db.password", value);
            }
        }
    }

    private static void aplicarVariavelDeAmbiente(Properties properties, String envKey, String propertyKey) {
        String value = System.getenv(envKey);
        if (value != null && !value.isBlank()) {
            properties.setProperty(propertyKey, value);
        }
    }

    private static String removerAspas(String value) {
        if (value.length() >= 2
                && ((value.startsWith("\"") && value.endsWith("\""))
                || (value.startsWith("'") && value.endsWith("'")))) {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }
}
