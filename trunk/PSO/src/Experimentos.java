/**
 * 
 */

/**
 * @author Vicente Belinchón González
 *
 */
public class Experimentos {

	/**
	 * @param args
	 */
	
	PSO pso = new PSO();
	
	/*Parametrizamos el porcentaje de vecindad*/
	public void exp1(){
		pso.setNumIteraciones(200);
		//pso.setPorcentajeVecindad(10);
		//pso.setPorcentajeVecindad(20);
		//pso.setPorcentajeVecindad(50);
		pso.setPorcentajeVecindad(70);
		Particula part = pso.psoLocal();
		part.mostrar();
		
	}
	
	/*Parametrizamos el ratio social*/
	public void exp2(){
		
		pso.setNumIteraciones(200);
		pso.setrCognitivo(0);
//		pso.setPorcentajeVecindad(20);
		pso.setPorcentajeVecindad(50);
//		pso.setrSocial(0.2);
//		pso.setrSocial(0.4);
//		pso.setrSocial(0.6);
		pso.setrSocial(0.8);
		Particula part = pso.psoLocal();
		part.mostrar();
	}
	public void exp3(){
		pso.setNumIteraciones(200);
		pso.setrCognitivo(0);
		pso.setPorcentajeVecindad(50);
//		pso.setrSocial(0.2);
		pso.setrSocial(0.4);
		pso.setrCognitivo(0.2);
//		pso.setrCognitivo(0.5);
//		pso.setrCognitivo(0.8);
//		pso.setrCognitivo(1);
		Particula part = pso.psoLocal();
		part.mostrar();
		
	}
	public void exp4(){
		
		
	}
	public void expFinal(){
		pso.setNumIteraciones(1000);
		pso.setrCognitivo(0);
		pso.setPorcentajeVecindad(50);
		pso.setrSocial(0.4);
		pso.setrCognitivo(0.2);
//		pso.setrCognitivo(0.5);
		Particula part = pso.psoLocal();
		part.mostrar();
		
	}

	public static void main(String[] args) {
		Experimentos experimentos = new Experimentos();
		System.out.print("Inicio exp");
		//experimentos.exp1();
		
		//experimentos.exp2();
		
		//experimentos.exp3();
		
		//experimentos.exp4();
		
		experimentos.expFinal();
		
		System.out.print("Fin exp");

	}

}
