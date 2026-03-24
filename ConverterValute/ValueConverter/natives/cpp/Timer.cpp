#include "Timer.h"
#include <iostream>
#include <string>
#include <iomanip>
#include <ctime>
#include <sstream>
#include "Api.h"
#include "DataTxt.h"


using namespace std;

//Функция решает обращаться ли снова api
string API_time(string dateTime)
{
    dateTime = dateTime.substr(0, 10);

    stringstream ss;// строка для потокового ввода

    time_t t = time(0); // время с начала 1970 года

#pragma warning(suppress : 4996)// чтоб компилятор не ругался
    tm time = *localtime(&t);// получаем местное время 

    ss << put_time(&time, "%Y-%m-%d");// форматируем его и вводим в строку

    string nowT = ss.str();

    if (nowT > dateTime)
    {

        return GetApi();//тут функция Вани которая обращается к api 
        
    }
    else
    {
        return GetText();//Тут функция вани которая обращается к файлу с валютами
    }
}