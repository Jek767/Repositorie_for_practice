#include <filesystem>
#include <iostream>
#include "ConvertTest.h"
#include "Convert.h"
#include <fstream>
#include <nlohmann/json.hpp>
#include <cmath>
using namespace std;
using json = nlohmann::json;

int ConvertTest(const int Val, const string First, const string Second, short n)
{
	double actualResult;

	try
	{
		actualResult = convert(Val, First, Second, "en");
	}
	catch (const std::exception& e)
	{
		cout << "Test " << n << " failed due to exception\n" << e.what() << endl;
		return 1;
	}

	cout << "Test " << n << " completed succesful" << endl;
	return 0;
}