package com.project.octwallet.config;

import com.google.common.io.ByteStreams;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.*;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

/**
 * @see https://springboot.cloud/19
 */
@Slf4j
@Configuration
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate restTemplateSimpleInsecure() {

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory() {

            @Override
            protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {

                if (connection instanceof HttpsURLConnection) {
//                    ((HttpsURLConnection) connection).setHostnameVerifier((hostname, session) -> true); // 호스트 검증을 항상 pass하고
                    // 호스트 검증을 항상 pass하고
                    ((HttpsURLConnection) connection).setHostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String s, SSLSession sslSession) {
                            return true;
                        }
                    });

                    SSLContext sc;

                    try {
                        sc = SSLContext.getInstance("SSL"); // SSLContext를 생성하여
                        sc.init(null, new TrustManager[]{new X509TrustManager() { // 공개키 암호화 설정을 무력화시킨다.
                            @Override
                            public X509Certificate[] getAcceptedIssuers() {
                                return null;
                            }

                            @Override
                            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                            }
                        }}, new SecureRandom());
                        ((HttpsURLConnection) connection).setSSLSocketFactory(sc.getSocketFactory());
                    } catch (NoSuchAlgorithmException e) {
                        log.error("{}", e);
                    } catch (KeyManagementException e) {
                        log.error("{}", e);
                    }
                }
                super.prepareConnection(connection, httpMethod);
            }
        };

        factory.setReadTimeout(120_000);
        factory.setConnectTimeout(20_000);

        RestTemplate restTemplatePayments = new RestTemplate(new BufferingClientHttpRequestFactory(factory));
        restTemplatePayments.setInterceptors(
                Arrays.asList(new RequestResponseLoggingInterceptor())
        );

        return restTemplatePayments;

    }

    @Bean
    public RestTemplate restTemplateHttpComponentsInsecure(OkHttpClient httpClient) {

        SSLContext sslContext = null;

        try {
            sslContext = SSLContext.getInstance("SSL"); // SSLContext를 생성하여
            sslContext.init(null, new TrustManager[]{new X509TrustManager() { // 공개키 암호화 설정을 무력화시킨다.
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }
            }}, new SecureRandom());
        } catch (NoSuchAlgorithmException e) {
            log.error("{}", e);
        } catch (KeyManagementException e) {
            log.error("{}", e);
        }

//        CloseableHttpClient httpClient = HttpClients.custom()
//                .setSSLSocketFactory(new SSLConnectionSocketFactory(sslContext))
//                .setMaxConnPerRoute(120)
//                .setMaxConnTotal(60)
//                .build();


        OkHttp3ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory(httpClient);
        factory.setReadTimeout(120_000);
        factory.setConnectTimeout(20_000);

        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(factory));
        restTemplate.setInterceptors(
                Arrays.asList(new RequestResponseLoggingInterceptor())
        );

        return restTemplate;
    }

    @Bean
    public HttpHeaders headersApplicationJSON() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }


}


@Slf4j
class RequestResponseLoggingInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        log.info("REQUEST URI: {}, HEADER: {}, BODY: {}", request.getURI(), request.getHeaders(), new String(body, StandardCharsets.UTF_8));
        ClientHttpResponse response = execution.execute(request, body);
        log.info("RESPONSE STATUS CODE: {}, Body: {}", response.getStatusCode(), new String(ByteStreams.toByteArray(response.getBody()), StandardCharsets.UTF_8));

        return response;
    }

}