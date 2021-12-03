/**
 *
 * Licensed Property to China UnionPay Co., Ltd.
 * 
 * (C) Copyright of China UnionPay Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * 
 * Modification History:
 * =============================================================================
 *   Author         Date          Description
 *   ------------ ---------- ---------------------------------------------------
 *   xshu       2014-05-28       证书工具类.
 * =============================================================================
 */
package com.xxcx.pay.untionUtil;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.*;
import java.security.spec.RSAPublicKeySpec;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @ClassName: CertUtil
 * @Description: acpsdk证书工具类，主要用于对证书的加载和使用
 * @date 2016-7-22 下午2:46:20
 * 声明：以下代码只是为了方便接入方测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考，不提供编码，性能，规范性等方面的保障
 */
@Slf4j
public class CertUtil {
	/** 证书容器，存储对商户请求报文签名私钥证书. */
	private static KeyStore keyStore = null;
	/** 敏感信息加密公钥证书 */
	private static X509Certificate encryptCert = null;
	/** 磁道加密公钥 */
	private static PublicKey encryptTrackKey = null;
	/** 验证银联返回报文签名证书. */
	private static X509Certificate validateCert = null;
	/** 验签中级证书 */
	private static X509Certificate middleCert = null;
	/** 验签根证书 */
	private static X509Certificate rootCert = null;
	/** 验证银联返回报文签名的公钥证书存储Map. */
	private static Map<String, X509Certificate> certMap = new HashMap<String, X509Certificate>();
	/** 商户私钥存储Map */
	private final static Map<String, KeyStore> keyStoreMap = new ConcurrentHashMap<String, KeyStore>();
	
	/**
	 * 初始化所有证书.
	 */
	public static void init() {
		try {
			addProvider();//向系统添加BC provider
			initSignCert();//初始化签名私钥证书
		} catch (Exception e) {
			log.info("init失败。（如果是用对称密钥签名的可无视此异常。）", e);
		}
	}
	
	/**
	 * 添加签名，验签，加密算法提供者
	 */
	private static void addProvider(){
		if (Security.getProvider("BC") == null) {
			log.info("add BC provider");
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		} else {
			//解决eclipse调试时tomcat自动重新加载时，BC存在不明原因异常的问题。
			Security.removeProvider("BC");
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			log.info("re-add BC provider");
		}
		printSysInfo();
	}
	
	/**
	 * 用配置文件acp_sdk.properties中配置的私钥路径和密码 加载签名证书
	 */
	private static void initSignCert() {
		if (null != keyStore) {
			keyStore = null;
		}
		try {
			keyStore = getKeyInfo("F:/Yaosq/production/saas-支付服务/第三方支付平台/对接银联/云闪付WAP支付/证书/5.1.0/acp_test_sign.pfx",
					"000000", "PKCS12");
			log.info("InitSignCert Successful. CertId=["
					+ getSignCertId() + "]");
		} catch (IOException e) {
			log.info("InitSignCert Error", e);
		}
	}

	/**
	 * 用给定的路径和密码 加载签名证书，并保存到certKeyStoreMap
	 * 
	 * @param certFilePath
	 * @param certPwd
	 */
	private static void loadSignCert(String certFilePath, String certPwd) {
		KeyStore keyStore = null;
		try {
			keyStore = getKeyInfo(certFilePath, certPwd, "PKCS12");
			keyStoreMap.put(certFilePath, keyStore);
			log.info("LoadRsaCert Successful");
		} catch (IOException e) {
			log.info("LoadRsaCert Error", e);
		}
	}

	/**
	 * 通过证书路径初始化为公钥证书
	 * @param path
	 * @return
	 */
	private static X509Certificate initCert(String path) {
		X509Certificate encryptCertTemp = null;
		CertificateFactory cf = null;
		FileInputStream in = null;
		try {
			cf = CertificateFactory.getInstance("X.509", "BC");
			in = new FileInputStream(path);
			encryptCertTemp = (X509Certificate) cf.generateCertificate(in);
			// 打印证书加载信息,供测试阶段调试
			log.info("[" + path + "][CertId="
					+ encryptCertTemp.getSerialNumber().toString() + "]");
		} catch (CertificateException e) {
			log.info("InitCert Error", e);
		} catch (FileNotFoundException e) {
			log.info("InitCert Error File Not Found", e);
		} catch (NoSuchProviderException e) {
			log.info("LoadVerifyCert Error No BC Provider", e);
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					log.info(e.toString());
				}
			}
		}
		return encryptCertTemp;
	}
	
	/**
	 * 通过keyStore 获取私钥签名证书PrivateKey对象
	 * 
	 * @return
	 */
	public static PrivateKey getSignCertPrivateKey() {
		try {
			Enumeration<String> aliasenum = keyStore.aliases();
			String keyAlias = null;
			if (aliasenum.hasMoreElements()) {
				keyAlias = aliasenum.nextElement();
			}
			PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyAlias,
					"000000".toCharArray());
			return privateKey;
		} catch (KeyStoreException | UnrecoverableKeyException | NoSuchAlgorithmException e) {
			log.info("getSignCertPrivateKey Error", e);
			return null;
		}
	}
	
	/**
	 * 获取配置文件acp_sdk.properties中配置的签名私钥证书certId
	 * 
	 * @return 证书的物理编号
	 */
	public static String getSignCertId() {
		try {
			Enumeration<String> aliasenum = keyStore.aliases();
			String keyAlias = null;
			if (aliasenum.hasMoreElements()) {
				keyAlias = aliasenum.nextElement();
			}
			X509Certificate cert = (X509Certificate) keyStore
					.getCertificate(keyAlias);
			return cert.getSerialNumber().toString();
		} catch (Exception e) {
			log.info("getSignCertId Error", e);
			return null;
		}
	}

	/**
	 * 获取敏感信息加密证书的certId
	 * 
	 * @return
	 */
	public static String getEncryptCertId() {
		if (null == encryptCert) {
			String path = "SDKConfig.getConfig().getEncryptCertPath()";
			if (!StrUtil.isEmpty(path)) {
				encryptCert = initCert(path);
				return encryptCert.getSerialNumber().toString();
			} else {
				log.info("acpsdk.encryptCert.path is empty");
				return null;
			}
		} else {
			return encryptCert.getSerialNumber().toString();
		}
	}

	/**
	 * 将签名私钥证书文件读取为证书存储对象
	 * 
	 * @param pfxkeyfile
	 *            证书文件名
	 * @param keypwd
	 *            证书密码
	 * @param type
	 *            证书类型
	 * @return 证书对象
	 * @throws IOException 
	 */
	private static KeyStore getKeyInfo(String pfxkeyfile, String keypwd,
			String type) throws IOException {
		log.info("加载签名证书==>" + pfxkeyfile);
		FileInputStream fis = null;
		try {
			KeyStore ks = KeyStore.getInstance(type, "BC");
			log.info("Load RSA CertPath=[" + pfxkeyfile + "],Pwd=["+ keypwd + "],type=["+type+"]");
			fis = new FileInputStream(pfxkeyfile);
			char[] nPassword = null;
			nPassword = null == keypwd || "".equals(keypwd.trim()) ? null: keypwd.toCharArray();
			if (null != ks) {
				ks.load(fis, nPassword);
			}
			return ks;
		} catch (Exception e) {
			log.info("getKeyInfo Error", e);
			return null;
		} finally {
			if(null!=fis)
				fis.close();
		}
	}
	
	/**
	 * 通过签名私钥证书路径，密码获取私钥证书certId
	 * @param certPath
	 * @param certPwd
	 * @return
	 */
	public static String getCertIdByKeyStoreMap(String certPath, String certPwd) {
		if (!keyStoreMap.containsKey(certPath)) {
			// 缓存中未查询到,则加载RSA证书
			loadSignCert(certPath, certPwd);
		}
		return getCertIdIdByStore(keyStoreMap.get(certPath));
	}
	
	/**
	 * 通过keystore获取私钥证书的certId值
	 * @param keyStore
	 * @return
	 */
	private static String getCertIdIdByStore(KeyStore keyStore) {
		Enumeration<String> aliasenum = null;
		try {
			aliasenum = keyStore.aliases();
			String keyAlias = null;
			if (aliasenum.hasMoreElements()) {
				keyAlias = aliasenum.nextElement();
			}
			X509Certificate cert = (X509Certificate) keyStore
					.getCertificate(keyAlias);
			return cert.getSerialNumber().toString();
		} catch (KeyStoreException e) {
			log.info("getCertIdIdByStore Error", e);
			return null;
		}
	}
	/**
	 * 打印系统环境信息
	 */
	private static void printSysInfo() {
		log.info("================= SYS INFO begin====================");
		log.info("os_name:" + System.getProperty("os.name"));
		log.info("os_arch:" + System.getProperty("os.arch"));
		log.info("os_version:" + System.getProperty("os.version"));
		log.info("java_vm_specification_version:"
				+ System.getProperty("java.vm.specification.version"));
		log.info("java_vm_specification_vendor:"
				+ System.getProperty("java.vm.specification.vendor"));
		log.info("java_vm_specification_name:"
				+ System.getProperty("java.vm.specification.name"));
		log.info("java_vm_version:"
				+ System.getProperty("java.vm.version"));
		log.info("java_vm_name:" + System.getProperty("java.vm.name"));
		log.info("java.version:" + System.getProperty("java.version"));
		log.info("java.vm.vendor=[" + System.getProperty("java.vm.vendor") + "]");
		log.info("java.version=[" + System.getProperty("java.version") + "]");
		printProviders();
		log.info("================= SYS INFO end=====================");
	}

	/**
	 * 打jre中印算法提供者列表
	 */
	private static void printProviders() {
		log.info("Providers List:");
		Provider[] providers = Security.getProviders();
		for (int i = 0; i < providers.length; i++) {
			log.info(i + 1 + "." + providers[i].getName());
		}
	}
}
