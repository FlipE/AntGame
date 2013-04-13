package parsers.brain;

import exceptions.LexicallyInvalidInputException;
%%

%{
  	/**
	 * Is the lexer at the end of the file?
	 * 
	 * @return true if at the end of file, false otherwise
	 */
	public boolean finished() {
		return this.zzAtEOF;
	}
%} 

%public
%class Lexer
%type Token
%line
%char
%state COMMENT
%full


ALPHA=[A-Za-z] 
DIGIT=[0-9]
NONNEWLINE_WHITE_SPACE_CHAR=[ \t\b\012]
NEWLINE=\r|\n|\r\n
COMMENT=;.*{NEWLINE}

%% 

<YYINITIAL> {
  
  "Sense"			| "sense"   		{ return new Token(yytext());}  
  "Mark"  			| "mark" 			{ return new Token(yytext());} 
  "Unmark" 			| "unmark"  		{ return new Token(yytext());} 
  "PickUp" 			| "pickup"  		{ return new Token(yytext());} 
  "Drop" 			| "drop"  			{ return new Token(yytext());} 
  "Turn" 			| "turn"  			{ return new Token(yytext());} 
  "Move" 			| "move"  			{ return new Token(yytext());} 
  "Flip"  			| "flip"  			{ return new Token(yytext());} 
  "Here" 			| "here"   			{ return new Token(yytext());}  
  "Ahead" 			| "ahead"   		{ return new Token(yytext());} 
  "LeftAhead" 		| "leftahead"  		{ return new Token(yytext());} 
  "RightAhead" 		| "rightahead"  	{ return new Token(yytext());} 
  "Friend" 			| "friend"  		{ return new Token(yytext());} 
  "Foe"  			| "foe" 			{ return new Token(yytext());} 
  "FriendWithFood" 	| "friendwithfood"	{ return new Token(yytext());} 
  "FoeWithFood" 	| "foewithfood"  	{ return new Token(yytext());} 
  "Food" 			| "food"  			{ return new Token(yytext());} 
  "Rock" 			| "rock"  			{ return new Token(yytext());} 
  "Marker" 			| "marker"  		{ return new Token(yytext());} 
  "FoeMarker" 		| "foemarker"   	{ return new Token(yytext());} 
  "Home" 			| "home"  			{ return new Token(yytext());} 
  "FoeHome" 		| "foehome"  		{ return new Token(yytext());} 
  "Left" 			| "left"   			{ return new Token(yytext());} 
  "Right" 			| "right"  			{ return new Token(yytext());}
  {DIGIT}+ 								{ return new Token(yytext());} 
  {COMMENT}								{ return new Token("comment");} 
  {NONNEWLINE_WHITE_SPACE_CHAR}+ 		{ /* ignore */ }
  {NEWLINE}+							{ /* ignore */ }

}

/* in case of error 
. {
	
	throw new LexicallyInvalidInputException("Lexically invalid input.");
}*/
