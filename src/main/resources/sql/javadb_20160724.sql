CREATE TABLE MG.TBL_TIMBRADOS_NOTAS_DE_CREDITO (
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

