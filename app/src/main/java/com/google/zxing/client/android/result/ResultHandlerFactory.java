package com.google.zxing.client.android.result;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;

import io.weichao.summary1704.interfaces.DecodeInterface;

public final class ResultHandlerFactory {
    private ResultHandlerFactory() {
    }

    public static ResultHandler makeResultHandler(DecodeInterface decodeInterface, Result rawResult) {
        ParsedResult result = parseResult(rawResult);
        switch (result.getType()) {
            default:
                return new ResultHandler(decodeInterface, result, rawResult);
        }
    }

    private static ParsedResult parseResult(Result rawResult) {
        return ResultParser.parseResult(rawResult);
    }
}
