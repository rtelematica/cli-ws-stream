package com.walkoding.cliwsstream.grpc.server;

import java.util.HashMap;
import java.util.Map;

public enum IndicatorType {

    SERVICE_NOT_RECOGNIZED(0),
    STARTING_COGNITIVE_PROCESS(1),
    VIDEO_SOURCE_NOT_FOUND(2),
    START_PROCESSING_FRAMES(3),
    INSUFFICIENT_DATA(4),
    EMPTY_SOURCE(5),
    END_COGNITIVE_PROCESS(6);


    private static Map<Integer, String> messages = new HashMap<>();
    static {
        messages.put(SERVICE_NOT_RECOGNIZED.code, "Service type not recognized");
        messages.put(STARTING_COGNITIVE_PROCESS.code, "Starting cognitive process");
        messages.put(VIDEO_SOURCE_NOT_FOUND.code, "Error: video source not found");
        messages.put(START_PROCESSING_FRAMES.code, "Start processing of frames");
        messages.put(INSUFFICIENT_DATA.code, "Error: Insuficient data");
        messages.put(EMPTY_SOURCE.code, "Error: Empty source");
        messages.put(END_COGNITIVE_PROCESS.code, "End of cognitive process");
    }

    IndicatorType(int code) {
        this.code = code;
    }

    private int code;

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return messages.get(this.code);
    }

}
