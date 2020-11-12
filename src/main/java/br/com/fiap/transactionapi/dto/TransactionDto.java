package br.com.fiap.transactionapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransactionDto {

    private String id;

    private Long cardNumber;

    private String locale;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date date;

    private BigDecimal value;

}
