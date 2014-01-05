LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := colorengine
LOCAL_SRC_FILES := com_hwanee_engine_ColorEngine.c
LOCAL_LDLIBS    := -lm -llog

include $(BUILD_SHARED_LIBRARY)