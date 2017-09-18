#include <jni.h>
#include <exception>
#include <iostream>

/**
 * http://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/types.html
 * 
 * use output of javap -s to find method signature
 */
 
int main(int argc, char **argv)
{
    JavaVM         *vm;
    JNIEnv         *env;
    JavaVMInitArgs  vm_args;
    jint            res;
 
    vm_args.version  = JNI_VERSION_1_8;
    vm_args.nOptions = 1;

		// pick this from command line 
	  char* cp = (char *)"-Djava.class.path=.:/home/sandeep/.m2/repository/org/apache/lucene/lucene-core/6.6.0/lucene-core-6.6.0.jar:/home/sandeep/.m2/repository/org/apache/lucene/lucene-queryparser/6.6.0/lucene-queryparser-6.6.0.jar";

		JavaVMOption opts[2];
    opts[0].optionString = cp;
    opts[1].optionString = (char*)"-Dverbose:class,jni";
    //opts[2].optionString = (char*)"-Xcheck:jni:all";
    vm_args.options = opts;

    res = JNI_CreateJavaVM(&vm, (void **)&env, &vm_args);
    if (res != JNI_OK) {
        printf("Failed to create Java VM\n");
        return 1;
    }

		// java lang string
    jclass string_cls = env->FindClass("java/lang/String"); 
    if (string_cls == nullptr) {
        printf("Failed to find String class\n");
        return 1;
    }

    jmethodID string_ctor = env->GetMethodID(string_cls, "<init>", "()V");
    if (string_ctor == nullptr) {
        printf("Failed to find string_ctor \n");
        return 1;
    }

		// StandardAnalyzer analyzer = new StandardAnalyzer()
    jclass analyzer_cls = env->FindClass("org/apache/lucene/analysis/standard/StandardAnalyzer"); 
    if (analyzer_cls == nullptr) {
        printf("Failed to find StandardAnalyzer class\n");
        return 1;
    }
 
    jmethodID analyzer_ctor = env->GetMethodID(analyzer_cls, "<init>", "()V");
    if (analyzer_ctor == nullptr) {
        printf("Failed to find analyzer_ctor \n");
        return 1;
    }

		// Query q = new QueryParser("title", analyzer)
    jclass queryparser_cls = env->FindClass("org/apache/lucene/queryparser/classic/QueryParser"); 
    if (queryparser_cls == nullptr) {
        printf("Failed to find QueryParser class\n");
        return 1;
    }

		jmethodID queryparser_ctor = env->GetMethodID(queryparser_cls, 
			"<init>", 
			"(Ljava/lang/String;Lorg/apache/lucene/analysis/Analyzer;)V");
		if (queryparser_ctor == nullptr) {
				printf("Failed to find queryparser_ctor \n");
				return 1;
		}

		jmethodID parse_method = env->GetMethodID(queryparser_cls, 
			"parse", 
			"(Ljava/lang/String;)Lorg/apache/lucene/search/Query;");
		if (parse_method == nullptr) {
				printf("Failed to find parse method \n");
				return 1;
		}

    jclass directory_reader_cls = env->FindClass("org/apache/lucene/index/DirectoryReader"); 
    if (directory_reader_cls == nullptr) {
        printf("Failed to find DirectoryReader class\n");
        return 1;
    }

		jmethodID open_method = env->GetStaticMethodID(directory_reader_cls, 
			"open", 
			"(Lorg/apache/lucene/store/Directory;)Lorg/apache/lucene/index/DirectoryReader;");

		if (open_method == nullptr) {
				printf("Failed to find open method \n");
				return 1;
		}

    jclass index_searcher_cls = env->FindClass("org/apache/lucene/search/IndexSearcher"); 
    if (index_searcher_cls == nullptr) {
        printf("Failed to find IndexSearcher class\n");
        return 1;
    }

		jmethodID index_searcher_ctor = env->GetMethodID(index_searcher_cls, 
			"<init>", 
			"(Lorg/apache/lucene/index/IndexReader;)V");
		if (index_searcher_ctor == nullptr) {
				printf("Failed to find IndexSearcher ctor \n");
				return 1;
		}

    jclass top_collector_cls = env->FindClass("org/apache/lucene/search/TopScoreDocCollector"); 
    if (top_collector_cls == nullptr) {
        printf("Failed to find TopScoreDocCollector class\n");
        return 1;
    }

		jmethodID top_collector_create = env->GetStaticMethodID(top_collector_cls, 
			"create", 
			"(I)Lorg/apache/lucene/search/TopScoreDocCollector;");
		if (top_collector_create == nullptr) {
				printf("Failed to find TopScoreDocCollector create \n");
				return 1;
		}

		jobject analyzer = env->NewObject(analyzer_cls, analyzer_ctor);

		try {

			jobject queryparser = env->NewObject(queryparser_cls, 
				queryparser_ctor, 
				"title",
				analyzer);

			jobject search_string = env->NewObject(string_cls, string_ctor, "title");

			// Call QueryParser.parse(search_string)
			jobject ret = env->CallObjectMethod(queryparser, parse_method, search_string);
			(void)ret;

		} catch (const std::exception& e) { 
			std::cout << e.what() << std::endl;
		}

    return 0;
}
