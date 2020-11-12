package br.com.fiap.transactionapi.repository;

import br.com.fiap.transactionapi.entity.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {

    Optional<List<Transaction>> findByCardNumber(Long cardNumber);

}
