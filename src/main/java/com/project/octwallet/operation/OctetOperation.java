package com.project.octwallet.operation;

import com.project.octwallet.dto.Address;
import com.project.octwallet.dto.WalletInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Component
public class OctetOperation {

    @Value("${octetwallet.api-token}")
    String API_TOKEN;

    @Value("${octetwallet.wallet-id}")
    public int walletId;

    @Autowired
    @Qualifier("restTemplateHttpComponentsInsecure")
    RestTemplate restTemplate;


    /**
     * 지갑 정보 조회
     * https://tetco.readme.io/reference/getwallet
     * 특정 지갑에 대한 정보를 조회합니다.
     * @return
     */
    public WalletInfo myWalletInfoRestTemplate() {

        String url = String.format("https://octet-api.blockchainapi.io/2.0/wallets/%d", walletId);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(API_TOKEN);

        HttpEntity requestEntity = new HttpEntity(headers);

        WalletInfo walletInfo = restTemplate.exchange(url, HttpMethod.GET, requestEntity, WalletInfo.class).getBody();

        return walletInfo;

    }


    /**
     * 자식주소 목록 조회
     * https://tetco.readme.io/reference/getchildaddresses
     *
     * @param walletId
     * @return
     */
    public List<Address> getChildAddresses() {

        String url = String.format("https://octet-api.blockchainapi.io/2.0/wallets/%d/child-addresses", walletId);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(API_TOKEN);

        ChildAddressesParam param = new ChildAddressesParam();
        param.setPos(0);
        param.setOffset(200);
        param.setOrder("desc");

        HttpEntity requestEntity = new HttpEntity(headers);

        List<Address> list = restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Address>>(){}).getBody();

        return list;
    }

    @Data
    public static class ChildAddressesParam {
        int pos;
        int offset;
        String order;
        String startDate;
        String endDate;
    }
}
