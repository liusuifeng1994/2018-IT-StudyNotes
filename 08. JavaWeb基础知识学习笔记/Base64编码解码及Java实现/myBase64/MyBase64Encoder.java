package myBase64;

import java.io.UnsupportedEncodingException;

public class MyBase64Encoder implements Base64 {
	/**
	 * base64码表
	 */

	private static final byte base[] = { 0x41, 0x42, 0x43, 0x44, 0x45, 0x46, 0x47, 0x48, 0x49, 0x4a, 0x4b, 0x4c, 0x4d,
			0x4e, 0x4f, 0x50, 0x51, 0x52, 0x53, 0x54, 0x55, 0x56, 0x57, 0x58, 0x59, 0x5a, 0x61, 0x62, 0x63, 0x64, 0x65,
			0x66, 0x67, 0x68, 0x69, 0x6a, 0x6b, 0x6c, 0x6d, 0x6e, 0x6f, 0x70, 0x71, 0x72, 0x73, 0x74, 0x75, 0x76, 0x77,
			0x78, 0x79, 0x7a, 0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x2b, 0x2f };

	private static String charset1 = "UTF-8";
	@Override
	public byte baseIndex(byte b) {
		for (int i = 0; i < base.length; i++) {
			if (base[i] == b) {
				return (byte) i;
			}
		}
		return -1;
	}

	@Override
	public String decode(String str) {
		byte[] bytes;
		try {
			bytes = str.getBytes(charset1);
			return decode(bytes);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String decode(byte[] b) {
		// b的长度应该是4的倍数，不过恶意条件下不是4的倍数（这里不予考虑）
		int real_len = b.length;
		if (real_len % 4 != 0) {
			// code
			System.out.println("不是4的倍数了");
		}
		
		String str = null;
		try {
			str = new String(b,charset1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 正常情况下，要么1个=要么2个=
		while (str.endsWith("=")) {
			str = str.substring(0, str.length() - 1);
		}
		b = str.getBytes();
		int len = str.length();
		// more_len 要么为3要么为2
		int more_len = len % 4;
		int use_len = len - more_len;
		StringBuilder sb = new StringBuilder();
		byte[] bytes = new byte[3];
		for (int i = 0; i + 4 <= use_len; i += 4) {
			bytes[0] = backFirst(baseIndex(b[i]), baseIndex(b[i + 1]));
			bytes[1] = backSecond(baseIndex(b[i + 1]), baseIndex(b[i + 2]));
			bytes[2] = backThird(baseIndex(b[i + 2]), baseIndex(b[i + 3]));
			try {
				sb.append(new String(bytes,charset1));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if (more_len == 3) {
			byte[] byte_2 = new byte[2];
			byte_2[0] = backFirst(baseIndex(b[len - 3]), baseIndex(b[len - 2]));
			byte_2[1] = backSecond(baseIndex(b[len - 2]), baseIndex(b[len - 1]));
			try {
				sb.append(new String(byte_2,charset1));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else if (more_len == 2) {
			byte[] byte_1 = new byte[1];
			byte_1[0] = backFirst(baseIndex(b[len - 2]), baseIndex(b[len - 1]));
			System.out.println(byte_1[0]);
			try {
				sb.append(new String(byte_1,charset1));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else if(more_len==1) {
			System.out.println("异常");
		}
		return sb.toString();
	}
	// @Override
	// public String decode(byte[] b) {
	// StringBuffer sb = new StringBuffer();
	// Vector<Byte> list = new Vector<Byte>();
	//
	// int real_len = b.length;
	// int len = real_len - 2;
	//// int len = real_len ;
	// int more_len = len & 3;
	// int use_len = len - more_len;
	// for (int i = 0; i < use_len; i += 4) {
	// list.add(backFirst(baseIndex(b[i]), baseIndex(b[i + 1])));
	// list.add(backSecond(baseIndex(b[i + 1]), baseIndex(b[i + 2])));
	// list.add(backThird(baseIndex(b[i + 2]), baseIndex(b[i + 3])));
	// }
	//
	// @SuppressWarnings("rawtypes")
	// Enumeration e = list.elements();
	// byte bytes[] = new byte[list.size()];
	// int k = -1;
	// while (e.hasMoreElements()) {
	// bytes[++k] = (Byte) e.nextElement();
	// }
	// sb.append(new String(bytes));
	//
	// if (more_len == 2) {
	// byte b_1[] = new byte[1];
	// b_1[0] = backLastOne(baseIndex(b[len - 2]), baseIndex(b[len - 1]), 2, 6);
	// sb.append(new String(b_1));
	// }
	// if (more_len == 3) {
	// byte b_2[] = new byte[2];
	// b_2[0] = backFirst(baseIndex(b[len - 3]), baseIndex(b[len - 2]));
	// b_2[1] = backLastOne(baseIndex(b[len - 2]), baseIndex(b[len - 1]), 4, 4);
	// sb.append(new String(b_2));
	// }
	//
	// return sb.toString();
	// }

	@Override
	public byte lastOneByte(byte b, int move) {
		int r_b = b & 0xff;
		r_b = r_b << move;
		r_b = r_b >>> 2;
		return (byte) (r_b & 0x3f);
	}

//	@Override
//	public byte backLastOne(byte last_b, byte next_b, int move_l, int move_b) {
//		int r_l = last_b & 0xff;
//		int r_n = next_b & 0xff;
//		r_l = r_l << move_l;
//		r_n = r_n << move_b;
//		r_n = r_n >>> move_b;
//		return (byte) ((r_l | r_n) & 0xff);
//	}

	@Override
	public byte backFirst(byte first, byte second) {
		int r_f = first & 0xff;
		int r_s = second & 0xff;
		r_f = r_f << 2;
		r_s = r_s >>> 4;
		return (byte) ((r_f | r_s) & 0xff);
	}

	@Override
	public byte backSecond(byte second, byte third) {
		int r_s = second & 0xff;
		int r_t = third & 0xff;
		r_s = r_s << 4;
		r_t = r_t >>> 2;
		return (byte) ((r_s | r_t) & 0xff);
	}

	@Override
	public byte backThird(byte third, byte fourth) {
		int r_t = third & 0xff;
		int r_f = fourth & 0xff;
		r_t = r_t << 6;
		return (byte) ((r_t | r_f) & 0xff);
	}

	@Override
	public String encode(byte[] b) {
		StringBuffer sb = new StringBuffer();
		int len = b.length;
		int more_len = len % 3;
		// use_len一定是3的倍数
		int use_len = len - more_len;
		byte[] bytes = new byte[4];
		// 把前面的3的整数倍的字节进行Base64编码
		for (int i = 0; i < use_len; i += 3) {
			// 3*8==》4*8的过程
			// 完成第1个
			bytes[0] = base[firstByte(b[i])];
			// 完成第2个
			bytes[1] = base[secondByte(b[i], b[i + 1])];
			// 完成第3个
			bytes[2] = base[thirdByte(b[i + 1], b[i + 2])];
			// 完成第4个
			bytes[3] = base[fourthByte(b[i + 2])];
			// 转换成字符串
			try {
				sb.append(new String(bytes,charset1));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		// 关键就是处理最后剩1个或2个的情况
		// 如果剩下1个
		if (more_len == 1) {
			byte b_2[] = new byte[2];
			b_2[0] = base[firstByte(b[len - 1])];
			b_2[1] = base[lastOneByte(b[len - 1], 6)];
			try {
				sb.append(new String(b_2,charset1));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			// 后面添加2个=
			return sb.append("==").toString();
		} else if (more_len == 2) {
			byte b_3[] = new byte[3];
			b_3[0] = base[firstByte(b[len - 2])];
			b_3[1] = base[secondByte(b[len - 2], b[len - 1])];
			b_3[2] = base[lastOneByte(b[len - 1], 4)];
			try {
				sb.append(new String(b_3,charset1));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			// 后面添加1个=
			return sb.append("=").toString();
		}
		return sb.toString();
	}

	@Override
	public String encode(String str) {
		byte[] bytes;
		try {
			bytes = str.getBytes(charset1);
			return encode(bytes);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public byte firstByte(byte b) {
		// 00000000000000000000000001010011
		// 01010011
		int r_f = b & 0xff;
		r_f = r_f >>> 2;
		return (byte) (r_f & 0x3f);
	}

	@Override
	public byte secondByte(byte last_b, byte next_b) {
		int r_l = last_b & 0xff;
		int r_n = next_b & 0xff;
		r_l = r_l << 6;
		r_l = r_l >>> 2;
		r_n = r_n >>> 4;
		return (byte) ((r_l | r_n) & 0x3f);
	}

	@Override
	public byte thirdByte(byte last_b, byte next_b) {
		int r_l = last_b & 0xff;
		int r_n = next_b & 0xff;
		r_l = r_l << 4;
		r_l = r_l >>> 2;
		r_n = r_n >>> 6;
		return (byte) ((r_l | r_n) & 0x3f);
	}

	@Override
	public byte fourthByte(byte b) {
		int r_b = b & 0xff;
		r_b = r_b << 2;
		r_b = r_b >>> 2;
		return (byte) (r_b & 0x3f);
	}
}
