package rest_api.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class FileCryptography {
	
	private static final String ALGORITHM = "Blowfish";
	private static String keyString = "Use your desired Key";

	public static File encrypt(File rawFile) throws Exception {
		File encryptedFile = doCrypto(Cipher.ENCRYPT_MODE, rawFile);
		return encryptedFile;
	}

	public static File decrypt(File encryptedFile) throws Exception {
		File decryptedFile = doCrypto(Cipher.DECRYPT_MODE, encryptedFile);
		return decryptedFile;
	}

	private static File doCrypto(int cipherMode, File inputFile) throws Exception {

		Key secretKey = new SecretKeySpec(keyString.getBytes(), ALGORITHM);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(cipherMode, secretKey);

		FileInputStream inputStream = new FileInputStream(inputFile);
		byte[] inputBytes = new byte[(int) inputFile.length()];
		inputStream.read(inputBytes);

		byte[] outputBytes = cipher.doFinal(inputBytes);

		File outputFile = new File(inputFile.getName());
		
		FileOutputStream outputStream = new FileOutputStream(outputFile);
		outputStream.write(outputBytes);

		inputStream.close();
		outputStream.close();
		
		return outputFile;
	}	
}