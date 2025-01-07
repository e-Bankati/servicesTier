package com.example.servicestiers.multiDevises;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DeviseService {


    public BigDecimal convertToBeneficiaryCurrency(String senderCurrency, String beneficiaryCurrency, BigDecimal amountFromSender) {

        BigDecimal exchangeRate = getExchangeRate(senderCurrency, beneficiaryCurrency);

        if (exchangeRate == null) {
            throw new IllegalArgumentException("Unable to retrieve exchange rate for "
                    + senderCurrency + " to " + beneficiaryCurrency);
        }

        return amountFromSender.multiply(exchangeRate);
    }



    private BigDecimal getExchangeRate(String fromCurrency, String toCurrency) {

        if (fromCurrency.equals("DH") && toCurrency.equals("EUR")) {
            return new BigDecimal("0.091"); // Exemple : 1 DH = 0.091 EUR
        }
        else if (fromCurrency.equals("EUR") && toCurrency.equals("DH")) {
            return new BigDecimal("11.00"); // Exemple : 1 EUR = 11 DH
        }
        else if (fromCurrency.equals("DH") && toCurrency.equals("USD")) {
            return new BigDecimal("0.10"); // Exemple : 1 DH = 0.10 USD
        }
        else if (fromCurrency.equals("USD") && toCurrency.equals("DH")) {
            return new BigDecimal("10.00"); // Exemple : 1 USD = 10 DH
        }
        else if (fromCurrency.equals("EUR") && toCurrency.equals("USD")) {
            return new BigDecimal("1.18"); // Exemple : 1 EUR = 1.18 USD
        }
        else if (fromCurrency.equals("USD") && toCurrency.equals("EUR")) {
            return new BigDecimal("0.85"); // Exemple : 1 USD = 0.85 EUR
        }
        return null; // Taux inconnu
    }


}
