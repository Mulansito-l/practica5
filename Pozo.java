import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public class Pozo {
    private ArrayList<Ficha> fichas;
    private ArrayList <Ficha> fichas2Lados;
    private ArrayList <FichaDeTriomino> fichas3Lados;
    public Pozo(){
        fichas=new ArrayList<Ficha>();
        fichas2Lados=new ArrayList<Ficha>();
        fichas3Lados=new ArrayList<FichaDeTriomino>();
    }
    public void generarFichasDe2Lados(){
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                fichas2Lados.add(new Ficha(i, j));
            }
        }
    }
    public void generarFichasDe3Lados(){
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                for (int k = j; k <= 6; k++) {
                    fichas3Lados.add(new FichaDeTriomino(i, j, k));
                }
            }
        }
    }
    public void añadirTodasLasFichasALPozo(){
        generarFichasDe2Lados();
        generarFichasDe3Lados();

        for (int i=0;i<fichas2Lados.size();i++){
            fichas.add(fichas2Lados.get(i));
        }
        for (int j=0;j<fichas3Lados.size();j++){
            fichas.add(fichas3Lados.get(j));
        }
    }
    public void mostrarFichasDe2(){
        for (Ficha ficha : fichas2Lados){
            System.out.println(ficha);
        }
    }
    public void mostrarFichasDe3(){
        for (Ficha ficha : fichas3Lados){
            System.out.println(ficha);
        }
    }
    public void mostrarPozo(){
        for (Ficha ficha : fichas) {
            System.out.println(ficha);
        }
    }
    public void mezclarPozo(){
        Collections.shuffle(fichas);
    }
    public ArrayList <Ficha> sacarFichasDelPozo(int cantidadDeFichas){
        ArrayList <Ficha> fichasSacadas=new ArrayList<Ficha>();
        for (int i=0;i<cantidadDeFichas;i++) {
            fichasSacadas.add(fichas.getFirst());
            fichas.removeFirst();
        }
        return fichasSacadas;
    }
    public Ficha getFicha(){
        return fichas.getFirst();
    }
}
