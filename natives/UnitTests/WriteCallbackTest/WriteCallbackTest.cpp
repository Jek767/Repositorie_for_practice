#include "WriteCallbackTest.h"
#include "VoidApi.cpp"
#include <sstream>
#include <ctime>

int WriteCallBackTest(std::string testData, size_t chunkSize, std::string expectedResult, short n)
{
    try
    {
        std::string output;

        size_t totalProcessed = 0;
        size_t dataSize = testData.size();

        while (totalProcessed < dataSize)
        {
            size_t currentChunk = min(chunkSize, dataSize - totalProcessed);
            size_t result = WriteCallBack((void*)(testData.c_str() + totalProcessed),
                currentChunk,
                1,
                &output);
            totalProcessed += result;

            if (result != currentChunk)
            {
                std::cout << "Test " << n << " failed" << std::endl;
                return 1;
            }
        }

        if (output == expectedResult)
        {
            std::cout << "Test " << n << " completed succesful" << std::endl;
            return 0;
        }
        else
        {
            std::cout << "Test " << n << " failed" << std::endl;
            return 1;
        }
    }
    catch (const std::exception& e)
    {
        std::cout << "Test " << " failed due to exception\n" << e.what() << std::endl;
        return 1;
    }
}