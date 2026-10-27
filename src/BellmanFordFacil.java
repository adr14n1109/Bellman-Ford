import java.util.Arrays;

/**
 * BELLMAN-FORD VERSIÓN FÁCIL
 *
 * Idea: tenemos ciudades conectadas por caminos de un solo sentido.
 * Cada camino tiene un costo (puede ser NEGATIVO = te pagan por usarlo).
 * Queremos saber: ¿cuál es el costo MÁS BARATO para llegar desde la
 * ciudad de inicio (A) hasta cada una de las otras ciudades?
 */
public class BellmanFordFacil {

    // Un número enorme que significa "todavía no sé cómo llegar a esa ciudad"
    static final int INFINITO = 999999;

    public static void main(String[] args) {

        // 1) LAS CIUDADES (las "bolitas de plastilina")
        String[] ciudades = {"A", "B", "C", "D", "E"};
        int cantidadCiudades = ciudades.length;

        // 2) LOS CAMINOS (los "palitos" que unen las bolitas)
        // Cada fila es: {ciudad de salida, ciudad de llegada, costo}
        // Los números son posiciones: 0=A, 1=B, 2=C, 3=D, 4=E
        int[][] caminos = {
                {0, 1, 6},    // A -> B cuesta 6
                {0, 2, 7},    // A -> C cuesta 7
                {1, 2, 8},    // B -> C cuesta 8
                {1, 3, 5},    // B -> D cuesta 5
                {1, 4, -4},   // B -> E cuesta -4  (¡te pagan 4!)
                {2, 3, -3},   // C -> D cuesta -3  (¡te pagan 3!)
                {2, 4, 9},    // C -> E cuesta 9
                {3, 1, -2},   // D -> B cuesta -2  (¡te pagan 2!)
                {4, 0, 2},    // E -> A cuesta 2
                {4, 3, 7}     // E -> D cuesta 7
        };

        int inicio = 0; // empezamos en la ciudad A

        // 3) PREPARAMOS LA LIBRETA DE DISTANCIAS
        // Al principio solo sabemos llegar a A (cuesta 0). Al resto: infinito.
        int[] distancia = new int[cantidadCiudades];
        Arrays.fill(distancia, INFINITO);
        distancia[inicio] = 0;

        System.out.println("AL INICIO:");
        mostrar(ciudades, distancia);

        // 4) EL CORAZÓN DEL ALGORITMO
        // Repetimos (cantidad de ciudades - 1) rondas.
        // En cada ronda revisamos TODOS los caminos, uno por uno.
        for (int ronda = 1; ronda <= cantidadCiudades - 1; ronda++) {

            System.out.println("--- RONDA " + ronda + " ---");

            for (int[] camino : caminos) {
                int desde = camino[0];
                int hasta = camino[1];
                int costo = camino[2];

                // Solo sirve si ya sabemos llegar a "desde"
                if (distancia[desde] != INFINITO) {

                    int costoNuevo = distancia[desde] + costo;

                    // ¿Este camino nos da una forma MÁS BARATA de llegar a "hasta"?
                    if (costoNuevo < distancia[hasta]) {
                        System.out.println("  Mejora: llegar a " + ciudades[hasta]
                                + " pasando por " + ciudades[desde]
                                + " cuesta " + costoNuevo
                                + " (antes: " + texto(distancia[hasta]) + ")");
                        distancia[hasta] = costoNuevo; // anotamos el mejor costo
                    }
                }
            }

            mostrar(ciudades, distancia);
        }

        // 5) REVISIÓN FINAL: ¿hay un "ciclo negativo"?
        // Si después de todas las rondas TODAVÍA se puede mejorar algo,
        // significa que hay un círculo donde cada vuelta te paga más y más.
        boolean hayCicloNegativo = false;
        for (int[] camino : caminos) {
            int desde = camino[0];
            int hasta = camino[1];
            int costo = camino[2];

            if (distancia[desde] != INFINITO && distancia[desde] + costo < distancia[hasta]) {
                hayCicloNegativo = true;
            }
        }

        // 6) RESULTADO
        if (hayCicloNegativo) {
            System.out.println("¡CUIDADO! Hay un ciclo negativo: no existe un camino más barato");
            System.out.println("porque dando vueltas siempre se puede bajar más el costo.");
        } else {
            System.out.println("RESULTADO FINAL (costo más barato desde " + ciudades[inicio] + "):");
            mostrar(ciudades, distancia);
        }
    }

    // Imprime la libreta de distancias, por ejemplo:  A=0  B=2  C=7 ...
    static void mostrar(String[] ciudades, int[] distancia) {
        String linea = "  ";
        for (int i = 0; i < ciudades.length; i++) {
            linea += ciudades[i] + "=" + texto(distancia[i]) + "   ";
        }
        System.out.println(linea + "\n");
    }

    // Convierte el número en texto: si es INFINITO escribe "inf"
    static String texto(int numero) {
        if (numero == INFINITO) {
            return "inf";
        }
        return String.valueOf(numero);
    }
}