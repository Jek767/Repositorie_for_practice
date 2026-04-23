#include <iostream>
#include <string>
#include <sstream>
#include <vector>
#include "Convert.h"

#ifdef _WIN32
#include <windows.h>
#endif

// Функция для разбиения строки по разделителю "==="
std::vector<std::string> split(const std::string& s, const std::string& delimiter) {
    std::vector<std::string> tokens;
    size_t start = 0;
    size_t end = s.find(delimiter);
    while (end != std::string::npos) {
        tokens.push_back(s.substr(start, end - start));
        start = end + delimiter.length();
        end = s.find(delimiter, start);
    }
    tokens.push_back(s.substr(start));
    return tokens;
}

std::string getMessage(std::string locale) {
    if (locale == "ru") {
        return "Число слишком мало!";
    }
    return "Number is too small!";
}

int main() {
#ifdef _WIN32
    // Устанавливаем UTF-8 для консоли
    SetConsoleOutputCP(CP_UTF8);
    SetConsoleCP(CP_UTF8);
#endif

    std::string request;
    if (!std::getline(std::cin, request)) {
        std::cerr << "Interface-cpp error: " << std::endl;
        return 1;
    }

    // Разделяем по "==="
    auto parts = split(request, "===");

    std::string locale = "en";
    locale = parts[3];

    double amount;
    amount = std::stod(parts[0]);

    std::string inputValue = parts[1];
    std::string outputValue = parts[2];
    // Заглушка конвертации
    double result = convert(amount, inputValue, outputValue, locale);

    // Пример: если сумма слишком мала, выдаём ошибку
    if (result <= 0.01) {
        std::cerr << getMessage(locale) << std::endl;
        return 1;
    }

    std::cout << result << std::endl;

    return 0;
}