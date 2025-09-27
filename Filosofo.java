public class Filosofo extends Thread {
    final static int TEMPO_MAXIMO = 100;
    final static int CICLOS = 1; // número de vezes que vai pensar/comer

    private Mesa mesa;
    private int filosofo;

    public Filosofo(String nome, Mesa mesadejantar, int fil) {
        super(nome);
        this.mesa = mesadejantar;
        this.filosofo = fil;
    }

    public void run() {
        for (int i = 0; i < CICLOS; i++) {
            try {
                // Filósofo pensa
                int tempo = (int) (Math.random() * TEMPO_MAXIMO);
                pensar(tempo);

                // Tenta pegar os garfos
                getGarfos();

                // Come
                tempo = (int) (Math.random() * TEMPO_MAXIMO);
                comer(tempo);

                // Devolve os garfos
                returnGarfos();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(getName() + " terminou seus ciclos.");
    }

    private void getGarfos() {
        try {
            mesa.pegarGarfos(filosofo);
            System.out.println(getName() + " pegou os garfos.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void returnGarfos() {
        mesa.devolverGarfos(filosofo);
        System.out.println(getName() + " devolveu os garfos.");
    }

    private void pensar(int tempo) {
        try {
            System.out.println(getName() + " está pensando...");
            sleep(tempo);
        } catch (InterruptedException e) {
            System.out.println(getName() + " pensou demais!");
        }
    }

    private void comer(int tempo) {
        try {
            System.out.println(getName() + " está comendo...");
            sleep(tempo);
        } catch (InterruptedException e) {
            System.out.println(getName() + " comeu demais!");
        }
    }
}