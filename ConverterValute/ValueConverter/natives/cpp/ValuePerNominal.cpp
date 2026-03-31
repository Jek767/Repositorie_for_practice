#include <nlohmann/json.hpp>
#include <string>
#include "ValuePerNominal.h"

using json = nlohmann::json;

double ValuePerNominal(const std::string& jsonString, const std::string& currencyCode) {
    
    if(currencyCode == "RUB")
    {
        return 1.0;
    }
    else
    {
        json data = json::parse(jsonString);

        int nominal = data["Valute"][currencyCode]["Nominal"];
        double value = data["Valute"][currencyCode]["Value"];

        return value / nominal;
    }
    
}
