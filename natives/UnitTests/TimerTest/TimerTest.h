#pragma once
#include <string>

enum should_be_exception_timer
{
	Exception_caught
};

int TimerTest(std::string DT_testArg, short n);

int TimerTest(std::string DT_testArg, should_be_exception_timer exc, short n);