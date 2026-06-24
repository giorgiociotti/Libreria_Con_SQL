CREATE DATABASE IF NOT EXISTS libreria;

use libreria;

create table autori(
	id int unsigned primary key auto_increment,
    nome varchar(100),
    cognome varchar(100)
);


insert into autori(nome, cognome) values
('Umberto', 'Eco'),
('George', 'Orwell'),
('F. Scott', 'Fitzgerald'),
('Herman', 'Melville'),
('Italo', 'Svevo'),
('Anna', 'Frank'),
('Miguel', 'de Cervantes'),
('Jane', 'Austen'),
('Alessandro', 'Manzoni'),
('Antoine', 'de Saint-Exupéry'),
('Antoine', 'Orwell'),
('Antoine', 'Orwell'),
('Antoine', 'Orwell');

DROP TABLE IF EXISTS libri;

CREATE TABLE libri (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    titolo VARCHAR(200),
    autore INT UNSIGNED,
    anno_pubblicazione INT,
    prezzo DOUBLE,
    FOREIGN KEY(autore) REFERENCES autori(id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO libri (titolo, autore, anno_pubblicazione, prezzo) VALUES 
('Il nome della rosa', 1, 1980, 40.00),
('1984', 2, 1949, 18.00),
('La fattoria degli animali', 2, 1937, 18.00),
('Il grande Gatsby', 3, 1925, 10.00),
('Moby Dick', 4, 1851, 9.99),
('La coscienza di Zeno', 5, 1923, 13.75),
('Il piccolo principe', 10, 1943, 8.99),
('Il diario di Anna Frank', 6, 1947, 11.50),
('Don Chisciotte della Mancia', 7, 1605, 14.99),
('Orgoglio e pregiudizio', 8, 1813, 12.00),
('I Promessi Sposi', 9, 1827, 18.00);