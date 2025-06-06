package com.example.smartstockanalysis;

import com.example.smartstockanalysis.model.StockData;
import com.example.smartstockanalysis.utils.DataPreprocessingUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataPreprocessingUtilsTest {

    @Test
    void testExtractNormalizedClosingPrices() {
        List<StockData> data = Arrays.asList(
                new StockData(new Date(), 0, 0, 0, 10.0, 0),
                new StockData(new Date(), 0, 0, 0, 20.0, 0),
                new StockData(new Date(), 0, 0, 0, 30.0, 0)
        );

        List<Double> normalized = DataPreprocessingUtils.extractNormalizedClosingPrices(data);

        assertEquals(3, normalized.size());
        assertEquals(0.0, normalized.get(0));
        assertEquals(0.5, normalized.get(1));
        assertEquals(1.0, normalized.get(2));
    }

    @Test
    void testExtractNormalizedClosingPricesConstantValues() {
        List<StockData> data = Arrays.asList(
                new StockData(new Date(), 0, 0, 0, 10.0, 0),
                new StockData(new Date(), 0, 0, 0, 10.0, 0),
                new StockData(new Date(), 0, 0, 0, 10.0, 0)
        );

        List<Double> normalized = DataPreprocessingUtils.extractNormalizedClosingPrices(data);

        assertEquals(3, normalized.size());
        assertEquals(0.0, normalized.get(0));
        assertEquals(0.0, normalized.get(1));
        assertEquals(0.0, normalized.get(2));
    }
}
