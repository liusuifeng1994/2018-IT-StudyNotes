package myBase64;

public class Test {

	public static void main(String[] args) {
		Base64 b = new MyBase64Encoder();
		String ss = "ͬһ������ͬһ�����룡";
		 ss = "372922199401110539";
		String str = b.encode(ss);
		System.out.println(str);
		System.out.println(ss);
		System.out.println(b.decode(str));
	}

}
