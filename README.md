# spring_api-transacao

### Configurar arquivo application.yml

**spring.data.mongodb:**
 - host -> host de conexão do mongoDB  
 - port -> porta de conexão do mongoDB  
 - databse -> base de dados da api (default transacao)  

### Documentação 
	http://localhost:8888/swagger-ui/#/  
  
### Cadastrar nova transaçao
**localhost:8088/transacao/cadastrar**
**exemplo: POST**
```
{
	"numeroCartao":1234,
	"data":"22-11-2020",
	"local":"São José",
	"valor":850
}
```

### Extrato de transação por numero de cartão
**localhost:8088/transacao/listar?numeroCartao=1234**
**GET**
