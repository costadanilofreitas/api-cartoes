# api-cartoes

Projeto de final do primeiro ciclo para criação de uma API.
No qual utilizaremos tudo que foi aprendido no curso de pré requisitos da Mastertech.

## Execução

### Execução local IDE 

A aplicação pode ser executada e alterada por uma IDE, para fazer isso é necessário ter as seguintes ferramentas:

#### Pré requisitos
- [Java 8](https://java.com/en/download/help/linux_x64_install.xml) 
- [Apache Maven >= 3.x](https://maven.apache.org/)
- [MariaDB](https://mariadb.com/kb/en/getting-installing-and-upgrading-mariadb/)

### Execução MariaDB
Depois da instalação do MariaDB, é necessario subir o banco de dados, criar a base de dados, criar o usuario e dar acesso para o mesmo.
Para isso basta executar o comandos abaixo respectivamente:

```
sudo systemctl start mariadb.service

mysql -u root

CREATE USER 'ac'@'localhost' IDENTIFIED BY '123';

CREATE DATABASE api_cartoes;

GRANT ALL ON api_cartoes.* to 'ac'@'%' identified by '123';

====================================================
Dados de link AWS das API CARTOES - utilizando POSTMAN para validar chamadas

URL : http://18.228.2.146/usuario/registrar
POST 
{
"nome":"Edson Arantes",
"senha":"mastersoccer20",
"email":"pele@futebol.com.br"
}

URL : http://18.228.2.146/login
POST
{
"email":"pele@futebol.com.br"",
"senha": "mastersoccer20"
} 

OBS: Copiar Token Authorization (Gerado  aba Header)
     Informar token nas API Abaixo Authorization  (sem a palavra Bearer)


URL : http://18.228.2.146/clientes
POST - INCLUSAO
 {
        "nome": "João Aparecido",
        "cpf": "470.129.120-06",
        "dataNascimento": "1991-11-25T02:00:00.000+0000",
        "email": "joao@email.com.br"
    },
URL : http://18.228.2.146/clientes/
GET - Pesquisa Todos Clientes

URL : http://18.228.2.146/clientes/1
GET - Pesquisa Cliente Especifico 1
PUT - Atualiza Cliente 1
DELETE - Exclui Cliente 1

URL http://18.228.2.146/cartoes
    POST - Inclusão de Cartão
    Exemplo : Json
    {	"limiteTotal":10000,
	"validade":"2020-11-29",
	"cvv":222,
	"limiteAtual":200,
	"cliente":{"id":1}
    }
GET - Consulta Todos os Cartões
URL : http://18.228.2.146/cartoes/1
      GET - Consulta o Cartão número 1

      PUT - Atualiza o Cartão número 1
      Exemplo : Json 
     { "validade":"2027-05-01"}

     DELETE - Exclui o Cartão número 1

URL  :http://18.228.2.146/lancamentos
POST - Inclui lancamento
{
  "tipoDeLancamento":"CREDITO",
  "valor":100.0,
  "data":"2020-05-01",
  "cartao":1,
  "categoria":"ALMIMENTACAO"
}
URL  :http://18.228.2.146/lancamentos
GET - Consulta Todos Lancamentos

URL  :http://18.228.2.146/lancamentos/1
GET - Consulta Lancamento 1
PUT - Atualiza Lancamento 1
DELETE - Exclui Lancamento 1


