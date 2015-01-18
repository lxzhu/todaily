package net.lxzhu.todaily.dao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * read sql scripts from text file and use dedicated GO as delimiter
 * 
 * @author RFIAS
 *
 */
public class SqlScriptReader {
	protected BufferedReader stream;
	protected StringBuffer buffer;

	public SqlScriptReader(InputStream stream) {
		this.stream = new BufferedReader(new InputStreamReader(stream));
	}

	public boolean next() {
		this.buffer = new StringBuffer();
		try {
			String line = this.stream.readLine();
			while (null != line) {
				if (line.equalsIgnoreCase("GO")) {
					break;
				} else {
					int index = line.lastIndexOf(";");
					if (index >= 0) {
						String ending = line.substring(index).trim();
						if (ending.equalsIgnoreCase(";GO")) {
							this.buffer.append(line.substring(0, index));
							break;
						} else {
							this.buffer.append(line);
						}
					} else {
						this.buffer.append(line);
					}
				}
				line = this.stream.readLine();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return this.buffer.length() > 0;
	}

	public String current() {
		return this.buffer.toString();
	}

}
