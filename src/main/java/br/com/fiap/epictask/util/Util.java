package br.com.fiap.epictask.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {

	public static String md5(String password) throws NoSuchAlgorithmException{
		
		MessageDigest message = MessageDigest.getInstance("md5");
		BigInteger hash = new BigInteger(1,message.digest(password.getBytes()));
		return hash.toString(16);
	}
}
