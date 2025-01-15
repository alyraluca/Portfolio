DROP DATABASE IF EXISTS DBChessGames;
CREATE DATABASE DBChessGames CHARACTER SET utf8 COLLATE utf8_spanish_ci;

--  CREATE USER 'mavenuser'@'localhost' IDENTIFIED BY 'ada0486';
GRANT ALL PRIVILEGES ON * . * to 'mavenuser'@'localhost';

FLUSH PRIVILEGES;


USE DBChessGames;

DROP TABLE IF EXISTS Player;
DROP TABLE IF EXISTS Tournament;
DROP TABLE IF EXISTS Game;
DROP TABLE IF EXISTS Deferral;
DROP TABLE IF EXISTS Message;

-- Player
CREATE TABLE Player (
playerID        INTEGER,
fullname        VARCHAR(100) NOT NULL,
has_match       BOOLEAN DEFAULT FALSE,
has_deferral    BOOLEAN DEFAULT FALSE,
CONSTRAINT pla_id_pk PRIMARY KEY (playerID)
);

-- Tournament
CREATE TABLE Tournament (
code        VARCHAR(10),
name        VARCHAR(100),
CONSTRAINT tou_cod_pk PRIMARY KEY (code)
);

-- Game
CREATE TABLE Game (
gameID      INTEGER,
code        VARCHAR(10),
playerID    INTEGER,
matchdate   DATETIME,
result      VARCHAR(10) DEFAULT 'PENDING',
CONSTRAINT gam_gam_pk PRIMARY KEY (gameID),
CONSTRAINT gam_cod_fk FOREIGN KEY (code) REFERENCES Tournament(code),
CONSTRAINT gam_pla_fk FOREIGN KEY (playerID) REFERENCES Player(playerID),
CONSTRAINT gam_res_ck CHECK (result IN ('PENDING','WON','LOST','DRAWS'))
);

-- Deferral
CREATE TABLE Deferral (
deferralID  INTEGER,
code        VARCHAR(10),
playerID    INTEGER,
defdate     DATETIME,
result      VARCHAR(10) DEFAULT 'REQUESTED',
CONSTRAINT def_def_pk PRIMARY KEY (deferralID),
CONSTRAINT def_cod_fk FOREIGN KEY (code) REFERENCES Tournament(code),
CONSTRAINT def_pla_fk FOREIGN KEY (playerID) REFERENCES Player(playerID),
CONSTRAINT def_res_ck CHECK (result IN ('REQUESTED','GRANTED','REJECTED'))
);

CREATE TABLE Message (
messageID   INTEGER AUTO_INCREMENT,
playerID    INTEGER,
description VARCHAR(500),
CONSTRAINT mes_mmc_pk PRIMARY KEY (messageID),
CONSTRAINT mes_pla_fk FOREIGN KEY (playerID) REFERENCES Player(playerID)
);

-- Data
INSERT INTO Player (playerID, fullname) VALUES (1, 'Anatoly Karpov');
INSERT INTO Player (playerID, fullname) VALUES (2, 'Garry Kasparov');
INSERT INTO Player (playerID, fullname) VALUES (3, 'Pepe Tableros');
INSERT INTO Player (playerID, fullname) VALUES (4, 'John Lost');
INSERT INTO Tournament (code, name) VALUES ('T01', 'Torneo local de Quart de Poblet');
INSERT INTO Tournament (code, name) VALUES ('T02', 'Torneo Internacional de Ajedrez Ciudad de Linares');
INSERT INTO Tournament (code, name) VALUES ('T03', 'Torneo Jaque con tomate');
INSERT INTO Tournament (code, name) VALUES ('T04', 'Torneo Maestro Yoda');