package com.project.octwallet.service;

import com.project.octwallet.dto.WalletInfo;
import com.project.octwallet.operation.OctetOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WalletService {

    @Autowired
    OctetOperation octetOperation;


//    @Autowired
//    OkHttpClient unsafeOkHttpClient;
//    public String myWalletInfo(int walletId) {
//
//        Request request = new Request.Builder()
//                .url(String.format("https://octet-api.blockchainapi.io/2.0/wallets/%d", walletId))
//                .get()
//                .addHeader("Accept", "application/json")
//                .addHeader("Authorization", API_TOKEN)
//                .build();
//
//        try {
//            Response response = unsafeOkHttpClient.newCall(request).execute();
//            String jsonText = response.body().string();
//
//            log.info("response :: {}", jsonText);
//
//            return jsonText;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//
//    }

    /**
     * 지갑 정보 조회
     * https://tetco.readme.io/reference/getwallet
     *
     * @return
     */
    public WalletInfo myWalletInfoRestTemplate() {
        return octetOperation.myWalletInfoRestTemplate();
    }


}
