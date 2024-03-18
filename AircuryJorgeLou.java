/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.aircuryjorgelou;

/**
 *
 * @author jorge
 */
import java.io.*;
import java.util.*;

public class AircuryJorgeLou {

    public static void main(String[] args) {
        //i indice filas, j indice columnas, m número filas, n número columnas
        //k índice pieza, l índice lado
        int i, j, m = 0, n = 0, k, l;
        //p vector de piezas p[k][l] lado l de la pieza k
        int[][] p;
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce nombre del archivo:\n");
        String nombreFichero = sc.nextLine();
        
        File f = new File(nombreFichero);
        Scanner entrada = null;

        try {
            entrada = new Scanner(f);
            //Leemos en el fichero la dimensión del puzzle
            m = entrada.nextInt();
            n = entrada.nextInt();
            p = new int[m * n][4];
            //Leemos en el fichero las piezas
            for (k = 0; k < m * n; k++) {
                for (l = 0; l < 4; l++) {
                    p[k][l] = entrada.nextInt();
                }
            }

            //Creamos tablas que representen los lados que tienen que coincidir, 
            //tanto los verticales como los horizontales, podemos tomarlas con
            //todo 0, ya que solo haremos comprobaciones contra los bordes, que
            //son 0, o contra lados que ya hayan sido cambiados.
            int hor[][], ver[][];
            hor = new int[m + 1][n];
            ver = new int[m][n + 1];

            for (i = 0; i <= m; i++) {
                for (j = 0; j <= n - 1; j++) {
                    hor[i][j] = 0;
                }
            }

            for (i = 0; i <= m - 1; i++) {
                for (j = 0; j <= n; j++) {
                    ver[i][j] = 0;
                }
            }

            //s índice de solución a obtener
            int s = 0;
            //sol vector de soluciones sol[i][j] pieza que encaja en posición i,j
            int sol[][][];
            //10 número arbitrario de soluciones maximas a mostrar
            sol = new int[m][n][10];
            //pos vector de posiciones pos[k] posición en la que encajó la pieza k
            int[] pos;
            pos = new int[m * n];

            //lastsol última solución errónea
            int lastsol = 0;
            //lastpos última posición errónea
            int lastpos = 0;
            boolean terminado = false;

            for (i = 0; i < m; i++) {
                for (j = 0; j < n; j++) {
                    //Nos permitirá controlar si ya hemos colocado una pieza en
                    //la posición i,j
                    boolean colocado = false;

                    for (k = lastsol; k < n * m && colocado == false; k++) {
                        //Nos permitirá controlar no colocar piezas repetidas
                        boolean repetido = false;
                        for (int i2 = 0; i2 < m; i2++) {
                            for (int j2 = 0; j2 < n; j2++) {
                                if (k == sol[i2][j2][s] - 1) {
                                    repetido = true;
                                }
                            }
                        }
                        //Si la pieza está ya colocada, pasamos a la siguiente
                        if (repetido == true) {
                            continue;
                        }

                        //Comprobamos si la pieza puede ser colocada en alguna 
                        //posición
			if (p[k][3] == ver[i][j] && p[k][0] == hor[i][j] && lastpos < 4) {
                            pos[k] = 4;
                            colocado = true;
			} else if (p[k][2] == ver[i][j] && p[k][3] == hor[i][j] && lastpos < 3) {
                            pos[k] = 3;
                            colocado = true;
                        } else if (p[k][1] == ver[i][j] && p[k][2] == hor[i][j] && lastpos < 2) {
                            pos[k] = 2;
                            colocado = true;

                        }else if (p[k][0] == ver[i][j] && p[k][1] == hor[i][j] && lastpos < 1) {
                            pos[k] = 1;
                            colocado = true;
			}

                        
                         
                        
                        //Comprobaciones extra que tenemos que hacer en caso de
                        //tener en cuenta otros bordes.
                        if (j == n - 1 && colocado == true) {
                            switch (pos[k]) {
                                case 1:
                                    if (p[k][2] != ver[i][j + 1]) {
                                        colocado = false;
                                    }
                                    break;
                                case 2:
                                    if (p[k][3] != ver[i][j + 1]) {
                                        colocado = false;
                                    }
                                    break;
                                case 3:
                                    if (p[k][0] != ver[i][j + 1]) {
                                        colocado = false;
                                    }
                                    break;
                                case 4:
                                    if (p[k][1] != ver[i][j + 1]) {
                                        colocado = false;
                                    }
                                    break;
                                default:
                            }
                        }
                        if (i == m - 1 && colocado == true) {
                            switch (pos[k]) {
                                case 1:
                                    if (p[k][3] != hor[i + 1][j]) {
                                        colocado = false;
                                    }
                                    break;
                                case 2:
                                    if (p[k][0] != hor[i + 1][j]) {
                                        colocado = false;
                                    }
                                    break;
                                case 3:
                                    if (p[k][1] != hor[i + 1][j]) {
                                        colocado = false;
                                    }
                                    break;
                                case 4:
                                    if (p[k][2] != hor[i + 1][j]) {
                                        colocado = false;
                                    }
                                    break;
                                default:
                            }
                        }
//                        
                        //Si la pieza ha pasado todas las comprobaciones, "la
                        //colocamos"
                        if (colocado == true) {
                            sol[i][j][s] = k + 1;
                            //Igualamos el lastsol a 0 para que se intenten
                            //todas las piezas en siguientes pasos del bucle
                            lastsol = 0;
                            //Cambiamos los lados que tienen que coincidir con
                            //esta nueva pieza
                            switch (pos[k]) {
                                case 1:
                                    ver[i][j + 1] = p[k][2];
                                    hor[i + 1][j] = p[k][3];
                                    break;
                                case 2:
                                    ver[i][j + 1] = p[k][3];
                                    hor[i + 1][j] = p[k][0];
                                    break;
                                case 3:
                                    ver[i][j + 1] = p[k][0];
                                    hor[i + 1][j] = p[k][1];
                                    break;
                                case 4:
                                    ver[i][j + 1] = p[k][1];
                                    hor[i + 1][j] = p[k][2];
                                    break;
                            }
                            //Si hemos colocado la última pieza, pasamos a buscar la 
                            //siguiente solución.
                            if (i == m - 1 && j == n - 1) {
                                s++;
                                for (int i2 = 0; i2 < m; i2++) {
                                    for (int j2 = 0; j2 < n; j2++) {
                                        sol[i2][j2][s] = sol[i2][j2][s - 1];
                                    }
                                }
                                sol[m - 1][n - 1][s] = 0;

                                colocado = false;
                            } else {
                                //Si hemos colocado una pieza que no sea la última,
                                //seguimos a la siguiente posición
                                continue;
                            }

                        }
                        //Solo necesitamos mirar "menos" posiciones de la pieza
                        //que ya hemos colocado.
                        lastpos = 0;
                    }

                    //Al mirar todas las piezas sin resultado, quitamos la
                    //última pieza colocada.
                    if (colocado == false) {
                        switch (j) {
                            case 0: {
                                i--;
                                j = m - 2;
                                lastsol = sol[i][j + 1][s] - 1;
                                lastpos = pos[sol[i][j + 1][s] - 1];
                                pos[sol[i][j + 1][s] - 1] = 0;
                                sol[i][j + 1][s] = 0;
                                break;
                            }
                            case 1: {
                                //No hace falta cambiar la primera pieza, así
                                //evitamos las soluciones múltiples giradas.
                                if (i == 0) {
                                    terminado = true;
                                    break;
                                }
                                i--;
                                j = m - 1;
                                lastsol = sol[i + 1][0][s] - 1;
                                lastpos = pos[sol[i + 1][0][s] - 1];
                                pos[sol[i + 1][0][s] - 1] = 0;
                                sol[i + 1][0][s] = 0;
                                break;
                            }
                            default: {
                                j = j - 2;
                                lastsol = sol[i][j + 1][s] - 1;
                                lastpos = pos[sol[i][j + 1][s] - 1];
                                pos[sol[i][j + 1][s] - 1] = 0;
                                sol[i][j + 1][s] = 0;
                                break;
                            }

                        }
                    }
                    if (terminado == true) {
                        break;
                    }
                }
                if (terminado == true) {
                    break;
                }
            }

            //Finalmente, escribimos por pantalla las soluciones
            System.out.print("Soluciones al puzzle:\n");
            for (int a = 0; a < s; a++) {
                for (i = 0; i < m; i++) {
                    for (j = 0; j < n; j++) {
                        System.out.print(sol[i][j][a]);
                        System.out.print("\t");
                    }
                    System.out.print("\n");
                }
                System.out.print("\n\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            entrada.close();
        }
    }
}
