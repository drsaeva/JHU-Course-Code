/*
Creates new tables
*/
CREATE TABLE species (
taxonomy_id INT(7) NOT NULL,
genus VARCHAR(20) NOT NULL,
species VARCHAR(10) NOT NULL);

CREATE TABLE genes (
accession CHAR(9) NOT NULL,
taxonomy_id INT(7) NOT NULL,
gene_product_name VARCHAR(20) NOT NULL,
gene VARCHAR(5) NOT NULL);
