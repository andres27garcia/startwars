package co.com.segurosalfa.siniestros.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponsePageableDTO {

	private Object content;
	private int totalPages;
	private long pageNumber;
	private int pageSize;
	private long totalElements;
	private boolean first;
	private boolean last;
	private boolean empty;

	public ResponsePageableDTO(Object content, int totalPages, long pageNumber, int pageSize, long totalElements,
			boolean first, boolean last, boolean empty) {
		super();
		this.content = content;
		this.totalPages = totalPages;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalElements = totalElements;
		this.first = first;
		this.last = last;
		this.empty = empty;
	}

	
}
