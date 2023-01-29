package com.blogapi.payloads;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data

public class CommentDto {
	private Long comtId;
	private String content;
}
