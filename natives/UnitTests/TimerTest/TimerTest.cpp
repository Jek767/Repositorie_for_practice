#include "TimerTest.h"
#include "Timer.h"
#include <iostream>

// DT_testArg - string in format "YYYY-mm-dd"
int TimerTest(std::string DT_testArg, short n)
{
	try
	{
		std::string currentResult = API_time(DT_testArg, "en");

		std::cout << "Test " << n << " completed succesful" << std::endl;
		return 0;
	}
	catch (const std::exception& e)
	{
		std::cout << "Test " << n << " failed due to exception\n" << e.what() << std::endl;
		return 1;
	}
}

int TimerTest(std::string DT_testArg, should_be_exception_timer exc, short n)
{
	try
	{
		std::string currentResult = API_time(DT_testArg, "en");
		
		std::cout << "Test " << n << " completed succesful :\nNo exception were caught" << std::endl;
		return 1;
	}
	catch (const std::exception& e)
	{
		std::cout << "Test " << n << " failed. Exception\n" << e.what() << std::endl;
		return 0;
	}
}