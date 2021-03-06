package ca.bcit.io.project;

import ca.bcit.net.Network;
import ca.bcit.net.demand.generator.TrafficGenerator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class EONProject extends Project {
	private final Network network;
	private final List<TrafficGenerator> trafficGenerators;
	private final BufferedImage map;
	
	public EONProject(File projectFile, Network network, List<TrafficGenerator> trafficGenerators, BufferedImage map) {
		super(projectFile);
		this.network = network;
		this.trafficGenerators = trafficGenerators;
		this.map = map;
	}

	@Override
	public Network getNetwork() {
		return network;
	}

	@Override
	public List<TrafficGenerator> getTrafficGenerators() {
		return trafficGenerators;
	}

	public BufferedImage getMap() {
		return map;
	}
}
