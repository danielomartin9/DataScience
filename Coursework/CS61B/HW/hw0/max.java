public class max{
	public static void main(String[] args){
		/** main method, where the program starts running */
		int[] nums = {11,25,3,49};	
		int max_f = max_for(nums);
		int max_w = max_while(nums);

		System.out.println("Max for loop #: " + max_f);
		System.out.println("Max while loop #: " + max_w);
	}

	public static int max_for(int[] a){
		/** Returns the maximum value of an int array.
		* Using a for loop
		* Return type is an int */
		int max_num = 0;
		for(int index = 0; index < a.length; index++){
			if(a[index] > max_num)
				max_num = a[index];
		}
		return max_num;
	}

	public static int max_while(int[] a){
		/** Returns the maximum value of an int array
		* Using a while loop
		* Return type is an int */
		int index = 0;
		int max_num = 0;

		while(index < a.length){
			if(a[index] > max_num)
				max_num = a[index];
			index += 1;
		}
		return max_num;
	}
}