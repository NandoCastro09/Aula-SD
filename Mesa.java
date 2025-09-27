public class Mesa {
    final static int PENSANDO = 1;
    final static int COMENDO = 2;
    final static int FOME = 3;
    final static int NR_FILOSOFOS = 5;
    final static int STARVATION = 3; // número de tentativas até starvation

    boolean[] garfos = new boolean[NR_FILOSOFOS];
    int[] filosofos = new int[NR_FILOSOFOS];
    int[] tentativas = new int[NR_FILOSOFOS];

    public Mesa() {
        for (int i = 0; i < NR_FILOSOFOS; i++) {
            garfos[i] = true;
            filosofos[i] = PENSANDO;
            tentativas[i] = 0;
        }
        System.out.println("------------------------INICIO------------------------------");
        imprimeEstadosFilosofos();
        imprimeGarfos();
        System.out.println();
    }

    public synchronized void pegarGarfos(int filosofo) throws InterruptedException {
        filosofos[filosofo] = FOME;
        tentativas[filosofo]++;

        while (filosofos[aEsquerda(filosofo)] == COMENDO || filosofos[aDireita(filosofo)] == COMENDO) {
            if (tentativas[filosofo] >= STARVATION) {
                System.out.println("O " + Thread.currentThread().getName() + " morreu devido a starvation");
                imprimeEstadosFilosofos();
                imprimeGarfos();
                imprimeTentativas();
                tentativas[filosofo] = 0; // reset após starvation
            }
            wait(50); // espera curta para tentar novamente
        }

        // Pegou os garfos
        garfos[garfoEsquerdo(filosofo)] = false;
        garfos[garfoDireito(filosofo)] = false;
        filosofos[filosofo] = COMENDO;
        imprimeEstadosFilosofos();
        imprimeGarfos();
        imprimeTentativas();
    }

    public synchronized void devolverGarfos(int filosofo) {
        garfos[garfoEsquerdo(filosofo)] = true;
        garfos[garfoDireito(filosofo)] = true;
        filosofos[filosofo] = PENSANDO;
        notifyAll();
        imprimeEstadosFilosofos();
        imprimeGarfos();
        imprimeTentativas();
        System.out.println("--------------------------------------------------------");
    }

    public int aDireita(int filosofo) {
        return (filosofo + 1) % NR_FILOSOFOS;
    }

    public int aEsquerda(int filosofo) {
        return (filosofo + NR_FILOSOFOS - 1) % NR_FILOSOFOS;
    }

    public int garfoEsquerdo(int filosofo) {
        return filosofo;
    }

    public int garfoDireito(int filosofo) {
        return (filosofo + 1) % NR_FILOSOFOS;
    }

    public void imprimeEstadosFilosofos() {
        System.out.print("Filósofos = [ ");
        for (int i = 0; i < NR_FILOSOFOS; i++) {
            switch (filosofos[i]) {
                case PENSANDO -> System.out.print("PENSANDO ");
                case FOME -> System.out.print("FOME ");
                case COMENDO -> System.out.print("COMENDO ");
            }
        }
        System.out.println("]");
    }

    public void imprimeGarfos() {
        System.out.print("Garfos = [ ");
        for (boolean g : garfos) {
            System.out.print((g ? "LIVRE " : "OCUPADO "));
        }
        System.out.println("]");
    }

    public void imprimeTentativas() {
        System.out.print("Tentou comer = [ ");
        for (int t : tentativas) {
            System.out.print(t + " ");
        }
        System.out.println("]");
    }
}






