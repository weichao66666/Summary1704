package com.google.zxing.client.android.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;

import io.weichao.summary1704.interfaces.DecodeInterface;

public class ResultHandler {
    private static final String TAG = ResultHandler.class.getSimpleName();

    private final ParsedResult result;
    private final DecodeInterface decodeInterface;
    private final Result rawResult;

    ResultHandler(DecodeInterface decodeInterface, ParsedResult result, Result rawResult) {
        this.result = result;
        this.decodeInterface = decodeInterface;
        this.rawResult = rawResult;
    }

    public final ParsedResultType getType() {
        return result.getType();
    }

    public CharSequence getDisplayContents() {
        String contents = result.getDisplayResult();
        return contents.replace("\r", "");
    }
}
