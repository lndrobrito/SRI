package main;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import helper.*;

public class FileProcess {

	public static void main(String[] args) throws IOException {

		try {
			Indice idx = new Indice();
				idx.indexFile(new File("C:\\Users\\leandro\\workspace\\recuperacao\\input\\ptwiki-v2.trec"));
			idx.search(Arrays.asList("Estados, Unidos ".split(",")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
