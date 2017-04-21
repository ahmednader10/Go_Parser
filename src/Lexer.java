import java.lang.System;
import java.io.*;
import java_cup.runtime.Symbol;
import java.util.Stack;


class Lexer implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

	//initialize  variables to be used by class
	public Stack stack = new Stack();
	public int lineCount = 1;
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	Lexer (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	Lexer (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Lexer () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yychar = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

//Add code to be executed on initialization of the lexer)
stack.push("$");
	}

	private boolean yy_eof_done = false;
	private final int open = 1;
	private final int YYINITIAL = 0;
	private final int yy_state_dtrans[] = {
		0,
		70
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yychar = yychar
			+ yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NOT_ACCEPT,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NOT_ACCEPT,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NOT_ACCEPT,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NOT_ACCEPT,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NOT_ACCEPT,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"4:8,1:2,2,4:2,38,4:18,1,30,39,4:2,52,28,4,41,42,51,35,48,36,49,3,34,33:9,50" +
",47,31,29,32,4:2,53:26,45,40,46,4,53,37,8,5,16,10,7,11,19,22,21,53,9,13,25," +
"15,23,18,53,6,17,14,12,26,20,53,24,53,43,27,44,4:2,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,120,
"0,1:3,2,1,3,4,5,6:2,7,8,9,10,1:8,11,12,1:2,13,14,1:9,14:2,1,14:12,1:3,15:2," +
"16,17,1,18:2,19,20,1,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38," +
"39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63," +
"64,65,66,67,68,69,70,14,71,72,73")[0];

	private int yy_nxt[][] = unpackFromString(74,54,
"1,2,3,4,5,6,115,96,116:2,117,82,116:2,97,116,98,118,119,116:2,58,116:4,83,7" +
",8,9,10,11,59,12,60,13,14,57,3,63,5,15,16,17,18,19,20,21,22,23,24,25,26,116" +
",-1:57,27,-1:55,116,99,116:20,-1:6,116:2,-1:18,116,-1:27,29,-1:54,30,-1:54," +
"65,-1:53,65,-1,31,-1:4,32,-1:50,12:2,-1:54,34,-1:54,35,-1:66,68,-1:33,37,-1" +
":25,27,-1,27:35,-1,27:15,-1:5,116:22,-1:6,116:2,-1:18,116,-1,56:36,36,56:16" +
",-1:5,116:6,28,116:13,106,116,-1:6,116:2,-1:18,116,-1:29,65,-1:2,33,-1:22,6" +
"2,-1,62:36,36,66,62:13,-1,62,-1,62:36,-1,66,62:13,-1:5,116,38,116:20,-1:6,1" +
"16:2,-1:18,116,-1,62,-1,62:36,61,66,62:13,-1:5,116,39,116:20,-1:6,116:2,-1:" +
"18,116,-1:49,40,-1:9,116:2,41,116:19,-1:6,116:2,-1:18,116,1,2,3,4,5,6,115,9" +
"6,116:2,117,82,116:2,97,116,98,118,119,116:2,58,116:4,83,7,8,9,10,11,59,12," +
"60,13,14,57,3,63,5,15,53,17,54,19,55,21,22,23,24,25,26,116,-1:5,116:11,42,1" +
"16:10,-1:6,116:2,-1:18,116,-1:5,116:2,43,116:19,-1:6,116:2,-1:18,116,-1:5,1" +
"16:2,44,116:19,-1:6,116:2,-1:18,116,-1:5,116:4,45,116:17,-1:6,116:2,-1:18,1" +
"16,-1:5,116:9,46,116:12,-1:6,116:2,-1:18,116,-1:5,116:10,47,116:11,-1:6,116" +
":2,-1:18,116,-1:5,116:9,48,116:12,-1:6,116:2,-1:18,116,-1:5,116:17,49,116:4" +
",-1:6,116:2,-1:18,116,-1:5,116:9,50,116:12,-1:6,116:2,-1:18,116,-1:5,116:9," +
"51,116:12,-1:6,116:2,-1:18,116,-1:5,116:2,52,116:19,-1:6,116:2,-1:18,116,-1" +
":5,116:7,85,116:10,64,116:3,-1:6,116:2,-1:18,116,-1:5,116:3,67,116:18,-1:6," +
"116:2,-1:18,116,-1:5,116:12,69,116:9,-1:6,116:2,-1:18,116,-1:5,116:10,71,11" +
"6:11,-1:6,116:2,-1:18,116,-1:5,116:13,72,116:8,-1:6,116:2,-1:18,116,-1:5,11" +
"6:12,73,116:9,-1:6,116:2,-1:18,116,-1:5,116:3,74,116:18,-1:6,116:2,-1:18,11" +
"6,-1:5,116:12,75,116:9,-1:6,116:2,-1:18,116,-1:5,116,76,116:20,-1:6,116:2,-" +
"1:18,116,-1:5,116:11,77,116:10,-1:6,116:2,-1:18,116,-1:5,116:11,78,116:10,-" +
"1:6,116:2,-1:18,116,-1:5,116,79,116:20,-1:6,116:2,-1:18,116,-1:5,116:8,80,1" +
"16:13,-1:6,116:2,-1:18,116,-1:5,116:14,81,116:7,-1:6,116:2,-1:18,116,-1:5,1" +
"16:8,84,116:13,-1:6,116:2,-1:18,116,-1:5,116:19,86,116:2,-1:6,116:2,-1:18,1" +
"16,-1:5,116:3,87,116:14,102,116:3,-1:6,116:2,-1:18,116,-1:5,116:2,88,116:19" +
",-1:6,116:2,-1:18,116,-1:5,116:9,107,116:12,-1:6,116:2,-1:18,116,-1:5,116:6" +
",108,116:15,-1:6,116:2,-1:18,116,-1:5,116:10,89,116:11,-1:6,116:2,-1:18,116" +
",-1:5,116,109,116:20,-1:6,116:2,-1:18,116,-1:5,116:16,110,116:5,-1:6,116:2," +
"-1:18,116,-1:5,116:11,111,116:10,-1:6,116:2,-1:18,116,-1:5,116:13,112,116:8" +
",-1:6,116:2,-1:18,116,-1:5,116:7,90,116:14,-1:6,116:2,-1:18,116,-1:5,116:3," +
"113,116:18,-1:6,116:2,-1:18,116,-1:5,116:7,91,116:14,-1:6,116:2,-1:18,116,-" +
"1:5,116:9,92,116:12,-1:6,116:2,-1:18,116,-1:5,116:4,114,116:17,-1:6,116:2,-" +
"1:18,116,-1:5,116:18,93,116:3,-1:6,116:2,-1:18,116,-1:5,116:7,94,116:14,-1:" +
"6,116:2,-1:18,116,-1:5,116:3,95,116:18,-1:6,116:2,-1:18,116,-1:5,116:2,100," +
"116:19,-1:6,116:2,-1:18,116,-1:5,116:2,101,116:19,-1:6,116:2,-1:18,116,-1:5" +
",116:9,103,116:5,104,116:6,-1:6,116:2,-1:18,116,-1:5,116:3,105,116:18,-1:6," +
"116:2,-1:18,116");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

//Add code to be executed when the end of the file is reached
System.out.println(stack.isEmpty());
if(!stack.isEmpty()) {
	String topValue = (String)stack.pop();
	if(!topValue.equals("$")) {
		if (topValue.equals("(")){
			return (new Symbol(sym.EOF,"There is some ( that is not closed"));
		}
		if (topValue.equals("{")){
			return (new Symbol(sym.EOF,"There is some { that is not closed"));
		}
		if (topValue.equals("[")){
			return (new Symbol(sym.EOF,"There is some [ that is not closed"));
		}
	}
	return (new Symbol(sym.EOF,"Done"));
}
return (new Symbol(sym.EOF,"Done"));
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{}
					case -3:
						break;
					case 3:
						{lineCount++;}
					case -4:
						break;
					case 4:
						{return (new Symbol(sym.SLASH,yytext()));}
					case -5:
						break;
					case 5:
						{
  return (new Symbol(sym.ERROR, "Invalid input: " + yytext()+" in line "+lineCount));
}
					case -6:
						break;
					case 6:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -7:
						break;
					case 7:
						{return (new Symbol(sym.BAR,yytext()));}
					case -8:
						break;
					case 8:
						{return (new Symbol(sym.AMBERSAND,yytext()));}
					case -9:
						break;
					case 9:
						{return (new Symbol(sym.EQUAL,yytext()));}
					case -10:
						break;
					case 10:
						{return (new Symbol(sym.EXCLAMATION,yytext()));}
					case -11:
						break;
					case 11:
						{return (new Symbol(sym.REL_OP,yytext()));}
					case -12:
						break;
					case 12:
						{return (new Symbol(sym.INT_LIT,yytext()));}
					case -13:
						break;
					case 13:
						{return (new Symbol(sym.PLUS,yytext()));}
					case -14:
						break;
					case 14:
						{return (new Symbol(sym.MINUS,yytext()));}
					case -15:
						break;
					case 15:
						{
		stack.push("(");
		yybegin(open);
		return (new Symbol(sym.OPEN_PARAN,yytext()));
}
					case -16:
						break;
					case 16:
						{return (new Symbol(sym.ERROR,") has no matching ( in line "+lineCount));}
					case -17:
						break;
					case 17:
						{
		stack.push("{");
		yybegin(open);
		return (new Symbol(sym.OPEN_CURLY,yytext()));
}
					case -18:
						break;
					case 18:
						{return (new Symbol(sym.ERROR,"} has no matching { in line "+lineCount));}
					case -19:
						break;
					case 19:
						{
		stack.push("[");
		yybegin(open);
		return (new Symbol(sym.OPEN_SQUARE,yytext()));
}
					case -20:
						break;
					case 20:
						{return (new Symbol(sym.ERROR,"] has no matching [ in line "+lineCount));}
					case -21:
						break;
					case 21:
						{return (new Symbol(sym.SEMI_COLON,yytext()));}
					case -22:
						break;
					case 22:
						{return (new Symbol(sym.COMMA,yytext()));}
					case -23:
						break;
					case 23:
						{return (new Symbol(sym.DOT,yytext()));}
					case -24:
						break;
					case 24:
						{return (new Symbol(sym.COLON,yytext()));}
					case -25:
						break;
					case 25:
						{return (new Symbol(sym.ASTRISK,yytext()));}
					case -26:
						break;
					case 26:
						{return (new Symbol(sym.PERCENT,yytext()));}
					case -27:
						break;
					case 27:
						{}
					case -28:
						break;
					case 28:
						{return (new Symbol(sym.IF,yytext()));}
					case -29:
						break;
					case 29:
						{return (new Symbol(sym.OR_OP,yytext()));}
					case -30:
						break;
					case 30:
						{return (new Symbol(sym.AND_OP,yytext()));}
					case -31:
						break;
					case 31:
						{return (new Symbol(sym.SHIFT_LEFT,yytext()));}
					case -32:
						break;
					case 32:
						{return (new Symbol(sym.LESS_DASH,yytext()));}
					case -33:
						break;
					case 33:
						{return (new Symbol(sym.SHIFT_RIGHT,yytext()));}
					case -34:
						break;
					case 34:
						{return (new Symbol(sym.INCREMENT,yytext()));}
					case -35:
						break;
					case 35:
						{return (new Symbol(sym.DECREMENT,yytext()));}
					case -36:
						break;
					case 36:
						{return (new Symbol(sym.STRING_LIT,yytext()));}
					case -37:
						break;
					case 37:
						{return (new Symbol(sym.COLON_EQUAL,yytext()));}
					case -38:
						break;
					case 38:
						{return (new Symbol(sym.FOR,yytext()));}
					case -39:
						break;
					case 39:
						{return (new Symbol(sym.VAR,yytext()));}
					case -40:
						break;
					case 40:
						{return (new Symbol(sym.CDOTS,yytext()));}
					case -41:
						break;
					case 41:
						{return (new Symbol(sym.ELSE,yytext()));}
					case -42:
						break;
					case 42:
						{return (new Symbol(sym.FUNC,yytext()));}
					case -43:
						break;
					case 43:
						{return (new Symbol(sym.TYPE,yytext()));}
					case -44:
						break;
					case 44:
						{return (new Symbol(sym.CASE,yytext()));}
					case -45:
						break;
					case 45:
						{return (new Symbol(sym.BREAK,yytext()));}
					case -46:
						break;
					case 46:
						{return (new Symbol(sym.CONST,yytext()));}
					case -47:
						break;
					case 47:
						{return (new Symbol(sym.RETURN,yytext()));}
					case -48:
						break;
					case 48:
						{return (new Symbol(sym.STRUCT,yytext()));}
					case -49:
						break;
					case 49:
						{return (new Symbol(sym.SWITCH,yytext()));}
					case -50:
						break;
					case 50:
						{return (new Symbol(sym.IMPORT,yytext()));}
					case -51:
						break;
					case 51:
						{return (new Symbol(sym.DEFAULT,yytext()));}
					case -52:
						break;
					case 52:
						{return (new Symbol(sym.PACKAGE,yytext()));}
					case -53:
						break;
					case 53:
						{
		if(!stack.isEmpty()) {
			String topValue = (String)stack.pop();
			if (topValue.equals("(")){
				if (!stack.isEmpty() && ((String)stack.peek()).equals("$")) {
					yybegin(YYINITIAL);
				}
				return (new Symbol(sym.CLOSE_PARAN,yytext()));
			}
			else {
				if (!stack.isEmpty()) {
					String top = (String)stack.peek();
					if (top.equals("(")) {
						stack.push(topValue);
						return (new Symbol(sym.ERROR,"You have a missing bracket in line "+lineCount));
					}
					else {
						stack.push(topValue);
						return (new Symbol(sym.ERROR,") has no matching ( in line "+lineCount));
					}
				}
				else {
					return (new Symbol(sym.ERROR,") has no matching ( in line "+lineCount));
				}
			}
		}
		else {
			return (new Symbol(sym.ERROR,") has no matching ( in line "+lineCount));
		}
}
					case -54:
						break;
					case 54:
						{
		if(!stack.isEmpty()) {
			String topValue = (String)stack.pop();
			if (topValue.equals("{")){
				if (!stack.isEmpty() && ((String)stack.peek()).equals("$")) {
					yybegin(YYINITIAL);
				}
				return (new Symbol(sym.CLOSE_PARAN,yytext()));
			}
			else {
				if (!stack.isEmpty()) {
					String top = (String)stack.peek();
					if (top.equals("{")) {
						stack.push(topValue);
						return (new Symbol(sym.ERROR,"You have a missing bracket in line "+lineCount));
					}
					else {
						stack.push(topValue);
						return (new Symbol(sym.ERROR,") has no matching ( in line "+lineCount));
					}
				}
				else {
					return (new Symbol(sym.ERROR,") has no matching ( in line "+lineCount));
				}
			}
		}
		else {
			return (new Symbol(sym.ERROR,"} has no matching { in line "+lineCount));
		}
}
					case -55:
						break;
					case 55:
						{
		if(!stack.isEmpty()) {
			String topValue = (String)stack.pop();
			if (topValue.equals("[")){
				if (!stack.isEmpty() && ((String)stack.peek()).equals("$")) {
					yybegin(YYINITIAL);
				}
				return (new Symbol(sym.CLOSE_PARAN,yytext()));
			}
			else {
				if (!stack.isEmpty()) {
					String top = (String)stack.peek();
					if (top.equals("[")) {
						stack.push(topValue);
						return (new Symbol(sym.ERROR,"You have a missing bracket in line "+lineCount));
					}
					else {
						stack.push(topValue);
						return (new Symbol(sym.ERROR,"] has no matching [ in line "+lineCount));
					}
				}
				else {
					return (new Symbol(sym.ERROR,"] has no matching [ in line "+lineCount));
				}
			}
		}
		else {
			return (new Symbol(sym.ERROR,"} has no matching { in line "+lineCount));
		}
}
					case -56:
						break;
					case 57:
						{
  return (new Symbol(sym.ERROR, "Invalid input: " + yytext()+" in line "+lineCount));
}
					case -57:
						break;
					case 58:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -58:
						break;
					case 59:
						{return (new Symbol(sym.REL_OP,yytext()));}
					case -59:
						break;
					case 60:
						{return (new Symbol(sym.INT_LIT,yytext()));}
					case -60:
						break;
					case 61:
						{return (new Symbol(sym.STRING_LIT,yytext()));}
					case -61:
						break;
					case 63:
						{
  return (new Symbol(sym.ERROR, "Invalid input: " + yytext()+" in line "+lineCount));
}
					case -62:
						break;
					case 64:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -63:
						break;
					case 65:
						{return (new Symbol(sym.REL_OP,yytext()));}
					case -64:
						break;
					case 67:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -65:
						break;
					case 69:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -66:
						break;
					case 71:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -67:
						break;
					case 72:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -68:
						break;
					case 73:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -69:
						break;
					case 74:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -70:
						break;
					case 75:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -71:
						break;
					case 76:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -72:
						break;
					case 77:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -73:
						break;
					case 78:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -74:
						break;
					case 79:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -75:
						break;
					case 80:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -76:
						break;
					case 81:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -77:
						break;
					case 82:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -78:
						break;
					case 83:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -79:
						break;
					case 84:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -80:
						break;
					case 85:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -81:
						break;
					case 86:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -82:
						break;
					case 87:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -83:
						break;
					case 88:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -84:
						break;
					case 89:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -85:
						break;
					case 90:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -86:
						break;
					case 91:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -87:
						break;
					case 92:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -88:
						break;
					case 93:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -89:
						break;
					case 94:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -90:
						break;
					case 95:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -91:
						break;
					case 96:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -92:
						break;
					case 97:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -93:
						break;
					case 98:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -94:
						break;
					case 99:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -95:
						break;
					case 100:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -96:
						break;
					case 101:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -97:
						break;
					case 102:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -98:
						break;
					case 103:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -99:
						break;
					case 104:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -100:
						break;
					case 105:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -101:
						break;
					case 106:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -102:
						break;
					case 107:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -103:
						break;
					case 108:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -104:
						break;
					case 109:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -105:
						break;
					case 110:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -106:
						break;
					case 111:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -107:
						break;
					case 112:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -108:
						break;
					case 113:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -109:
						break;
					case 114:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -110:
						break;
					case 115:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -111:
						break;
					case 116:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -112:
						break;
					case 117:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -113:
						break;
					case 118:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -114:
						break;
					case 119:
						{return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -115:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
