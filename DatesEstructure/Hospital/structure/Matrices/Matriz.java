package structure.Matrices;

public class Matriz<T> {
    private Object[][] datos;
    private  int filas;
    private int columnas;

    public Matriz(int filas,int columnas){
        this.filas=filas;
        this.columnas=columnas;
        this.datos=new Object[filas][columnas];
    }

    public void insertar(int fila,int columna, T valor){
        if(fila>=0 && fila<filas&&columna>=0&&columna<columnas){
            datos[fila][columna]=valor;
        }else{
            throw new IndexOutOfBoundsException("Indices fuera de limites");
        }
    }

    public int getFilas() {return filas;}

    public int getColumnas() {return columnas;}

    public void imprimir(){
        for(int i= 0;i<filas;i++){
             for (int j=0;i<columnas;j++){
                 System.out.println(datos[i][j]+"\t");
             }
            System.out.println();
        }
    }
}
