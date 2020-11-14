package br.com.fiap.transactionapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentDTO {

    private  String nome;
    private  String rm;
    private  String numeroCartao;
}
