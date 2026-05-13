#include "APITest.h"
#include "Api.h"
#include <string>
#include <iostream>
#include <sstream>
#include <iomanip>
#include <ctime>
#include <curl/curl.h>
#include <nlohmann/json.hpp>
using json = nlohmann::json;

int GetApiTest()
{
    json currentResult;

    try
    {
        currentResult = json::parse(GetApi("en"));

        for (json valute : currentResult["Valute"])
        {
            if (valute["Value"] <= 0)
            {
                throw std::runtime_error("Valute value error");
                return 1;
            }
        }

    }
    catch (const std::exception& e)
    {
        std::cout << "Test failed due to exception\n" << e.what() << std::endl;
        return 1;
    }

    std::cout << "Test completed succesful" << std::endl;
    return 0;
}