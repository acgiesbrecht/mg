DROP VIEW MG.RECIBO;
DROP VIEW MG.TRANSFERENCIA;
/*DROP VIEW MG.ENTIDADES_CON_PAGOS_PENDIENTES;*/
DROP TRIGGER MG.EVENTO_CUOTA_TRIGGER;
DROP TABLE MG.TBL_DATABASE_UPDATES;
DROP TABLE MG.TBL_RECIBOS;
DROP TABLE MG.TBL_TRANSFERENCIAS;
DROP TABLE MG.TBL_EVENTO_DETALLE;
DROP TABLE MG.TBL_EVENTO_CUOTAS;
DROP TABLE MG.TBL_CATEGORIAS_ARTICULOS;
DROP TABLE MG.TBL_EVENTOS;
DROP TABLE MG.TBL_EVENTO_TIPOS;
DROP TABLE MG.TBL_FACTURAS;
DROP TABLE MG.TBL_TIMBRADOS;
DROP TABLE MG.TBL_MIEMBROS_RELACIONES;
DROP TABLE MG.TBL_MIEMBROS_RELACIONES_TIPOS;
DROP TABLE MG.TBL_MIEMBROS_RELACIONES_ROLES;
DROP TABLE MG.TBL_MIEMBROS_FAMILIAS;
DROP TABLE MG.TBL_ENTIDADES;
DROP TABLE MG.TBL_FORMAS_DE_PAGO;
DROP TABLE MG.TBL_AREAS_SERVICIO_EN_IGLESIA;
DROP TABLE MG.TBL_MIEMBROS_CATEGORIAS_DE_PAGO;
DROP TABLE MG.TBL_MIEMBROS_ALERGIAS;
DROP TABLE MG.TBL_IGLESIA;
DROP TABLE MG.TBL_ROLES_USERS;
DROP TABLE MG.TBL_GRUPOS_USERS;
DROP TABLE MG.TBL_ROLES;
DROP TABLE MG.TBL_GRUPOS;
DROP TABLE MG.TBL_USERS;
DROP TABLE MG.TBL_CONTRIBUYENTES;

/*
ID_TIPO_EVENTO
    1   REMATE
    2   COLECTA
    3   APORTE

ID_CATEGORIA_TRIBUTARIA
    1   APORTE
    2   DONACION
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
	ID_ENTIDAD INTEGER NOT NULL,
	ID_EVENTO INTEGER NOT NULL,
	ID_CATEGORIA_ARTICULO INTEGER NOT NULL,
        ID_FORMA_DE_PAGO_PREFERIDA INTEGER NOT NULL DEFAULT 1,
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
	NOMBRE VARCHAR(256) NOT NULL,
	RUC_SIN_DV VARCHAR(20),
	CTACTE INTEGER,
	DOMICILIO VARCHAR(50),
	BOX INTEGER,
	PRIMARY KEY (ID)
);

CREATE TABLE MG.TBL_MIEMBROS_RELACIONES (
    ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
    ID_MIEMBROS_FAMILIA INTEGER,
    ID_ENTIDAD_1 INTEGER,
    ID_ENTIDAD_2 INTEGER,
    ID_MIEMBROS_RELACIONES_TIPO INTEGER,
    ID_MIEMBROS_RELACIONES_ROL_1 INTEGER,
    ID_MIEMBROS_RELACIONES_ROL_2 INTEGER,
    FECHA_INICIO TIMESTAMP,
    FECHA_FIN TIMESTAMP,
    ID_USER INTEGER,
    PRIMARY KEY (ID)
);

CREATE TABLE MG.TBL_MIEMBROS_RELACIONES_TIPOS (
        ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
	DESCRIPCION VARCHAR(50) NOT NULL,
	PRIMARY KEY (ID)
);

CREATE TABLE MG.TBL_MIEMBROS_RELACIONES_ROLES (
        ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
	DESCRIPCION VARCHAR(50) NOT NULL,
	PRIMARY KEY (ID)
);

CREATE TABLE MG.TBL_MIEMBROS_FAMILIAS (
        ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
	DESCRIPCION VARCHAR(50),
        FOTO BLOB(2147483647),
	PRIMARY KEY (ID)
);

CREATE TABLE MG.TBL_ENTIDADES (
	ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
	NOMBRES VARCHAR(128) NOT NULL,
        APELLIDOS VARCHAR(128) NOT NULL DEFAULT '',
        RAZON_SOCIAL VARCHAR(256) NOT NULL DEFAULT '',
        RUC_SIN_DV VARCHAR(20) NOT NULL DEFAULT '44444401',
	CTACTE INTEGER NOT NULL DEFAULT 99999,
	DOMICILIO VARCHAR(50),
	BOX INTEGER,
        IS_MIEMBRO_ACTIVO BOOLEAN NOT NULL DEFAULT FALSE,
        ID_FORMA_DE_PAGO_PREFERIDA INTEGER,
        APORTE_MENSUAL INTEGER NOT NULL DEFAULT 0,
        ID_ENTIDAD_PAGANTE_APORTES INTEGER,
        FECHA_NACIMIENTO TIMESTAMP,
        FECHA_BAUTISMO TIMESTAMP,
        FECHA_ENTRADA_CONGREGACION TIMESTAMP,
        FECHA_SALIDA_CONGREGACION TIMESTAMP,
        FECHA_DEFUNCION TIMESTAMP,
        ID_AREA_SERVICIO_EN_IGLESIA INTEGER,
        ID_MIEMBROS_CATEGORIA_DE_PAGO INTEGER,
        ID_MIEMBROS_ALERGIA INTEGER,
	ID_USER INTEGER,
	PRIMARY KEY (ID)
);

CREATE TABLE MG.TBL_FORMAS_DE_PAGO (
	ID INTEGER NOT NULL,
	DESCRIPCION VARCHAR(50) NOT NULL,
	PRIMARY KEY (ID)
);

CREATE TABLE MG.TBL_AREAS_SERVICIO_EN_IGLESIA(
    ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
    DESCRIPCION VARCHAR(50) NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE MG.TBL_MIEMBROS_CATEGORIAS_DE_PAGO(
    ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
    DESCRIPCION VARCHAR(50) NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE MG.TBL_MIEMBROS_ALERGIAS(
    ID INTEGER,
    DESCRIPCION VARCHAR(50) NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE MG.TBL_RECIBOS (
    ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
    FECHAHORA TIMESTAMP NOT NULL,
    CONCEPTO VARCHAR(50),
    MONTO_APORTE INTEGER NOT NULL,
    MONTO_DONACION INTEGER NOT NULL,
    ID_ENTIDAD INTEGER NOT NULL,
    ID_EVENTO_TIPO INTEGER NOT NULL,
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
	MONTO_APORTE INTEGER NOT NULL,
        MONTO_DONACION INTEGER NOT NULL,
	ID_ENTIDAD INTEGER NOT NULL,
        ID_EVENTO_TIPO INTEGER NOT NULL,
        COBRADO BOOLEAN NOT NULL DEFAULT FALSE,
	ID_USER INTEGER NOT NULL,
	PRIMARY KEY (ID)
);

CREATE TABLE MG.TBL_USERS (
	ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
	NOMBRE VARCHAR(50) NOT NULL,
	PASSWORD VARCHAR(100) NOT NULL,
	NOMBRECOMPLETO VARCHAR(100) NOT NULL,
        PRIMARY KEY (ID)
);

CREATE TABLE MG.TBL_FACTURAS (
        NRO INTEGER NOT NULL,
        ID_TIMBRADO INTEGER NOT NULL,
        FECHAHORA TIMESTAMP NOT NULL,
        ID_ENTIDAD INTEGER NOT NULL,
        RAZON_SOCIAL VARCHAR(50) NOT NULL,
        RUC VARCHAR(20) NOT NULL,
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

CREATE TABLE MG.TBL_CONTRIBUYENTES (
    RUC_SIN_DV VARCHAR(20) NOT NULL,
    DV VARCHAR(1) NOT NULL,
    RAZON_SOCIAL VARCHAR(256) NOT NULL,
    PRIMARY KEY (RUC_SIN_DV)
);

CREATE TABLE MG.TBL_DATABASE_UPDATES (
	ID VARCHAR(50) NOT NULL,
	PRIMARY KEY (ID)
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
	ADD FOREIGN KEY (ID_ENTIDAD)
	REFERENCES MG.TBL_ENTIDADES (ID);

ALTER TABLE MG.TBL_EVENTO_DETALLE
	ADD FOREIGN KEY (ID_FORMA_DE_PAGO_PREFERIDA)
	REFERENCES MG.TBL_FORMAS_DE_PAGO (ID);

ALTER TABLE MG.TBL_EVENTO_DETALLE
	ADD FOREIGN KEY (ID_USER)
	REFERENCES MG.TBL_USERS (ID);

ALTER TABLE MG.TBL_EVENTO_CUOTAS
	ADD FOREIGN KEY (ID_EVENTO)
	REFERENCES MG.TBL_EVENTOS (ID)
        ON DELETE CASCADE;

ALTER TABLE MG.TBL_MIEMBROS_RELACIONES
	ADD FOREIGN KEY (ID_ENTIDAD_1)
	REFERENCES MG.TBL_ENTIDADES (ID);

ALTER TABLE MG.TBL_MIEMBROS_RELACIONES
	ADD FOREIGN KEY (ID_ENTIDAD_2)
	REFERENCES MG.TBL_ENTIDADES (ID);

ALTER TABLE MG.TBL_MIEMBROS_RELACIONES
	ADD FOREIGN KEY (ID_MIEMBROS_FAMILIA)
	REFERENCES MG.TBL_MIEMBROS_FAMILIAS (ID);

ALTER TABLE MG.TBL_MIEMBROS_RELACIONES
	ADD FOREIGN KEY (ID_MIEMBROS_RELACIONES_TIPO)
	REFERENCES MG.TBL_MIEMBROS_RELACIONES_TIPOS (ID);

ALTER TABLE MG.TBL_MIEMBROS_RELACIONES
	ADD FOREIGN KEY (ID_MIEMBROS_RELACIONES_ROL_1)
	REFERENCES MG.TBL_MIEMBROS_RELACIONES_ROLES (ID);

ALTER TABLE MG.TBL_MIEMBROS_RELACIONES
	ADD FOREIGN KEY (ID_MIEMBROS_RELACIONES_ROL_2)
	REFERENCES MG.TBL_MIEMBROS_RELACIONES_ROLES (ID);

ALTER TABLE MG.TBL_MIEMBROS_RELACIONES
	ADD FOREIGN KEY (ID_USER)
	REFERENCES MG.TBL_USERS (ID);

ALTER TABLE MG.TBL_ENTIDADES
	ADD FOREIGN KEY (ID_FORMA_DE_PAGO_PREFERIDA)
        REFERENCES MG.TBL_FORMAS_DE_PAGO (ID);

ALTER TABLE MG.TBL_ENTIDADES
	ADD FOREIGN KEY (ID_ENTIDAD_PAGANTE_APORTES)
        REFERENCES MG.TBL_ENTIDADES (ID);

ALTER TABLE MG.TBL_ENTIDADES
	ADD FOREIGN KEY (ID_AREA_SERVICIO_EN_IGLESIA)
        REFERENCES MG.TBL_AREAS_SERVICIO_EN_IGLESIA (ID);

ALTER TABLE MG.TBL_ENTIDADES
	ADD FOREIGN KEY (ID_MIEMBROS_CATEGORIA_DE_PAGO)
        REFERENCES MG.TBL_MIEMBROS_CATEGORIAS_DE_PAGO (ID);

ALTER TABLE MG.TBL_ENTIDADES
	ADD FOREIGN KEY (ID_MIEMBROS_ALERGIA)
        REFERENCES MG.TBL_MIEMBROS_ALERGIAS (ID);

ALTER TABLE MG.TBL_ENTIDADES
	ADD FOREIGN KEY (ID_USER)
	REFERENCES MG.TBL_USERS (ID);

ALTER TABLE MG.TBL_RECIBOS
	ADD FOREIGN KEY (ID_ENTIDAD)
	REFERENCES MG.TBL_ENTIDADES (ID);

ALTER TABLE MG.TBL_RECIBOS
	ADD FOREIGN KEY (ID_EVENTO_TIPO)
	REFERENCES MG.TBL_EVENTO_TIPOS (ID);

ALTER TABLE MG.TBL_RECIBOS
	ADD FOREIGN KEY (ID_USER)
	REFERENCES MG.TBL_USERS (ID);

ALTER TABLE MG.TBL_FACTURAS
	ADD FOREIGN KEY (ID_ENTIDAD)
	REFERENCES MG.TBL_ENTIDADES (ID);

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
	ADD FOREIGN KEY (ID_ENTIDAD)
	REFERENCES MG.TBL_ENTIDADES (ID);

ALTER TABLE MG.TBL_TRANSFERENCIAS
	ADD FOREIGN KEY (ID_EVENTO_TIPO)
	REFERENCES MG.TBL_EVENTO_TIPOS (ID);

ALTER TABLE MG.TBL_TRANSFERENCIAS
	ADD FOREIGN KEY (ID_USER)
	REFERENCES MG.TBL_USERS (ID);

CREATE VIEW MG.RECIBO AS SELECT p.id, p.fechahora, d.nombres || d.apellidos AS nombre, p.concepto, p.monto_aporte + p.monto_donacion AS monto FROM TBL_RECIBOS p, TBL_ENTIDADES d WHERE ((p.ID_ENTIDAD = d.id));

CREATE VIEW MG.TRANSFERENCIA AS
    SELECT t.id,
        t.fechahora,
        d.ctacte AS cta_debito,
        i.ctacte AS cta_credito,
        d.nombres || ' ' || d.apellidos  AS d_nombre,
        i.nombre  AS c_nombre,
        d.domicilio AS d_domicilio,
        i.domicilio AS c_domicilio,
        d.box AS d_box,
        i.box AS c_box,
        t.monto_aporte + t.monto_donacion AS monto,
        t.concepto
    FROM TBL_TRANSFERENCIAS t, TBL_ENTIDADES d, TBL_IGLESIA i
    WHERE t.ID_ENTIDAD = d.ID;

INSERT INTO MG.TBL_FORMAS_DE_PAGO (ID, DESCRIPCION) VALUES (1, 'Transferencia');
INSERT INTO MG.TBL_FORMAS_DE_PAGO (ID, DESCRIPCION) VALUES (2, 'Efectivo');

INSERT INTO MG.TBL_MIEMBROS_CATEGORIAS_DE_PAGO (DESCRIPCION) VALUES ('Estudiante');
INSERT INTO MG.TBL_MIEMBROS_CATEGORIAS_DE_PAGO (DESCRIPCION) VALUES ('Miembro Nuevo');
INSERT INTO MG.TBL_MIEMBROS_CATEGORIAS_DE_PAGO (DESCRIPCION) VALUES ('Regular');
INSERT INTO MG.TBL_MIEMBROS_CATEGORIAS_DE_PAGO (DESCRIPCION) VALUES ('Tercera Edad');
INSERT INTO MG.TBL_MIEMBROS_CATEGORIAS_DE_PAGO (DESCRIPCION) VALUES ('Viudo/a');

INSERT INTO MG.TBL_ROLES (DESCRIPCION) VALUES ('Cajero');
INSERT INTO MG.TBL_ROLES (DESCRIPCION) VALUES ('Admin');
INSERT INTO MG.TBL_ROLES (DESCRIPCION) VALUES ('Master');

INSERT INTO MG.TBL_EVENTO_TIPOS (ID, DESCRIPCION) VALUES (1, 'Remate');
INSERT INTO MG.TBL_EVENTO_TIPOS (ID, DESCRIPCION) VALUES (2, 'Colecta');
INSERT INTO MG.TBL_EVENTO_TIPOS (ID, DESCRIPCION) VALUES (3, 'Aporte');

INSERT INTO MG.TBL_GRUPOS (DESCRIPCION) VALUES ('Gemeinde');

INSERT INTO MG.TBL_CATEGORIAS_ARTICULOS (DESCRIPCION) VALUES ('Colecta');
INSERT INTO MG.TBL_CATEGORIAS_ARTICULOS (DESCRIPCION) VALUES ('Aporte');

CREATE TRIGGER MG.EVENTO_CUOTA_TRIGGER
AFTER
INSERT
ON MG.TBL_EVENTOS
REFERENCING NEW AS new_row
FOR EACH ROW
MODE DB2SQL
INSERT INTO MG.TBL_EVENTO_CUOTAS (ID_EVENTO, FECHA_1) VALUES (new_row.ID, new_row.FECHA);

/*
CREATE VIEW MG.ENTIDADES_CON_PAGOS_PENDIENTES AS
SELECT remates.id, remates.nombre, remates.ctacte, remates.domicilio, remates.box FROM
        (SELECT m.id, m.nombres || ' ' || m.apellidos AS nombre, m.ctacte, m.domicilio, m.box, SUM(rd.monto) AS monto FROM TBL_ENTIDADES m
            LEFT JOIN TBL_EVENTO_DETALLE rd ON m.id = rd.ID_ENTIDAD
            group by m.id, m.nombres, m.apellidos, m.RAZON_SOCIAL, m.RUC_SIN_DV, m.ctacte, m.domicilio, m.box, m.aporte_mensual, m.id_user, m.id_forma_de_pago_preferida,
                    m.ID_ENTIDAD_PAGANTE_APORTES,
                    m.IS_MIEMBRO_ACTIVO,
                    m.ID_FORMA_DE_PAGO_PREFERIDA,
                    m.APORTE_MENSUAL,
                    m.FECHA_NACIMIENTO,
                    m.FECHA_BAUTISMO,
                    m.FECHA_ENTRADA_CONGREGACION,
                    m.FECHA_SALIDA_CONGREGACION,
                    m.FECHA_DEFUNCION,
                    m.ID_AREA_SERVICIO_EN_IGLESIA,
                    m.ID_MIEMBROS_CATEGORIA_DE_PAGO,
                    m.ID_MIEMBROS_ALERGIA) remates,
        (SELECT m.*, COALESCE(SUM(p.monto),0) AS monto FROM TBL_ENTIDADES m
            LEFT JOIN TBL_TRANSFERENCIAS p ON m.id = p.ID_ENTIDAD
            group by m.id, m.nombres, m.apellidos, m.RAZON_SOCIAL, m.RUC_SIN_DV, m.ctacte, m.domicilio, m.box, m.aporte_mensual, m.id_user, m.id_forma_de_pago_preferida,
                    m.IS_MIEMBRO_ACTIVO,
                    m.ID_ENTIDAD_PAGANTE_APORTES,
                    m.ID_FORMA_DE_PAGO_PREFERIDA,
                    m.APORTE_MENSUAL,
                    m.FECHA_NACIMIENTO,
                    m.FECHA_BAUTISMO,
                    m.FECHA_ENTRADA_CONGREGACION,
                    m.FECHA_SALIDA_CONGREGACION,
                    m.FECHA_DEFUNCION,
                    m.ID_AREA_SERVICIO_EN_IGLESIA,
                    m.ID_MIEMBROS_CATEGORIA_DE_PAGO,
                    m.ID_MIEMBROS_ALERGIA) transferencias,
        (SELECT m.*, COALESCE(SUM(p.monto),0) AS monto FROM TBL_ENTIDADES m
            LEFT JOIN TBL_RECIBOS p ON m.id = p.ID_ENTIDAD
            group by m.id, m.nombres, m.apellidos, m.RAZON_SOCIAL, m.RUC_SIN_DV, m.ctacte, m.domicilio, m.box, m.aporte_mensual, m.id_user, m.id_forma_de_pago_preferida,
                    m.IS_MIEMBRO_ACTIVO,
                    m.ID_FORMA_DE_PAGO_PREFERIDA,
                    m.APORTE_MENSUAL,
                    m.ID_ENTIDAD_PAGANTE_APORTES,
                    m.FECHA_NACIMIENTO,
                    m.FECHA_BAUTISMO,
                    m.FECHA_ENTRADA_CONGREGACION,
                    m.FECHA_SALIDA_CONGREGACION,
                    m.FECHA_DEFUNCION,
                    m.ID_AREA_SERVICIO_EN_IGLESIA,
                    m.ID_MIEMBROS_CATEGORIA_DE_PAGO,
                    m.ID_MIEMBROS_ALERGIA) recibos
    WHERE remates.id = transferencias.id AND remates.id = recibos.id AND (remates.monto - transferencias.monto - recibos.monto) > 0
    ORDER BY remates.nombre;
*/