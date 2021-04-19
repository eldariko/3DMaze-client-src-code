package model;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import view.ClientProperties;

public interface Model {
	public void generateMaze( String name, String Generatoralgorithm,final int height,final int length,final int width);
	public void saveMazeInFile(String mazeName,String fileName)throws IOException;
	public void loadMazeFromFile(String fileName,String mazeName) throws IOException;
	public void solveMaze3D(String mazeName,String solverAlgorithm);
	public void getMaze3d(String mazeName);
	public void getSolution(String mazeName);
	public void getCrossSection(char axis,int index,String mazeName);
	public void getMazeSize(String mazeName);
	public void getFileSize(String mazeName);
	public void sendChoiceAndArgs(Object args);
	public void finalize() throws Throwable;
	public HashMap<String, Date> getSaveData();
	public void setSaveData(HashMap<String, Date> saveMazesAndLastSave);
	public void getData(Object params);
	ClientProperties getCp();
	void setCp(ClientProperties cp);
}
