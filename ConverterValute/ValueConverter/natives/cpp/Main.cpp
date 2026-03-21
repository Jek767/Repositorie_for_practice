#include <filesystem>
#include <iostream>
#include "Api.h"
#include "DataTxt.h"
#include "Timer.h"
#include <nlohmann/json.hpp> 

using namespace std;
using json = nlohmann::json;

int main()
{

	if (std::filesystem::exists("myExchangeRate.txt"))
	{
		string Data = GetText();

		json time = json::parse(Data);

		cout << API_time(time["Date"]);
	}
	else
	{
		string data = GetApi();

		EnteringString(data);
	}
	return 0;
}