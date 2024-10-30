import java.io.File;

public class ProcessText {
	public static void main(String[] args) {
		for(int i = 0; i < args.length; i++) {
			File file = new File(args[i]);
			if(file.isFile() && file.canRead()) {
				TextStatistics text = new TextStatistics(file);
				System.out.println(text);
				System.out.println("");
			}else {
				System.out.println(args.clone()[i] + " file does not exist or cannot be read");
				System.out.println("");
			}
		}
		
	}
	
}
