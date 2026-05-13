#include <iostream>
#include <filesystem>
#include "APITest/APITest.h"							// ready
#include "ConvertTest/ConvertTest.h"					// ready
#include "EnteringTest/EnteringTest.h"					// ready
#include "GetTest/GetTest.h"							// ready
#include "TimerTest/TimerTest.h"						// ready
#include "ValuePerNominalTest/ValuePerNominalTest.h"	// ready
using namespace std;

int main()
{
	cout << "EnteringString tests" << endl;
	short n = 0;
	EnteringTest("aboba", ++n); // Test with Latin 
	EnteringTest("007", ++n);   // Test with numbers
	EnteringTest("абоба", ++n); // Test with Cyrillic
	cout << endl;

	cout << "GetText tests" << endl;
	n = 0;
	GetTest("aboba", ++n); // Test with Latin 
	GetTest("007", ++n);   // Test with numbers
	GetTest("абоба", ++n); // Test with Cyrillic
	cout << endl;

	cout << "API_time tests" << endl;
	n = 0;
	TimerTest("2020-05-10", ++n); // date from file < current date
	TimerTest("2100-03-07", ++n); // date from file > current date
	TimerTest("0000-00-00", ++n); // test incorrect date 
	TimerTest("2026-12-32", ++n); // test incorrect date 
	TimerTest("9999-12-31", ++n); // test very big date
	cout << endl;

	cout << "ValuePerNominal tests" << endl;
	n = 0;
	ValuePerNominalTest(JsonStub_Standard(), "USD", 88.59, ++n);									 // test with valid JSON-stub
	ValuePerNominalTest(JsonStub_Invalid(), "USD", should_be_exception_value::Exception_value, ++n); // test with invalid JSON-stub
	cout << endl;


	char internet_connection;
	cout << "Do you have stable internet connection? [Y/N] "; // if there is no stable internet connection Convert and GetApi test would be skipped
	cin >> internet_connection;

	if (filesystem::exists("myExchangeRate.txt"))
	{
		filesystem::remove("myExchangeRate.txt");
	}

	if (internet_connection == 'Y')
	{
		cout << "Convert tests" << endl;
		n = 0;

		ConvertTest(90, "RUB", "EUR", ++n); // test RUB -> EUR, normal value
		ConvertTest(90, "RUB", "RUB", ++n); // test RUB -> RUB
		ConvertTest(90, "USD", "RUB", ++n); // test USD -> RUB, normal value
		ConvertTest(90, "USD", "EUR", ++n); // test USD -> EUR, normal value
		ConvertTest( 1, "RUB", "EUR", ++n); // test RUB -> EUR, small value
		ConvertTest( 1, "EUR", "EUR", ++n); // test EUR -> EUR
	}
	cout << endl;

	if (internet_connection == 'Y')
	{
		cout << "GetApi test" << endl;
		GetApiTest(); // test all JSON values
		cout << endl;
	}
	
	cout << "All tests completed" << endl;
	cin.ignore();
	cin.get();
	return 0;
}