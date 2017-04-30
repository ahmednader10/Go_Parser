
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import java_cup.runtime.Symbol;

public class AP {

	public static void main(String[] args) {

		String inFile = "MS3/Go5.in";
		String outFile = "Gotest.out";

		if (args.length > 1) {
			inFile = args[0];
		}

		try {
			FileReader fis = new FileReader(inFile);
			BufferedReader bis = new BufferedReader(fis);

//			Lexer lexer = new Lexer(bis);
//			
//			Symbol t;
//
//			while (((t = lexer.next_token()) != null)) {
//				if (t.sym != sym.EOF) {
//					System.out.println(sym.terminalNames[t.sym]);
//				} else {
//					System.out.println(sym.terminalNames[t.sym]);
//					break;
//				}
//			}

			
			BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
			@SuppressWarnings("deprecation")
			parser parser = new parser(new Lexer(bis));

			try {
				Symbol t = parser.parse();
				System.out.println("Done ");
				writer.write(t.value.toString());
			} catch (Exception e) {
				e.printStackTrace();
				writer.write("ParseError");
			}
			
			fis.close();
			bis.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
