package com.lvdou;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.EnumSet;
import java.util.Properties;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Identities;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
import org.hyperledger.fabric.sdk.Peer;
import org.junit.Test;

/**
 *  调用绿豆的“转豆”
 */
public class TesDecrease {
	@Test
	public  void Test01() {
		try {
			// 获取相应参数
			Properties properties = new Properties();
			InputStream inputStream = TesDecrease.class.getResourceAsStream("/fabric.config.properties");
			properties.load(inputStream);

			String networkConfigPath = properties.getProperty("networkConfigPath");
			String channelName = properties.getProperty("channelName");
			String contractName = properties.getProperty("contractName");
			String certificatePath = properties.getProperty("certificatePath");
			X509Certificate certificate = readX509Certificate(Paths.get(certificatePath));

			String privateKeyPath = properties.getProperty("privateKeyPath");
			PrivateKey privateKey = getPrivateKey(Paths.get(privateKeyPath));

			Wallet wallet = Wallets.newInMemoryWallet();
			wallet.put("user1", Identities.newX509Identity("Org1MSP", certificate, privateKey));

			Gateway.Builder builder = Gateway.createBuilder().identity(wallet, "user1")
					.networkConfig(Paths.get(networkConfigPath));
			Gateway gateway = builder.connect();
			Network network = gateway.getNetwork(channelName);
			Contract contract = network.getContract(contractName);
			contract.createTransaction("Decrease")
					.setEndorsingPeers(network.getChannel().getPeers(EnumSet.of(Peer.PeerRole.ENDORSING_PEER)))
					.submit("456","200");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static X509Certificate readX509Certificate(final Path certificatePath)
			throws IOException, CertificateException {
		try (Reader certificateReader = Files.newBufferedReader(certificatePath, StandardCharsets.UTF_8)) {
			return Identities.readX509Certificate(certificateReader);
		}
	}

	private static PrivateKey getPrivateKey(final Path privateKeyPath) throws IOException, InvalidKeyException {
		try (Reader privateKeyReader = Files.newBufferedReader(privateKeyPath, StandardCharsets.UTF_8)) {
			return Identities.readPrivateKey(privateKeyReader);
		}
	}
}
