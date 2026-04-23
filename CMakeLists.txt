cmake_minimum_required(VERSION 3.10)
project(CurrencyConverter)

# ========== C++ часть ==========
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

 # ======= Сюда добавлять и изменять файлы из проекта ======
add_executable(converter
    natives/cpp/Main.cpp
    natives/cpp/Convert.cpp
    natives/cpp/Timer.cpp
    natives/cpp/ValuePerNominal.cpp
    natives/cpp/VoidApi.cpp
    natives/cpp/VoidDataTxt.cpp
)

# ==========================================================

set(CURL_ROOT ${CMAKE_CURRENT_SOURCE_DIR}/libs/curl-8.19.0_6-win64-mingw)
set(CURL_INCLUDE_DIR "${CMAKE_CURRENT_SOURCE_DIR}/libs/curl-8.19.0_6-win64-mingw/include")
set(CURL_LIBRARY "${CMAKE_CURRENT_SOURCE_DIR}/libs/curl-8.19.0_6-win64-mingw/lib/libcurl.dll.a")
target_include_directories(converter PRIVATE ${CURL_INCLUDE_DIR})
target_link_libraries(converter ${CURL_LIBRARY})

target_include_directories(converter PRIVATE ${CMAKE_CURRENT_SOURCE_DIR}/libs)

if(CMAKE_COMPILER_IS_GNUCXX OR CMAKE_CXX_COMPILER_ID STREQUAL "Clang")
    target_link_libraries(converter stdc++fs)
endif()

if(MSVC)
    target_compile_options(converter PRIVATE /utf-8)
endif()
target_include_directories(converter PRIVATE natives/cpp)

# Определяем платформенную папку (для Windows – win/x64)
if(WIN32)
    set(PLATFORM_FOLDER "win/x64")
elseif(APPLE)
    set(PLATFORM_FOLDER "mac/x64")
else()
    set(PLATFORM_FOLDER "linux/x64")
endif()

add_custom_command(TARGET converter POST_BUILD
    COMMAND ${CMAKE_COMMAND} -E make_directory ${CMAKE_CURRENT_SOURCE_DIR}/natives/${PLATFORM_FOLDER}
    COMMAND ${CMAKE_COMMAND} -E copy
        $<TARGET_FILE:converter>
        ${CMAKE_CURRENT_SOURCE_DIR}/natives/${PLATFORM_FOLDER}/
    COMMAND ${CMAKE_COMMAND} -E copy
        ${CMAKE_CURRENT_SOURCE_DIR}/natives/cpp/myExchangeRate.txt
        ${CMAKE_CURRENT_SOURCE_DIR}/natives/${PLATFORM_FOLDER}/
    COMMAND ${CMAKE_COMMAND} -E copy
        ${CMAKE_CURRENT_SOURCE_DIR}/natives/cpp/myExchangeRate.txt
        $<TARGET_FILE_DIR:converter>
    COMMAND ${CMAKE_COMMAND} -E copy
        ${CMAKE_CURRENT_SOURCE_DIR}/libs/curl-8.19.0_6-win64-mingw/bin/libcurl-x64.dll
        $<TARGET_FILE_DIR:converter>
    COMMAND ${CMAKE_COMMAND} -E copy
        ${CMAKE_CURRENT_SOURCE_DIR}/libs/curl-8.19.0_6-win64-mingw/bin/libcurl-x64.dll
        ${CMAKE_CURRENT_SOURCE_DIR}/natives/${PLATFORM_FOLDER}/
    COMMENT "Copying C++ executable to natives/${PLATFORM_FOLDER}/"
)
# ========== Java часть ==========
# Путь к локальному JDK (относительно корня проекта)
set(LOCAL_JDK "${CMAKE_CURRENT_SOURCE_DIR}/libs/openjdk-25.0.2")

# Проверяем, существует ли локальный JDK и есть ли в нём java
if(EXISTS "${LOCAL_JDK}/bin/java${CMAKE_EXECUTABLE_SUFFIX}")
    message(STATUS "Found local JDK at ${LOCAL_JDK}")
    set(Java_JAVA_EXECUTABLE "${LOCAL_JDK}/bin/java${CMAKE_EXECUTABLE_SUFFIX}" CACHE FILEPATH "Java executable")
    set(Java_JAVAC_EXECUTABLE "${LOCAL_JDK}/bin/javac${CMAKE_EXECUTABLE_SUFFIX}" CACHE FILEPATH "Java compiler")
    set(Java_JAR_EXECUTABLE "${LOCAL_JDK}/bin/jar${CMAKE_EXECUTABLE_SUFFIX}" CACHE FILEPATH "Java archiver")
    set(Java_JAVADOC_EXECUTABLE "${LOCAL_JDK}/bin/javadoc${CMAKE_EXECUTABLE_SUFFIX}" CACHE FILEPATH "Java doc tool")
    set(Java_JAVAH_EXECUTABLE "Java_JAVAH_EXECUTABLE-NOTFOUND" CACHE FILEPATH "javah (not used)" FORCE)
else()
    # Если локального JDK нет, пробуем использовать JAVA_HOME
    if(DEFINED ENV{JAVA_HOME})
        set(JAVA_HOME_PATH $ENV{JAVA_HOME})
        string(REGEX REPLACE "[\\/]*$" "" JAVA_HOME_PATH "${JAVA_HOME_PATH}")
        set(Java_JAVA_EXECUTABLE "${JAVA_HOME_PATH}/bin/java${CMAKE_EXECUTABLE_SUFFIX}" CACHE FILEPATH "Java executable")
        set(Java_JAVAC_EXECUTABLE "${JAVA_HOME_PATH}/bin/javac${CMAKE_EXECUTABLE_SUFFIX}" CACHE FILEPATH "Java compiler")
        set(Java_JAR_EXECUTABLE "${JAVA_HOME_PATH}/bin/jar${CMAKE_EXECUTABLE_SUFFIX}" CACHE FILEPATH "Java archiver")
        set(Java_JAVADOC_EXECUTABLE "${JAVA_HOME_PATH}/bin/javadoc${CMAKE_EXECUTABLE_SUFFIX}" CACHE FILEPATH "Java doc tool")
        set(Java_JAVAH_EXECUTABLE "Java_JAVAH_EXECUTABLE-NOTFOUND" CACHE FILEPATH "javah (not used)" FORCE)
        message(STATUS "Using JAVA_HOME: ${JAVA_HOME_PATH}")
    else()
        message(WARNING "Neither local JDK nor JAVA_HOME is set; attempting to find Java automatically")
    endif()
endif()

find_package(Java REQUIRED COMPONENTS Runtime Development)

set(JAVAFX_SDK "${CMAKE_CURRENT_SOURCE_DIR}/libs/javafx-sdk-25.0.2")
set(JAVAFX_MODULES "${JAVAFX_SDK}/lib")

# Пути к исходникам и ресурсам
set(JAVA_SRC_DIR ${CMAKE_CURRENT_SOURCE_DIR}/src/main/java)
set(RESOURCES_DIR ${CMAKE_CURRENT_SOURCE_DIR}/src/main/resources)
set(CLASSES_DIR ${CMAKE_CURRENT_BINARY_DIR}/classes)
set(JAR_FILE ${CMAKE_CURRENT_SOURCE_DIR}/converter.jar)

# Собираем все .java файлы
file(GLOB_RECURSE JAVA_SOURCES ${JAVA_SRC_DIR}/*.java)

# Копируем ресурсы в папку модуля (внутри CLASSES_DIR)
add_custom_command(
    OUTPUT ${CLASSES_DIR}/.resources-copied
    DEPENDS ${RESOURCES_DIR}
    COMMAND ${CMAKE_COMMAND} -E copy_directory ${RESOURCES_DIR} ${CLASSES_DIR}/currency.converter.project_interface
    COMMAND ${CMAKE_COMMAND} -E touch ${CLASSES_DIR}/.resources-copied
    COMMENT "Copying resources to module directory"
)

# Компилируем Java модули с JavaFX (без изменений)
add_custom_command(
    OUTPUT ${CLASSES_DIR}/.compiled
    DEPENDS ${JAVA_SOURCES} ${CLASSES_DIR}/.resources-copied
    COMMAND ${Java_JAVAC_EXECUTABLE}
        -d ${CLASSES_DIR}
        --module-source-path ${JAVA_SRC_DIR}
        --module-path ${JAVAFX_MODULES}
        ${JAVA_SOURCES}
    COMMAND ${CMAKE_COMMAND} -E touch ${CLASSES_DIR}/.compiled
    COMMENT "Compiling Java modules with JavaFX"
)

# Создаём JAR-файл с правильной модульной структурой
add_custom_command(
    OUTPUT ${JAR_FILE}
    DEPENDS ${CLASSES_DIR}/.compiled
    COMMAND ${Java_JAR_EXECUTABLE} cfe ${JAR_FILE} currency.converter.project_interface.MainApp -C ${CLASSES_DIR}/currency.converter.project_interface .
    COMMENT "Creating converter.jar"
)
# Общая цель сборки
add_custom_target(build_all ALL
    DEPENDS converter ${JAR_FILE}
    COMMENT "Building both C++ and Java parts"
)

set_property(DIRECTORY APPEND PROPERTY ADDITIONAL_CLEAN_FILES
    ${CLASSES_DIR} ${JAR_FILE}
)