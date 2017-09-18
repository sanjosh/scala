#include <jni.h>
 
int main(int argc, char **argv)
{
    JavaVM         *vm;
    JNIEnv         *env;
    JavaVMInitArgs  vm_args;
    jint            res;
    jclass          cls;
    jmethodID       mid;
    jstring         jstr;
    jobjectArray    main_args;
 
    vm_args.version  = JNI_VERSION_1_8;
    vm_args.nOptions = 0;
    res = JNI_CreateJavaVM(&vm, (void **)&env, &vm_args);
    if (res != JNI_OK) {
        printf("Failed to create Java VM\n");
        return 1;
    }
 
    cls = env->FindClass("Main"); 
    if (cls == NULL) {
        printf("Failed to find Main class\n");
        return 1;
    }
 
    mid = env->GetStaticMethodID(cls, "main", "([Ljava/lang/String;)V");
    if (mid == NULL) {
        printf("Failed to find main function\n");
        return 1;
    }
 
    jstr      = env->NewStringUTF("");
    main_args = env->NewObjectArray(1, env->FindClass("java/lang/String"), jstr);
    env->CallStaticVoidMethod(cls, mid, main_args); 
 
    return 0;
}
