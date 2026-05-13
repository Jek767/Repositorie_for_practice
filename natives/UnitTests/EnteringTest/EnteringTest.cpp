#include "EnteringTest.h"
#include "DataTxt.h"
#include <fstream>
#include <iostream>

int EnteringTest(std::string expectedResult, short n)
{
	try
	{
		EnteringString(expectedResult);
		std::string CurrentResult;

		std::ifstream Result;
		Result.open("myExchangeRate.txt");

		if (Result.is_open())
		{
			std::string line;
			while (std::getline(Result, line))
			{
				CurrentResult += line;

				Result.close();
			}
		}
		else
		{
			std::cerr << "Test " << n << " failed due to file opening error " << std::endl;
		}

		if (CurrentResult == expectedResult)
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