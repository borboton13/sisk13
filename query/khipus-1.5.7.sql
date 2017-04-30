-- Generado por Oracle SQL Developer Data Modeler 3.1.4.710
--   en:        2014-09-23 17:29:56 CEST
--   sitio:      Oracle Database 10g
--   tipo:      Oracle Database 10g



CREATE TABLE EOS.TIPOBANDAHORARIA 
    ( 
     IDTIPOBANDAHORARIA NUMBER (24)  NOT NULL , 
     NOMBRE VARCHAR2 (20 CHAR)  NOT NULL , 
     IDCOMPANIA	NUMBER(24,0)
    );

ALTER TABLE TIPOBANDAHORARIA 
    ADD CONSTRAINT TIPOBANDAHORARIA_PK PRIMARY KEY ( IDTIPOBANDAHORARIA  ) ;
    
ALTER TABLE BANDAHORARIACONTRATO ADD (IDTIPOBANDAHORARIA NUMBER (24) NULL);
ALTER TABLE EOS.BANDAHORARIACONTRATO 
    ADD CONSTRAINT TIPOBANDAHORARIA_FK FOREIGN KEY 
    ( 
     IDTIPOBANDAHORARIA
    ) 
    REFERENCES EOS.TIPOBANDAHORARIA 
    ( 
     IDTIPOBANDAHORARIA
    ) 
    NOT DEFERRABLE 
;
/*
ALTER TABLE EOS.TIPOBANDAHORARIA 
    ADD CONSTRAINT TIPOBANDHOR_BANDHORCONTR_FK FOREIGN KEY 
    ( 
     IDBANDAHORARIACONTRATO
    ) 
    REFERENCES EOS.BANDAHORARIACONTRATO 
    ( 
     IDBANDAHORARIACONTRATO
    ) 
    NOT DEFERRABLE 
;
*/
ALTER TABLE BANDAHORARIA
ADD (IDTIPOBANDAHORARIA NUMBER (24) NULL);
ALTER TABLE BANDAHORARIA
ADD (TIPO VARCHAR(10) NULL);

ALTER TABLE EOS.BANDAHORARIA 
    ADD CONSTRAINT BANDHOR_TIPOBANDHOR_FK FOREIGN KEY 
    ( 
     IDTIPOBANDAHORARIA
    ) 
    REFERENCES EOS.TIPOBANDAHORARIA 
    ( 
     IDTIPOBANDAHORARIA
    ) 
    NOT DEFERRABLE 
;

--COMMIT