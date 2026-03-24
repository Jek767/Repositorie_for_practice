#include <filesystem>
#include <iostream>
#include "Api.h"
#include "DataTxt.h"
#include "Timer.h"
#include <nlohmann/json.hpp> 
#include "Convert.h"

using namespace std;
using json = nlohmann::json;

//Val - количество валюты
//First - трехбуквенное обозначение первой валюты
//Second - трехбуквенное обозначение второй валюты
double convert(const int Val,const string First,const string Second)
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

			json Curency = json::parse(DataBase);

			double usd = Curency["Valute"]["USD"]["Value"];// Тут функция будет Ильи которая достает конкретные данные

			return usd * Val;
		}
		catch (const std::runtime_error& ex)
		{
			std::cerr << "Из-за проблем с сетью не удалось выполнить запрос к API, используются данне из сохраненного ранее файла. Дата сохранения: " << data["Date"] << endl;

			double usd = data["Valute"]["USD"]["Value"];// Тут функция будет Ильи которая достает конкретные данные

			return usd * Val;
		}

	}
	//Если файла с курсами нет
	else
	{
		//Если интенета нет - значение не считается(программа не ломается просто сообщает об этом)
		try
		{
			string dataFileNot = GetApi();

			EnteringString(dataFileNot);

			json dataFileNot2 = json::parse(dataFileNot);

			double usd = dataFileNot2["Valute"]["USD"]["Value"];// Тут функция будет Ильи которая достает конкретные данные

			return usd * 100;
		}
		catch (const std::runtime_error& ex)
		{
			std::cerr << "Из-за проблем с сетью не удалось выполнить запрос к API" << endl;
		}
	}
}