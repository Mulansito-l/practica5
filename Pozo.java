import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Collections;

public class Pozo {
    private ArrayList<Ficha> fichas;
    private ArrayList <Ficha> fichas2Lados;
    private ArrayList <FichaDeTriomino> fichas3Lados;
    public Pozo(){
        fichas=new ArrayList<Ficha>();
        fichas2Lados=new ArrayList<Ficha>();
        fichas3Lados=new ArrayList<FichaDeTriomino>();
        generarFichasDe2Lados();
        generarFichasDe3Lados();
        añadirTodasLasFichasALPozo();
    }
    private void generarFichasDe2Lados(){
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                Ficha ficha = new Ficha(i,j);
                String imageName = "recursos/di"+i+j+".png";
                ficha.setImagen(new Sprite(imageName, 0, 0));
                fichas2Lados.add(ficha);
            }
        }
    }
    private void generarFichasDe3Lados(){
        for (int i = 0; i <= 5; i++) {
            for (int j = i; j <= 5; j++) {
                for (int k = j; k <= 5; k++) {
                    FichaDeTriomino ficha = new FichaDeTriomino(i,j,k);
                    String imageName = "recursos/tri"+i+j+k+".png";
                    ficha.setImagen(new Sprite(imageName, 0, 0));
                    fichas3Lados.add(ficha);
                }
            }
        }
    }
    private void añadirTodasLasFichasALPozo(){
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
    public void mezclarPozo(long seed){
        Collections.shuffle(fichas, new Random(seed));
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
        Ficha ficha = fichas.get(0);
        fichas.remove(0);
        return ficha;
    }

    @Override
    public String toString() {
        return fichas.toString();
    }
}
