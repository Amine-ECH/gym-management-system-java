
CREATE DATABASE db_gym;
USE gym_db;

CREATE TABLE admin (
    id_admin INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);


CREATE TABLE client (
    id_client INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    telephone VARCHAR(20)
);

-- ================================
--  Table TYPE_ABONNEMENT
-- ================================
CREATE TABLE type_abonnement (
    id_type INT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL,
    duree_mois INT NOT NULL,
    prix DOUBLE NOT NULL
);


CREATE TABLE abonnement (
    id_abonnement INT AUTO_INCREMENT PRIMARY KEY,
    date_debut DATE NOT NULL,
    date_fin DATE NOT NULL,
    statut VARCHAR(20) NOT NULL,

    id_client INT NOT NULL,
    id_type INT NOT NULL,

    CONSTRAINT fk_abonnement_client
        FOREIGN KEY (id_client)
        REFERENCES client(id_client)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_abonnement_type
        FOREIGN KEY (id_type)
        REFERENCES type_abonnement(id_type)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);
