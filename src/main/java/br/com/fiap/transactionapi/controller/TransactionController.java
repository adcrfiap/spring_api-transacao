package br.com.fiap.transactionapi.controller;

import br.com.fiap.transactionapi.dto.TransactionDto;
import br.com.fiap.transactionapi.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionDto create(@RequestBody TransactionDto transactionDto) {
        return this.transactionService.create(transactionDto);
    }

    @GetMapping
    public List<TransactionDto> findAll() {
        return this.transactionService.findAll();
    }

    @GetMapping("/{cardNumber}")
    public List<TransactionDto> findByCardNumber(@PathVariable Long cardNumber) {
        return this.transactionService.findByCardNumber(cardNumber);
    }

}
