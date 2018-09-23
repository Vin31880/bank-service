package com.blue.bankservice.remote.transaction;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@FeignClient(url = "http://localhost:9090", name = "TRANSACTION-SERVICE")
public interface TransactionClient {

    String BASE_URL = "/api/v1";

    String SAVE_TRANSACTION_URL = BASE_URL + "/transaction";

    String GET_TRANSACTION_BY_ACCOUNT_ID_URL = BASE_URL + "/transaction/{accountId}";

    @PostMapping(value = SAVE_TRANSACTION_URL, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<Transaction> saveTransaction(@RequestBody TransactionResourceIn transaction);

    @GetMapping(value = GET_TRANSACTION_BY_ACCOUNT_ID_URL, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<List<Transaction>> getTransactionByAccountId(@PathVariable("accountId") Long accountId);

}
