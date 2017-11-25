package org.ju.cse.gobinda;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.swing.filechooser.FileSystemView;

public class ComputerManager {

	public static String detectUsb() {
		FileSystemView fsv = FileSystemView.getFileSystemView();
		File[] f = File.listRoots();

		for (int i = 0; i < f.length; i++) {
			String type = fsv.getSystemTypeDescription(f[i]);
			if (type.toLowerCase().contains("removable")) {
				return f[i].getPath().substring(0, 2);
			}
		}
		return "";
	}

	public static void copyFileUsingFileStreams(String sourchFilePath, String destinationFilePath) {

		
		System.out.println(sourchFilePath);
		System.out.println(destinationFilePath);
		
		File source = new File(sourchFilePath);
		File dest = new File(destinationFilePath);
		
		if (source.isDirectory()) {
			dest.mkdirs();
			return;
		}

		String extra = dest.getParent();
		new File(extra).mkdirs();

		InputStream input = null;
		OutputStream output = null;
		try {
			input = new FileInputStream(source);
			output = new FileOutputStream(dest);
			byte[] buf = new byte[8192];
			int bytesRead;
			while ((bytesRead = input.read(buf)) > 0) {
				output.write(buf, 0, bytesRead);
			}
			input.close();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getPresentComputerUserName() {
		return System.getProperty("user.name");
	}

	public static String getPresentComputerTimeAsString() {
		return new Date().toString().replaceAll(":", "_").replaceAll(" ", "_");
	}

}
