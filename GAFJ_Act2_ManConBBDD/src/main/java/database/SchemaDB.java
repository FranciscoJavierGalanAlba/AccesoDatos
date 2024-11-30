package database;

public interface SchemaDB {
    //Nombre de la base de datos:
    String DB_NAME = "Act2";

    //Nombre de la tabla:
    String TAB_CAR = "coches";
    String TAB_PAS = "pasajeros";
    String TAB_ASIG = "asignaciones";

    //Nombre de las columnas:
    String COL_ID = "id";
    String COL_MATRI = "matricula";
    String COL_MARCA = "marca";
    String COL_MODEL = "modelo";
    String COL_COLOR = "color";

    //
    String COL_ID_PAS = "id";
    String COL_NAM_PAS = "nombre";
    String COL_AGE_PAS = "edad";
    String COL_PES_PAS = "peso";
    String COL_ID_COC_PAS = "id_coche";

    //
    String COL_ID_ASIG = "id";
    String COL_ID_ASIG_CO = "id_coche";
    String COL_ID_ASIG_PA = "id_pasajero";
}
