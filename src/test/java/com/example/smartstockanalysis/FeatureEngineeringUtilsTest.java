package com.example.smartstockanalysis;

import com.example.smartstockanalysis.utils.FeatureEngineeringUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FeatureEngineeringUtilsTest {

    @Test
    void testCalculateSMA() {
        List<Double> prices = Arrays.asList(10.0, 20.0, 30.0, 40.0, 50.0);
        List<Double> sma = FeatureEngineeringUtils.calculateSMA(prices, 3);

        assertEquals(3, sma.size());
        assertEquals(20.0, sma.get(0)); // (10+20+30)/3
        assertEquals(30.0, sma.get(1)); // (20+30+40)/3
        assertEquals(40.0, sma.get(2)); // (30+40+50)/3
    }

    @Test
    void testCalculateRSI() {
        List<Double> prices = Arrays.asList(44.0, 46.0, 47.0, 45.0, 48.0, 50.0, 49.0, 51.0, 53.0, 52.0, 55.0, 56.0, 58.0, 60.0, 59.0);
        List<Double> rsi = FeatureEngineeringUtils.calculateRSI(prices, 14);

        assertEquals(1, rsi.size()); // Solo 1 valore possibile con periodo 14
        // Puoi aggiungere un controllo sul valore atteso se conosci il calcolo esatto
    }
}
