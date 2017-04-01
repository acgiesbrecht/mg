DROP VIEW MG.TRANSFERENCIA;

CREATE VIEW MG.TRANSFERENCIA AS
    SELECT t.id,
        t.fechahora,
        d.ctacte AS cta_debito,
        CASE WHEN cdc.cta_cte = 0 THEN i.ctacte  ELSE cdc.cta_cte END AS cta_credito,
        d.nombres || ' ' || d.apellidos  AS d_nombre,
        i.nombre  AS c_nombre,
        d.domicilio AS d_domicilio,
        i.domicilio AS c_domicilio,
        d.box AS d_box,
        i.box AS c_box,
        t.monto_aporte + t.monto_donacion AS monto,
        t.concepto
    FROM TBL_TRANSFERENCIAS t, TBL_ENTIDADES d, TBL_IGLESIA i, TBL_EVENTOS e, TBL_CENTROS_DE_COSTO cdc
    WHERE t.ID_ENTIDAD = d.ID
          AND t.ID_EVENTO = e.ID AND cdc.ID = e.ID_CENTRO_DE_COSTO;