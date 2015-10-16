DROP VIEW MG.RECIBO;
DROP VIEW MG.TRANSFERENCIA;
DROP TABLE MG.TBL_RECIBOS;
DROP TABLE MG.TBL_TRANSFERENCIAS;
DROP TABLE MG.TBL_EVENTO_DETALLE;
DROP TABLE MG.TBL_EVENTO_CUOTAS;
DROP TABLE MG.TBL_CATEGORIAS_ARTICULOS;
DROP TABLE MG.TBL_EVENTOS;
DROP TABLE MG.TBL_EVENTO_TIPOS;
DROP TABLE MG.TBL_FACTURAS;
DROP TABLE MG.TBL_TIMBRADOS;
DROP TABLE MG.TBL_MIEMBROS;
DROP TABLE MG.TBL_IGLESIA;
DROP TABLE MG.TBL_ROLES_USERS;
DROP TABLE MG.TBL_GRUPOS_USERS;
DROP TABLE MG.TBL_ROLES;
DROP TABLE MG.TBL_GRUPOS;
DROP TABLE MG.TBL_USERS;

/*
ID_TIPO_EVENTO
    0   REMATE
    1   COLECTA
    2   APORTE

ID_CATEGORIA_TRIBUTARIA
    0   APORTE
    1   DONACION
*/

CREATE TABLE MG.TBL_EVENTOS (
	ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
	FECHA TIMESTAMP NOT NULL,
	DESCRIPCION VARCHAR(100),
	PORCENTAJE_APORTE INTEGER NOT NULL,
        ID_EVENTO_TIPO INTEGER NOT NULL,
	ID_USER INTEGER NOT NULL,
	ID_GRUPO INTEGER NOT NULL,
	PRIMARY KEY (ID)
);

CREATE TABLE MG.TBL_EVENTO_TIPOS (
	ID INTEGER NOT NULL,
        DESCRIPCION VARCHAR(50) NOT NULL,
	PRIMARY KEY (ID)
);

CREATE TABLE MG.TBL_EVENTO_DETALLE (
	ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
	FECHAHORA TIMESTAMP NOT NULL,
	OBSERVACION VARCHAR(50),
	MONTO INTEGER NOT NULL,
	ID_MIEMBRO INTEGER NOT NULL,
	ID_EVENTO INTEGER NOT NULL,
	ID_CATEGORIA_ARTICULO INTEGER NOT NULL,
	ID_USER INTEGER,
	PRIMARY KEY (ID)
);


CREATE TABLE MG.TBL_CATEGORIAS_ARTICULOS (
	ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
	DESCRIPCION VARCHAR(50),
	PRIMARY KEY (ID)
);

CREATE TABLE MG.TBL_EVENTO_CUOTAS (
	ID_EVENTO INTEGER NOT NULL,
	FECHA_1 TIMESTAMP,
	FECHA_2 TIMESTAMP,
	FECHA_3 TIMESTAMP,
	FECHA_4 TIMESTAMP,
	ID_USER INTEGER,
	PRIMARY KEY (ID_EVENTO)
);

CREATE TABLE MG.TBL_GRUPOS (
	ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
	DESCRIPCION VARCHAR(50) NOT NULL,
	PRIMARY KEY (ID)
);

CREATE TABLE MG.TBL_IGLESIA (
	ID INTEGER NOT NULL,
	NOMBRE VARCHAR(50) NOT NULL,
	RUC INTEGER,
	CTACTE INTEGER,
	DOMICILIO VARCHAR(50),
	BOX INTEGER,
	PRIMARY KEY (ID)
);

CREATE TABLE MG.TBL_MIEMBROS (
	ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
	NOMBRE VARCHAR(50) NOT NULL,
        RUC INTEGER,
	CTACTE INTEGER,
	DOMICILIO VARCHAR(50),
	BOX INTEGER,
        APORTE_MENSUAL INTEGER NOT NULL,
	ID_USER INTEGER,
	PRIMARY KEY (ID)
);

CREATE TABLE MG.TBL_RECIBOS (
	ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
	FECHAHORA TIMESTAMP NOT NULL,
	CONCEPTO VARCHAR(50),
	MONTO INTEGER NOT NULL,
	ID_MIEMBRO INTEGER NOT NULL,
	ID_EVENTO INTEGER NOT NULL,
	ID_USER INTEGER NOT NULL,
	PRIMARY KEY (ID)
);

CREATE TABLE MG.TBL_ROLES (
	ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
	DESCRIPCION VARCHAR(50) NOT NULL,
	PRIMARY KEY (ID)
);

CREATE TABLE MG.TBL_ROLES_USERS (
        ID_USER INTEGER NOT NULL,
        ID_ROLE INTEGER NOT NULL,
	PRIMARY KEY (ID_ROLE,ID_USER)
);

CREATE TABLE MG.TBL_GRUPOS_USERS (
	ID_USER INTEGER NOT NULL,
	ID_GRUPO INTEGER NOT NULL,
	PRIMARY KEY (ID_GRUPO,ID_USER)
);

CREATE TABLE MG.TBL_TRANSFERENCIAS (
	ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
	FECHAHORA TIMESTAMP NOT NULL,
	CONCEPTO VARCHAR(50),
	MONTO INTEGER NOT NULL,
	ID_MIEMBRO INTEGER NOT NULL,
        ID_EVENTO INTEGER NOT NULL,
        COBRADO BOOLEAN DEFAULT FALSE,
	ID_USER INTEGER NOT NULL,
	PRIMARY KEY (ID)
);

CREATE TABLE MG.TBL_USERS (
	ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
	NOMBRE VARCHAR(50) NOT NULL,
	PASSWORD VARCHAR(100) NOT NULL,
	PRIMARY KEY (ID)
);

CREATE TABLE MG.TBL_FACTURAS (
        NRO INTEGER NOT NULL,
        ID_TIMBRADO INTEGER NOT NULL,
        FECHAHORA TIMESTAMP NOT NULL,
        ID_MIEMBRO INTEGER NOT NULL,
        RAZON_SOCIAL VARCHAR(50) NOT NULL,
        RUC INTEGER NOT NULL,
        IMPORTE_DONACION INTEGER NOT NULL,
        IMPORTE_APORTE INTEGER NOT NULL,
        ANULADO BOOLEAN DEFAULT FALSE NOT NULL,
        ID_USER INTEGER NOT NULL,
	PRIMARY KEY (NRO)
);

CREATE TABLE MG.TBL_TIMBRADOS (
        NRO INTEGER NOT NULL,
        FECHA_INICIO TIMESTAMP NOT NULL,
        FECHA_VENCIMIENTO TIMESTAMP NOT NULL,
        NRO_FACTURA_INCIO INTEGER NOT NULL,
        NRO_FACTURA_FIN INTEGER NOT NULL,
        ACTIVO BOOLEAN NOT NULL,
        ID_USER INTEGER NOT NULL,
	PRIMARY KEY (NRO)
);

ALTER TABLE MG.TBL_EVENTOS
	ADD FOREIGN KEY (ID_EVENTO_TIPO)
	REFERENCES MG.TBL_EVENTO_TIPOS (ID);

ALTER TABLE MG.TBL_EVENTOS
	ADD FOREIGN KEY (ID_GRUPO)
	REFERENCES MG.TBL_GRUPOS (ID);

ALTER TABLE MG.TBL_EVENTOS
	ADD FOREIGN KEY (ID_USER)
	REFERENCES MG.TBL_USERS (ID);


ALTER TABLE MG.TBL_EVENTO_DETALLE
	ADD FOREIGN KEY (ID_EVENTO)
	REFERENCES MG.TBL_EVENTOS (ID);

ALTER TABLE MG.TBL_EVENTO_DETALLE
	ADD FOREIGN KEY (ID_CATEGORIA_ARTICULO)
	REFERENCES MG.TBL_CATEGORIAS_ARTICULOS (ID);

ALTER TABLE MG.TBL_EVENTO_DETALLE
	ADD FOREIGN KEY (ID_MIEMBRO)
	REFERENCES MG.TBL_MIEMBROS (ID);

ALTER TABLE MG.TBL_EVENTO_DETALLE
	ADD FOREIGN KEY (ID_USER)
	REFERENCES MG.TBL_USERS (ID);

ALTER TABLE MG.TBL_EVENTO_CUOTAS
	ADD FOREIGN KEY (ID_EVENTO)
	REFERENCES MG.TBL_EVENTOS (ID)
        ON DELETE CASCADE;


ALTER TABLE MG.TBL_MIEMBROS
	ADD FOREIGN KEY (ID_USER)
	REFERENCES MG.TBL_USERS (ID);



ALTER TABLE MG.TBL_RECIBOS
	ADD FOREIGN KEY (ID_MIEMBRO)
	REFERENCES MG.TBL_MIEMBROS (ID);

ALTER TABLE MG.TBL_RECIBOS
	ADD FOREIGN KEY (ID_EVENTO)
	REFERENCES MG.TBL_EVENTOS (ID);

ALTER TABLE MG.TBL_RECIBOS
	ADD FOREIGN KEY (ID_USER)
	REFERENCES MG.TBL_USERS (ID);

ALTER TABLE MG.TBL_FACTURAS
	ADD FOREIGN KEY (ID_MIEMBRO)
	REFERENCES MG.TBL_MIEMBROS (ID);

ALTER TABLE MG.TBL_FACTURAS
	ADD FOREIGN KEY (ID_TIMBRADO)
	REFERENCES MG.TBL_TIMBRADOS (NRO);

ALTER TABLE MG.TBL_FACTURAS
	ADD FOREIGN KEY (ID_USER)
	REFERENCES MG.TBL_USERS (ID);

ALTER TABLE MG.TBL_TIMBRADOS
	ADD FOREIGN KEY (ID_USER)
	REFERENCES MG.TBL_USERS (ID);

ALTER TABLE MG.TBL_ROLES_USERS
	ADD FOREIGN KEY (ID_USER)
	REFERENCES MG.TBL_USERS (ID);

ALTER TABLE MG.TBL_ROLES_USERS
	ADD FOREIGN KEY (ID_ROLE)
	REFERENCES MG.TBL_ROLES (ID);

ALTER TABLE MG.TBL_GRUPOS_USERS
	ADD FOREIGN KEY (ID_USER)
	REFERENCES MG.TBL_USERS (ID);

ALTER TABLE MG.TBL_GRUPOS_USERS
	ADD FOREIGN KEY (ID_GRUPO)
	REFERENCES MG.TBL_GRUPOS (ID);

ALTER TABLE MG.TBL_TRANSFERENCIAS
	ADD FOREIGN KEY (ID_MIEMBRO)
	REFERENCES MG.TBL_MIEMBROS (ID);

ALTER TABLE MG.TBL_TRANSFERENCIAS
	ADD FOREIGN KEY (ID_EVENTO)
	REFERENCES MG.TBL_EVENTOS (ID);

ALTER TABLE MG.TBL_TRANSFERENCIAS
	ADD FOREIGN KEY (ID_USER)
	REFERENCES MG.TBL_USERS (ID);

CREATE VIEW MG.RECIBO AS SELECT p.id, p.fechahora, d.nombre, p.concepto, p.monto FROM tbl_RECIBOS p, tbl_miembros d WHERE ((p.id_miembro = d.id));

CREATE VIEW TRANSFERENCIA AS
    SELECT t.id,
        t.fechahora,
        d.ctacte AS cta_debito,
        i.ctacte AS cta_credito,
        d.nombre AS d_nombre,
        i.nombre AS c_nombre,
        d.domicilio AS d_domicilio,
        i.domicilio AS c_domicilio,
        d.box AS d_box,
        i.box AS c_box,
        t.monto,
        t.concepto
    FROM tbl_transferencias t, tbl_miembros d, tbl_iglesia i
    WHERE t.id_miembro = d.id;

INSERT INTO MG.TBL_ROLES (DESCRIPCION) VALUES ('Cajero');
INSERT INTO MG.TBL_ROLES (DESCRIPCION) VALUES ('Admin');
INSERT INTO MG.TBL_ROLES (DESCRIPCION) VALUES ('Master');

INSERT INTO MG.TBL_EVENTO_TIPOS (ID, DESCRIPCION) VALUES (1, 'Remate');
INSERT INTO MG.TBL_EVENTO_TIPOS (ID, DESCRIPCION) VALUES (2, 'Colecta');
INSERT INTO MG.TBL_EVENTO_TIPOS (ID, DESCRIPCION) VALUES (3, 'Aporte');

CREATE TRIGGER MG.EVENTO_CUOTA_TRIG
AFTER
INSERT
ON MG.TBL_EVENTOS
REFERENCING NEW AS new_row
FOR EACH ROW
MODE DB2SQL
INSERT INTO MG.TBL_EVENTO_CUOTAS (ID_EVENTO, FECHA_1) VALUES (new_row.ID, CURRENT_TIMESTAMP);