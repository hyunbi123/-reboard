package reboard.model;



import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Board {
	int id;
	public String title;
	String author;
	Date createdat;
	String content;
	String attachment;
	int viewcnt;
	String type;
	int isdel;
	Date updatedat;
	int parentid;
	int tab;
}





