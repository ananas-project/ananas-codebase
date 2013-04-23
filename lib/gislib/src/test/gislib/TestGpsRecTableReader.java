package test.gislib;

import java.io.IOException;
import java.io.InputStream;

import ananas.lib.gislib.GpsRecord;
import ananas.lib.gislib.GpsRecordTableReader;

public class TestGpsRecTableReader {

	public static void main(String[] arg) {
		TestGpsRecTableReader inst = new TestGpsRecTableReader();
		try {
			inst.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("the end");
	}

	private void run() throws IOException {
		InputStream in = this.getClass().getResourceAsStream("sample.txt");
		GpsRecordTableReader rdr = GpsRecordTableReader.Factory.newReader(in);
		for (; rdr.hasMore();) {
			GpsRecord rec = rdr.next();
			System.out.println(rec + "");
		}
	}
}
