#pragma once
#include <string>

enum should_be_exception_value
{
	Exception_value
};

int ValuePerNominalTest(std::string jsonString, std::string currencyCode, double expectedResult, short n);

int ValuePerNominalTest(std::string jsonString, std::string currencyCode, should_be_exception_value exc, short n);

std::string JsonStub_Standard();
std::string JsonStub_Invalid();