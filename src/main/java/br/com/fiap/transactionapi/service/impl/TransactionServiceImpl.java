package br.com.fiap.transactionapi.service.impl;

import br.com.fiap.transactionapi.config.email.EmailConfig;
import br.com.fiap.transactionapi.dto.StudentDTO;
import br.com.fiap.transactionapi.dto.TransactionDto;
import br.com.fiap.transactionapi.entity.Transaction;
import br.com.fiap.transactionapi.repository.TransactionRepository;
import br.com.fiap.transactionapi.service.TransactionService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final ModelMapper mapper;
    private WebClient webClient;
    private final TransactionRepository transactionRepository;

    @Autowired
    private EmailConfig emailConfig;



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
    public List<TransactionDto> findByCardNumber(String cardNumber) throws IOException {
        List<Transaction> transactions = this.transactionRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        send(mapListTransaction(transactions), cardNumber);
        return this.mapListTransaction(transactions);
    }

    private List<TransactionDto> mapListTransaction(List<Transaction> transactions) {
        return transactions.stream()
                .map(transaction -> this.mapper.map(transaction, TransactionDto.class))
                .collect(Collectors.toList());
    }

    private void send(List<TransactionDto> listTransaction, String cardNumber) throws IOException {


        String emailBody = createEmailBody(listTransaction, cardNumber);

        emailConfig.send(emailBody);

    }

    private String createEmailBody(List<TransactionDto> listTransaction, String cardNumber) {
        StringBuilder extrato = new StringBuilder();
        StudentDTO student = searchStudent(cardNumber);

        extrato.append("Nome: " + student.getNome())
                .append(System.lineSeparator())
                .append("RM: " + student.getRm())
                .append(System.lineSeparator())
                .append("Numero do cartão: " + student.getNumeroCartao())
                .append(System.lineSeparator())
                .append("Extrato completo de transações");

        for (TransactionDto transactionDTO : listTransaction){
            extrato.append(System.lineSeparator())
                    .append(System.lineSeparator())
                    .append("Data: " + transactionDTO.getDate())
                    .append(System.lineSeparator())
                    .append("Local: " + transactionDTO.getLocale())
                    .append(System.lineSeparator())
                    .append("Valor: " + transactionDTO.getValue())
                    .append(System.lineSeparator());
        }

        return extrato.toString();
    }

    private StudentDTO searchStudent(String cardNumber){
        StudentDTO student = new StudentDTO();

        try {
            Mono<StudentDTO> monoAluno = webClient
                    .method(HttpMethod.GET)
                    .uri("http://localhost:8888/alunos/"+cardNumber)
                    .retrieve().bodyToMono(StudentDTO.class);

            student = monoAluno.block();

        } catch (Exception e) {
            e.getCause();
        }
        return student;
    }
}
