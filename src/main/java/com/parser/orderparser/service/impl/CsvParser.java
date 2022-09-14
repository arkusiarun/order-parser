package com.parser.orderparser.service.impl;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.parser.orderparser.constants.AppConstants;
import com.parser.orderparser.dto.Output;
import com.parser.orderparser.enums.ParserType;
import com.parser.orderparser.service.Parsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @author Arun Singh
 * Parser Implementation For CSV Type
 */

@Service
public class CsvParser implements Parsers {

    @Autowired
    @Qualifier("parserExecutor")
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public ParserType getParserType() {
        return ParserType.CSV;
    }

    @Override
    public List<Output> parseAndConvert(String fileName, FileReader fileReader) {
        List<Output> outputList = new ArrayList<>();
        List<CompletableFuture<Output>> completableFutureList = new ArrayList<>();
        try (CSVReader reader = new CSVReaderBuilder(
                fileReader)
                .withCSVParser(buildCsvParser())
                .build()) {
            List<String[]> r = reader.readAll();
            int lineNumber = 0;
            for (String[] str : r) {
                lineNumber++;
                int finalLineNumber = lineNumber;
                completableFutureList.add(CompletableFuture.supplyAsync(()-> {
                    return populateOutputData(str, fileName, finalLineNumber);
                }, threadPoolExecutor));
                outputList = completableFutureList.stream().map(CompletableFuture::join)
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {

        }
        return  outputList;
    }

    private CSVParser buildCsvParser() {
        return new CSVParserBuilder().withSeparator(AppConstants.CSV_SEPERATOR).build();
    }

    private Output populateOutputData(String[] strings, String fileName, Integer lineNumber) {
        Output output = new Output();
        try {
            output.setFilename(fileName);
            output.setLine(lineNumber);
            output.setOrderId(Long.parseLong(strings[0]));
            output.setComment(strings[3]);
            output.setAmount(Double.parseDouble(strings[1]));
            output.setResult(AppConstants.RESULT_OK);
        } catch (Exception e) {
            output.setResult(e.getMessage());
        }
        return output;
    }
}