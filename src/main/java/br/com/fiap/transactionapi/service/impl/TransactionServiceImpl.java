package br.com.fiap.transactionapi.service.impl;

import br.com.fiap.transactionapi.dto.TransactionDto;
import br.com.fiap.transactionapi.entity.Transaction;
import br.com.fiap.transactionapi.repository.TransactionRepository;
import br.com.fiap.transactionapi.service.TransactionService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final ModelMapper mapper;

    private final TransactionRepository transactionRepository;

    @Override
    public TransactionDto create(TransactionDto transactionDto) {
        Transaction record = this.transactionRepository.save(this.mapper.map(transactionDto, Transaction.class));
        return this.mapper.map(record, TransactionDto.class);
    }

    @Override
    public List<TransactionDto> findAll() {
        List<Transaction> transactions = this.transactionRepository.findAll();
        return this.mapListTransaction(transactions);
    }

    @Override
    public List<TransactionDto> findByCardNumber(Long cardNumber) {
        List<Transaction> transactions = this.transactionRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return this.mapListTransaction(transactions);
    }

    private List<TransactionDto> mapListTransaction(List<Transaction> transactions) {
        return transactions.stream()
                .map(transaction -> this.mapper.map(transaction, TransactionDto.class))
                .collect(Collectors.toList());
    }

}
