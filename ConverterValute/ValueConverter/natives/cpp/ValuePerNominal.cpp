#include <nlohmann/json.hpp>
#include <string>

using json = nlohmann::json;

double ValuePerNominal(const std::string& jsonString, const std::string& currencyCode) {
    json data = json::parse(jsonString);
    
    int nominal = data["Valute"][currencyCode]["Nominal"];
    double value = data["Valute"][currencyCode]["Value"];
    
    return value / nominal;
}
