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
		
		//��������������,�ҵ��������,ͨ������������¸�ֵ����
		Class clazz = a.getClass();
		
		try {
			Field value = clazz.getDeclaredField("value");
			value.setAccessible(true);//����Ϊ�ɷ���
			value.setInt(a, b);
			value.setInt(b, temp);//Integer�ǵ���ģʽ��?
			
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