public class threeSumDistinct{
	public static void main(String[] args){
		int[] nums1 = {-6, 2, 4}; 			//Expect True
		int[] nums2 = {-6, 2, 5}; 			//Expect False
		int[] nums3 = {-6, 3, 10, 200}; 	//Expect False
		int[] nums4 = {8, 2, -1, 15}; 		//Expect False
		int[] nums5 = {8, 2, -1, -1, 15}; 	//Expect True
		int[] nums6 = {5, 1, 0, 3, 6};		//Expect False

		boolean result1 = threeSumDistinct(nums1);
		boolean result2 = threeSumDistinct(nums2);
		boolean result3 = threeSumDistinct(nums3);
		boolean result4 = threeSumDistinct(nums4);
		boolean result5 = threeSumDistinct(nums5);
		boolean result6 = threeSumDistinct(nums6);

		System.out.println("3SUM result1: " + result1);
		System.out.println("3SUM reslut2: " + result2);
		System.out.println("3SUM result3: " + result3);
		System.out.println("3SUM result4: " + result4);
		System.out.println("3SUM result5: " + result5);
		System.out.println("3SUM result6: " + result6);
	}

	public static boolean threeSumDistinct(int[] a){
		/** Returns true if there exists three intergers
		* in a that sum to 0 and false otherwise */

		for(int j = 0; j < a.length; j++){
			for(int k = j+1; k < a.length; k++){
				for(int i = k+1; i < a.length; i++){
					//System.out.print("{ " + a[j] + ", " + a[k] + ", " + a[i] + " }");
					int sum = a[j] + a[k] +a[i];
					//System.out.println(" = " + sum);
					if(sum == 0){
						//System.out.println("SUM == 0!");
						return true;
					}
				}
			}
		}
		return false;
	}
}