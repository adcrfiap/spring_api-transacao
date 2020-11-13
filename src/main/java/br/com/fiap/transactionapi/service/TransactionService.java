package br.com.fiap.transactionapi.service;

import br.com.fiap.transactionapi.dto.TransactionDto;

import java.io.IOException;
import java.util.List;

public interface TransactionService {

    TransactionDto create(TransactionDto transactionDto);

    List<TransactionDto> findAll();

    List<TransactionDto> findByCardNumber(Long cardNumber) throws IOException;

}
