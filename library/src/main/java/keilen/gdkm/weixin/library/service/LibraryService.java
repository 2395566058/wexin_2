package keilen.gdkm.weixin.library.service;

import keilen.gdkm.weixin.library.domain.Book;
import org.springframework.data.domain.Page;

public interface LibraryService {

	Page<Book> search(String keyword, int pageNumber);

}
