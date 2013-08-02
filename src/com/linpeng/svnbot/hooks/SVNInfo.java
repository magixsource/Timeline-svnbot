package com.linpeng.svnbot.hooks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * SVNInfo bean
 * @author linpeng
 * @version 1.0
 * 
 */
public class SVNInfo {

	private static final Logger logger = LogManager.getLogger(SVNInfo.class);

	private String svnServer;
	private long revision;

	private BufferedReader reader;

	public SVNInfo(String svnServer, long revision) {
		this.svnServer = svnServer;
		this.revision = revision;
	}

	public String toString() {
		Commit commitEntry = null;
		try {
			reader = new BufferedReader(new FileReader(getLogFile()));
			String readBuffer = "";
			int lineNumber = 1;
			String log = "";
			String author = "";
			String time = "";
			String diff = "";

			while ((readBuffer = reader.readLine()) != null) {
				if (lineNumber == 1) {
					log = readBuffer;
				} else if (lineNumber == 2) {
					author = readBuffer;
				} else if (lineNumber == 3) {
					time = readBuffer;
				} else {
					diff += readBuffer + "\r\n";
				}
				lineNumber++;
			}
			commitEntry = new Commit(log, author, time, diff, revision);
			reader.close();
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return null == commitEntry ? "" : commitEntry.toString();
	}

	private File getLogFile() {
		return new File(getLogFilePath());
	}

	private String getLogFilePath() {
		return svnServer;
	}

	class Commit {
		private String log;
		private String author;
		private String time;
		private String diff;
		private long revision;

		public Commit(String log, String author, String time, String diff,
				long revision) {
			this.log = filter(log);
			this.author = filter(author);
			this.time = filter(time);
			this.diff = filter(diff);
			this.revision = revision;
		}

		public String getLog() {
			return log;
		}

		public void setLog(String log) {
			this.log = filter(log);
		}

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = filter(author);
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = filter(time);
		}

		public String getDiff() {
			return diff;
		}

		public void setDiff(String diff) {
			this.diff = filter(diff);
		}

		public long getRevision() {
			return revision;
		}

		public void setRevision(long revision) {
			this.revision = revision;
		}

		// ---------------Utils--------------/
		/**
		 * 
		 * @param str
		 * @param srcCharset
		 * @param distCharset
		 * @return
		 * 
		 */
		private String undecode(String str, String srcCharset,
				String distCharset) {
			try {
				return new String(str.getBytes(srcCharset), distCharset);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return "";
		}

		private String filter(String str) {
			return undecode(str, "gbk", "utf8");
		}

		@Override
		public String toString() {
			return "SVNCommit{" + "log='" + log + '\'' + ", author='" + author
					+ '\'' + ", time='" + time + '\'' + ", diff='" + diff
					+ '\'' + ", revision='" + revision + '\'' + '}';
		}

		public String toJson() {
			return "";
		}

	}

}
