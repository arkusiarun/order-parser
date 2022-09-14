package com.parser.orderparser.service.impl;

import com.parser.orderparser.factory.ParserFactory;
import com.parser.orderparser.service.Parsers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

@RunWith(MockitoJUnitRunner.class)
public class ParserServiceImplTest {

    private CsvParser csvParser;
    private JsonParser jsonParser;
    @Mock
    private ThreadPoolExecutor threadPoolExecutor;

    @InjectMocks
    private ParserServiceImpl parserService;

    @Before
    public void init() {
        Mockito.doAnswer(invocation -> {
            ((Runnable) invocation.getArguments()[0]).run();
            return null;
        }).when(threadPoolExecutor).execute(Mockito.any());
        initializeFactory();
    }

    private void initializeFactory() {
        jsonParser = new JsonParser(threadPoolExecutor);
        csvParser = new CsvParser(threadPoolExecutor);
        List<Parsers> parsersList = new ArrayList<>();
        parsersList.add(jsonParser);
        parsersList.add(csvParser);
        ParserFactory parserFactory = new ParserFactory(parsersList);
    }

    @Test
    public void parse() throws URISyntaxException {
        ClassLoader classLoader = ParserServiceImplTest.class.getClassLoader();
        File file1 = Paths.get(classLoader.getResource("Test.csv").toURI()).toFile();
        File file2 = Paths.get(classLoader.getResource("Test.json").toURI()).toFile();
        String[] fileNames = new String[2];
        fileNames[0] = file1.getAbsolutePath();
        fileNames[1] = file2.getAbsolutePath();
        this.parserService.parse(fileNames);
    }
}