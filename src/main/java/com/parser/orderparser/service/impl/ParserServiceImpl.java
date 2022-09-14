package com.parser.orderparser.service.impl;

import com.parser.orderparser.constants.AppConstants;
import com.parser.orderparser.dto.Output;
import com.parser.orderparser.enums.ParserType;
import com.parser.orderparser.factory.ParserFactory;
import com.parser.orderparser.service.ParserService;
import com.parser.orderparser.util.CommonUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Arun Singh
 * Main Service Class Responsible for Calling factory Methods
 * based on file Extension and print result.
 */

@Service
public class ParserServiceImpl implements ParserService {

    @Override
    public void parse(String[] fileNames) {
        try {
            List<Output> result = new ArrayList<>();
            for (String fileName : fileNames) {
                String[] file = fileName.split(AppConstants.SPLIT_DELIMITER);
                /*
                Call to Factory and Store Resultant in a List.
                 */
                result.addAll(ParserFactory.getParser(ParserType.valueOfLabel(file[1]))
                        .parseAndConvert(fileName, CommonUtils.readSourceFile(fileName)));
            }

            /*
            Sort Resultant List Based on OrderId Field
            Generate identifier and Print Sequentially
             */
            int counter = 0;
            for (Output output : result.stream()
                    .sorted(Comparator.comparingLong(Output::getOrderId))
                    .collect(Collectors.toList())) {
                counter++;
                output.setId(counter);
                System.out.println(output);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}