package com.example.servicestiers.cmi;

import com.example.servicestiers.multiDevises.DeviseService;
import com.example.servicestiers.walletClient.BankAccountResponse;
import com.example.servicestiers.walletClient.WalletClient;
import com.example.servicestiers.walletClient.WalletRequest;
import com.example.servicestiers.walletClient.WalletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;


@RestController
@RequestMapping("/api/v1/servicestiers")
@RequiredArgsConstructor
public class cmiService {


    private final WalletClient walletClient;

    private final DeviseService deviseService;

    @PostMapping("/doTransaction")
    public  ResponseEntity<CmiResponse> doTransaction(@RequestBody CmiRequest cmiRequest){

        WalletResponse walletSender = walletClient.getWalletByIdClient(cmiRequest.senderId()).getBody();
        WalletResponse walletBeneficiary = walletClient.getWalletByIdClient(cmiRequest.beneficiaryId()).getBody();

        // Currency
        double amount = 0;
        if(!Objects.equals(cmiRequest.senderCurrency(), cmiRequest.beneficiaryCurrency())) {
            amount = deviseService.convertToBeneficiaryCurrency(cmiRequest.senderCurrency(), cmiRequest.beneficiaryCurrency(),cmiRequest.amount()).doubleValue();
        }else{
            amount = cmiRequest.amount().doubleValue();
        }


        if (walletSender.balance() >= amount) {
            WalletRequest walletSenderUpdate = new WalletRequest(walletSender.id(),walletSender.balance()-amount,walletSender.clientId(),walletSender.bankAccountId());
            WalletRequest walletBeneficiaryUpdate = new WalletRequest(walletBeneficiary.id(),walletBeneficiary.balance()+amount,walletBeneficiary.clientId(),walletBeneficiary.bankAccountId());
            walletClient.updateWallet(walletSenderUpdate);
            walletClient.updateWallet(walletBeneficiaryUpdate);

            return ResponseEntity.ok(new CmiResponse(true));
        } else {
            return ResponseEntity.ok(new CmiResponse(false));
        }
    }


    @GetMapping("/feedWallet")
    public ResponseEntity<Boolean> feedWallet(@RequestParam String clientId,@RequestParam double amount){
        WalletResponse wallet = walletClient.getWalletByIdClient(clientId).getBody();
        BankAccountResponse bk = walletClient.getBankAccount(wallet.bankAccountId()).getBody();
        BankAccountResponse bankAccountUpdate = new BankAccountResponse(bk.id(),bk.solde()-amount);
        String UpdateBankAccount =  walletClient.updateBankAccount(bankAccountUpdate);
        WalletRequest walletUpdate = new WalletRequest(wallet.id(),wallet.balance()+amount,wallet.clientId(),UpdateBankAccount);
        walletClient.updateWallet(walletUpdate);
        return ResponseEntity.ok(true);
    }




}
