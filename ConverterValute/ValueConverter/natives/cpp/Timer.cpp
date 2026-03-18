#include "Timer.h"
#include <iostream>
#include <string>
#include <iomanip>
#include <ctime>
#include <sstream>


using namespace std;
//Функция решает обращаться ли снова api
string API_time(string dateTime)
{
    stringstream ss;// строка для потокового ввода

    time_t t = time(0); // время с начала 1970 года

#pragma warning(suppress : 4996)// чтоб компилятор не ругался
    tm time = *localtime(&t);// получаем местное время 

    ss << put_time(&time, "%Y-%m-%dT%H:%M:%S");// форматируем его и вводим в строку

    string nowT = ss.str();

    if (nowT <= dateTime)
    {
        return "Data from fail";//Тут функция вани которая обращается к файлу с валютами
    }
    else
    {
        return "Data from API";//тут функция Вани которая обращается к api 
    }
}