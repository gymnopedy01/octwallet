package com.project.octwallet.service;

import com.project.octwallet.dto.WalletInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class AddressService {

    @Value("${octwallet.api-token}")
    String API_TOKEN;

    @Autowired
    @Qualifier("restTemplateHttpComponentsInsecure")
    RestTemplate restTemplate;

    /**
     * 자식주소 목록 조회
     * https://tetco.readme.io/reference/getchildaddresses
     * @param walletId
     * @return
     */
    public List<WalletInfo.Address> getChildAddresses(int walletId) {

        String url = String.format("https://octet-api.blockchainapi.io/2.0/wallets/%d/child-addresses", walletId);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(API_TOKEN);

        ChildAddressesParam param = new ChildAddressesParam();
        param.setPos(0);
        param.setOffset(10);
        param.setOrder("desc");

        HttpEntity requestEntity = new HttpEntity(headers);

        List<WalletInfo.Address> list = restTemplate.exchange(url, HttpMethod.GET, requestEntity, List.class).getBody();

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
