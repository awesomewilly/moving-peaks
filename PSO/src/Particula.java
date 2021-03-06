import java.util.Random;

import peaks.movmain;


/** 
 * @author  vicenbg
 */
public class Particula {
	final static int VMAX = 1;
	/**
	 * @uml.property  name="actual"
	 */
	double[] actual = new double[5];
	/**
	 * @uml.property  name="pBest"
	 */
	double[] pBest = new double[5];
	/**
	 * @uml.property  name="pBestVecino"
	 */
	double[] pBestVecino = new double[5];
	/**
	 * @uml.property  name="actualFitness"
	 */
	double actualFitness = 0.0;
	/**
	 * @uml.property  name="pFitness"
	 */
	double pFitness = 0.0;
	
	//Debe de ser una velocidad para cada coordenada del punto
	/**
	 * @uml.property  name="gradiente"
	 */
	double [] gradiente = null;
		

	public Particula(movmain instance) {
	  actual = PSO.getSolRandom();
	  pBest = PSO.getSolRandom();
	  actualFitness = instance.getFktnLib().eval_movpeaks(actual);
	  pFitness = instance.getFktnLib().eval_movpeaks(pBest);
	  //Miro cual es mejor para ponerlo en pBest
	  if(pFitness < actualFitness){
		  double aux = pFitness;
		  pFitness = actualFitness;
		  actualFitness = aux;
		  double [] solAux = new double[pBest.length];
		  ArraysUtil.copy(actual, solAux);
		  ArraysUtil.copy(pBest, actual);
		  ArraysUtil.copy(solAux, pBest);
	  }
	  gradiente = new double[pBest.length];
	  for (int i = 0; i < pBest.length;i++){
		  Random rnd = new Random();
		  gradiente[i] = (rnd.nextDouble()*(VMAX*2))-VMAX;
	  }
		
	}


	public Particula() {
		
	}


	/**
	 * @param gradiente
	 * @uml.property  name="gradiente"
	 */
	public void setGradiente(double[] gradiente) {
		this.gradiente = gradiente;
	}
	/**
	 * @return
	 * @uml.property  name="pBest"
	 */
	public double[] getpBest() {
		return pBest;
	}

	/**
	 * @param pBest
	 * @uml.property  name="pBest"
	 */
	public void setpBest(double[] pBest) {
		this.pBest = pBest;
	}
	
	/**
	 * @return
	 * @uml.property  name="pBestVecino"
	 */
	public double[] getpBestVecino() {
		return pBestVecino;
	}

	/**
	 * @param pBestVecino
	 * @uml.property  name="pBestVecino"
	 */
	public void setpBestVecino(double[] pBestVecino) {
		this.pBestVecino = pBestVecino;
	}

	/**
	 * @return
	 * @uml.property  name="actualFitness"
	 */
	public double getActualFitness() {
		return actualFitness;
	}

	/**
	 * @param actualFitness
	 * @uml.property  name="actualFitness"
	 */
	public void setActualFitness(double actualFitness) {
		this.actualFitness = actualFitness;
	}

	/**
	 * @return
	 * @uml.property  name="pFitness"
	 */
	public double getpFitness() {
		return pFitness;
	}

	/**
	 * @param pFitness
	 * @uml.property  name="pFitness"
	 */
	public void setpFitness(double pFitness) {
		this.pFitness = pFitness;
	}

	/**
	 * @return
	 * @uml.property  name="gradiente"
	 */
	public double[] getGradiente() {
		return gradiente;
	}
	/**
	 * @return
	 * @uml.property  name="actual"
	 */
	public double[] getActual() {
		return actual;
	}
	/**
	 * @param actual
	 * @uml.property  name="actual"
	 */
	public void setActual(double[] actual) {
		this.actual = actual;
	}
	public double[] getPBest() {
		return pBest;
	}
	public void setPBest(double[] pBest) {
		ArraysUtil.copy(pBest, this.pBest);
	}
	
	//C�lcula la distancia entre las componentes actuales de las part�culas.
	public double getDistanciaActuales(Particula part){
		double sumaComponentes = 0;
		double distAux= 0;
		for(int i = 0; i < part.getActual().length;i++){
			sumaComponentes += Math.pow(part.getActual()[i] - this.getActual()[i], 2);
		}
		distAux = Math.sqrt(sumaComponentes);
		//System.out.println(distAux);
		return distAux;
	}

	public void moverParticula(movmain instance){
		for(int i = 0; i<actual.length;i++){
			double nuevaCoordenada = actual[i]+gradiente[i];
			if (nuevaCoordenada < instance.getFktnLib().getMaxCoordinate() &&
					nuevaCoordenada > instance.getFktnLib().getMinCoordinate()){
				actual[i] = nuevaCoordenada;
			}
		}
		actualFitness = instance.getFktnLib().eval_movpeaks(actual);
		pFitness = instance.getFktnLib().eval_movpeaks(pBest);
		//Miro cual es mejor para ponerlo en pBest
	    if(pFitness < actualFitness){
		  double aux = pFitness;
		  pFitness = actualFitness;
		  actualFitness = aux;
		  double [] solAux = new double[pBest.length];
		  ArraysUtil.copy(actual, solAux);
		  ArraysUtil.copy(pBest, actual);
		  ArraysUtil.copy(solAux, pBest);
        }
	}
/*
 * M�todo encargado de ajustar el vector v antes de realizar el moviemiento de las particulas
 * para ello recibe por un lado los dos par�metros que van a decidir la evoluci�n del algoritmo,
 * por un lado la parte cognitiva y por otro la parte social.
 * 
 * Para esta parte social tambi�n es necesario recibir la mejor soluci�n existente hasta el momento
 * en la nube de part�culas.
 * 
 * */
	public void ajustarGradiante(double rCognitivo, double rSocial, double[] mejorSolNube) {
		Random rnd = new Random();
		//System.out.println("Antes: ");
		//ArraysUtil.mostrar(this.getGradiente());
		for (int i = 0; i < pBest.length;i++){
			double aleatorio = rnd.nextDouble();
			double factorCognitivo = rCognitivo*aleatorio*(this.getpBestVecino()[i]-this.getActual()[i]);
			//System.out.println("FactorCognitivo = "+rCognitivo+"*"+aleatorio+"*("+this.getpBest()[i]+"-"+this.getActual()[i]+") = "+ factorCognitivo);	
			aleatorio = rnd.nextDouble();
			double factorSocial = rSocial*aleatorio*(mejorSolNube[i]-this.getActual()[i]);
			//System.out.println( "factorSocial = "+rSocial+"*"+aleatorio+"*("+mejorSolNube[i]+"-"+this.getActual()[i]+") = "+factorSocial);
			gradiente[i] = gradiente[i] + factorCognitivo + factorSocial;
		 }
		//System.out.println("Despues: ");
		//ArraysUtil.mostrar(this.getGradiente());
	}


	public void calcularMejorVecino(Particula[] nube, double distanciaVecino) {
		//Ojo si no hay mejor vecino pongo mi mejor situación en el tiempo.
		Particula vecinoAux = new Particula();
		for(int i = 0;i<nube.length;i++){
			if (this.getDistanciaActuales(nube[i]) < distanciaVecino){ //Si es de la vecindad
				if(nube[i].getActualFitness()>vecinoAux.getActualFitness()){ // Si es mejor que el mejor vecino de momento
					vecinoAux = nube[i];
				}
				
			}
		}
		// Le asigno al pBestVecino el vecino encontrado.
		this.setpBestVecino(vecinoAux.getActual());	
	}
	
	public void mostrar() {
		System.out.print("Particula: Sol actual:");
		ArraysUtil.mostrar(actual);
		System.out.print("Sol pBest:");
		ArraysUtil.mostrar(pBest);
		System.out.print("Fitness actual:");
		System.out.print(actualFitness);
		System.out.print("Fitness pBest:");
		System.out.println(pFitness);
		
	}
	
	public static void main(String args[]){
		movmain instance = new movmain();
		instance.getFktnLib();
		Particula part1 = new Particula(instance);
		Particula part2 = new Particula(instance);
		part1.mostrar();
		part2.mostrar();
		System.out.println(part1.getDistanciaActuales(part2));
		
	}


	
	
	
}
