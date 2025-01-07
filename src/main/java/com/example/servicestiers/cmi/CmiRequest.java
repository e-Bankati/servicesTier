package com.example.servicestiers.cmi;

import java.math.BigDecimal;

public record CmiRequest(
        String senderCurrency,
        String beneficiaryCurrency,
        BigDecimal amount,
        String senderId,
        String beneficiaryId
) {
}
