package com.linpeng.svnbot.hooks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Hook around post-commit
 * 
 * @author linpeng
 * @version 1.0
 * 
 */
public class PostCommintHook implements IHook {
	private static final Logger log = LogManager
			.getLogger(PostCommintHook.class);
	private SVNInfo svnInfo;

	public PostCommintHook(String svnServer, long revision) {
		svnInfo = new SVNInfo(svnServer, revision);
	}

	@Override
	public void invoke() {
		
		File file = new File("E:\\test.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileWriter fw = null;
		try {
			fw = new FileWriter(file);
			fw.write(svnInfo.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != fw) {
				try {
					fw.close();
				} catch (IOException e) {
					fw = null;
				}
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			if (args == null || args.length != 2) {
				throw new RuntimeException(
						"Args Length should be 2 (SVNServer, revision) and it is not!");
			}
			PostCommintHook hook = new PostCommintHook(args[1], Long
					.parseLong(args[0]));
			hook.invoke();
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
