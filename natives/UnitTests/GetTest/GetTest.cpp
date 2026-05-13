#include "GetTest.h"
#include "DataTxt.h"
#include <iostream>
#include <fstream>

int GetTest(std::string expectedResult, short n)
{
	try
	{
		std::ofstream TestFile;
		TestFile.open("myExchangeRate.txt");
		if (TestFile.is_open())
		{
			TestFile << expectedResult;
			TestFile.close();
		}
		else
		{
			std::cerr << "Test " << n << " failed due to file opening error " << std::endl;
		}
		std::string currentResult = GetText();

		if (currentResult == expectedResult)
		{
			std::cout << "Test " << n << " completed succesful" << std::endl;
			return 0;
		}
		else
		{
			std::cout << "Test " << n << " failed" << std::endl;
			std::cout << currentResult << std::endl;
			return 1;
		}
	}
	catch (const std::exception& e)
	{
		std::cout << "Test " << n << " failed due to exception\n" << e.what() << std::endl;
		return 1;
	}
}