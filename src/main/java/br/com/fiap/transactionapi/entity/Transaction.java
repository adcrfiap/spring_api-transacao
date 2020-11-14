package br.com.fiap.transactionapi.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Document(collection = "transactions")
public class Transaction {

    @Id
    private String id;

    private String cardNumber;

    @CreatedDate
    private Date date;

    private String locale;

    private BigDecimal value;

}
