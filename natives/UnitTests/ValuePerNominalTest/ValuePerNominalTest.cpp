#include "ValuePerNominalTest.h"
#include "ValuePerNominal.h"
#include <iostream>
#include <cmath>
#include <sstream>

int ValuePerNominalTest(std::string jsonString, std::string currencyCode, double expectedResult, short n)
{
    try
    {
        double currentResult = ValuePerNominal(jsonString, currencyCode);

        if (std::abs(currentResult - expectedResult) < 0.0001)
        {
            std::cout << "Test " << n << " completed succesful" << std::endl;
            return 0;
        }
        else
        {
            std::cout << "Test " << n << " failed" << std::endl;
            return 1;
        }
    }
    catch (const std::exception& e)
    {
        std::cout << "Test " << n << " failed due to exception\n" << e.what() << std::endl;
        return 1;
    }
}

int ValuePerNominalTest(std::string jsonString, std::string currencyCode, should_be_exception_value exc, short n)
{
    try
    {
        double currentResult = ValuePerNominal(jsonString, currencyCode);

        std::cout << "Test " << n << " completed succesful. Exception caught by function" << std::endl;
        return 0;
    }
    catch (const std::exception& e)
    {
        std::cout << "Test " << n << " completed succesful. Exception\n" << e.what() << std::endl;
        return 0;
    }
}

std::string JsonStub_Standard()
{
    return R"({
        "Date": "2024-01-15T11:44:00+03:00",
        "Valute": {
            "USD": {
                "Nominal": 1,
                "Value": 88.59
            },            
            "KZT": {
                "Nominal": 100,
                "Value": 19.45
            },
            "CNY": {
                "Nominal": 10,
                "Value": 124.30
            },
            "GBP": {
                "Nominal": 1,
                "Value": 112.80
            }
        }
    })";
}

std::string JsonStub_Invalid()
{
    return "{invalid json";
}
