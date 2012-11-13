package com.saperion.sdb.client.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Utility class for copying streams, file channels and append byte arrays.
 */
public final class StreamUtil {

	/**
	 * Private contructor.
	 */
	private StreamUtil() {
	}

	/**
	 * Copies the content from in to out.
	 * 
	 * @param in
	 *            The source InputStream.
	 * @param inputEncoding
	 *            The encoding of the input stream.
	 * @param out
	 *            The destination OutputStream.
	 * @param outputEncoding
	 *            The encoding of the output stream.
	 * @param bufferSize
	 *            The buffer size.
	 * @param forceClose
	 *            If true, the given streams will be closed after execution.
	 * @throws IOException
	 *             Throws an IOException if an IO error occurs.
	 * 
	 */
	public static void copyInToOut(InputStream in, String inputEncoding,
			OutputStream out, String outputEncoding, int bufferSize,
			boolean forceClose) throws IOException {
		BufferedInputStream bis = new BufferedInputStream(in);
		BufferedOutputStream bos = new BufferedOutputStream(out);

		try {
			byte[] buffer = new byte[bufferSize];
			for (;;) {
				int len = bis.read(buffer);
				if (len < 0) {
					break;
				}
				String b = new String(buffer, 0, len, inputEncoding);
				bos.write(b.getBytes(outputEncoding));
			}
			bos.flush();
		} finally {
			if (forceClose) {
				try {
					bis.close();
				} catch (Exception e) {
					// ignore
				}
				try {
					bos.close();
				} catch (Exception e) {
					return;
				}
			}
		}
	}

	/**
	 * Copies the content from in to out.
	 * 
	 * @param in
	 *            The source InputStream.
	 * @param out
	 *            The destination OutputStream.
	 * @param buffer
	 *            The buffer size.
	 * @param forceClose
	 *            If true, the given channels will be closed after execution.
	 * @throws IOException
	 *             Throws an IOException if an IO error occurs.
	 */
	public static void copyInToOut(InputStream in, OutputStream out,
			ByteBuffer buffer, boolean forceClose) throws IOException {
		ReadableByteChannel inChannel = Channels.newChannel(in);
		WritableByteChannel outChannel = Channels.newChannel(out);

		try {
			for (;;) {
				int len = inChannel.read(buffer);
				if (len < 0) {
					break;
				}

				buffer.flip();
				outChannel.write(buffer);
				buffer.clear();
			}

			buffer.flip();
			while (buffer.hasRemaining()) {
				outChannel.write(buffer);
			}
		} finally {
			if (forceClose) {
				try {
					inChannel.close();
				} catch (Exception e) {
					// ignore
				}
				try {
					outChannel.close();
				} catch (Exception e) {
					return;
				}
			}
		}
	}

	/**
	 * Transfers (copies) the content from in to out.
	 * 
	 * @param in
	 *            The source ReadableByteChannel.
	 * @param out
	 *            The destination FileChannel.
	 * @param buffer
	 *            The buffer size.
	 * @param forceClose
	 *            If true, the given channels will be closed after execution.
	 * @throws IOException
	 *             Throws an IOException if an IO error occurs.
	 */
	public static void transferFrom(ReadableByteChannel in, FileChannel out,
			int buffer, boolean forceClose) throws IOException {

		try {
			long position = 0L;
			for (;;) {
				long transfered = out.transferFrom(in, position, buffer);
				if (transfered <= 0) {
					break;
				}
				position += transfered;
			}
		} finally {
			if (forceClose) {
				try {
					in.close();
				} catch (Exception e) {
					// ignore
				}
				try {
					out.close();
				} catch (Exception e) {
					return;
				}
			}
		}
	}

	/**
	 * Transforms (copies) the content of the given FileChannel to the given
	 * WritableByteChannel.
	 * 
	 * @param in
	 *            The FileChannel to read byte from.
	 * @param out
	 *            The WritableByteChannel to transform bytes to.
	 * @param buffer
	 *            The buffer size.
	 * @param forceClose
	 *            If true, the given channels will be closed after execution.
	 * @throws IOException
	 *             Throws an IOException if an IO error occurs.
	 */
	public static void transferTo(FileChannel in, WritableByteChannel out,
			int buffer, boolean forceClose) throws IOException {

		try {
			long position = 0L;
			for (;;) {
				long transfered = in.transferTo(position, buffer, out);
				if (transfered <= 0) {
					break;
				}
				position += transfered;
			}
		} finally {
			if (forceClose) {
				try {
					in.close();
				} catch (Exception e) {
					// ignore
				}
				try {
					out.close();
				} catch (Exception e) {
					// ignore
				}
			}
		}
	}

	/**
	 * Copies the content of the given InputStream to the given OutputStream.
	 * 
	 * @param in
	 *            The InputStream to read the content from.
	 * @param out
	 *            The OutputStream to copy the content to.
	 * @param bufferSize
	 *            The buffer size.
	 * @param forceClose
	 *            If true, the given streams will be closed after execution.
	 * @throws IOException
	 *             Throws an IOException if an IO error occurs.
	 */
	public static void copyInToOut(InputStream in, OutputStream out,
			int bufferSize, boolean forceClose) throws IOException {
		BufferedInputStream bis = new BufferedInputStream(in);
		BufferedOutputStream bos = new BufferedOutputStream(out);

		try {
			byte[] buffer = new byte[bufferSize];
			for (;;) {
				int len = bis.read(buffer);
				if (len < 0) {
					break;
				}
				bos.write(buffer, 0, len);
			}
			bos.flush();
		} finally {
			if (forceClose) {
				try {
					bis.close();
				} catch (Exception e) {
					// ignore
				}
				try {
					bos.close();
				} catch (Exception e) {
					// ignore
				}
			}
		}
	}
	
	public static void copyInToOut(InputStream in, OutputStream out,
			int bufferSize, boolean forceClose, ProgressListener listener) throws IOException {
		BufferedInputStream bis = new BufferedInputStream(in);
		BufferedOutputStream bos = new BufferedOutputStream(out);

		long count = 0;
		try {
			byte[] buffer = new byte[bufferSize];
			for (;;) {
				int len = bis.read(buffer);
				if (len < 0) {
					break;
				}
				count = count + len;
				bos.write(buffer, 0, len);
				listener.alreadyRead(count);
			}
			bos.flush();
		} finally {
			if (forceClose) {
				try {
					bis.close();
				} catch (Exception e) {
					// ignore
				}
				try {
					bos.close();
				} catch (Exception e) {
					// ignore
				}
			}
		}
	}
	
	public static interface ProgressListener{
		void alreadyRead(long bytes);
	}

	/**
	 * Creates a new array from the content of the two given byte arrays.
	 * 
	 * @param array1
	 *            The first content array.
	 * @param array2
	 *            The second content array.
	 * @return The newly created byte array.
	 */
	public static byte[] append(byte[] array1, byte[] array2) {
		byte[] newArray = new byte[array1.length + array2.length];

		System.arraycopy(array1, 0, newArray, 0, array1.length);
		System.arraycopy(array2, 0, newArray, array1.length, array2.length);

		return newArray;
	}

	/**
	 * Creates a byte array of the given InputStream and returns it.
	 * 
	 * @param in
	 *            The InputSteam in to copy.
	 * @param bufferSize
	 *            The size of the array to be created.
	 * @param forceClose
	 *            It true, all streams will be closed.
	 * @return the created byte array.
	 * @throws IOException
	 *             Throws an IOException if an IO error occurs.
	 */
	public static byte[] readBytes(InputStream in, int bufferSize,
			boolean forceClose) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(bufferSize);

		copyInToOut(in, baos, bufferSize, forceClose);
		return baos.toByteArray();
	}

	/**
	 * Compares the given input streams (pair-wise byte comparison).
	 * 
	 * @param content1
	 *            The first InputStream in question.
	 * @param content2
	 *            The second InputStream in question.
	 * @return Returns true, if the given stream are equal.
	 * @throws IOException
	 *             Throws an IOException if an IO error occurs.
	 */
	public static boolean compare(InputStream content1, InputStream content2)
			throws IOException {
		// read and compare bytes pair-wise
		InputStream i1 = new BufferedInputStream(content1);
		InputStream i2 = new BufferedInputStream(content2);
		int b1, b2;
		do {
			b1 = i1.read();
			b2 = i2.read();
		} while (b1 == b2 && b1 != -1);
		i1.close();
		i2.close();
		// true only if end of file is reached
		return b1 == -1;
	}
}
