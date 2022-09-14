package com.parser.orderparser.service;

import com.parser.orderparser.dto.Output;
import com.parser.orderparser.enums.ParserType;

import java.io.FileReader;
import java.util.List;

public interface Parsers {

    ParserType getParserType();

    List<Output> parseAndConvert(String fileName, FileReader fileReader);
}