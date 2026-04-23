#include <filesystem>
#include <iostream>
#include "Api.h"
#include "DataTxt.h"
#include "Timer.h"
#include <nlohmann/json.hpp> 
#include "Convert.h"
#include "ValuePerNominal.h"

using namespace std;
using json = nlohmann::json;

std::string GetLocaleMessage(std::string locale, std::string key) {
	if (locale == "ru") {
		if (key == "NE") return "Из-за проблем с сетью не удалось выполнить запрос к API, используются данне из сохраненного ранее файла. Дата сохранения: ";
		else if (key == "UNE") return "Из-за проблем с сетью не удалось выполнить запрос к API.";
	}
	else {
		if (key == "NE") return "Due to network issues, the API request could not be completed, and data from a previously saved file is being used. Date of saving: ";
		else if (key == "UNE") return "Due to network issues, the API request could not be completed.";
	}
}

//Val - количество валюты
//First - трехбуквенное обозначение первой валюты
//Second - трехбуквенное обозначение второй валюты
//locale - локаль ("ru" / "en")
double convert(const double Val,const string First,const string Second, const string locale)
{
	//Если файл с курсами есть
	if (std::filesystem::exists("myExchangeRate.txt"))
	{
		string parser = GetText();

		json data = json::parse(parser);

		//Если нет интернета значение считается по курсу сохренному в файле
		try
		{
			string DataBase = API_time(data["Date"]);

			EnteringString(DataBase);


			double FirstCurency = ValuePerNominal(DataBase, First);// Получаем курс второй валюты

			double SecondCurency = ValuePerNominal(DataBase, Second);// Получаем курс второй валюты

			return FirstCurency * Val/SecondCurency;
		}
		catch (const std::runtime_error& ex)
		{
			std::cerr << GetLocaleMessage(locale, "NE") << data["Date"] << endl;

			double FirstCurency = ValuePerNominal(parser, First);// Получаем курс второй валюты

			double SecondCurency = ValuePerNominal(parser, Second);// Получаем курс второй валюты

			return FirstCurency * Val / SecondCurency;
		}

	}
	//Если файла с курсами нет
	else
	{
		//Если интенета нет - значение не считается(программа не ломается просто сообщает об этом)
		try
		{
			string dataFileNot = GetApi(locale);

			EnteringString(dataFileNot);

			double FirstCurency = ValuePerNominal(dataFileNot, First);// Получаем курс второй валюты

			double SecondCurency = ValuePerNominal(dataFileNot, Second);// Получаем курс второй валюты

			return FirstCurency * Val / SecondCurency;
		}
		catch (const std::runtime_error& ex)
		{
			std::cerr << GetLocaleMessage(locale, "UNE") << endl;
		}
	}
}