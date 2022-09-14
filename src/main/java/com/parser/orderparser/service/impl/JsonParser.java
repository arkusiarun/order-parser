package com.parser.orderparser.service.impl;

import com.parser.orderparser.constants.AppConstants;
import com.parser.orderparser.dto.Output;
import com.parser.orderparser.enums.ParserType;
import com.parser.orderparser.service.Parsers;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @author Arun Singh
 * Parser Implementation For Json Type
 */

@Service
public class JsonParser implements Parsers {

    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    public JsonParser(@Qualifier("parserExecutor") ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    @Override
    public ParserType getParserType() {
        return ParserType.JSON;
    }

    @Override
    public List<Output> parseAndConvert(String fileName, FileReader fileReader) {
        List<Output> outputList = new ArrayList<>();

        try {
            List<CompletableFuture<Output>> completableFutureList = new ArrayList<>();
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            JSONParser jsonParser = new JSONParser();
            String jsonData;
            int lineNumber = 0;
            while ((jsonData = bufferedReader.readLine()) != null) {
                String finalJsonData = jsonData;
                lineNumber++;

                int finalLineNumber = lineNumber;
                completableFutureList.add(CompletableFuture.supplyAsync(()-> {
                    return populateOutputData(jsonParser, finalJsonData, fileName, finalLineNumber);
                }, threadPoolExecutor));
                outputList = completableFutureList.stream().map(CompletableFuture::join)
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
        }
        return outputList;
    }

    /**
    Populate Output Object From Json  String
     **/
    private Output populateOutputData(JSONParser jsonParser, String jsonString, String fileName, Integer lineNumber) {
        Output output = new Output();
        try {
            output.setFilename(fileName);
            output.setLine(lineNumber);
            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);
            output.setOrderId((Long) jsonObject.get("orderId"));
            output.setComment(String.valueOf(jsonObject.get("comment")));
            output.setAmount((Double) jsonObject.get("amount"));
            output.setResult(AppConstants.RESULT_OK);
        } catch(Exception e) {
            output.setResult(e.getMessage());
        }
        return output;
    }
}