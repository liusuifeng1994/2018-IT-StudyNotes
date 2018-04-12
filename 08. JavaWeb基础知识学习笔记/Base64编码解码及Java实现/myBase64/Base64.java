package myBase64;

//ģ����ģ����д�����ٰ�˼·д��ʵ�־Ϳ�����
public interface Base64 {
	/**
	 * ���ݴ��������ַ����ֽ��룬��ѯbase64�������������������鵽������
	 *
	 * @param bһ���������ֽ���
	 * @return ����base64������������Χ1-63
	 */
	public abstract byte baseIndex(byte b);

	/**
	 * ����ķ��� ���������Ǳ�����base64�ַ����ֽ��� ����ʱ��4��һ����н���
	 * 
	 * @param b�������ַ����ֽ�������
	 * @return ����ԭ�����ַ���
	 */
	public abstract String decode(byte[] b);
	public abstract String decode(String str);

	/**
	 * ���� ��4���ֽ����еĵ�1���ĺ�6λ��00xxxxxx���͵�2�� �ֽڵ�ǰ4λ�ĺ�2λ��00yy0000�� ��ԭΪԭ�����ֽ��루xxxxxxyy��
	 *
	 * @param first 4���ֽ����еĵ�1��
	 * @param second 4���ֽ����еĵ�2��
	 * @return ԭ�����ַ����ֽ���
	 */
	public abstract byte backFirst(byte first, byte second);

	/**
	 * ���� ��4���ֽ����еĵ�2���ĺ�4λ��0000xxxx���͵�3�� �ֽڵ�ǰ6λ�ĺ�4λ��00yyyy00�� ��ԭΪԭ�����ֽ��루xxxxyyyy��
	 * 
	 * @param second
	 *            4���ֽ����еĵ�2��
	 * @param third
	 *            4���ֽ����еĵ�3��
	 * @return ԭ�����ַ����ֽ���
	 */
	public abstract byte backSecond(byte second, byte third);

	/**
	 * ���� ��4���ֽ����еĵ�3���ĺ�2λ��000000xx���͵�4�� �ֽڵĺ�6λ��00yyyyyy�� ��ԭΪԭ�����ֽ��루xxyyyyyy��
	 * 
	 * @param third
	 *            �������ĵ�3���ַ�
	 * @param fourth
	 *            �������ĵ�4���ַ�
	 * @return ԭ�����ַ����ֽ���
	 */
	public abstract byte backThird(byte third, byte fourth);

//	/**
//	 * ���� ���������ַ�����������2���ֽ��뻹ԭΪԭ�����ֽ��� 
//	 * ��������ĩβʣ��2���ֽڣ� ��������2���ֽڵ�ǰ��6λ(00xxxxxx)
//	 * �͵�����һ���ֽڵĺ�2λ(000000yy) ��ԭΪԭ���ı��루xxxxxxyy��
//	 * ��������ĩβʣ��3���ֽڣ�
//	 * ��������2���ֽڵ�ǰ��4λ(0000xxxx) �͵�����һ���ֽڵĺ�4λ(0000yyyy) ��ԭΪԭ���ı��루xxxxyyyy��
//	 * 
//	 * @param last_b������2���ֽ�
//	 * @param next_b������1���ֽ�
//	 * @param move_l������2���ֽ��ƶ�λ���Ĳ���
//	 * @param move_b������1���ֽ��ƶ�λ���Ĳ���
//	 * @return ԭ�����ַ����ֽ���
//	 */
//	public byte backLastOne(byte last_b, byte next_b, int move_l, int move_b);

	/************************* �����õ��ķ��� *************************************/

	/**
	 * Base64����ʵ��ԭ��
	 *  1. ����ȥ�Ĳ����Ǵ�������ַ���������ת�����ֽ����飨�����и��ַ��������⣩
	 *  2. ���ȴ���ǰ3����������ֽڣ�ÿ3��һ�飬�����漰��һЩС������ 
	 * ��Ҫ3*8==��4*8������4���ֽ�Ϊ�ߣ�����һ���ֽ��õ���һ��������
	 * ����Ҫ�ĸ�С���������صĶ�����Ӧ���ֽڣ�
	 *  2.1 firstByte(byte b) ����ֻ��Ҫ3���ֽ��еĵ�һ���ֽڼ��ɣ�
	 *  2.2 secondByte(byte last_b,byte next_b) ������Ҫ3���ֽ��еĵ�1���ֽں͵�2���ֽڣ�
	 *  2.3 thirdByte(byte last_b,byte next_b) ������Ҫ3���ֽ��еĵ�2���ֽں͵�3���ֽڣ�
	 *  2.4 fourthByte(byte b) ������Ҫ3���ֽ��еĵ�3���ֽڡ� 
	 *  ���⣬���������ܻ�����1���ֽڻ�2���ֽڲ������һ�飬��Ҫ��������
	 * ����ʣ��1���ֽ�ʱ�����ɵ�һ���ֽ���Ȼ����firstByte(byte)������
	 * ֻ�账�����ɵڶ����ֽڣ� ��3��4��ֱ��ƴ��=���ɡ�
	 * ʣ��2���ֽ�ʱ�����ɵ�1��2���ֽ���firstByte(byte b)��secondByte(byte last_b,byte
	 * next_b)���ɣ� ���� ��3���ֽ���Ҫ����������4��ֱ��ƴ��=���ɡ�
	 * ��������ĵ��������й��Ծ���ȡ���ض�ԭʼ�ֽڵ�ĳЩλ��һ���ֽڵ�00���ϣ�
	 * ��������0���룬������� ����lastOneByte(byte b,int move)�� 
	 *  3. ��ת������ֽڣ�0-63�����ɱ������������Ӷ������������Ҫ���ַ����ֽڣ�
	 *  ����new String(byte[])��ת�����ַ���������StringBuilder��StringBufferƴ�Ӽ��ɡ�
	 *  ��Ҫ���÷���baseIndex(byte b)��
	 * 
	 */
	/**
	 * ���� �����������ַ�����Ϊbase64������һ��base64���ַ��� ����ʱ3���ֽ�һ����б��룬����������Ҫ���б�����ַ�������
	 * 
	 * @param b
	 *            Ҫ���б�����ַ�������
	 * @return �������ַ���
	 */
	public abstract String encode(byte[] b);

	/**
	 * ������ķ����������أ��������һ��������str.getBytes()
	 * 
	 * @param str
	 * @return �������ַ���
	 */
	public abstract String encode(String str);

	/**
	 * �����ַ�����%3��=0��ʹ�ô˷�������ĩβ�ַ� ����b=xxxxyyyy ����ĩβ�ֽڸ�������1��
	 * ������ֽڵ�ǰ6λ��Ϊһ���ֽ�(00xxxxyy) ������ֽڵĺ�6λ��Ϊһ���ֽ�(00xxyyyy) ����ĩβ�ֽڸ�������2��
	 * ������ֽڵĺ�6λ��Ϊһ���ֽ�(00xxyyyy)
	 * 
	 * @param bĩβ���ַ����ֽ���
	 * @param moveĩβ���ַ����ֽ���Ҫ�ƶ���λ���Ĳ���
	 * @return �������ֽ���
	 */
	public abstract byte lastOneByte(byte b, int move);

	/**
	 * ���� ����b=xxxxyyyy ����1���ֽڵ�ǰ6λ����Ϊbase64 ��3���ֽ��еĵ�1���ӽ���תΪ��00xxxxyy��
	 * 
	 * @param b
	 *            3���ֽ��еĵ�1���ֽ�
	 * @return �������ֽ���
	 */
	public abstract byte firstByte(byte b);

	/**
	 * ���� ����last_b=xxxxyyyynext_b=kkkkffff ��3���ֽ��еĵ�1���ֽڵ����2λ��000000yy��
	 * �͵�2���ֽڵ�ǰ4λ��kkkk0000������Ϊ��00yykkkk��
	 *
	 * @param last_b
	 *            3���ֽ��еĵ�1���ֽ�
	 * @param next_b
	 *            3���ֽ��еĵ�2���ֽ�
	 * @return �������ֽ���
	 */
	public abstract byte secondByte(byte last_b, byte next_b);

	/**
	 * ���� ����last_b=xxxxyyyynext_b=kkkkffff ��3���ֽ��еĵ�2���ֽڵ����4λ��0000yyyy��
	 * �͵�3���ֽڵ�ǰ2λ��kk000000������Ϊ��00yyyykk��
	 *
	 *
	 * @param last_b
	 *            3���ֽ��еĵ�2���ֽ�
	 * @param next_b
	 *            3���ֽ��еĵ�3���ֽ�
	 * @return �������ֽ���
	 */
	public abstract byte thirdByte(byte last_b, byte next_b);

	/**
	 * ���� ����b=xxxxyyyy ��3���ֽ��еĵ�3���ֽڵ����6λ��00xxyyyy�� ת��Ϊ��00xxyyyy��
	 * 
	 * @paramb3���ֽ��еĵ�3���ֽ�
	 * @return�������ֽ���
	 */
	public abstract byte fourthByte(byte b);
}
