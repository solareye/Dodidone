package piechart.lib;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UIRessources {
	
	private List<String> hexs;
	private Random randomGenerator;
	
	public UIRessources(){
		hexs = new ArrayList<String>();
		randomGenerator = new Random();
		initHexs();
	}
	
	public String getRandomColor(){
		int i = randomGenerator.nextInt(hexs.size());
		String color = hexs.get(i);
		hexs.remove(i);
		return color;
	}
	
	private void initHexs() {
		hexs.add("#34AADC");
		hexs.add("#4CD964");
		hexs.add("#5856D6");
		hexs.add("#FF2D55");
		hexs.add("#C86EDF");
		hexs.add("#5BCAFF");
		hexs.add("#FF3A2D");
		hexs.add("#FFD3E0");
		hexs.add("#D1EEFC");
		hexs.add("#FF9500");
		hexs.add("#5856D6");
		
		hexs.add("#16a085");
		hexs.add("#2ecc71");
		hexs.add("#27ae60");
		hexs.add("#2980b9");
		hexs.add("#e74c3c");
		hexs.add("#9b59b6");
		hexs.add("#8e44ad");
		hexs.add("#34495e");
		hexs.add("#2c3e50");
		hexs.add("#95a5a6");
		hexs.add("#7f8c8d");
		
		hexs.add("#a7d78d");
		hexs.add("#bbbbbb");
		hexs.add("#35bcaf");
		hexs.add("#8890b3");
		hexs.add("#b39a78");
		hexs.add("#ef7e67");
		hexs.add("#85a5e0");
		hexs.add("#b36e7c");
	}

}
