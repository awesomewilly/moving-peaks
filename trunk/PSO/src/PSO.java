/**
 * 
 */


import java.util.ArrayList;
import java.util.Random;

import peaks.movmain;
import peaks.movpeaks;

/**
 * @author Vicente Belinch�n Gonz�lez
 *
 */

public class PSO {

	private static final int NUM_PARTICULAS = 10;
	private static final int NUM_ITERACIONES = 100;
	private static movmain instance = new movmain();
	 
	
	
	/*
	 * Kennedy identifica 4 tipos de algoritmos de PSO
	 * en funci�n de los valores de rCognitivo y rSocial
	 * 
	 * Modelo completo: rCognitivo, rSocial > 0. 
	 * S�loCognitivo:rCognitivo>0 y rSocial=0. 
	 * S�loSocial:rCognitivo=0 y rSocial>0.
	 */
	double rCognitivo = 1; //ratio cognitivo,
	double rSocial = 0.5; //ratio social,
	
	/*
	 * Este valor es el encargado de elegir lo grande que será la vecindad, se cogeran todas las 
	 * partículas que se encuentren a una distancia menor del porcentaje respecto del mayor ratio.
	 * 
	 * distanciaVecino < porcentajeVecindad*mayorRatio/100
	 * 
	 */
	double porcentajeVecindad = 10;
	
	public movmain getInstance() {
		return instance;
	}

	public void setInstance(movmain instance) {
		PSO.instance = instance;
	}

	public double getrCognitivo() {
		return rCognitivo;
	}

	public void setrCognitivo(double rCognitivo) {
		this.rCognitivo = rCognitivo;
	}

	public double getrSocial() {
		return rSocial;
	}

	public void setrSocial(double rSocial) {
		this.rSocial = rSocial;
	}

	/**
	 * 
	 */
	public PSO() {
		instance = new movmain();
		String [] args = new String[0];
		//arg[0]= "-h";
		//arg[0]= "-e";
		//arg[1]= "-b";
		//arg[0]= "-Y";
		instance.run(args);	
	}
	
	/**
	 * @return
	 */
	public static double [] getSolRandom(){
		Random rnd = new Random();
		double [] solucion = new double [movpeaks.getGeno_size()]; 
		for (int i = 0; i< movpeaks.getGeno_size(); i++){
			//Genero una coordenada entre 0.0 y el numero maximo de cordenada
			double coordenada = rnd.nextDouble()* (instance.getFktnLib().getMaxCoordinate());
			solucion[i] = coordenada;
		}
		//ArraysUtil.mostrar(solucion);
		return solucion;
	}
	
	
	/**
	 * @param soluciones
	 */
	public void mostrarSoluciones(ArrayList<double []> soluciones){
		for (int i = 0; i<soluciones.size();i++) {
		  System.out.print("Solucion: "+i);
		  ArraysUtil.mostrar(soluciones.get(i));
		}
	}
	
	/**
	 * @param soluciones
	 * @return
	 */
	public double [] getBestSol(ArrayList<double[]> soluciones){
		double [] bestSol = new double [movpeaks.getGeno_size()]; 
		double optSol = 0.0,temSol = 0.0;
		for(int i = 0; i<soluciones.size(); i++){
			temSol = instance.getFktnLib().eval_movpeaks(soluciones.get(i));
			if(temSol > optSol){
				optSol = temSol;
				ArraysUtil.copy(soluciones.get(i), bestSol);
			}
		}
		
		return bestSol;
		
	}
	
	/**
	 * @param numSoluciones
	 * @return
	 */
	public ArrayList<double[]> generarSolucionesRandom(int numSoluciones){
		ArrayList<double[]> soluciones = new ArrayList<double[]>(numSoluciones);
		for(int i = 0 ; i < numSoluciones; i++){
			soluciones.add(getSolRandom());
		}
		return soluciones;
		
	}
	/*M�todo encargado de la inicializaci�n de la nube de particulas
	 * Se puede generar las soluciones de forma aleatoria o de fomra regular
	 * Las velocidades se generan aleatoriamente [-Vmax, Vmax] no es conveniente fijarlas a cero,
	 * ya que no se obtienen buenos resultados.
	 * Vmax ser� la velocidad m�xima que puede tomar una p�rticula
	 */
	public Particula[] inicializacion(int numParticulas){
		Particula [] nubeAux = new Particula[numParticulas];
		this.getInstance().getFktnLib();
		for(int i = 0; i<numParticulas;i++){
			Particula aux = new Particula(this.getInstance());
			nubeAux[i] = aux;
		}
		
		/*for(int i = 0; i < numParticulas;i++){
			nube[i].mostrar();
			
		}*/
		return nubeAux;
		
	}
	
	public void mostrarNube(Particula[] nube){
		for(int i = 0; i<nube.length;i++){
			nube[i].mostrar();
		}
		
	}
	public void moverParticulas(Particula[] nube){
		for(int i=0;i<nube.length;i++){
			System.out.println("Antes de mover: ");
			nube[i].mostrar();
			nube[i].ajustarGradiante(this.getrCognitivo(),this.getrSocial(), this.mejorSolucionNube(nube));
			nube[i].moverParticula(this.getInstance());
			System.out.println("Despu de mover: ");
			nube[i].mostrar();
		}
	}
	
	/*Este m�todo ser� el encargado de devolver la mejor soluci�n 
	 * para una nube de particulas dada*/
	
	public double[] mejorSolucionNube(Particula[] nube){
		double [] optimo = new double[movpeaks.getGeno_size()];
		double mejorFitness = 0.0;
		for(int i = 0; i<nube.length;i++){
			//System.out.print(nube[i].getpFitness()+" > "+mejorFitness);
			if(nube[i].getpFitness() > mejorFitness){
				mejorFitness = nube[i].getpFitness(); 
				ArraysUtil.copy(nube[i].getpBest(), optimo);
				//System.out.println("el mejor: "+i);
			}			
		}		
		return optimo;
	}
	
	/*Este m�todo ser� el encargado de devolver el mejor �ptimo 
	 * para una nube de particulas dada*/
	
	public double mejorOptimoNube(Particula[] nube){
		double mejorFitness = 0.0;
		for(int i = 0; i<nube.length;i++){
			//System.out.print(nube[i].getpFitness()+" > "+mejorFitness);
			if(nube[i].getpFitness() > mejorFitness){
				mejorFitness = nube[i].getpFitness(); 
				//System.out.println("el mejor: "+i);
			}			
		}		
		return mejorFitness;
	}
	
	/*Este m�todo ser� el encargado de devolver el mejor �ptimo 
	 * para una nube de particulas dada*/
	
	public Particula mejorParticulaNube(Particula[] nube){
		double mejorFitness = 0.0;
		int optimo = 0;
		for(int i = 0; i<nube.length;i++){
			//System.out.print(nube[i].getpFitness()+" > "+mejorFitness);
			if(nube[i].getpFitness() > mejorFitness){
				optimo = i; 
				//System.out.println("el mejor: "+i);
			}			
		}		
		return nube[optimo];
	}
	/* Calcula la distancia entre todas las particulas*/
	private double[][] calcularRatios(Particula[] nube) {
		double[][] ratios = new double [NUM_PARTICULAS][NUM_PARTICULAS];
		for(int i = 0;i<nube.length;i++){
			for(int j =0;j<nube.length;j++){	
				ratios[i][j] = nube[i].getDistanciaActuales(nube[j]);
			}
		}
		return ratios;
	}
	
	/*  
	 *  Devuelve el mayor valor del ratio que nos será útil para encontrar con el procentaje de ratio,
	 *  las vecindades de cada partícula.
	 */
	private double getMayorRatio(double[][] ratios) {
		double mayorRatio = 0;
		for(int i = 0;i<ratios.length;i++){
			for(int j =0;j<ratios.length;j++){
				if (ratios[i][j] > mayorRatio){
					mayorRatio = ratios[i][j];
				}
			}
		}
		return mayorRatio;
	}
	
	
	private Particula psoLocal() {
		Particula[] nube = new Particula[NUM_PARTICULAS];
		nube = this.inicializacion(NUM_PARTICULAS);
		double ratios [][] = this.calcularRatios(nube);
		double mayorRatio = this.getMayorRatio(ratios);
		double distanciaVecino = mayorRatio*porcentajeVecindad/100;
		
		
		int iteraciones = 0;
		Particula pBest = this.mejorParticulaNube(nube);
		//for(int j = 0;j<100;j++){
		  while(iteraciones < NUM_ITERACIONES){
			this.calcularRatios(nube);
			
			System.out.println("---------Iteraccion "+iteraciones+"----------------------");
			for(int i =0;i<nube.length;i++){
				nube[i].calcularMejorVecino(nube, distanciaVecino); //Se le asignamos a la Partícula
				nube[i].ajustarGradiante(rCognitivo, rSocial, pBest.getPBest());
				nube[i].moverParticula(instance);
			}
			//System.out.println("NUBE");
			//mostrarNube(nube);
			//System.out.println("FIN_NUBE");
			pBest = this.mejorParticulaNube(nube);
			//pBest.mostrar();
			double [] solucion = LDR.ldr(this.getInstance(), pBest.getpBest(), pBest.getpFitness());
			//ArraysUtil.mostrar(solucion);
			pBest.setpBest(solucion);
			pBest.setpFitness(instance.getFktnLib().eval_movpeaks(solucion));
			//System.out.println(instance.getFktnLib().getEvals());
			pBest.mostrar();
			//nube[(int) (rnd.nextDouble()*nube.length-1)] = pBest;
			//System.out.print(instance.getFktnLib().getEvals());
			//System.out.println("Iteraci�n: "+iteraciones);
			iteraciones++;
			instance.getFktnLib().change_peaks();
//			System.out.println("ERROR:" + instance.getFktnLib().get_offline_error());
			instance.getFktnLib().setEvals(0);
			//System.out.println("NUBE");
			//mostrarNube(nube);
			//System.out.println("FIN_NUBE");
			//System.out.println("-------------------------------");
			
		  }
		  
		  iteraciones = 0;
		  
		  //System.out.println(instance.getFktnLib().getEvals());
		//}
		return pBest;
	}
	
	/**
	 * @param args
	 */
	public static void main (String args[]){
		PSO pso = new PSO();
		
		 
		//Particula[] nube = new Particula[NUM_PARTICULAS];
		//ArrayList<double[]> soluciones = pso.generarSolucionesRandom(100);
		//pso.mostrarSoluciones(soluciones);
//		System.out.println("Depurar");
		
		//double [] solucion = PSO.getSolRandom();
		//System.out.println(pso.getInstance().getFktnLib().eval_movpeaks(solucion));
		//System.out.println("1");
		//nube = pso.inicializacion(NUM_PARTICULAS);
		//pso.calcularRatios(nube);
		//System.out.println("2");
		
		//pso.mostrarNube(nube);
		//pso.mejorSolucionNube(nube);
		//pso.moverParticulas(nube);
		Particula part = pso.psoLocal();
		part.mostrar();
		System.out.println(instance.getFktnLib().getEvals());
		//double [] solucion = LDR.ldr(instance, part.getpBest(), part.getpFitness());
		//ArraysUtil.mostrar(solucion);
		//System.out.println(pso.getInstance().getFktnLib().eval_movpeaks(solucion));
		//System.out.println(instance.getFktnLib().get_offline_error());
		
	}


	

}
