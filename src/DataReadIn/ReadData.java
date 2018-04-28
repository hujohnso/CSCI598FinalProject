package DataReadIn;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class ReadData {
	private String pathToDataSetFolder;
	private String pathToSubFolder;
	
	
	
	public ArrayList<File> allFiles;
	File folder;
	ReadData(String pathToDataSetFolder, String subFolder){
		allFiles = new ArrayList<>();
		this.pathToSubFolder = subFolder;
		this.pathToDataSetFolder = pathToDataSetFolder;
		folder = new File(pathToDataSetFolder);
		fillAllFiles(fillPathsToData());
	}
	public ArrayList<String> fillPathsToData(){
		String [] storyWriters = getDirectories(folder);
		ArrayList<String> pathsToEmmoodStories = new ArrayList<>();
		for(String x: storyWriters){
			pathsToEmmoodStories.add(pathToDataSetFolder + x + "\\" + pathToSubFolder);
		}
		return pathsToEmmoodStories;
	}
	private String [] getDirectories(File folder){
		return  folder.list(new FilenameFilter(){
			@Override
			public boolean accept(File current, String name){
				return new File(current, name).isDirectory();
			}
		});
	}
	private void fillAllFiles(ArrayList<String> pathsToEmmoodStories){
		for(String x: pathsToEmmoodStories){
			getFiles(x);
		}
	}
	private void getFiles(String fileName){
		File folder = new File(fileName);
		File [] stories = folder.listFiles();
		for(File x: stories){
			allFiles.add(x);
		}
	}
}
