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
```