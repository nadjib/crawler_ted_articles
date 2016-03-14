package com.nadjib.crawler.ted;
import java.io.FileWriter;
import java.util.ArrayList;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

// https://github.com/jhy/jsoup
// http://jsoup.org/
// http://www.java2s.com/Code/Jar/j/Downloadjavajsonjar.htm

public class ListURLs {

	public static final String ROOT_URL = "https://www.ted.com";

	public static final int NB_PAGES = 5;
	// TODO Add all list of languages
	public static final String LANG = "AR";

	public static void main(String[] args) {
		try {
			System.out.println("Begin");

			ArrayList<String> urlsArray = new ArrayList<String>();

			for (int i = 1; i <= NB_PAGES; i++) {

				System.out.println("Download page N° " + i + " ...");

				Document doc = Jsoup.connect(ROOT_URL + "/talks?language=" + LANG + "&page=" + i).get();
				Elements links = doc.select("[href~=^(?i)/talks/.*=" + LANG + "]");

				for (int j = 1; j < links.size(); j++) {
					String link = links.get(j).attr("href");
					if (link != null) {
						link = link.replaceFirst("[\\?|&].*", "");

						if (!urlsArray.contains(link))
							urlsArray.add(link);
					}
				}
			}

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("root_url", ROOT_URL);

			jsonObject.put("child_urls", urlsArray);

			FileWriter file = new FileWriter("corpus/_urls.json");
			file.write(jsonObject.toString(4));

			file.close();

			System.out.println("Finish.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
