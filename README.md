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
Dados de link AWS das API CARTOES

URL : http://18.228.2.146/clientes

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

http://18.228.2.146/lancamentos
