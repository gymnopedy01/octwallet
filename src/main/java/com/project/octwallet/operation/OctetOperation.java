package com.project.octwallet.operation;

import com.project.octwallet.dto.request.CreateChildAddressReq;
import com.project.octwallet.dto.response.Address;
import com.project.octwallet.dto.response.WalletInfo;
import com.project.octwallet.util.ParameterUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

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
     *
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

        UriComponents builder = UriComponentsBuilder
                .fromHttpUrl(url)
                .queryParam("pos", 0)
                .queryParam("offset", 200)
                .queryParam("desc", "desc")
//                .queryParam("startDate", startDate)
//                .queryParam("endDate", endDate)
                .encode().build(); //자동으로 인코딩해주는걸 막아줌

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(API_TOKEN);
        HttpEntity requestEntity = new HttpEntity(headers);

        return restTemplate.exchange(
                builder.toUri()
                , HttpMethod.GET
                , requestEntity
                , new ParameterizedTypeReference<List<Address>>() {
                })
                .getBody();
    }

    public List<CreateChildAddressRes> createChildAddress() {

        String url = String.format("https://octet-api.blockchainapi.io/2.0/wallets/%d/child-addresses", walletId);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(API_TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String param = ParameterUtil.makeJsonString(CreateChildAddressReq.builder()
                .offset(1)
                .build()
        );

        HttpEntity<CreateChildAddressReq> requestEntity = new HttpEntity(param, headers);

        return restTemplate.exchange(
                url
                , HttpMethod.POST
                , requestEntity
                , new ParameterizedTypeReference<List<CreateChildAddressRes>>() {
                })
                .getBody();

    }

    public ChildAddressBalance getChildAddressBalance(String address) {
        return null;
    }

    @Data
    public static class ChildAddressBalance {
        String total;
        String liquid;
    }

    @Data
    public static class CreateChildAddressRes {
        String address;
        String name;
    }

}
