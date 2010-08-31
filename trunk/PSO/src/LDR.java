import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;

import peaks.movmain;
import peaks.movpeaks;


public class LDR {
	private static long numEvals = 4900;

	


	/**
	 * @param instance
	 * @param solucion
	 * @param fitnessSol
	 * @return
	 */
	public static double[] ldr(movmain instance, double [] solucion, double fitnessSol) {
		double auxFitness = -1;
		boolean existeMejora = true; //n�mero de iteracione sin mejora.
		boolean mejorado = false;
		//El siguiente array va a conener todas las posibles soluciones de la vecindad para ello se podr� realizar una
		//variaci�n (delta) la cual se podr� aplicar a cada coordenada de manera positiva o negativa.
		
		ArrayList<double []> vecindades = new ArrayList<double []>((movpeaks.getGeno_size()*movpeaks.getGeno_size()-1)*2);
		
		//while (existeMejora){
			while (existeMejora && instance.getFktnLib().getEvals() %  numEvals > 0){
			mejorado = false;
			vecindades = LDR.generaVecindadTotal(solucion); //generarVecindad
			int i = 0;
			int veces = 0;
			while(i < vecindades.size() && !mejorado && instance.getFktnLib().getEvals() % numEvals > 0){
			  	auxFitness = instance.getFktnLib().eval_movpeaks(vecindades.get(i));
			  	veces++;
			  	if (auxFitness > fitnessSol){
			  		//System.out.print("Mejoramos:  ");
			  		ArraysUtil.copy(vecindades.get(i), solucion);
			  		fitnessSol = auxFitness;
			  		//ArraysUtil.mostrar(solucion);
			  		//System.out.println("Con valor: " + fitnessSol);
			  		//System.out.println("veces: " + veces);
			  		mejorado = true;
			  		existeMejora = true;
			  	}
			  	i++;
			}
			if (!mejorado){
				existeMejora = false;
			}
			//System.out.println(instance.getFktnLib().getEvals());
		}
		
		return solucion;
		
	}


	/**
	 * Este m�todo ser� el encargado de generar todas las posibles soluciones cercanas a una dada como par�metro.
	 * A este conjunto de posibles soluciones se le denomina vecindad.
	 * Para realizar el c�lculo de la misma realizaremos una alteraci�n de la soluci�n recibida, aumentando o disminuyendo 
	 * las coordenadas en un peque�o factor (delta).
	 * 
	 * Para realizar esta alteraci�n, generamos todas las posibles permutaciones con todos los n�meros binarios desde 0 hasta 2^n 
	 * siendo n el tama�o de la soluci�n.
	 * 
	 * Desp�es le aplicamos el peque�o empuj�n a la solucion, primero empujando y despu�s restando.
	 * 
	 * Notas: Al solo combinar sumas por un lado y restas por otro me falta combinar las restas con las sumas.
	 * 
	 * @param solucion
	 * @return ArrayList <double[]> cada posici�n contiene una posible soluci�n pertenenciente a la vecindad.  
	 */
	
	private static ArrayList<double[]> generaVecindad(double [] solucion) {
		ArrayList<double []> vecindades = new ArrayList<double []>((movpeaks.getGeno_size()*movpeaks.getGeno_size()-1)*2);
		ArrayList<String> variaciones = new ArrayList<String>(movpeaks.getGeno_size()*movpeaks.getGeno_size()-1); 
		int delta = 1;
		int numVariaciones = (int)Math.pow(2,movpeaks.getGeno_size());
		for(int i = 1; i < numVariaciones;i++){ // la i empieza en 1 porque el 0 no altera las coordenadas.
			double [] auxPos = new double[movpeaks.getGeno_size()];
			double [] auxNeg = new double[movpeaks.getGeno_size()];
			ArraysUtil.copy(solucion, auxPos);
			ArraysUtil.copy(solucion, auxNeg);
			int variacion = i;
			String binario = Integer.toBinaryString(variacion);
			//System.out.println("Para el binario :" + binario);
			variaciones.add(binario);
			//System.out.println(binario);
			for(int j = binario.length()-1; j >= 0; j--){
				//System.out.print("--" + binario);
				int valor = binario.charAt(binario.length() - j - 1) - 48; // Pasamos el valor a entero rest�ndole el valor ASCII del 0
				//System.out.println("Hacemos cambio en  "+(movpeaks.getGeno_size() - j - 1)+" del valor: "+ delta*valor);
				auxPos[movpeaks.getGeno_size() - j - 1] = auxPos[movpeaks.getGeno_size() - j - 1] + delta*valor;
				auxNeg[movpeaks.getGeno_size() - j - 1] = auxNeg[movpeaks.getGeno_size() - j - 1] - delta*valor; 
			}
			vecindades.add(auxPos);
			vecindades.add(auxNeg);
		}
		return vecindades;
	}
	
	private static ArrayList<double[]> generaVecindadTotal(double [] solucion) {
		int numVariaciones = (int)Math.pow(3,movpeaks.getGeno_size());
		ArrayList<double []> vecindades = new ArrayList<double []>(numVariaciones);
		int [][] variaciones = new int [numVariaciones][movpeaks.getGeno_size()]; 
		int delta = 1;
		int [] elementos = {-1,0,1};
		int [] variacion = new int [movpeaks.getGeno_size()];
		for(int i = 0; i < variacion.length; i++){
			int varia = (int)Math.pow(3,i);
			int indiceElementos = 0;
			int iterador = 0;
			for(int j = 0; j < numVariaciones;j++){ // la i empieza en 1 porque el 0 no altera las coordenadas.	
				if (iterador == varia){
					iterador = 0;
					if(indiceElementos == elementos.length-1){
					  indiceElementos = 0;	
					} else {
					  indiceElementos++;
					}
				}
				variaciones[j][i] = elementos[indiceElementos];
				iterador++;	
			}
		
		}
		//System.out.println("tama�o: "+vecindades.size());
		setVecindades(vecindades, variaciones, delta, solucion);
		//System.out.println("tama�o: "+vecindades.size());
		return vecindades;
	}
		
	
	
	private static void setVecindades(ArrayList<double[]> vecindades, int[][] variaciones, int delta, double[] solucion) {
		for(int i = 0; i<variaciones.length; i++){
			double [] temporal = new double [solucion.length];
			for(int j = 0; j<solucion.length; j++){
				temporal[j] = solucion[j]+delta*variaciones[i][j];
			}
			vecindades.add(temporal);
		}
		//Utilidades.mostrar(vecindades);
	}


	public static void main(String args[]){
		PSO pso = new PSO();
		double [] solucion = pso.getSolRandom();

		/*
		 * PRUEBA GENERACI�N DE VECINDADES 
		 */
		
		/*ArrayList<double []> vecindades = new ArrayList<double []>((movpeaks.getGeno_size()*movpeaks.getGeno_size()-1)*2);
		vecindades = LDR.generaVecindadTotal(solucion);
		System.out.println("---------------------------------------");
		Utilidades.mostrar(vecindades);
		*/
		
		double fitnessSol = pso.getInstance().getFktnLib().eval_movpeaks(solucion);
		LDR.ldr(pso.getInstance(), solucion, fitnessSol);
		
	}
	

}
