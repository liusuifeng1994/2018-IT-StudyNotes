package myBase64;

//模板类模板类写好了再按思路写个实现就可以了
public interface Base64 {
	/**
	 * 根据传进来的字符的字节码，查询base64码表的索引，并返回所查到的索引
	 *
	 * @param b一个编码后的字节码
	 * @return 返回base64码表的索引，范围1-63
	 */
	public abstract byte baseIndex(byte b);

	/**
	 * 解码的方法 传进来的是编码后的base64字符的字节码 解析时是4个一组进行解析
	 * 
	 * @param b编码后的字符的字节码数组
	 * @return 返回原来的字符串
	 */
	public abstract String decode(byte[] b);
	public abstract String decode(String str);

	/**
	 * 解码 将4个字节码中的第1个的后6位（00xxxxxx）和第2个 字节的前4位的后2位（00yy0000） 还原为原来的字节码（xxxxxxyy）
	 *
	 * @param first 4个字节码中的第1个
	 * @param second 4个字节码中的第2个
	 * @return 原来的字符的字节码
	 */
	public abstract byte backFirst(byte first, byte second);

	/**
	 * 解码 将4个字节码中的第2个的后4位（0000xxxx）和第3个 字节的前6位的后4位（00yyyy00） 还原为原来的字节码（xxxxyyyy）
	 * 
	 * @param second
	 *            4个字节码中的第2个
	 * @param third
	 *            4个字节码中的第3个
	 * @return 原来的字符的字节码
	 */
	public abstract byte backSecond(byte second, byte third);

	/**
	 * 解码 将4个字节码中的第3个的后2位（000000xx）和第4个 字节的后6位（00yyyyyy） 还原为原来的字节码（xxyyyyyy）
	 * 
	 * @param third
	 *            传进来的第3个字符
	 * @param fourth
	 *            传进来的第4个字符
	 * @return 原来的字符的字节码
	 */
	public abstract byte backThird(byte third, byte fourth);

//	/**
//	 * 解码 将编码后的字符串数组的最后2个字节码还原为原来的字节码 
//	 * 假如数组末尾剩下2个字节： 将倒数第2个字节的前后6位(00xxxxxx)
//	 * 和倒数第一个字节的后2位(000000yy) 还原为原来的编码（xxxxxxyy）
//	 * 假如数组末尾剩下3个字节：
//	 * 将倒数第2个字节的前后4位(0000xxxx) 和倒数第一个字节的后4位(0000yyyy) 还原为原来的编码（xxxxyyyy）
//	 * 
//	 * @param last_b倒数第2个字节
//	 * @param next_b倒数第1个字节
//	 * @param move_l倒数第2个字节移动位数的参数
//	 * @param move_b倒数第1个字节移动位数的参数
//	 * @return 原来的字符的字节码
//	 */
//	public byte backLastOne(byte last_b, byte next_b, int move_l, int move_b);

	/************************* 编码用到的方法 *************************************/

	/**
	 * Base64编码实现原理：
	 *  1. 传进去的参数是待编码的字符串，首先转换成字节数组（这里有个字符集的问题）
	 *  2. 首先处理前3的最大倍数的字节，每3个一组，这里涉及到一些小方法， 
	 * 需要3*8==》4*8，生成4个字节为线，生成一个字节用单独一个方法，
	 * 则需要四个小方法，返回的都是相应的字节：
	 *  2.1 firstByte(byte b) 参数只需要3个字节中的第一个字节即可；
	 *  2.2 secondByte(byte last_b,byte next_b) 参数需要3个字节中的第1个字节和第2个字节；
	 *  2.3 thirdByte(byte last_b,byte next_b) 参数需要3个字节中的第2个字节和第3个字节；
	 *  2.4 fourthByte(byte b) 参数需要3个字节中的第3个字节。 
	 *  另外，对于最后可能会留下1个字节或2个字节不能组成一组，需要单独处理：
	 * 但是剩下1个字节时，生成第一个字节仍然调用firstByte(byte)方法，
	 * 只需处理生成第二个字节， 第3、4个直接拼接=即可。
	 * 剩下2个字节时，生成第1、2个字节用firstByte(byte b)、secondByte(byte last_b,byte
	 * next_b)即可， 但是 第3个字节需要单独处理，第4个直接拼接=即可。
	 * 对于上面的单独处理，有共性就是取出特定原始字节的某些位到一个字节的00后上，
	 * 不够的用0补齐，这个方法 就是lastOneByte(byte b,int move)。 
	 *  3. 把转换后的字节（0-63）当成编码表的索引，从而查出编码所需要的字符的字节，
	 *  利用new String(byte[])即转换成字符串，利用StringBuilder或StringBuffer拼接即可。
	 *  需要利用方法baseIndex(byte b)。
	 * 
	 */
	/**
	 * 编码 将传进来的字符编码为base64，返回一个base64的字符串 编码时3个字节一组进行编码，传进来的是要进行编码的字符串数组
	 * 
	 * @param b
	 *            要进行编码的字符串数组
	 * @return 编码后的字符串
	 */
	public abstract String encode(byte[] b);

	/**
	 * 对上面的方法进行重载，这里多了一步，就是str.getBytes()
	 * 
	 * @param str
	 * @return 编码后的字符串
	 */
	public abstract String encode(String str);

	/**
	 * 假如字符长度%3！=0，使用此方法编码末尾字符 假如b=xxxxyyyy 假如末尾字节个数等于1：
	 * 将这个字节的前6位作为一个字节(00xxxxyy) 将这个字节的后6位作为一个字节(00xxyyyy) 假如末尾字节个数等于2：
	 * 将这个字节的后6位作为一个字节(00xxyyyy)
	 * 
	 * @param b末尾的字符的字节码
	 * @param move末尾的字符的字节码要移动的位数的参数
	 * @return 编码后的字节码
	 */
	public abstract byte lastOneByte(byte b, int move);

	/**
	 * 编码 假如b=xxxxyyyy 将第1个字节的前6位编码为base64 将3个字节中的第1个子节码转为（00xxxxyy）
	 * 
	 * @param b
	 *            3个字节中的第1个字节
	 * @return 编码后的字节码
	 */
	public abstract byte firstByte(byte b);

	/**
	 * 编码 假如last_b=xxxxyyyynext_b=kkkkffff 将3个字节中的第1个字节的最后2位（000000yy）
	 * 和第2个字节的前4位（kkkk0000）编码为（00yykkkk）
	 *
	 * @param last_b
	 *            3个字节中的第1个字节
	 * @param next_b
	 *            3个字节中的第2个字节
	 * @return 编码后的字节码
	 */
	public abstract byte secondByte(byte last_b, byte next_b);

	/**
	 * 编码 假如last_b=xxxxyyyynext_b=kkkkffff 将3个字节中的第2个字节的最后4位（0000yyyy）
	 * 和第3个字节的前2位（kk000000）编码为（00yyyykk）
	 *
	 *
	 * @param last_b
	 *            3个字节中的第2个字节
	 * @param next_b
	 *            3个字节中的第3个字节
	 * @return 编码后的字节码
	 */
	public abstract byte thirdByte(byte last_b, byte next_b);

	/**
	 * 编码 假如b=xxxxyyyy 将3个字节中的第3个字节的最后6位（00xxyyyy） 转码为（00xxyyyy）
	 * 
	 * @paramb3个字节中的第3个字节
	 * @return编码后的字节码
	 */
	public abstract byte fourthByte(byte b);
}
