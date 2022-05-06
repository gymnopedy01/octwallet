package com.project.octwallet.service;

import com.project.octwallet.dto.WalletInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class WalletService {

    @Value("${octwallet.api-token}")
    String API_TOKEN;

    @Autowired
    @Qualifier("restTemplateHttpComponentsInsecure")
    RestTemplate restTemplate;


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
     * @param walletId
     * @return
     */
    public WalletInfo myWalletInfoRestTemplate(int walletId) {

        String url = String.format("https://octet-api.blockchainapi.io/2.0/wallets/%d", walletId);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(API_TOKEN);

        HttpEntity requestEntity = new HttpEntity(headers);

        WalletInfo walletInfo = restTemplate.exchange(url, HttpMethod.GET, requestEntity, WalletInfo.class).getBody();

        return walletInfo;
    }

}
