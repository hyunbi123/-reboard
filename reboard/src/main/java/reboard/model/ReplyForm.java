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
public class ReplyForm {
int parentid; //부모정보에서 tab, id획득
String title;
String content;
//String attachment; //파일업로드 일경우 해당 필드는 제외
}
