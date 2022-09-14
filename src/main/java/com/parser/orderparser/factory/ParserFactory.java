package com.parser.orderparser.factory;

import com.parser.orderparser.constants.ErrorConstants;
import com.parser.orderparser.enums.ParserType;
import com.parser.orderparser.exception.ApplicationException;
import com.parser.orderparser.service.Parsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Arun Singh
 * This  Factory is for Storing Reference For all the File Types Parsers
 * If a new File Parser is to be added only Code for that has to be Implemented.
 * Factory Will Automatically retreive Parser Bean on Load, if that class implement Parsers Interface
 */
@Component
public class ParserFactory {

    private static Map<ParserType, Parsers> elementParsersMap = new EnumMap<>(ParserType.class);

    @Autowired
    public ParserFactory(List<Parsers> parsers) {
        populateFactoryMap(parsers.stream().collect(Collectors.toMap(Parsers:: getParserType, Function.identity())));
    }

    public static Parsers getParser(ParserType parserType) {
        return Optional.ofNullable(elementParsersMap.get(parserType))
                .orElseThrow(() -> new ApplicationException(ErrorConstants.PARSER_NOT_FOUND));
    }

    private static void populateFactoryMap(Map<ParserType, Parsers> factoryMap) {
        elementParsersMap = factoryMap;
    }
}