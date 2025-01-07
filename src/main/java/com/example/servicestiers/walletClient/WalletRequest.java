package com.example.servicestiers.walletClient;

public record WalletRequest(
        String id,
        Double balance,
        String clientId,

        String bankAccountId

        ) {
}
