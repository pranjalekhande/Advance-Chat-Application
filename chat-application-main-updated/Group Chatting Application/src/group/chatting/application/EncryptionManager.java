package group.chatting.application;

import java.util.Base64;
import java.util.Base64.Encoder;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;


/**
 * This class manages encryption and decryption using the Triple DES (DESede) algorithm.
 * It provides a singleton instance to handle encryption keys and cipher operations securely.
 */
public class EncryptionManager  {
	private static EncryptionManager encryptionManager = null;
	public SecretKey secretKey;
	private static Cipher cipher;

	/**
	 * Private constructor to prevent direct instantiation.
	 * Initializes the encryption key and cipher.
	 * @throws Exception if any error occurs during initialization.
	 */
	private EncryptionManager() throws Exception {
		String encryptionKey = "Er%Pm_89nNiO$/pqL@#mGxxY0"; // The key string used for encryption
		String encryptionScheme = "DESede"; // Specifies the encryption scheme
		byte[] arrBytes = encryptionKey.getBytes("UTF8"); // Convert the encryption key into byte array
		
		DESedeKeySpec ks = new DESedeKeySpec(arrBytes); // Key specification for the DESede key
		SecretKeyFactory skf = SecretKeyFactory.getInstance(encryptionScheme); // Factory for generating the secret keys
		EncryptionManager.cipher = Cipher.getInstance("DESede"); // Initialize the cipher for DESede encryption
		
		this.secretKey = skf.generateSecret(ks); // Generate and store the secret key from the key specification
	}

	/**
	 * Provides the singleton instance of the EncryptionManager.
	 * @return the singleton instance of the EncryptionManager.
	 * @throws Exception if an instance cannot be created.
	 */
	public static synchronized EncryptionManager getInstance() throws Exception {
		if (encryptionManager == null) {
			encryptionManager = new EncryptionManager();
		}
		return encryptionManager;
	}


	/**
	 * Encrypts plain text using the specified secret key.
	 * @param plainText The text to be encrypted.
	 * @param secretKey The secret key used for encryption.
	 * @return The encrypted string in Base64 encoding.
	 * @throws Exception if encryption fails.
	 */
	public static String encrypt(String plainText, SecretKey secretKey) throws Exception {
	    	byte[] plainTextByte = plainText.getBytes();
	    	cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	    	byte[] encryptedByte = cipher.doFinal(plainTextByte);
	    	Encoder encoder = Base64.getEncoder();
	    	return encoder.encodeToString(encryptedByte);
	    }

	/**
	 * Decrypts encrypted text using the specified secret key.
	 * @param encryptedText The encrypted text in Base64 encoding to be decrypted.
	 * @param secretKey The secret key used for decryption.
	 * @return The decrypted string.
	 * @throws Exception if decryption fails.
	 */
	public static String decrypt(String encryptedText, SecretKey secretKey) throws Exception {
	    	Base64.Decoder decoder = Base64.getDecoder();
	    	byte[] encryptedTextByte = decoder.decode(encryptedText);
	    	cipher.init(Cipher.DECRYPT_MODE, secretKey);
	    	byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
	    	return new String(decryptedByte);
	    }
}
