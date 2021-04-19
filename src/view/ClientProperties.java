package view;


import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class ClientProperties.
 */
public class ClientProperties implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The port. */
	private String port;
	
	/** The ip. */
	private String ip;
	
	/** The generator algorithm. */
	private String generatorAlgorithm;
	
	/** The solution algorithm. */
	private String solutionAlgorithm;
	
	/** The heuristic. */
	private String heuristic;
	
	/**
	 * Instantiates a new client properties.
	 */
	public ClientProperties(){
		
	}
	
	/**
	 * Gets the port.
	 *
	 * @return the port
	 */
	public String getPort() {
		return port;
	}
	
	/**
	 * Sets the port.
	 *
	 * @param port the new port
	 */
	public void setPort(String port) {
		this.port = port;
	}
	
	/**
	 * Gets the ip.
	 *
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}
	
	/**
	 * Sets the ip.
	 *
	 * @param ip the new ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	/**
	 * Gets the generator algorithm.
	 *
	 * @return the generator algorithm
	 */
	public String getGeneratorAlgorithm() {
		return generatorAlgorithm;
	}
	
	/**
	 * Sets the generator algorithm.
	 *
	 * @param generatorAlgorithm the new generator algorithm
	 */
	public void setGeneratorAlgorithm(String generatorAlgorithm) {
		this.generatorAlgorithm = generatorAlgorithm;
	}
	
	/**
	 * Gets the solution algorithm.
	 *
	 * @return the solution algorithm
	 */
	public String getSolutionAlgorithm() {
		return solutionAlgorithm;
	}
	
	/**
	 * Sets the solution algorithm.
	 *
	 * @param solutionAlgorithm the new solution algorithm
	 */
	public void setSolutionAlgorithm(String solutionAlgorithm) {
		this.solutionAlgorithm = solutionAlgorithm;
	}
	
	/**
	 * Gets the heuristic.
	 *
	 * @return the heuristic
	 */
	public String getHeuristic() {
		return heuristic;
	}
	
	/**
	 * Sets the heuristic.
	 *
	 * @param heuristic the new heuristic
	 */
	public void setHeuristic(String heuristic) {
		this.heuristic = heuristic;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("generator: "+generatorAlgorithm+"\n");
		sb.append("solutionAlgo: "+solutionAlgorithm+"\n");
		sb.append("heuristic: "+heuristic+"\n");
		sb.append("ip: "+ip+"\n");
		sb.append("port: "+port+"\n");
		
		
		return sb.toString();
	}
		
			
			
	
	

 }
