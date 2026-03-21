#include <nlohmann/json.hpp>
#include <fstream>
#include <iostream>
#include <string>

using json = nlohmann::json;

/**
 * Читает строку из JSON файла по указанному ключу и возвращает её длину
 * @param filename - имя JSON файла
 * @param key - ключ, по которому находится строка
 * @return длина строки или -1 в случае ошибки
 */
int getStringLengthFromJson(const std::string& filename, const std::string& key) {
    try {
        // Открываем файл
        std::ifstream file(filename);
        if (!file.is_open()) {
            std::cerr << "Ошибка: не удалось открыть файл " << filename << std::endl;
            return -1;
        }
        
        // Парсим JSON
        json data = json::parse(file);
        
        // Проверяем наличие ключа и тип данных
        if (data.contains(key) && data[key].is_string()) {
            std::string value = data[key];
            return value.length();
        } else {
            std::cerr << "Ошибка: ключ '" << key << "' не найден или не является строкой" << std::endl;
            return -1;
        }
    } catch (const json::parse_error& e) {
        std::cerr << "Ошибка парсинга JSON: " << e.what() << std::endl;
        return -1;
    } catch (const std::exception& e) {
        std::cerr << "Ошибка: " << e.what() << std::endl;
        return -1;
    }
}

/**
 * Читает строку из JSON файла и преобразует её в число
 * @param filename - имя JSON файла
 * @param key - ключ, по которому находится строка
 * @return число или 0 в случае ошибки
 */
double getNumberFromJsonString(const std::string& filename, const std::string& key) {
    try {
        std::ifstream file(filename);
        if (!file.is_open()) {
            std::cerr << "Ошибка: не удалось открыть файл " << filename << std::endl;
            return 0;
        }
        
        json data = json::parse(file);
        
        if (data.contains(key) && data[key].is_string()) {
            std::string strValue = data[key];
            try {
                // Пытаемся преобразовать строку в число
                return std::stod(strValue);
            } catch (const std::invalid_argument& e) {
                std::cerr << "Ошибка: строка '" << strValue << "' не является числом" << std::endl;
                return 0;
            }
        } else {
            std::cerr << "Ошибка: ключ '" << key << "' не найден или не является строкой" << std::endl;
            return 0;
        }
    } catch (const std::exception& e) {
        std::cerr << "Ошибка: " << e.what() << std::endl;
        return 0;
    }
}
