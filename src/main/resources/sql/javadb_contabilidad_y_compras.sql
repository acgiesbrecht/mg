DROP TABLE MG.TBL_EVENTO_DETALLE_ASIENTOS;
DROP TABLE MG.TBL_FACTURAS_ASIENTOS;
DROP TABLE MG.TBL_FACTURAS_COMPRA_ASIENTOS;
DROP TABLE MG.TBL_RECIBOS_ASIENTOS_TEMPORALES;
DROP TABLE MG.TBL_TRANSFERENCIAS_ASIENTOS_TEMPORALES;
DROP TABLE MG.TBL_ASIENTOS_TEMPORALES;
DROP TABLE MG.TBL_RECIBOS_COMPRA_FACTURAS_COMPRA;
DROP TABLE MG.TBL_FACTURAS_COMPRA;
DROP TABLE MG.TBL_AUTOFACTURAS_ASIENTOS;
DROP TABLE MG.TBL_AUTOFACTURAS;
DROP TABLE MG.TBL_TIMBRADOS_AUTOFACTURAS;
DROP TABLE MG.TBL_NOTAS_DE_CREDITO_ASIENTOS;
DROP TABLE MG.TBL_NOTAS_DE_CREDITO;
DROP TABLE MG.TBL_RECIBOS_COMPRA_ASIENTOS;
DROP TABLE MG.TBL_RECIBOS_COMPRA;
DROP TABLE MG.TBL_ASIENTOS;
DROP TABLE MG.TBL_CENTROS_DE_COSTO;
DROP TABLE MG.TBL_CUENTAS_CONTABLES_POR_DEFECTO;
DROP TABLE MG.TBL_CUENTAS_CONTABLES;

CREATE TABLE MG.TBL_FACTURAS_COMPRA (
        ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
        NRO VARCHAR(15) NOT NULL,
        NRO_TIMBRADO VARCHAR(8) NOT NULL,
        VENCIMIENTO_TIMBRADO TIMESTAMP NOT NULL,
        CONDICION_CONTADO BOOLEAN DEFAULT TRUE NOT NULL,
        FECHA_VENCIMIENTO_CREDITO TIMESTAMP,
        CUOTAS_CREDITO INTEGER,
        FECHAHORA TIMESTAMP NOT NULL,
        RAZON_SOCIAL VARCHAR(255) NOT NULL,
        RUC VARCHAR(20) NOT NULL,
        MONTO_EXENTAS INTEGER DEFAULT 0 NOT NULL,
        MONTO_IVA5 INTEGER DEFAULT 0 NOT NULL,
        MONTO_IVA10 INTEGER DEFAULT 0 NOT NULL,
        IVA5 INTEGER DEFAULT 0 NOT NULL,
        IVA10 INTEGER DEFAULT 0 NOT NULL,
        OBSERVACION VARCHAR(255),
        ID_USER INTEGER NOT NULL,
	PRIMARY KEY (ID)
);

CREATE TABLE MG.TBL_AUTOFACTURAS (
        ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
        NRO VARCHAR(15) NOT NULL,
        ID_TIMBRADO INTEGER NOT NULL,
        FECHAHORA TIMESTAMP NOT NULL,
        NOMBRE VARCHAR(255) NOT NULL,
        DOMICILIO VARCHAR(255) DEFAULT '' NOT NULL,
        DIRECCION_DE_TRANSACCION VARCHAR(255) DEFAULT '' NOT NULL,
        CI VARCHAR(20) NOT NULL,
        CANTIDAD INTEGER DEFAULT 1 NOT NULL,
        CONCEPTO VARCHAR(255) DEFAULT '' NOT NULL,
        PRECIO_UNITARIO INTEGER DEFAULT 0 NOT NULL,
        MONTO INTEGER DEFAULT 0 NOT NULL,
        OBSERVACION VARCHAR(255),
        ANULADO BOOLEAN DEFAULT FALSE NOT NULL,
        ID_USER INTEGER NOT NULL,
	PRIMARY KEY (ID)
);

CREATE TABLE MG.TBL_NOTAS_DE_CREDITO (
        ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
        NRO VARCHAR(15) NOT NULL,
        NRO_TIMBRADO VARCHAR(8) NOT NULL,
        VENCIMIENTO_TIMBRADO TIMESTAMP NOT NULL,
        FECHAHORA TIMESTAMP NOT NULL,
        RAZON_SOCIAL VARCHAR(255) NOT NULL,
        RUC VARCHAR(20) NOT NULL,
        MONTO_EXENTAS INTEGER DEFAULT 0 NOT NULL,
        MONTO_IVA5 INTEGER DEFAULT 0 NOT NULL,
        MONTO_IVA10 INTEGER DEFAULT 0 NOT NULL,
        IVA5 INTEGER DEFAULT 0 NOT NULL,
        IVA10 INTEGER DEFAULT 0 NOT NULL,
        OBSERVACION VARCHAR(255),
        ID_USER INTEGER NOT NULL,
	PRIMARY KEY (ID)
);

CREATE TABLE MG.TBL_RECIBOS_COMPRA (
        ID INTEGER NOT NULL,
        FECHAHORA TIMESTAMP NOT NULL,
        RAZON_SOCIAL VARCHAR(255) NOT NULL,
        RUC VARCHAR(20) NOT NULL,
        MONTO INTEGER DEFAULT 0 NOT NULL,
        OBSERVACION VARCHAR(255),
        ID_USER INTEGER NOT NULL,
	PRIMARY KEY (ID)
);

CREATE TABLE MG.TBL_CUENTAS_CONTABLES (
    ID INTEGER NOT NULL,
    DESCRIPCION VARCHAR(255) NOT NULL,
    ID_CUENTA_MADRE INTEGER,
    IMPUTABLE BOOLEAN,
    PRIMARY KEY (ID)
);

CREATE TABLE MG.TBL_CUENTAS_CONTABLES_POR_DEFECTO (
    ID INTEGER NOT NULL,
    ID_CUENTA_HABER_COMPRAS_FACTURA_CONTADO INTEGER DEFAULT 101010200 NOT NULL,
    ID_CUENTA_HABER_COMPRAS_FACTURA_CREDITO INTEGER DEFAULT 201010100 NOT NULL,
    ID_CUENTA_DEBE_COMPRAS INTEGER DEFAULT 501010900 NOT NULL,
    ID_CUENTA_A_COBRAR INTEGER DEFAULT 101020100 NOT NULL,
    ID_CUENTA_APORTES INTEGER DEFAULT 401010100 NOT NULL,
    ID_CUENTA_DONACIONES INTEGER DEFAULT 401010200 NOT NULL,
    ID_CUENTA_CAJA INTEGER DEFAULT 101010100 NOT NULL,
    ID_CUENTA_CTA_CTE INTEGER DEFAULT 101010200 NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE MG.TBL_CENTROS_DE_COSTO (
    ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
    DESCRIPCION VARCHAR(255) NOT NULL,
    ID_CUENTA_CONTABLE_EFECTIVO_POR_DEFECTO INTEGER DEFAULT 101010100 NOT NULL,
    ID_CUENTA_CONTABLE_CTA_CTE_POR_DEFECTO INTEGER DEFAULT 101010200 NOT NULL,
    CTA_CTE INTEGER NOT NULL,
    PREFERIDO BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (ID)
);

CREATE TABLE MG.TBL_ASIENTOS (
    ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
    FECHAHORA TIMESTAMP NOT NULL,
    ID_CENTRO_DE_COSTO INTEGER NOT NULL,
    ID_CUENTA_CONTABLE_DEBE INTEGER NOT NULL,
    ID_CUENTA_CONTABLE_HABER INTEGER NOT NULL,
    OBSERVACION VARCHAR(255),
    MONTO INTEGER NOT NULL,
    ID_USER INTEGER NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE MG.TBL_EVENTO_DETALLE_ASIENTOS (
    ID_EVENTO_DETALLE INTEGER NOT NULL,
    ID_ASIENTO INTEGER NOT NULL
);

CREATE TABLE MG.TBL_FACTURAS_COMPRA_ASIENTOS (
    ID_FACTURA_COMPRA INTEGER NOT NULL,
    ID_ASIENTO INTEGER NOT NULL
);

CREATE TABLE MG.TBL_AUTOFACTURAS_ASIENTOS (
    ID_AUTOFACTURA INTEGER NOT NULL,
    ID_ASIENTO INTEGER NOT NULL
);

CREATE TABLE MG.TBL_NOTAS_DE_CREDITO_ASIENTOS (
    ID_NOTA_DE_CREDITO INTEGER NOT NULL,
    ID_ASIENTO INTEGER NOT NULL
);

CREATE TABLE MG.TBL_RECIBOS_COMPRA_ASIENTOS (
    ID_RECIBO INTEGER NOT NULL,
    ID_ASIENTO INTEGER NOT NULL
);

CREATE TABLE MG.TBL_FACTURAS_ASIENTOS (
    NRO_FACTURA INTEGER NOT NULL,
    ID_ASIENTO INTEGER NOT NULL
);

CREATE TABLE MG.TBL_ASIENTOS_TEMPORALES (
    ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
    FECHAHORA TIMESTAMP NOT NULL,
    ID_CENTRO_DE_COSTO INTEGER NOT NULL,
    ID_CUENTA_CONTABLE_DEBE INTEGER NOT NULL,
    ID_CUENTA_CONTABLE_HABER INTEGER NOT NULL,
    MONTO INTEGER NOT NULL,
    ES_APORTE BOOLEAN NOT NULL,
    FACTURADO BOOLEAN DEFAULT FALSE NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE MG.TBL_TRANSFERENCIAS_ASIENTOS_TEMPORALES (
    ID_TRANSFERENCIA INTEGER NOT NULL,
    ID_ASIENTO_TEMPORAL INTEGER NOT NULL
);

CREATE TABLE MG.TBL_RECIBOS_ASIENTOS_TEMPORALES (
    ID_RECIBO INTEGER NOT NULL,
    ID_ASIENTO_TEMPORAL INTEGER NOT NULL
);

CREATE TABLE MG.TBL_RECIBOS_COMPRA_FACTURAS_COMPRA (
    ID_RECIBO INTEGER NOT NULL,
    ID_FACTURA_COMPRA INTEGER NOT NULL,
    MONTO INTEGER DEFAULT 0 NOT NULL,
    PRIMARY KEY (ID_RECIBO, ID_FACTURA_COMPRA)
);

CREATE TABLE MG.TBL_TIMBRADOS_AUTOFACTURAS (
        ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
        NRO VARCHAR(8) NOT NULL,
        FECHA_INICIO TIMESTAMP NOT NULL,
        FECHA_VENCIMIENTO TIMESTAMP NOT NULL,
        NRO_FACTURA_INCIO INTEGER NOT NULL,
        NRO_FACTURA_FIN INTEGER NOT NULL,
        ACTIVO BOOLEAN NOT NULL,
        ID_USER INTEGER NOT NULL,
	PRIMARY KEY (ID)
);

ALTER TABLE MG.TBL_EVENTOS
	ADD FOREIGN KEY (ID_CENTRO_DE_COSTO) REFERENCES MG.TBL_CENTROS_DE_COSTO (ID);

ALTER TABLE MG.TBL_EVENTO_DETALLE_ASIENTOS
	ADD FOREIGN KEY (ID_EVENTO_DETALLE) REFERENCES MG.TBL_EVENTO_DETALLE (ID);

ALTER TABLE MG.TBL_EVENTO_DETALLE_ASIENTOS
	ADD FOREIGN KEY (ID_ASIENTO) REFERENCES MG.TBL_ASIENTOS (ID);

ALTER TABLE MG.TBL_FACTURAS_COMPRA_ASIENTOS
	ADD FOREIGN KEY (ID_FACTURA_COMPRA) REFERENCES MG.TBL_FACTURAS_COMPRA (ID);

ALTER TABLE MG.TBL_FACTURAS_COMPRA_ASIENTOS
	ADD FOREIGN KEY (ID_ASIENTO) REFERENCES MG.TBL_ASIENTOS (ID);

ALTER TABLE MG.TBL_AUTOFACTURAS_ASIENTOS
	ADD FOREIGN KEY (ID_AUTOFACTURA) REFERENCES MG.TBL_AUTOFACTURAS (ID);

ALTER TABLE MG.TBL_AUTOFACTURAS_ASIENTOS
	ADD FOREIGN KEY (ID_ASIENTO) REFERENCES MG.TBL_ASIENTOS (ID);

ALTER TABLE MG.TBL_NOTAS_DE_CREDITO_ASIENTOS
	ADD FOREIGN KEY (ID_NOTA_DE_CREDITO) REFERENCES MG.TBL_NOTAS_DE_CREDITO (ID);

ALTER TABLE MG.TBL_NOTAS_DE_CREDITO_ASIENTOS
	ADD FOREIGN KEY (ID_ASIENTO) REFERENCES MG.TBL_ASIENTOS (ID);

ALTER TABLE MG.TBL_RECIBOS_COMPRA_ASIENTOS
	ADD FOREIGN KEY (ID_RECIBO) REFERENCES MG.TBL_RECIBOS_COMPRA (ID);

ALTER TABLE MG.TBL_RECIBOS_COMPRA_ASIENTOS
	ADD FOREIGN KEY (ID_ASIENTO) REFERENCES MG.TBL_ASIENTOS (ID);

ALTER TABLE MG.TBL_FACTURAS_ASIENTOS
	ADD FOREIGN KEY (NRO_FACTURA) REFERENCES MG.TBL_FACTURAS (NRO);

ALTER TABLE MG.TBL_FACTURAS_ASIENTOS
	ADD FOREIGN KEY (ID_ASIENTO) REFERENCES MG.TBL_ASIENTOS (ID);

ALTER TABLE MG.TBL_TRANSFERENCIAS_ASIENTOS_TEMPORALES
	ADD FOREIGN KEY (ID_TRANSFERENCIA) REFERENCES MG.TBL_TRANSFERENCIAS (ID);

ALTER TABLE MG.TBL_TRANSFERENCIAS_ASIENTOS_TEMPORALES
	ADD FOREIGN KEY (ID_ASIENTO_TEMPORAL) REFERENCES MG.TBL_ASIENTOS_TEMPORALES (ID);

ALTER TABLE MG.TBL_RECIBOS_ASIENTOS_TEMPORALES
	ADD FOREIGN KEY (ID_RECIBO) REFERENCES MG.TBL_RECIBOS (ID);

ALTER TABLE MG.TBL_RECIBOS_ASIENTOS_TEMPORALES
	ADD FOREIGN KEY (ID_ASIENTO_TEMPORAL) REFERENCES MG.TBL_ASIENTOS_TEMPORALES (ID);

ALTER TABLE MG.TBL_RECIBOS_COMPRA_FACTURAS_COMPRA
	ADD FOREIGN KEY (ID_RECIBO) REFERENCES MG.TBL_RECIBOS_COMPRA (ID);

ALTER TABLE MG.TBL_RECIBOS_COMPRA_FACTURAS_COMPRA
	ADD FOREIGN KEY (ID_FACTURA_COMPRA) REFERENCES MG.TBL_FACTURAS_COMPRA (ID);

ALTER TABLE MG.TBL_ASIENTOS
	ADD FOREIGN KEY (ID_CENTRO_DE_COSTO) REFERENCES MG.TBL_CENTROS_DE_COSTO (ID);

ALTER TABLE MG.TBL_ASIENTOS
	ADD FOREIGN KEY (ID_CUENTA_CONTABLE_DEBE) REFERENCES MG.TBL_CUENTAS_CONTABLES (ID);

ALTER TABLE MG.TBL_ASIENTOS
	ADD FOREIGN KEY (ID_CUENTA_CONTABLE_HABER) REFERENCES MG.TBL_CUENTAS_CONTABLES (ID);

ALTER TABLE MG.TBL_ASIENTOS
	ADD FOREIGN KEY (ID_USER) REFERENCES MG.TBL_USERS (ID);

ALTER TABLE MG.TBL_ASIENTOS_TEMPORALES
	ADD FOREIGN KEY (ID_CENTRO_DE_COSTO) REFERENCES MG.TBL_CENTROS_DE_COSTO (ID);

ALTER TABLE MG.TBL_ASIENTOS_TEMPORALES
	ADD FOREIGN KEY (ID_CUENTA_CONTABLE_DEBE) REFERENCES MG.TBL_CUENTAS_CONTABLES (ID);

ALTER TABLE MG.TBL_ASIENTOS_TEMPORALES
	ADD FOREIGN KEY (ID_CUENTA_CONTABLE_HABER) REFERENCES MG.TBL_CUENTAS_CONTABLES (ID);

ALTER TABLE MG.TBL_CUENTAS_CONTABLES
	ADD FOREIGN KEY (ID_CUENTA_MADRE) REFERENCES MG.TBL_CUENTAS_CONTABLES (ID);

ALTER TABLE MG.TBL_CUENTAS_CONTABLES_POR_DEFECTO
        ADD FOREIGN KEY (ID_CUENTA_HABER_COMPRAS_FACTURA_CONTADO) REFERENCES MG.TBL_CUENTAS_CONTABLES (ID);

ALTER TABLE MG.TBL_CUENTAS_CONTABLES_POR_DEFECTO
        ADD FOREIGN KEY (ID_CUENTA_HABER_COMPRAS_FACTURA_CREDITO) REFERENCES MG.TBL_CUENTAS_CONTABLES (ID);

ALTER TABLE MG.TBL_CUENTAS_CONTABLES_POR_DEFECTO
        ADD FOREIGN KEY (ID_CUENTA_DEBE_COMPRAS) REFERENCES MG.TBL_CUENTAS_CONTABLES (ID);

ALTER TABLE MG.TBL_CUENTAS_CONTABLES_POR_DEFECTO
        ADD FOREIGN KEY (ID_CUENTA_A_COBRAR) REFERENCES MG.TBL_CUENTAS_CONTABLES (ID);

ALTER TABLE MG.TBL_CUENTAS_CONTABLES_POR_DEFECTO
        ADD FOREIGN KEY (ID_CUENTA_APORTES) REFERENCES MG.TBL_CUENTAS_CONTABLES (ID);

ALTER TABLE MG.TBL_CUENTAS_CONTABLES_POR_DEFECTO
        ADD FOREIGN KEY (ID_CUENTA_DONACIONES) REFERENCES MG.TBL_CUENTAS_CONTABLES (ID);

ALTER TABLE MG.TBL_CUENTAS_CONTABLES_POR_DEFECTO
        ADD FOREIGN KEY (ID_CUENTA_CAJA) REFERENCES MG.TBL_CUENTAS_CONTABLES (ID);

ALTER TABLE MG.TBL_CUENTAS_CONTABLES_POR_DEFECTO
        ADD FOREIGN KEY (ID_CUENTA_CTA_CTE) REFERENCES MG.TBL_CUENTAS_CONTABLES (ID);

ALTER TABLE MG.TBL_CENTROS_DE_COSTO
        ADD FOREIGN KEY (ID_CUENTA_CONTABLE_EFECTIVO_POR_DEFECTO) REFERENCES MG.TBL_CUENTAS_CONTABLES (ID);

ALTER TABLE MG.TBL_CENTROS_DE_COSTO
        ADD FOREIGN KEY (ID_CUENTA_CONTABLE_CTA_CTE_POR_DEFECTO) REFERENCES MG.TBL_CUENTAS_CONTABLES (ID);

ALTER TABLE MG.TBL_FACTURAS_COMPRA
	ADD FOREIGN KEY (ID_USER) REFERENCES MG.TBL_USERS (ID);

ALTER TABLE MG.TBL_AUTOFACTURAS
	ADD FOREIGN KEY (ID_USER) REFERENCES MG.TBL_USERS (ID);

ALTER TABLE MG.TBL_AUTOFACTURAS
	ADD FOREIGN KEY (ID_TIMBRADO) REFERENCES MG.TBL_TIMBRADOS_AUTOFACTURAS (ID);

ALTER TABLE MG.TBL_ASIENTOS
	ADD FOREIGN KEY (ID_USER) REFERENCES MG.TBL_USERS (ID);

ALTER TABLE MG.TBL_TIMBRADOS_AUTOFACTURAS
	ADD FOREIGN KEY (ID_USER) REFERENCES MG.TBL_USERS (ID);

INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (100000000, 'ACTIVO',NULL,FALSE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (101000000, 'ACTIVO CIRCULANTE',100000000,FALSE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (101010000, 'DISPONIBILIDADES',101000000,FALSE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (101010100, 'Caja',101010000,TRUE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (101010200, 'Cta. Cte. Chortitzer',101010000,TRUE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (101020000, 'CREDITOS',101000000,FALSE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (101020100, 'A Cobrar',101020000,TRUE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (101020200, 'Clientes',101020000,TRUE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (101030000, 'MERCADERIAS',101000000,FALSE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (101030100, 'Utiles de oficina',101030000,TRUE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (101030200, 'Mercaderias - Provista',101030000,TRUE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (102000000, 'ACTIVO FIJO',101000000,FALSE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (102010100, 'Muebles y Utiles',102000000,TRUE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (102020100, 'Equipos de Informática',102000000,TRUE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (102030100, 'Rodados',102000000,TRUE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (102040100, 'Instalaciones',102000000,TRUE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (102050100, 'Edificios',102000000,TRUE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (102060100, 'Maquinarias Y Equipos',102000000,TRUE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (102070100, 'Terrenos',102000000,TRUE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (102080100, 'Herramientas',102000000,TRUE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (103000000, 'ACTIVOS INTANGIBLES',100000000,FALSE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (103010000, 'CARGOS DIFERIDOS',103000000,FALSE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (103010100, 'Software',103010000,TRUE);

INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (200000000, 'PASIVO',NULL,FALSE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (201000000, 'DEUDAS A CORTO PLAZO',200000000,FALSE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (201010000, 'DEUDAS COMERCIALES',201000000,FALSE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (201010100, 'Proveedores a Pagar',201010000,TRUE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (201020000, 'DEUDAS FISCALES',201000000,FALSE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (201020100, 'IVA Debito Fiscal',201020000,TRUE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (201030000, 'OTRAS DEUDAS',201000000,FALSE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (201030100, 'Sueldos y Jornales a Pagar',201030000,TRUE);

INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (300000000, 'PATRIMONIO NETO',NULL,FALSE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (301010100, 'Capital',300000000,TRUE);

INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (400000000, 'INGRESOS',NULL,FALSE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (401000000, 'INGRESOS OPERATIVOS',400000000,FALSE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (401010000, 'APORTES Y DONACIONES',401000000,FALSE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (401010100, 'Aportes',401010000,TRUE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (401010200, 'Donaciones',401010000,TRUE);

INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (500000000, 'EGRESOS',NULL,FALSE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (501000000, 'EGRESOS OPERATIVOS',500000000,FALSE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (501010000, 'GASTOS OPERACIONALES',501000000,FALSE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (501010100, 'SUELDOS Y JORNALES',501010000,FALSE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (501010101, 'Sueldo Pastor',501010100,TRUE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (501010102, 'Sueldo Cuidador',501010100,TRUE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (501010200, 'REPARACION Y MANTENIMIENTO',501010000,FALSE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (501010201, 'Rep. Y Mant. Edificios',501010200,TRUE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (501010202, 'Rep. Y Mant. Rodados',501010200,TRUE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (501010203, 'Rep. Y Mant. Maquinarias y Equipos',501010200,TRUE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (501010300, 'Limpieza',501010000,TRUE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (501010400, 'Energia Electrica',501010000,TRUE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (501010500, 'Internet y Telefono',501010000,TRUE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (501010600, 'Combustibles',501010000,TRUE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (501010700, 'HONORARIOS PROFESIONALES',501010000,FALSE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (501010900, 'Utiles para Oficina',501010000,TRUE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (501011000, 'Utiles para Cocina',501010000,TRUE);
INSERT INTO MG.TBL_CUENTAS_CONTABLES (ID, DESCRIPCION, ID_CUENTA_MADRE, IMPUTABLE) VALUES (501011100, 'Utiles para Patio',501010000,TRUE);

INSERT INTO MG.TBL_CUENTAS_CONTABLES_POR_DEFECTO (ID) VALUES (1);

INSERT INTO MG.TBL_CENTROS_DE_COSTO (DESCRIPCION, CTA_CTE, PREFERIDO) VALUES ('Interner Haushalt', 0, TRUE);
INSERT INTO MG.TBL_CENTROS_DE_COSTO (DESCRIPCION, CTA_CTE, PREFERIDO) VALUES ('Jugend', 0, FALSE);