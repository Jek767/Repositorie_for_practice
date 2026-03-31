#include "DataTxt.h"
#include <string>
#include <iostream>
#include <fstream>

void EnteringString(std::string& txt) 
{
	std::ofstream outputFile;
	outputFile.open("myExchangeRate.txt");
    if (outputFile.is_open()) {
        outputFile << txt;

        outputFile.close();
    }
    return;
}
std::string GetText() 
{
    std::ifstream inputFile;
    inputFile.open("myExchangeRate.txt");
    std::string line;
    std::string AllText = "";
    while (std::getline(inputFile, line)) 
    {
        AllText += line;
    }
    inputFile.close();
    return AllText;
}
