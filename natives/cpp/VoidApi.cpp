#include "Api.h"
#include <string>
#include <iostream>
#include <fstream>
#include <curl/curl.h>

static size_t WriteCallBack(void* contents, size_t size, size_t nmemb, std::string* output) 
{
	size_t totalSize = size * nmemb;
	output->append((char*)contents, totalSize);
	return totalSize;
}
std::string GetApi()
{
	std::string url = "https://www.cbr-xml-daily.ru/daily_json.js";
	std::string response = "";
	CURL* curl = curl_easy_init();
	curl_easy_setopt(curl, CURLOPT_URL, url.c_str());
	curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, WriteCallBack);
	curl_easy_setopt(curl, CURLOPT_WRITEDATA, &response);
	curl_easy_setopt(curl, CURLOPT_TIMEOUT, 30L);
	curl_easy_setopt(curl, CURLOPT_USERAGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
	CURLcode res = curl_easy_perform(curl);
	if (res != CURLE_OK) 
	{
		throw std::runtime_error("Ошибка: " + std::string(curl_easy_strerror(res)));
	}
	curl_easy_cleanup(curl);
	return response;
}