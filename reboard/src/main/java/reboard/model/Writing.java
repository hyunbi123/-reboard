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
//글 한개에 대한 모델
public class Writing {
	int id;
	String title;
	String author;
	Date createdat;
	int viewcnt;
	int parentid;
	String attachment;
	int tab;	
}
