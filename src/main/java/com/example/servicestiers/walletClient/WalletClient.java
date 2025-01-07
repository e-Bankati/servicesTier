package com.example.servicestiers.walletClient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "wallet-service",
        url = "${application.config.wallet-url}"
)
public interface WalletClient {

    @PostMapping("/addWallet")
    public ResponseEntity<String> saveWallet(@RequestBody WalletRequest walletRequest);

    @GetMapping("/getwallet/{walletId}")
    public ResponseEntity<WalletResponse> getWallet(@PathVariable("walletId") String walletId);


    @GetMapping("/IdClient/{clientId}")
    public ResponseEntity<WalletResponse> getWalletByIdClient(@PathVariable("clientId") String clientId);

    @PutMapping("/updateWallet")
    public ResponseEntity<WalletResponse> updateWallet(@RequestBody WalletRequest walletRequest);


    @GetMapping("/getBankAccount/{bankAccountId}")
    public ResponseEntity<BankAccountResponse> getBankAccount(@PathVariable("bankAccountId") String bankAccountId);


    @PutMapping("/updateBankAccount")
    public String updateBankAccount(@RequestBody BankAccountResponse bankAccountRequest);

}
