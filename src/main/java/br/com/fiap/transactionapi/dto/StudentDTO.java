package br.com.fiap.transactionapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentDTO {

    private String name;
    private Integer rm;
    private Integer cardNumber;
}
