import java.io.*;
import java.util.*;

public class ColoanaBetoane implements  Cloneable,Estimabil {

    private String denumireReteta;
    private materiale[] Materiale;
    private float[] Volume;

    public ColoanaBetoane(String denumireReteta, float[] volume,materiale[] materiale) {
        this.denumireReteta = denumireReteta;
        this.Volume = volume;
        this.Materiale=materiale;
    }

    public ColoanaBetoane() {
        this.denumireReteta=null;
        this.Volume=new float[materiale.values().length];
        this.Materiale=new materiale[materiale.values().length];
    }


    //1)Nu sunt spatii intre metodele de get si set, codul nefiind "aerisit"
    public String getDenumireReteta() {
        return denumireReteta;
    }
    public void setDenumireReteta(String denumireReteta) {
        this.denumireReteta = denumireReteta;
    }
    public float getVolume(int i) {
        return Volume[i];
    }
    public float getVolum() {
        float SumaVolum=0;
        for(int i=0;i<Volume.length;i++)
            SumaVolum+=Volume[i];
        return SumaVolum;
    }
    public void setVolume(float[] volume) {
        Volume = volume;
    }
    public void seteazaVolum(int i, float volume) {
        this.Volume[i] = volume;
    }
    public materiale getMateriale(int i) {
        return this.Materiale[i];
    }
    public void setMateriale(materiale m,int i) {
        Materiale[i] = m;
    }

    @Override
    public String toString() {
        //3)Nume de variabile care nu sunt foarte clare sau intuitive (in loc de aux ar fi fost mai ok auxDenumire sau auxDenumireReteta) in cazul unui cod mai lung
        String aux = "" + this.denumireReteta;
        for (int i = 0; i < materiale.values().length; i++) {
            aux = aux + "," + materiale.values()[i] + "," + Volume[i];
        }
        return aux;
    }

    protected Object clone() throws CloneNotSupportedException{
        ColoanaBetoane temp = new ColoanaBetoane();
        temp.denumireReteta=this.denumireReteta;
        temp.Materiale=this.Materiale;
        for(int i=0;i<this.Volume.length;i++){
            temp.Volume[i]=this.Volume[i];
        }
        return temp;
    }


    public static void main(String args[]) throws CloneNotSupportedException {
        List<ColoanaBetoane> ListaCB = new ArrayList<ColoanaBetoane>();
        float[] volume1 = new float[]{ 100f,50f,20f,10f };
        float[] volume2 = new float[]{ 150f,40f,12.4f,2.5f };
        float[] volume3 = new float[]{ 12.2f,81.2f,18.2f,3.8f };
        ColoanaBetoane CB1 = new ColoanaBetoane("Asfalt",volume1,materiale.values());
        ColoanaBetoane CB2 = new ColoanaBetoane("Beton",volume2,materiale.values());
        ColoanaBetoane CB3 = new ColoanaBetoane("Beton Aerisit",volume3,materiale.values());
        ListaCB.add(CB1);
        ListaCB.add(CB2);
        ListaCB.add(CB3);
        //region Fisiere
        FileWriter CSVFile= null;
        BufferedWriter writer = null;


        //2)comentarii inutile(linia 92 si 114), intrucat este foarte clar ce se intampla in liniile imediat urmatoare


        //write CSV
        try {
            CSVFile= new FileWriter("ReteteBetoane.csv",false);
            writer = new BufferedWriter(CSVFile);

            for(ColoanaBetoane i:ListaCB){
                System.out.println(i.toString());
                writer.write(i.toString());
                writer.newLine();
            }
            writer.close();
            CSVFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Stack<ColoanaBetoane> stiva = new Stack<ColoanaBetoane>();
        Vector<ColoanaBetoane> VectorCB=new Vector<>();
        FileReader CSVFileCitire = null;
        BufferedReader reader = null;

        //read CSV
        try{
            CSVFileCitire = new FileReader("ReteteBetoane.csv");
            reader = new BufferedReader(CSVFileCitire);

            Scanner fileScanner = new Scanner(reader);
            while(fileScanner.hasNext()) {
                String linie = fileScanner.nextLine();
                Scanner linieScanner = new Scanner(linie);
                linieScanner.useDelimiter(",");

                ColoanaBetoane local = new ColoanaBetoane();
                local.setDenumireReteta(linieScanner.next());
                float[] f = new float[4];
                int i=0;
                while (linieScanner.hasNext()) {
                    linieScanner.next();
                    f[i]=linieScanner.nextFloat();
                    i++;
                }
                local.setVolume(f);
                VectorCB.add(local);
            }
            Collections.reverse(VectorCB);
            for(ColoanaBetoane i:VectorCB) {
                stiva.push(i);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(!stiva.empty()){
            System.out.println(stiva.pop().toString());
        }
        //endregion
        ColoanaBetoane temp= (ColoanaBetoane) CB1.clone();
    }


}
