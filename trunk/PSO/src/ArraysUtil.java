/* ******************************************************************************
 * 
 * This file is part of JMH
 * 
 * License:
 *   EPL: http://www.eclipse.org/legal/epl-v10.html
 *   LGPL 3.0: http://www.gnu.org/licenses/lgpl-3.0-standalone.html
 *   See the LICENSE file in the project's top-level directory for details.
 *
 * **************************************************************************** */

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Random;

public abstract class ArraysUtil {

	public static double max(double[] values) {
		double max = Double.NEGATIVE_INFINITY;
		for (double value : values) {
			if (value > max) {
				max = value;
			}
		}
		return max;
	}

	public static int max(int[] values) {
		int max = Integer.MIN_VALUE;
		for (int value : values) {
			if (value > max) {
				max = value;
			}
		}
		return max;
	}

	public static int indexOfMax(int[] values) {
		int max = Integer.MIN_VALUE;
		int index = Integer.MIN_VALUE;
		for (int i = 0; i < values.length; i++) {
			if (values[i] > max) {
				max = values[i];
				index = i;
			}
		}
		return index;
	}
	public static double min(double[] values) {
		double min = Double.POSITIVE_INFINITY;
		for (double value : values) {
			if (value < min) {
				min = value;
			}
		}
		return min;
	}

	public static int min(int[] values) {
		int min = Integer.MAX_VALUE;
		for (int value : values) {
			if (value < min) {
				min = value;
			}
		}
		return min;
	}

	/**
	 * Returns the position of the minimum value of the array. If there are more
	 * than one values that conforms to be the minimum then the first one is
	 * returned.
	 * 
	 * @param values
	 *            The array
	 * @return the first of the values that conforms to be the minimum
	 */
	public static int indexOfMin(int[] values) {
		int min = Integer.MAX_VALUE;
		int position = -1;
		for (int index = 0; index < values.length; index++) {
			if (values[index] < min) {
				position = index;
				min = values[index];
			}
		}
		return position;
	}

	public static double sum(double[] values) {
		double sum = 0;
		for (double value : values) {
			sum += value;
		}
		return sum;
	}

	public static int[] minmax(int[] values) {
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for (int value : values) {
			if (value < min) {
				min = value;
			}
			if (value > max) {
				max = value;
			}
		}
		return new int[] { min, max };
	}

	public static double[] minmax(double[] values) {
		double min = Integer.MAX_VALUE;
		double max = Integer.MIN_VALUE;
		for (double value : values) {
			if (value < min) {
				min = value;
			}
			if (value > max) {
				max = value;
			}
		}
		return new double[] { min, max };
	}

	public static double average(double[] values) {
		double acc = 0;
		for (double value : values) {
			acc += value;
		}
		return acc / values.length;
	}

	/*public static int[] sort(double[] inArray, int begin, int end) {
		return Sorter.sort(inArray, begin, end);
	}*/

	/**
	 * Ordena de menor a mayor y devuelve en el array de enteros el índice que
	 * tenía el primer valor, en la primera posición. El índice que tenía el
	 * segundo valor, en la segunda posición. etc...
	 * 
	 * @param inArray
	 * @return
	 */
	/*public static int[] sort(double[] inArray) {
		return Sorter.sort(inArray);
	}*/

	public static void reverse(int[] values) {
		for (int i = 0; i < values.length / 2; i++) {
			int index = values.length - i - 1;
			int aux = values[i];
			values[i] = values[index];
			values[index] = aux;
		}
	}

	public static void reverse(double[] values) {
		for (int i = 0; i < values.length / 2; i++) {
			int index = values.length - i - 1;
			double aux = values[i];
			values[i] = values[index];
			values[index] = aux;
		}
	}

	public static void mapTo(double[] values, double destMin, double destMax) {
		double[] minmax = minmax(values);
		double min = minmax[0];
		double max = minmax[1];
		mapTo(values, destMin, destMax, min, max);
	}

	public static void mapTo(double[] values, double srcMin, double srcMax, double destMin, double destMax) {
		double targetRange = destMax - destMin;
		double sourceRange = srcMax - srcMin;
		double ratio = targetRange / sourceRange;
		for (int i = 0; i < values.length; i++) {
			values[i] = ((values[i] - srcMin) * ratio) + destMin;
		}
	}

	public static void add(double[] array, double[] sumValues) {
		if (array.length != sumValues.length) {
			throw new InvalidParameterException("The arrays must have the same length.");
		}
		for (int i = 0; i < array.length; i++) {
			array[i] += sumValues[i];
		}

	}

	/*public static int[] moveRandomPositions(int[] array) {

		int lenght = array.length;
		int[] result = new int[lenght];

		Random random = RandomManager.getRandom();
		int positions = random.nextInt(lenght);

		for (int i = 0; i < lenght; i++) {
			result[(i + positions) % lenght] = array[i];
		}
		return result;
	}*/

	public static String toStringObj(Object obj) {

		String infoToSaveStr;
		if (obj.getClass().isArray()) {
			if (obj instanceof int[]) {
				infoToSaveStr = Arrays.toString((int[]) obj);
			} else if (obj instanceof double[]) {
				infoToSaveStr = Arrays.toString((double[]) obj);
			} else if (obj instanceof float[]) {
				infoToSaveStr = Arrays.toString((float[]) obj);
			} else if (obj instanceof short[]) {
				infoToSaveStr = Arrays.toString((short[]) obj);
			} else if (obj instanceof byte[]) {
				infoToSaveStr = Arrays.toString((byte[]) obj);
			} else if (obj instanceof boolean[]) {
				infoToSaveStr = Arrays.toString((boolean[]) obj);
			} else if (obj instanceof long[]) {
				infoToSaveStr = Arrays.toString((long[]) obj);
			} else if (obj instanceof char[]) {
				infoToSaveStr = Arrays.toString((char[]) obj);
			} else {
				infoToSaveStr = Arrays.deepToString((Object[]) obj);
			}
		} else {
			infoToSaveStr = obj.toString();
		}
		return infoToSaveStr;

	}

	/*public static double getBest(BestMode mode, double[] values) {
		if (mode == BestMode.MAX_IS_BEST) {
			return ArraysUtil.max(values);
		} else {
			return ArraysUtil.min(values);
		}
	}*/

	/*public static int[] sort(double[] values, BestMode mode) {
		int[] newPositions = ArraysUtil.sort(values);

		if (mode == BestMode.MAX_IS_BEST) {
			ArraysUtil.reverse(newPositions);
			ArraysUtil.reverse(values);
		}

		return newPositions;
	}

	public static <T extends Comparable<? super T>> int[] sort(T[] array) {
		return ObjectSorter.sort(array);
	}*/

	public static int[] toIntArray(boolean[] selectedValues) {

		int numValues = 0;
		for (boolean value : selectedValues) {
			if (value) {
				numValues++;
			}
		}

		int[] intValues = new int[numValues];
		int numValue = 0;
		for (int i = 0; i < selectedValues.length; i++) {
			if (selectedValues[i]) {
				intValues[numValue] = i;
				numValue++;
			}
		}
		return intValues;
	}

	public static boolean[] toBooleanArray(int[] intArray, int n) {

		boolean[] array = new boolean[n];
		for (int element : intArray) {
			array[element] = true;
		}
		return array;
	}

	/**
	 * Returns the index of the first apparition of the target value into the
	 * given array.
	 * 
	 * This is a convenience method when the array is not sorted. If the array
	 * is sorted, the {@link Arrays#binarySearch(int[], int)} method is the
	 * preferred way to find values within an array.
	 * 
	 * @param values
	 * @param target
	 * @return
	 */
	public static int firstIndexOf(int[] values, int target) {
		for (int index = 0; index < values.length; index++) {
			if (values[index] == target) {
				return index;
			}
		}
		return -1;
	}

	/**
	 * Swaps the positions index1 and index2 of inArray.
	 * 
	 * @param inArray
	 * @param index1
	 * @param index2
	 */
	public static void swap(int[] inArray, int index1, int index2) {
		int temp = inArray[index1];
		inArray[index1] = inArray[index2];
		inArray[index2] = temp;
	}

	public static void copy(int[] origen, int[] destino) {
		for(int i = 0; i < origen.length;i++){
			destino[i] = origen[i];
		}
		
	}
	
	public static void copy(double[] origen, double[] destino) {
		for(int i = 0; i < origen.length;i++){
			destino[i] = origen[i];
		}
		
	}

	public static void mostrar(int[] solucion) {
		for(int i = 0; i < solucion.length;i++){
			System.out.print(solucion[i]);
		}
		System.out.println();
		
	}

	public static int numApariciones(int elemento, int[] solucion) {
		int numVeces = 0;
		for(int i = 0;i<solucion.length;i++){
			if(solucion[i] == elemento){
				numVeces++;
			}
		}
		return numVeces;
	}

	public static boolean equals(int[] c1, int[] c2) {
		boolean iguales = true;
		int i = 0;
		while(iguales && i < c1.length){
			if(c1[i]!=c2[i]){
				iguales = false;
			}
			i++;
		}
		return iguales;
	}
	
	public static int distancia(int[] c1, int[] c2) {
		int distancia = 0;
		for(int i = 0; i < c1.length; i++){
			if(c1[i]!=c2[i]){
				distancia++;
			}
		}
		return distancia;
	}

	public static void cruce(int[] sol1, int[] sol2) {
		int aux = 0;
		int mitad = sol1.length/2;
		for(int i = 0;i < mitad ; i++){
			aux = sol1[i];
			sol1[i] = sol2[i];
			sol2[i] = aux;
		}
	}
	
	/**
	 * @param solucion
	 */
	public static void mostrar(double [] solucion){
		System.out.print("[ ");
		for (double d : solucion) {
		  System.out.print(d +" ");	
		}
		System.out.println(" ]");
		
	}
	/**
	 * Ordena de menor a mayor y devuelve en el array de enteros el índice que tenía el primer valor, en la primera posición.
	 * El índice que tenía el segundo valor, en la segunda posición. etc...
	 * @param inArray
	 * @return
	 */
	/*public static int[] sort(int[] inArray) {
		return IntSorter.sort(inArray);
	}

	*//**
	 * Ordena de menor a mayor y devuelve en el array de enteros el índice que tenía el primer valor, en la primera posición.
	 * El índice que tenía el segundo valor, en la segunda posición. etc...
	 * @param inArray
	 * @return
	 *//*
	public static int[] sort(float[] inArray) {
		return FloatSorter.sort(inArray);
	}*/
}
