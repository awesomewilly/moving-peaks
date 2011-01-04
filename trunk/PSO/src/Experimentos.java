/**
 * 
 */

/**
 * @author  vicenbg
 */
public class Experimentos {

	private static final int NUM_EJECUCIONES = 50;// Nœmero de iteraciones que
													// se debe ejecutar el pso
	/**
	 * @param  args
	 * @uml.property  name="pso"
	 * @uml.associationEnd  
	 */

	PSO pso = new PSO();
	private double sumatorio = 0;
	private double media = 0;
	private double error = 0.0;

	/* Parametrizamos el porcentaje de vecindad */
	public void exp1() {
		// pso.setNumEvaluaciones(2500);
		pso.setNumEvaluaciones(5000);
		
		Particula pBestIteraciones = new Particula();
		for (int i = 0; i < NUM_EJECUCIONES; i++) {
			pso.setNumIteraciones(200);
			// pso.setPorcentajeVecindad(10);
			pso.setPorcentajeVecindad(20);
			// pso.setPorcentajeVecindad(50);
			// pso.setPorcentajeVecindad(70);
			Particula part = pso.psoGlobal();
			System.out.println(part.getpFitness());
			sumatorio += part.pFitness;
			if (part.getpFitness() > pBestIteraciones.getpFitness()) {
				pBestIteraciones.setpBest(part.getpBest());
				pBestIteraciones.setpFitness(part.getpFitness());
			}
		}
		pBestIteraciones.mostrar();
		media = sumatorio / NUM_EJECUCIONES;
		System.out.println("Sumatorio es:" + sumatorio);
		System.out.println("La media es: " + media);

	}

	/* Parametrizamos el ratio social */
	public void exp2() {
		// pso.setNumEvaluaciones(2500);
		pso.setNumEvaluaciones(5000);
		Particula pBestIteraciones = new Particula();
		for (int i = 0; i < NUM_EJECUCIONES; i++) {
			pso.setNumIteraciones(200);
			pso.setrCognitivo(0);
			// pso.setPorcentajeVecindad(20);
			pso.setPorcentajeVecindad(50);
			// pso.setrSocial(0.2);
			// pso.setrSocial(0.4);
			// pso.setrSocial(0.6);
			pso.setrSocial(0.8);
			Particula part = pso.psoGlobal();
			System.out.println(part.getpFitness());
			sumatorio += part.pFitness;
			if (part.getpFitness() > pBestIteraciones.getpFitness()) {
				pBestIteraciones.setpBest(part.getpBest());
				pBestIteraciones.setpFitness(part.getpFitness());
			}
		}
		pBestIteraciones.mostrar();
		media = sumatorio / NUM_EJECUCIONES;
		System.out.println("Sumatorio es:" + sumatorio);
		System.out.println("La media es: " + media);

	}

	public void exp3() {
		// pso.setNumEvaluaciones(2500);
		pso.setNumEvaluaciones(5000);
		Particula pBestIteraciones = new Particula();
		for (int i = 0; i < NUM_EJECUCIONES; i++) {
			pso.setNumIteraciones(200);
			pso.setrCognitivo(0);
			pso.setPorcentajeVecindad(50);
			// pso.setrSocial(0.2);
			pso.setrSocial(0.4);
			pso.setrCognitivo(0.2);
			// pso.setrCognitivo(0.5);
			// pso.setrCognitivo(0.8);
			// pso.setrCognitivo(1);
			Particula part = pso.psoGlobal();
			System.out.println(part.getpFitness());
			sumatorio += part.pFitness;
			if (part.getpFitness() > pBestIteraciones.getpFitness()) {
				pBestIteraciones.setpBest(part.getpBest());
				pBestIteraciones.setpFitness(part.getpFitness());
			}
		}
		pBestIteraciones.mostrar();
		media = sumatorio / NUM_EJECUCIONES;
		System.out.println("Sumatorio es:" + sumatorio);
		System.out.println("La media es: " + media);

	}

	public void exp4() {

	}

	public void expFinal() {
		// pso.setNumEvaluaciones(2500);
		pso.setNumEvaluaciones(5000);
		Particula pBestIteraciones = new Particula();
		for (int i = 0; i < NUM_EJECUCIONES; i++) {
			pso.setNumIteraciones(1000);
			pso.setrCognitivo(0);
			pso.setPorcentajeVecindad(50);
			pso.setrSocial(0.4);
			pso.setrCognitivo(0.2);
			// pso.setrCognitivo(0.5);
			Particula part = pso.psoGlobal();
			System.out.println(part.getpFitness());
			sumatorio += part.pFitness;
			if (part.getpFitness() > pBestIteraciones.getpFitness()) {
				pBestIteraciones.setpBest(part.getpBest());
				pBestIteraciones.setpFitness(part.getpFitness());
			}
		}
		pBestIteraciones.mostrar();
		media = sumatorio / NUM_EJECUCIONES;
		System.out.println("Sumatorio es:" + sumatorio);
		System.out.println("La media es: " + media);

	}

	public static void main(String[] args) {
		Experimentos experimentos = new Experimentos();
		System.out.print("Inicio exp");
		experimentos.exp1();

		// experimentos.exp2();

		// experimentos.exp3();

		// experimentos.exp4();

		// experimentos.expFinal();

		System.out.print("Fin exp");

	}

}
