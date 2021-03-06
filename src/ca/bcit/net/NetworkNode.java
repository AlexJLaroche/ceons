package ca.bcit.net;

import ca.bcit.graph.positioned.PositionedNode;
import ca.bcit.io.YamlSerializable;

import java.util.HashMap;
import java.util.Map;

import ca.bcit.drawing.Node;

/**
 * Network Node with information about the regenerators
 */
public class NetworkNode extends PositionedNode implements YamlSerializable {

	private final String name;
	private final String location;
	int regeneratorsCount, occupiedRegenerators;
	private Node figureNode;
	private HashMap<String, Boolean> nodeGroups = new HashMap<>();

	public NetworkNode(){
		this.name = "unknown";
		this.location = "unknown";
	}

	/**
	 * Class constructor specifying name of the NetworkNode
	 * @param name
	 */
	public NetworkNode(String name, String location) {
		this.name = name;
		this.location = location;
	}

	public void setFigure() {
		this.figureNode = new Node(getPosition(), getName(), getFreeRegenerators(), getNodeGroups());
	}

	public Node getFigure() {
		return this.figureNode;
	}

	public void updateRegeneratorCount() {
		this.figureNode.setNumberOfRegenerators(getFreeRegenerators());
	}

	/**
	 * Getter method for name variable
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter method for the node num
	 */
	public int getNodeNum(){
		return Integer.parseInt(name.split("_")[1]);
	}

	/**
	 * Getting method for the node location
	 */
	public String getLocation(){
		return location;
	}

	/**
	 * Getting method for the regenerators count initially set
	 */
	public int getRegeneratorsCount(){
		return regeneratorsCount;
	}

	/**
	 * Reset the occupiedRegenerators variable to 0
	 */
	public void clearOccupied(){
		this.occupiedRegenerators = 0;
	}

	/**
	 * Setter Method for regeneratorsCount variable
	 * @param regeneratorsCount
	 */
	public void setRegeneratorsCount(int regeneratorsCount) {
		this.regeneratorsCount = regeneratorsCount;
	}

	/**
	 * Setter Method for nodeGroups variable
	 * @param groupName
	 * @param value
	 */
	public void setNodeGroup(String groupName, Boolean value) {
		this.nodeGroups.put(groupName, value);
	}

	/**
	 * Getter method that return name of the group the node belongs to
	 * @return
	 */
	public HashMap<String, Boolean> getNodeGroups() {
		return this.nodeGroups;
	}

	/**
	 * Getter method that return the number of free regenerators
	 * @return
	 */
	public int getFreeRegenerators() {
		return regeneratorsCount - occupiedRegenerators;
	}

	/**
	 * Getter method for occupiedRegenerators variable
	 * @return
	 */
	public int getOccupiedRegenerators() {
		return occupiedRegenerators;
	}

	/**
	 * Check if there is any unoccupied regenerators left
	 * @return		<code>true</code> if there is at least one free regenerator
	 * 				<code>false</code> otherwise
	 */
	public boolean hasFreeRegenerators() {
		return regeneratorsCount - occupiedRegenerators > 0;
	}

	/**
	 * =======need comment========
	 * @param count
	 * @param deallocate
	 */
	public void occupyRegenerators(int count, boolean deallocate) {
		if (deallocate)
			occupiedRegenerators += count;
		else if (count > regeneratorsCount - occupiedRegenerators || occupiedRegenerators < 0)
			throw new NetworkException("Regenerators occupation exception! (" + occupiedRegenerators + "/" + regeneratorsCount + ")");
		else
			occupiedRegenerators += count;
	}

	/**
	 * Defines the equal operation of NetworkNode class
	 * @param o		the object that is being compared with
	 * @return		<code>true</code> if o is a NetworkNode and
	 * 				has the shares the same name.
	 * 				<code>false</code> otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		return o instanceof NetworkNode && ((NetworkNode) o).getName().equals(name);
	}

	/**
	 * Parse the NetworkNode object and return
	 * @return
	 */
	@Override
	public String toString() {
        return "{name: " + name + ", regenerators: " + regeneratorsCount + ", xcoordinate: " + getPosition().getX() + ", ycoordinate: " + getPosition().getY() + "}";
	}

	/**
	 * Class constructor specifying all variables from a Map object which is
	 * generated by deserialzing topology.yaml file
	 * @param map		<code>Map</code> Object which contains name, regeneratorsCount,
	 *                  x-coordinate, and y-coordinate
	 */
	private NetworkNode(Map map) {
		name = (String) map.get("name");
		location = (String) map.get("location");
		regeneratorsCount = (Integer) map.get("regenerators");
        setPosition((Integer) map.get("xcoordinate"),(Integer) map.get("ycoordinate"));
	}

	/**
	 * Method that serialize NetworkNode variables into map object for
	 * converting to yaml file later
	 *
	 * @return 		<code>Map</code> Object which contains name, regeneratorsCount,
	 *              x-coordinate, and y-coordinate
	 */
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("name", name);
		map.put("regenerators", regeneratorsCount);
		map.put("xcoordinate", getPosition().getX());
		map.put("ycoordinate", getPosition().getY());
		return map;
	}
}
