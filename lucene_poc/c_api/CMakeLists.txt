
cmake_minimum_required(VERSION 2.8 FATAL_ERROR)

project(lucene_from_c C CXX)

set(CMAKE_VERBOSE_MAKEFILE on)

set(CMAKE_COLOR_MAKEFILE)

set(CXX_STANDARD c++14)

set(CMAKE_BUILD_TYPE Release)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=${CXX_STANDARD} -Wnon-virtual-dtor -Woverloaded-virtual -DBOOST_LOG_DYN_LINK")

find_package(JNI REQUIRED)


# these below apply to C and C++ files
add_compile_options(
  -Wall
  -Wextra
  -Wstrict-aliasing
  -Wno-unused-parameter
  -Wno-missing-field-initializers
  -Wchar-subscripts
  -Wpointer-arith
  -Wformat
  -Wformat-security
  -Werror=format-security
  -fstack-protector-all
  -fPIE
  -fpie
  -fPIC
  -fpic
  -pipe
  -fdata-sections
  -ffunction-sections
)


#set(JLI_LIB /usr/lib/jvm/java-8-openjdk-amd64//jni.h)

ADD_EXECUTABLE(lucene_caller
    lucene_caller.cpp
		)

target_include_directories(lucene_caller PUBLIC 
	"$ENV{JAVA_HOME}/../include"
	"$ENV{JAVA_HOME}/../include/linux" # for jni_md.h
	)

set (JLI_LIB "$ENV{JAVA_HOME}/lib/amd64/jli/libjli.so")
set (JVM_LIB "$ENV{JAVA_HOME}/lib/amd64/server/libjvm.so")

TARGET_LINK_LIBRARIES(lucene_caller
		${JLI_LIB}
		${JVM_LIB}
    pthread
    rt)

