package com.example.wholesaler.helper;

import com.example.wholesaler.model.Product;
import com.example.wholesaler.model.Wholesaler;
import org.apache.commons.csv.*;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class CsvReader {



//    public void readWholesalerCsv( ArrayList<Wholesaler> wholsalerList) throws IOException {
//        try (
//                Reader reader = Files.newBufferedReader(Paths.get("/Users/ruthsan/Downloads/springDemo 2/csvfiles/wholesalers.csv"));
//                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
//        ){
//            for (CSVRecord csvRecord : csvParser) {
//                int id = Integer.parseInt(csvRecord.get(0));
//                String name = csvRecord.get(1);
//                ArrayList<Product> wholesalersProductList = new ArrayList<Product>();
//                Wholesaler wholesaler = new Wholesaler(id, name, wholesalersProductList);
//                wholsalerList.add(wholesaler);
//            }}
//
//    }

}
