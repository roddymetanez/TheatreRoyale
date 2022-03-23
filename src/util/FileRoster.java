package util;

import java.io.File;
import java.util.ArrayList;

public class FileRoster {
	private ArrayList<File> fileNames;

	/**
	 * empty constructor
	 */
	public FileRoster() {
	}

	/**
	 * Getter returning an array of strings as an ArrayList of path names Sort of
	 * using the ideas at
	 * https://stackoverflow.com/questions/1844688/how-to-read-all-files-in-a-folder-from-java
	 * 
	 * @return ArrayList of strings
	 */
	public ArrayList<File> getFilesForFolder(final File filePath) {
		fileNames = new ArrayList<File>();
		for (File fileEntry : filePath.listFiles()) {
			if (fileEntry.isDirectory()) {
				getFilesForFolder(fileEntry);
			}
			else {
				// make an array list
				fileNames.add(fileEntry);
				// System.out.println(fileEntry.getName());
			}
		}
		return fileNames;
	}
}
