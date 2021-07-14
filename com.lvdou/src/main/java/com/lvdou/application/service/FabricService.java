package com.lvdou.application.service;

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
import java.util.concurrent.TimeoutException;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Identities;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
import org.hyperledger.fabric.sdk.Peer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FabricService {
	Logger log = LoggerFactory.getLogger(FabricService.class);

	private static FabricService instance = new FabricService();
	private Contract contract;
	private Network network;

	public static FabricService getInstance() {
		return instance;
	}

	private FabricService() {
		try {
			// 获取相应参数
			Properties properties = new Properties();
			InputStream inputStream = FabricService.class.getResourceAsStream("/fabric.config.properties");
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
			network = gateway.getNetwork(channelName);
			contract = network.getContract(contractName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 调用查询
	public int QueryBal(String phone) throws ContractException {
		byte[] bb = contract.evaluateTransaction("QueryBal", phone);
		log.info("QueryBal:" + new String(bb));
		return new Integer(new String(bb)).intValue();
	}

	// 调用转豆
	public void Transer(String phone, String phone2, int amt)
			throws ContractException, TimeoutException, InterruptedException {
		log.info("Transer");
		contract.createTransaction("Transer")
				.setEndorsingPeers(network.getChannel().getPeers(EnumSet.of(Peer.PeerRole.ENDORSING_PEER)))
				.submit(phone, phone2, new Integer(amt).toString());
	}

	// 初始化
	public void InitLedger() throws ContractException, TimeoutException, InterruptedException {
		log.info("InitLedger");
		contract.createTransaction("InitLedger")
				.setEndorsingPeers(network.getChannel().getPeers(EnumSet.of(Peer.PeerRole.ENDORSING_PEER))).submit();
	}

	// 增加(衣食住行等行为积累的豆)
	public void Increase(String phone, int amt) throws ContractException, TimeoutException, InterruptedException {
		log.info("Increase");
		contract.createTransaction("Increase")
				.setEndorsingPeers(network.getChannel().getPeers(EnumSet.of(Peer.PeerRole.ENDORSING_PEER)))
				.submit(phone, new Integer(amt).toString());
	}

	// 减少（消费时可以抵扣绿豆）
	public void Decrease(String phone, int amt) throws ContractException, TimeoutException, InterruptedException {
		log.info("Decrease");
		contract.createTransaction("Decrease")
				.setEndorsingPeers(network.getChannel().getPeers(EnumSet.of(Peer.PeerRole.ENDORSING_PEER)))
				.submit(phone, new Integer(amt).toString());
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
