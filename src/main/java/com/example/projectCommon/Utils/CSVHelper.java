package com.example.projectCommon.Utils;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

@Component
public class CSVHelper {

    private static final Logger logger = LoggerFactory.getLogger(CSVHelper.class);

    public <T> List<T> csvBeanBuilder(String csvString, Class<T> T) throws IOException {
        CSVToObject<T> csvToObject = new CSVToObject();
        try (Reader reader = new StringReader(csvString)){
            CsvToBean cb = new CsvToBeanBuilder<T>(reader).withType(T).build();
            csvToObject.setCsvList(cb.parse());
        }
        return csvToObject.getCsvList();
    }


    public <T> void writeCSVFromBean(String fileName, Class<T> T, List<T> csvData) {

        final CSVMappingStrategy<T> mappingStrategy = new CSVMappingStrategy<>();
        mappingStrategy.setType(T);

        try (Writer writer = new FileWriter(fileName)){
            StatefulBeanToCsv<T> sbc = new StatefulBeanToCsvBuilder<T>(writer)
                    .withMappingStrategy(mappingStrategy)
                    .withOrderedResults(true)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();
            sbc.write(csvData);;
        }
        catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            throw new RuntimeException(e);
        }
    }
}
