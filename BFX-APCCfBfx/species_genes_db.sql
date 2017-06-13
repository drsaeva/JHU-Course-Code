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

/*
Inserts data from NCBI Protein into the tables
*/
INSERT INTO species (taxonomy_id, genus, species)
VALUES ('216592', 'Escherichia', 'coli 042'), ('985081', 'Helicobacter', 'pylori 2017');
 
INSERT INTO genes (accession, taxonomy_id, gene_product_name, gene)
VALUES ('ADZ49168', '985081','50S ribosomal protein L13', 'rplM'),
('ADZ50584', '985081', 'Glucosamine-fructose-6-phosphate aminotransferase', 'glmS'),
('ADZ50581', '985081', 'Chromosomal replication initiator protein', 'dnaA'),
('CBG33788', '216592', 'pyruvate oxidase', 'poxB'),
('CBG32879', '216592', 'FAD-dependent oxidoreductase', 'fixC'),
('CBG36883', '216592', 'chromosomal replication initiator protein', 'dnaA');
