import java.lang.reflect.Field;


public class test3 {

	public static void main(String[] args){
		
		Integer a = 3;
		Integer b = 5;
		swap(a,b);
		
		System.out.println(a);
		System.out.println(b);
		
	}
	public static void swap(Integer a,Integer b){
		
		int temp = a;
		
		//必须根据这个引用,找到这个对象,通过这个对象重新赋值才行
		Class clazz = a.getClass();
		
		try {
			Field value = clazz.getDeclaredField("value");
			value.setAccessible(true);//设置为可访问
			value.setInt(a, b);
			value.setInt(b, temp);//Integer是单例模式吗?
			
		} catch (NoSuchFieldException e) {
			
			e.printStackTrace();
		} catch (SecurityException e) {
			
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		}
		
		
	}
}
