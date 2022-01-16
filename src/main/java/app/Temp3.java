package app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import read.ReadPOJO;
import read.ReadService;
import read.ReadLocalFileServiceImpl;
import write.WriteObj;
import write.WriteService;
import write.WriteServiceImpl;

public class Temp3 {

	public static Gson gson = new GsonBuilder().create();
	
	public static void main(String[] args) throws IOException {
		String filename = args[0];
		ReadPOJO rJson;
		Map<String, WriteObj> out = new HashMap<>();
		ReadService rs = new ReadLocalFileServiceImpl();
		try {
			rs.getReader(filename);
			while(rs.hasNext()) {
				rJson = (ReadPOJO) rs.next(ReadPOJO.class);
				out.put(rJson.getPath(), new WriteObj(rJson.getUrl(), rJson.getSize()));
			}
			rs.closeReader();
		} catch (Exception e) {
			System.out.println("oops");
			e.printStackTrace();
			System.exit(1);
		}
		
		WriteService<String, WriteObj> ws = new WriteServiceImpl<>();
		filename = args[1];
			ws.getWriter(filename);
			ws.write(out);
			ws.closeWriter();
	}

}
